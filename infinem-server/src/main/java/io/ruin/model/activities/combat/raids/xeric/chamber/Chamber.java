package io.ruin.model.activities.combat.raids.xeric.chamber;

import io.ruin.cache.def.ObjectDefinition;
import io.ruin.model.activities.combat.raids.xeric.ChambersOfXeric;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.*;
import io.ruin.model.map.dynamic.DynamicChunk;
import io.ruin.model.map.object.GameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Edu
 */
public abstract class Chamber {

    public static final int CHUNK_SIZE = 4;
    public static final int TILE_SIZE = CHUNK_SIZE * 8;

    private final DynamicChunk[][] chunks = new DynamicChunk[getChunkSize()][getChunkSize()];

    private int basePointX, basePointY, pointZ;

    @Setter
    @Getter
    private int layout;

    @Setter
    @Getter
    private int rotation = 0;

    @Getter
    private Position basePosition;

    @Setter
    @Getter
    private ChamberDefinition definition;
    @Setter
    @Getter
    private ChambersOfXeric raid;
    @Getter
    private Bounds chamberBounds;

    public Chamber() {

    }

    public Chamber(int basePointX, int basePointY, int pointZ, int layout, int rotation) {
        this();
        setRotation(rotation);
        setLayout(layout);
        setLocation(basePointX, basePointY, pointZ);
    }

    public void setLocation(int basePointX, int basePointY, int pointZ) {
        if (basePointX < 0 || basePointY < 0)
            throw new IllegalArgumentException();
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.pointZ = pointZ;
        Chunk baseChunk = getDefinition().getBaseChunk(layout);
        for (int x = 0; x < getChunkSize(); x++) {
            for (int y = 0; y < getChunkSize(); y++) {
                chunks[x][y] = new DynamicChunk(baseChunk.getX() + x, baseChunk.getY() + y, baseChunk.getZ());
            }
        }
        rotate(rotation);
    }

    public abstract void onRaidStart();

    public void onBuild() {

    }

    private void rotate() {
        for (int x = 0; x < getChunkSize(); x++) {
            for (int y = 0; y < getChunkSize(); y++) {
                DynamicChunk chunk = chunks[x][y];
                chunk.pos(basePointX + rotatedX(getChunkSize(), x, y, 1, 1, 0),
                        basePointY + rotatedY(getChunkSize(), x, y, 1, 1, 0),
                        pointZ);
                chunk.rotate(rotation);
            }
        }
    }

    private void rotate(int rotation) {
        setRotation(rotation);
        rotate();
    }

    /**
     * Converts the given local chamber coords to absolute coordinates, rotating if necessary
     * @param localX LOCAL TO THE CHAMBER, so 0-31
     * @param localY LOCAL TO THE CHAMBER, so 0-31
     * @return absolute position, depending on the chamber's location and rotation.
     */
    public Position getPosition(int localX, int localY) {
        if (basePosition == null)
            throw new IllegalStateException("Base position not set");
        return basePosition.relative(rotatedX(localX, localY),  rotatedY(localX, localY));
    }

    public Position getPosition(int[] localCoords) {
        return getPosition(localCoords[0], localCoords[1]);
    }

    public List<DynamicChunk> getChunks() {
        ArrayList<DynamicChunk> list = new ArrayList<>(getChunkSize() * getChunkSize());
        for (DynamicChunk[] chunk : chunks) {
            list.addAll(Arrays.asList(chunk));
        }
        return list;
    }

    public NPC spawnNPC(int id, int localX, int localY, Direction face, int walkRange) {
        NPC npc = new NPC(id);
        npc.putTemporaryAttribute("RAID_CHAMBER", this);
        Position spawnPosition = basePosition.relative(rotatedX(getTileSize(), localX, localY, npc.getSize(), npc.getSize(), 0), // direction is irrelevant here since npcs are always square
                rotatedY(getTileSize(), localX, localY, npc.getSize(), npc.getSize(), 0));
        npc.spawn(spawnPosition.getX(), spawnPosition.getY(), spawnPosition.getZ(), rotatedDir(face), walkRange);
        if (raid != null) {
            if (raid.getMap() != null)
                raid.getMap().addNpc(npc);
            raid.scaleNPC(npc);
        }
        npc.attackBounds = getChamberBounds();
        return npc;
    }

    public NPC spawnNPC(int id, int[] localCoords, Direction face, int walkRange) {
        return spawnNPC(id, localCoords[0], localCoords[1], face, walkRange);
    }

    public GameObject spawnObject(int id, int localX, int localY, int type, int direction) {
        ObjectDefinition def = ObjectDefinition.get(id);
        if (def == null)
            throw new IllegalArgumentException("Invalid object " + id);
        Position pos = basePosition.relative(rotatedX(getTileSize(), localX, localY, def.xLength, def.yLength, direction),
                rotatedY(getTileSize(), localX, localY, def.xLength, def.yLength, direction));
        direction = (direction + getRotation()) % 4; // rotate
        return GameObject.spawn(id, pos.getX(), pos.getY(), pos.getZ(), type, direction);
    }

    public GameObject spawnObject(int id, int[] localCoords, int type, int direction) {
        return spawnObject(id, localCoords[0], localCoords[1], type, direction);
    }

    public GameObject getObject(int id, int localX, int localY, int direction) {
        ObjectDefinition def = ObjectDefinition.get(id);
        if (def == null)
            throw new IllegalArgumentException("Invalid object " + id);
        Position pos = basePosition.relative(rotatedX(getTileSize(), localX, localY, def.xLength, def.yLength, direction),
                rotatedY(getTileSize(), localX, localY, def.xLength, def.yLength, direction));
        return Tile.getObject(id, pos.getX(), pos.getY(), pos.getZ());
    }

    public void setBasePosition(Position basePosition) {
        this.basePosition = basePosition;
        this.chamberBounds = new Bounds(basePosition.getX(), basePosition.getY(), basePosition.getX() + getTileSize(), basePosition.getY() + getTileSize(), basePosition.getZ());
    }

    public int rotatedX(int areaSize, int localX, int localY, int xLength, int yLength, int direction) {
        if((direction & 0x1) == 1) {
            int oldXLength = xLength;
            xLength = yLength;
            yLength = oldXLength;
        }
        if(rotation == 0)
            return localX;
        if(rotation == 1)
            return localY;
        if(rotation == 2)
            return areaSize - 1 - localX - (xLength - 1);
        return areaSize - 1 - localY - (yLength - 1);
    }

    public int rotatedY(int areaSize, int localX, int localY, int xLength, int yLength, int direction) {
        if((direction & 0x1) == 1) {
            int oldXLength = xLength;
            xLength = yLength;
            yLength = oldXLength;
        }
        if(rotation == 0)
            return localY;
        if(rotation == 1)
            return areaSize - 1 - localX - (xLength - 1);
        if(rotation == 2)
            return areaSize - 1 - localY - (yLength - 1);
        return localX;
    }

    public int rotatedX(int localX, int localY) {
        return rotatedX(getTileSize(), localX, localY, 1, 1, 0);
    }

    public int rotatedY(int localX, int localY) {
        return rotatedY(getTileSize(), localX, localY, 1, 1, 0);
    }


    private static final Direction[] directions = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};

    public Direction rotatedDir(Direction dir, int rotation) {
        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == dir) {
                return directions[(i + rotation) % directions.length];
            }
        }
        return dir;
    }

    public Direction rotatedDir(Direction dir) {
        return rotatedDir(dir, rotation);
    }

    protected int getTileSize() {
        return TILE_SIZE;
    }

    protected int getChunkSize() {
        return CHUNK_SIZE;
    }
}

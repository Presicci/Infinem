package io.ruin.model.map.object;

import io.ruin.Server;
import io.ruin.api.utils.TemporaryAttributesHolder;
import io.ruin.cache.def.ObjectDefinition;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.impl.CrypticClue;
import io.ruin.model.activities.cluescrolls.impl.MapClue;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.owned.OwnedObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GameObject extends TemporaryAttributesHolder {

    public int id, originalId;
    public final int originalDirection;
    public final int x, y, z;
    public final int type, direction;
    public BiFunction<Player, GameObject, Position> nearPosition;

    private boolean skipClipping, spawned;
    public Tile tile;

    public Position walkTo;
    public Predicate<Position> skipReachCheck;

    public ObjectAction[] actions;
    public HashMap<Integer, ItemObjectAction> itemActions;
    public ItemObjectAction defaultItemAction;

    public CrypticClue crypticClue;
    public MapClue mapClue;

    public int doorCloses;
    public long doorJamEnd;
    public GameObject doorReplaced;
    public int conOwner = -1;

    private long lastAnimationTick;

    public Map<String, Object> attributes = new HashMap<>();

    public GameObject(int id, int x, int y, int z, int type, int direction) {
        this.id = id;
        this.originalId = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        if (direction >= 4)
            direction -= 4;
        if (direction < 0)
            direction += 4;
        this.direction = direction;
        this.originalDirection = direction;
    }

    public GameObject(int id, Position pos, int type, int direction) {
        this(id, pos.getX(), pos.getY(), pos.getZ(), type, direction);
    }

    public Position getPosition(){
        return Position.of(x, y, z);
    }

    public GameObject skipClipping(boolean skipClipping) {
        this.skipClipping = skipClipping;
        return this;
    }

    public GameObject spawn() {
        spawned = true;
        Tile.get(x, y, z, true).addObject(this);
        for(Player player : tile.region.players)
            send(player);
        return this;
    }

    public GameObject remove() {
        setId(-1);
        return this;
    }

    public GameObject restore() {
        setId(originalId);
        return this;
    }

    public void removeFor(int ticks) {
        ArrayList<Player> players = tile.region.players;
        World.startEvent(event -> {
            clip(true);
            id = -1;
            for(Player player : players)
                send(player);
            event.delay(ticks);
            restore();
        });
    }

    public void setId(int newId) {
        ArrayList<Player> players = tile.region.players;
        if(spawned && newId == -1) {
            tile.removeObject(this);
            for(Player player : players)
                sendRemove(player);
        } else {
            clip(true);
            id = newId;
            tile.checkActive();
            clip(false);
            for(Player player : players)
                send(player);
        }
    }

    public void clip(boolean remove) {
        if(id == -1 || skipClipping)
            return;
        ObjectDefinition def = getDef();
        if(def == null)
            return;
        if(type == 22) {
            if(def.isClippedDecoration()) {
                if(def.clipType == 1) {
                    if(remove)
                        tile.unflagDecoration();
                    else
                        tile.flagDecoration();
                }
            }
        } else if(type >= 9) {
            int xLength, yLength;
            if(direction == 1 || direction == 3) {
                xLength = def.yLength;
                yLength = def.xLength;
            } else {
                xLength = def.xLength;
                yLength = def.yLength;
            }
            if(def.clipType != 0) {
                if(remove) {
                    ClipUtils.removeClipping(x, y, z, xLength, yLength, def.tall, false);
                    if(def.tall)
                        ClipUtils.removeClipping(x, y, z, xLength, yLength, true, true);
                } else {
                    ClipUtils.addClipping(x, y, z, xLength, yLength, def.tall, false);
                    if(def.tall)
                        ClipUtils.addClipping(x, y, z, xLength, yLength, true, true);
                }
            }
        } else if(type >= 0 && type <= 3) {
            if(def.clipType != 0) {
                if(remove) {
                    ClipUtils.removeVariableClipping(x, y, z, type, direction, def.tall, false);
                    if(def.tall)
                        ClipUtils.removeVariableClipping(x, y, z, type, direction, true, true);
                } else {
                    ClipUtils.addVariableClipping(x, y, z, type, direction, def.tall, false);
                    if(def.tall)
                        ClipUtils.addVariableClipping(x, y, z, type, direction, true, true);
                }
            }
        }
    }

    public void send(Player player) {
        if(id != -1)
            sendCreate(player);
        else
            sendRemove(player);
    }

    public void sendCreate(Player player) {
        player.getPacketSender().sendCreateObject(id, x, y, z, type, direction);
    }

    public void sendRemove(Player player) {
        player.getPacketSender().sendRemoveObject(x, y, z, type, direction);
    }

    public void animate(int id) {
        long currentTick = Server.currentTick();
        if(lastAnimationTick != currentTick) {
            lastAnimationTick = currentTick;
            for(Player player : tile.region.players)
                player.getPacketSender().sendObjectAnimation(x, y, z, type, direction, id);
        }
    }

    public void graphics(int id) {
        graphics(id, 0, 0);
    }

    public void graphics(int id, int height, int delay) {
        for(Player player : tile.region.players)
            player.getPacketSender().sendGraphics(id, height, delay, x, y, z);
    }

    public boolean isSpawned() {
        //Note: This basically means the object isn't static on the map.
        return spawned;
    }

    public boolean isRemoved() {
        return id == -1 || tile == null;
    }

    public boolean isCustom() {
        return spawned || id != originalId || conOwner != -1;
    }

    public ObjectDefinition getDef() {
        return ObjectDefinition.get(this.id);
    }

    public ObjectDefinition getConfigDef(Player player) {
        return ObjectDefinition.getConfigDef(id, player);
    }

    @Override
    public String toString() {
        return "GameObject[name="+ getDef().name + ", id=" + id + ", x=" + x + ", y=" + y + ", z=" + z +"]";
    }

    /**
     * Utils
     */

    public static void forObj(int id, int x, int y, int z, Consumer<GameObject> consumer) {
        //At first I didn't want to have this method,
        //but it's convenient so we don't have to manually check if null.
        GameObject obj = Tile.getObject(id, x, y, z);
        if(obj != null)
            consumer.accept(obj);
    }

    public static GameObject spawn(int id, int x, int y, int z, int type, int direction) {
        return new GameObject(id, x, y, z, type, direction).spawn();
    }

    public static GameObject spawn(int id, Position pos, int type, int direction) {
        return new GameObject(id, pos.getX(), pos.getY(), pos.getZ(), type, direction).spawn();
    }

    public static GameObject spawnUnclipped(int id, int x, int y, int z, int type, int direction) {
        return new GameObject(id, x, y, z, type, direction).skipClipping(true).spawn();
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public int component1() {
        return id;
    }

    public int component2() {
        return x;
    }

    public int component3() {
        return y;
    }

    public boolean isOwnedObject() {
        return this instanceof OwnedObject;
    }

    public OwnedObject asOwnedObject() {
        return ((OwnedObject) this);
    }

    public void examine(Player player) {
        ObjectDefinition def = getDef();
        if (def == null || def.examine == null)
            return;
        player.sendMessage(def.examine);
    }

    public void publicSound(int id) {
        publicSound(id, 1, 0);
    }

    public void publicSound(int id, int type, int delay) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int distance = 15; //idk how to calc
        for (Player p : tile.region.players)
            p.getPacketSender().sendAreaSound(id, type, delay, x, y, distance);
    }

    public Direction getFaceDirection() {
        return direction == 1 ? Direction.NORTH : direction == 0 ?
                Direction.WEST : direction == 2 ?
                Direction.EAST :
                Direction.SOUTH;
    }
}
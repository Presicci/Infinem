package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/22/2023
 */
public class OutOfRangeNPC {

    private static final int[] TWO_TILE_NPCS = {
            4022,   // Mike (mos le'harmless)
            4020,   // Mama (mos le'harmless)
            4019,   // Joe (mos le'harmless)
            4017,   // Charley (mos le`harmless)
    };

    private static final int[] ONE_TILE_NPCS = {
            3101,   // Sawmill operator
    };

    private static void addTwoTileWalkException(NPC npc) {
        int deltaX = npc.spawnDirection.deltaX;
        int deltaY = npc.spawnDirection.deltaY;
        int x = npc.getAbsX() + deltaX;
        int y = npc.getAbsY() + deltaY;
        int z = npc.getHeight();
        npc.walkTo = new Position(x + deltaX, y + deltaY, z);
    }

    private static void addOneTileWalkException(NPC npc) {
        int deltaX = npc.spawnDirection.deltaX;
        int deltaY = npc.spawnDirection.deltaY;
        int x = npc.getAbsX() + deltaX;
        int y = npc.getAbsY() + deltaY;
        int z = npc.getHeight();
        npc.walkTo = new Position(x, y, z);
    }

    static {
        for (int id : TWO_TILE_NPCS) {
            SpawnListener.register(id, OutOfRangeNPC::addTwoTileWalkException);
        }
        for (int id : ONE_TILE_NPCS) {
            SpawnListener.register(id, OutOfRangeNPC::addOneTileWalkException);
        }
    }
}

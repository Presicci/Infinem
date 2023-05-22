package io.ruin.model.entity.npc.actions;

import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.item.containers.bank.BankActions;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/22/2023
 */
public class OutOfRangeNPC {

    private static final int[] NPCS = {
            4022,   // Mike (mos le'harmless)
            4020,   // Mama (mos le'harmless)
            4019,   // Joe (mos le'harmless)
            4017,   // Charley (mos le`harmless)
    };

    private static void addWalkException(NPC npc) {
        int deltaX = npc.spawnDirection.deltaX;
        int deltaY = npc.spawnDirection.deltaY;
        int x = npc.getAbsX() + deltaX;
        int y = npc.getAbsY() + deltaY;
        int z = npc.getHeight();
        npc.walkTo = new Position(x + deltaX, y + deltaY, z);
    }

    static {
        for (int id : NPCS) {
            SpawnListener.register(id, OutOfRangeNPC::addWalkException);
        }
    }
}

package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/15/2024
 */
public class BabaYagaHouse {

    static {
        NPCAction.register(3836, "go-inside", (player, npc) -> player.getMovement().teleport(2451, 4645, 0));
        ObjectAction.register(16774, 2451, 4644, 0, "open", (player, npc) -> player.getMovement().teleport(2094, 3930, 0));
    }
}

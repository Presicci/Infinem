package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/23/2024
 */
public class RazmireKeelgan {

    static {
        NPCAction.register(1290, "trade-general-store", (player, npc) -> npc.openShop(player));
        NPCAction.register(1290, "trade-builders-store", (player, npc) -> npc.openShop(player));
    }
}

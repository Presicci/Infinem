package io.ruin.model.entity.npc.actions.varrock;

import io.ruin.model.entity.npc.NPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/22/2023
 */
public class Apothecary {

    static {
        NPCAction.register(5036, "potions", ((player, npc) -> npc.getDef().shops.get(0).open(player)));
    }
}

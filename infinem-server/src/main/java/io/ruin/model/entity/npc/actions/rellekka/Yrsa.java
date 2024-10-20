package io.ruin.model.entity.npc.actions.rellekka;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.handlers.makeover.MakeoverInterface;
import io.ruin.model.inter.handlers.makeover.MakeoverType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/19/2024
 */
public class Yrsa {

    static {
        NPCAction.register(3933, "makeover", (player, npc) -> MakeoverInterface.open(player, MakeoverType.FEET, npc));
    }
}

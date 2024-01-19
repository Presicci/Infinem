package io.ruin.model.map.object.actions.impl.transportation;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/16/2020
 */
public class EdgevillePortal {

    static {
        ObjectAction.register(34752, "Use", ((player, obj) -> teleport(player)));
    }

    private static void teleport(Player player) {
        Traveling.fadeTravel(player, 3085, 3492, 0);
    }
}

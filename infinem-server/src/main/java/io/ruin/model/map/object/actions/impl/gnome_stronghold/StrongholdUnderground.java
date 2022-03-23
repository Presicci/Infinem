package io.ruin.model.map.object.actions.impl.gnome_stronghold;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class StrongholdUnderground {

    static {
        ObjectAction.register(17209, "enter", (player, obj) -> Traveling.fadeTravel(player ,2409, 9812, 0));
        ObjectAction.register(17223, "exit", (player, obj) -> Traveling.fadeTravel(player ,2402, 3419, 0));
    }
}

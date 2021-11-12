package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 11/11/2021
 */
public class FeldipHills {
    static {
        ObjectAction.register(31791, 2523, 2861, 0, "enter", (player, obj) -> Ladder.climb(player, 2012, 9004, 1, false, true, false));
        ObjectAction.register(31790, 2012, 9005, 1, "climb", (player, obj) -> Ladder.climb(player, 2523, 2860, 0, true, true, false));
    }
}

package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class DwarvenMine {

    static {
        /*
         * Dwarven entrance
         */
        ObjectAction.register(11867, 3019, 3450, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3018, 9850, 0, false, true, false));
        ObjectAction.register(17387, 3019, 9850, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3018, 3450, 0, true, true, false));
    }
}

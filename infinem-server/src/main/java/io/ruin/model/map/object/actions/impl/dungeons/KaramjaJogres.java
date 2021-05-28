package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
public class KaramjaJogres {

    static {
        // Castle cellar
        ObjectAction.register(2585, 2830, 9522, 0, "climb", (player, obj) -> Ladder.climb(player, 2824, 3120, 0, true, true, false));
        ObjectAction.register(2584, 2824, 3118, 0, "search", (player, obj) -> Ladder.climb(player, 2830, 9522, 0, false, true, false));
    }

}

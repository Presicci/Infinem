package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/1/2022
 */
public class DorgeshKaanMine {

    static {
        // Castle cellar
        ObjectAction.register(17385, 3209, 9616, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3210, 3216, 0, true, true, false));
        ObjectAction.register(14880, 3209, 3216, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3210, 9616, 0, false, true, false));
    }
}

package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/27/2022
 */
public class GoblinVillage {

    static {
        // Ladder
        ObjectAction.register(16450, 2954, 3497, 0, "climb-up", (player, obj) -> Ladder.climb(player, new Position(2955, 3497, 2), true, true, false));
        ObjectAction.register(16556, 2954, 3497, 2, "climb-down", (player, obj) -> Ladder.climb(player, new Position(2955, 3497), false, true, false));
    }
}

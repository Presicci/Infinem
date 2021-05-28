package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.Stairs;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/25/2021
 */
public class Lumbridge {

    static {

        /**
         * Staircases
         */
        Stairs.registerStair(16671, new Position(3204, 3207, 0));
        Stairs.registerStair(16672, new Position(3204, 3207, 1));
        Stairs.registerStair(16673, new Position(3205, 3208, 2));

        Stairs.registerStair(16671, new Position(3204, 3229, 0));
        Stairs.registerStair(16672, new Position(3204, 3229, 1));
        Stairs.registerStair(16673, new Position(3205, 3229, 2));

        // Castle cellar
        ObjectAction.register(17385, 3209, 9616, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3210, 3216, 0, true, true, false));
        ObjectAction.register(14880, 3209, 3216, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3210, 9616, 0, true, true, false));
    }
}

package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.Stairs;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
public class Ardougne {

    static {
        // Chaos druid basement
        ObjectAction.register(17385, 2562, 9756, 0, "climb-up", (player, obj) -> Ladder.climb(player, 2563, 3356, 0, true, true, false));
        ObjectAction.register(17384, 2562, 3356, 0, "climb-down", (player, obj) -> Ladder.climb(player, 2563, 9756, 0, false, true, false));
        Stairs.registerStair(15645, new Position(2571, 3295, 0), Direction.SOUTH, 4);
        Stairs.registerStair(15648, new Position(2571, 3295, 1), Direction.NORTH, 4);
    }
}

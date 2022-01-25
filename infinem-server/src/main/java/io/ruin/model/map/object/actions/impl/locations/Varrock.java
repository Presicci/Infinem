package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.impl.Stairs;

public class Varrock {

    static {
        // Champion's guild stairs
        Stairs.registerStair(11797, new Position(3188, 3355), Direction.SOUTH, 4);
        Stairs.registerStair(11799, new Position(3188, 3355, 1), Direction.NORTH, 4);
    }
}

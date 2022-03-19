package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Stairs;

public class Varrock {

    static {
        // Champion's guild stairs
        Stairs.registerStair(11797, new Position(3188, 3355), Direction.SOUTH, 4);
        Stairs.registerStair(11799, new Position(3188, 3355, 1), Direction.NORTH, 4);

        ObjectAction.register(11800, 3187, 3433, 0, "climb-down", (player, obj) -> player.getMovement().teleport(3190, 9834, 0));
        ObjectAction.register(11805, 3187, 9833, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3186, 3434, 0));
    }
}

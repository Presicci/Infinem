package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.Stairs;
import io.ruin.model.map.object.actions.impl.Trapdoor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 10/5/2021
 */
public class DraynorVillage {
    static {
        //  Wise old man's house
        Stairs.registerStair(16670, new Position(3090, 3251), Direction.EAST, 4);
        Stairs.registerStair(16669, new Position(3091, 3251, 1), Direction.WEST, 4);
        //  Morgan's house
        Stairs.registerStair(15645, new Position(3099, 3266), Direction.EAST, 4);
        Stairs.registerStair(15648, new Position(3100, 3266, 1), Direction.WEST, 4);
        //  Sewer
        ObjectAction.register(6434, "open", (player, obj) -> Trapdoor.open(player, obj, 6435));
        ObjectAction.register(6435, "climb-down", (player, obj) -> Trapdoor.climbDown(player, new Position(3085, 9672, 0)));
    }
}

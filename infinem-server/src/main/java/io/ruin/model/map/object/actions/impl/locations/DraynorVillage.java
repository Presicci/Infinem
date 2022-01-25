package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.Stairs;
import io.ruin.model.map.object.actions.impl.Trapdoor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 10/5/2021
 */
public class DraynorVillage {

    /*
     * Has to be done like this because the objects aren't present at runtime and thus
     * cannot be registered with coordinates
     */
    private static void climbDownTrapdoor(Player player, GameObject obj) {
        if (obj.getPosition().getX() == 3084) {
            Trapdoor.climbDown(player, new Position(3085, 9672, 0));
        } else {
            Trapdoor.climbDown(player, new Position(3118, 9644, 0));
        }
    }

    static {
        //  Wise old man's house
        Stairs.registerStair(16670, new Position(3090, 3251), Direction.EAST, 4);
        Stairs.registerStair(16669, new Position(3091, 3251, 1), Direction.WEST, 4);
        //  Morgan's house
        Stairs.registerStair(15645, new Position(3099, 3266), Direction.EAST, 4);
        Stairs.registerStair(15648, new Position(3100, 3266, 1), Direction.WEST, 4);
        //  Sewer
        ObjectAction.register(6434, "open", (player, obj) -> Trapdoor.open(player, obj, 6435));
        ObjectAction.register(6435, "climb-down", DraynorVillage::climbDownTrapdoor);
        // Sewer ladders
        ObjectAction.register(17385, 3084, 9672, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3084, 3273, 0, true, true, false));
        ObjectAction.register(6436, 3118, 9643, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3118, 3243, 0, true, true, false));
    }
}

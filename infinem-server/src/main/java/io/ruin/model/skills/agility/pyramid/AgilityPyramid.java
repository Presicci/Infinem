package io.ruin.model.skills.agility.pyramid;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/8/2023
 */
public class AgilityPyramid {

    private static final int[] STAIRS = { 10857, 10858 };

    static {
        for (int stairId : STAIRS) {
            ObjectAction.register(stairId, 1, AgilityPyramid::climbStairs);
        }
    }

    private static void climbStairs(Player player, GameObject object) {
        if (object.id == 10857) {
            player.getMovement().teleport(AgilityPyramid.getHigherTile(player.getPosition().relative(Direction.NORTH, 3)));
        } else {
            player.getMovement().teleport(AgilityPyramid.getLowerTile(player.getPosition().relative(Direction.SOUTH, 3)));
        }
    }

    public static Position getHigherTile(Position position) {
        if(position.getZ() == 3) {
            return position.relative(-320, 1856, -1);
        }
        return position.relative(0, 0, 1);
    }

    public static Position getLowerTile(Position position) {
        if (position.getY() >= 4686 && position.getY() <= 4709 && position.getZ() == 2) {
            return position.relative(320, -1856, 1);
        }
        return position.relative(0, 0, -1);
    }
}

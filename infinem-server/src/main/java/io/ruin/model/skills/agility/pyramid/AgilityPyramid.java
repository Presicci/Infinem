package io.ruin.model.skills.agility.pyramid;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
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
            player.getMovement().teleport(AgilityPyramidArea.getHigherTile(player.getPosition().relative(Direction.NORTH, 3)));
        } else {
            player.getMovement().teleport(AgilityPyramidArea.getLowerTile(player.getPosition().relative(Direction.SOUTH, 3)));
        }
    }
}

package io.ruin.model.map.object.actions.impl.varlamore;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/2/2024
 */
public class CamTorum {

    private static void squeezeThroughDoor(Player player, GameObject object) {
        Direction direction = Direction.getFromObjectDirection(object.direction, 2);
        int difY = direction == Direction.NORTH || direction == Direction.SOUTH ? 2 : 0;
        int difX = direction == Direction.EAST || direction == Direction.WEST ? 2 : 0;
        if ((direction == Direction.NORTH || direction == Direction.SOUTH) && player.getPosition().getY() > object.y) {
            difY = -difY;
        } else if ((direction == Direction.EAST || direction == Direction.WEST) && player.getPosition().getX() > object.x) {
            difX = -difX;
        }
        player.getMovement().teleport(player.getPosition().relative(difX, difY));
    }

    static {
        ObjectAction.register(51005, "squeeze-through", CamTorum::squeezeThroughDoor);
        ObjectAction.register(51005, 1413, 9582, 1, "squeeze-through", (player, obj) -> player.sendMessage("It's locked."));
        ObjectAction.register(51005, 1465, 9582, 1, "squeeze-through", (player, obj) -> player.sendMessage("It's locked."));
    }
}

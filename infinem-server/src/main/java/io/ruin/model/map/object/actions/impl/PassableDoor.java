package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class PassableDoor {

    /**
     * Passes the player through the door, with a little animation.
     * default rotationOffset of 0
     * @param player The player passing through the door.
     * @param obj The door game object.
     * @param doorOpenDirection The direction the door will be opening.
     */
    public static void passDoor(Player player, GameObject obj, Direction doorOpenDirection) {
        passDoor(player, obj, doorOpenDirection, 0);
    }

    /**
     * Passes the player through the door, with a little animation.
     * @param player The player passing through the door.
     * @param obj The door game object.
     * @param doorOpenDirection The direction the door will be opening.
     * @param rotationOffset The offset for the rotation of the game in its open state, basically just trial and error to find this.
     */
    public static void passDoor(Player player, GameObject obj, Direction doorOpenDirection, int rotationOffset) {
        World.startEvent(e -> {
            e.delay(1);
            GameObject tempGate = new GameObject(obj.id, obj.getPosition().translate(
                    doorOpenDirection == Direction.EAST ? 1 : doorOpenDirection == Direction.WEST ? -1 : 0,
                    doorOpenDirection == Direction.NORTH ? 1 : doorOpenDirection == Direction.SOUTH ? -1 : 0),
                    0, obj.direction + 1 + rotationOffset);
            GameObject tempRemove = new GameObject(-1, obj.getPosition(), 0, 0);
            tempRemove.spawn();
            tempGate.spawn();
            player.stepAbs(
                    player.getAbsX() + (doorOpenDirection == Direction.EAST ? (player.getAbsX() <= obj.getPosition().getX() ? 1 : -1) : doorOpenDirection == Direction.WEST ? (player.getAbsX() >= obj.getPosition().getX() ? -1 : 1) : 0),
                    player.getAbsY() + (doorOpenDirection == Direction.NORTH ? (player.getAbsY() <= obj.getPosition().getY() ? 1 : -1) : doorOpenDirection == Direction.SOUTH ? (player.getAbsY() >= obj.getPosition().getY() ? -1 : 1) : 0),
                    StepType.FORCE_WALK);
            e.delay(2);
            tempGate.remove();
            tempRemove.remove();
            obj.spawn();
        });
    }
}

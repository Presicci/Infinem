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

    public static void passDoor(Player player, GameObject obj, Direction doorOpenDirection) {
        passDoor(player, obj, doorOpenDirection, 0);
    }

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

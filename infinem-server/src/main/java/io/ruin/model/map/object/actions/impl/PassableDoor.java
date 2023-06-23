package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public enum PassableDoor {
    TROLL_STRONGHOLD_PRISON_DOOR(3780, "unlock", new Position(2848, 10107, 1), Direction.WEST);

    private final int id, rotationOffset, tempObjectId;
    private final String option;
    private final Position objectPos;
    private final Direction direction;

    PassableDoor(int id, String option, Position objectPos, Direction direction) {
        this(id, option, objectPos, direction, -1, -1);
    }

    PassableDoor(int id, String option, Position objectPos, Direction direction, int rotationOffset) {
        this(id, option, objectPos, direction, rotationOffset, -1);
    }

    PassableDoor(int id, String option, Position objectPos, Direction direction, int rotationOffset, int tempObjectId) {
        this.id = id;
        this.option = option;
        this.objectPos = objectPos;
        this.direction = direction;
        this.rotationOffset = rotationOffset;
        this.tempObjectId = tempObjectId;
    }

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

    public static void passDoor(Player player, GameObject obj, Direction doorOpenDirection, int rotationOffset) {
        passDoor(player, obj, doorOpenDirection, rotationOffset, null, -1);
    }

    public static void passDoor(Player player, GameObject obj, Direction doorOpenDirection, int rotationOffset, int tempObjectId) {
        passDoor(player, obj, doorOpenDirection, rotationOffset, null, tempObjectId);
    }

    /**
     * Passes the player through the door, with a little animation.
     * @param player The player passing through the door.
     * @param obj The door game object.
     * @param doorOpenDirection The direction the door will be opening.
     * @param rotationOffset The offset for the rotation of the game in its open state, basically just trial and error to find this.
     * @param objectOffset The position offset for where the temp door is spawned.
     * @param tempObjectId The id for the temp door.
     */
    public static void passDoor(Player player, GameObject obj, Direction doorOpenDirection, int rotationOffset, Position objectOffset, int tempObjectId) {
        World.startEvent(e -> {
            e.delay(1);
            Position tempGatePos = obj.getPosition().translate(
                    doorOpenDirection == Direction.EAST ? 1 : doorOpenDirection == Direction.WEST ? -1 : 0,
                    doorOpenDirection == Direction.NORTH ? 1 : doorOpenDirection == Direction.SOUTH ? -1 : 0);
            if (objectOffset != null)
                tempGatePos = tempGatePos.translate(objectOffset.getX(), objectOffset.getY());
            GameObject tempGate = new GameObject(tempObjectId == -1 ? obj.id : tempObjectId, tempGatePos,
                    obj.type, obj.direction + 1 + rotationOffset);
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

    static {
        for (PassableDoor entry : values()) {
            if (entry.rotationOffset == -1) {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> passDoor(player, obj, entry.direction)));
            } else if (entry.tempObjectId == -1) {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> passDoor(player, obj, entry.direction, entry.rotationOffset)));
            } else {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> passDoor(player, obj, entry.direction, entry.rotationOffset, entry.tempObjectId)));
            }
        }
    }
}

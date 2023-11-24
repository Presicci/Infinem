package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/16/2023
 */
public class DoubleDoor {

    static {
        // Piscatoris fishing colony entrance
        ObjectAction.register(12725, 2344, 3662, 0, "open", ((player, obj) -> passThroughLongitudinal(player, obj, 12723, 12725, new Position(2343, 3662), new Position(2344, 3662))));
        ObjectAction.register(12723, 2343, 3662, 0, "open", ((player, obj) -> passThroughLongitudinal(player, obj, 12723, 12725, new Position(2343, 3662), new Position(2344, 3662))));
        // Dwarf Cannon Camp
        ObjectAction.register(15605, 2568, 3456, 0, "open", ((player, obj) -> passThroughLongitudinal(player, obj, 15604, 15605, new Position(2567, 3456), new Position(2568, 3456), false)));
        ObjectAction.register(15604, 2567, 3456, 0, "open", ((player, obj) -> passThroughLongitudinal(player, obj, 15604, 15605, new Position(2567, 3456), new Position(2568, 3456), false)));
    }

    /**
     * Passes the player through a set of double doors going east to west/west to east.
     * @param player The player moving through the door.
     * @param obj The door game object.
     * @param doorTopId Object ID for the top door.
     * @param doorBottomId Object ID for the bottom door.
     * @param doorTopPosition Position of the top door.
     * @param doorBottomPosition Position of the bottom door.
     */
    public static void passThroughLateral(Player player, GameObject obj, int doorTopId, int doorBottomId, Position doorTopPosition, Position doorBottomPosition) {
        passThroughLateral(player, obj, doorTopId, doorBottomId, doorTopPosition, doorBottomPosition, false);
    }

    /**
     * Passes the player through a set of double doors going east to west/west to east.
     * @param player The player moving through the door.
     * @param obj The door game object.
     * @param doorTopId Object ID for the top door.
     * @param doorBottomId Object ID for the bottom door.
     * @param doorTopPosition Position of the top door.
     * @param doorBottomPosition Position of the bottom door.
     */
    public static void passThroughLateral(Player player, GameObject obj, int doorTopId, int doorBottomId, Position doorTopPosition, Position doorBottomPosition, boolean openEast) {
        Direction dir = player.getPosition().getX() == (openEast ? obj.getPosition().getX() + 1 : obj.getPosition().getX()) ? Direction.WEST : Direction.EAST;
        World.startEvent(e -> {
            e.delay(1);
            // Temp objects used for the animation
            GameObject temp = new GameObject(doorTopId, doorTopPosition.getX() + (openEast ? 1 : -1), doorTopPosition.getY(), doorTopPosition.getZ(), 0, 1);
            GameObject tempRemove = new GameObject(-1, doorTopPosition, 0, 0);
            GameObject temp2 = new GameObject(doorBottomId, doorBottomPosition.getX() + (openEast ? 1 : -1), doorBottomPosition.getY(), doorBottomPosition.getZ(), 0, 3);
            GameObject tempRemove2 = new GameObject(-1, doorBottomPosition, 0, 0);
            // Naturally spawns for the gates, for respawning after the player walks through
            GameObject north = new GameObject(doorTopId, doorTopPosition, 0, openEast ? 2 : 0);
            GameObject south = new GameObject(doorBottomId, doorBottomPosition, 0, openEast ? 2 : 0);
            // Spawn temp objects
            tempRemove.spawn();
            temp.spawn();
            tempRemove2.spawn();
            temp2.spawn();
            // Move player
            player.getMovement().force(dir == Direction.EAST ? 1 : -1, 0, 0, 0, 1, 0, dir);
            e.delay(2);
            // Remove temp objects
            temp.remove();
            tempRemove.remove();
            temp2.remove();
            tempRemove2.remove();
            // Respawn gates
            north.spawn();
            south.spawn();
        });
    }

    /**
     * Passes the player through a set of double doors going north to south/south to north.
     * @param player The player moving through the door.
     * @param obj The door game object.
     * @param doorLeftId Object ID for the left door.
     * @param doorRightId Object ID for the right door.
     * @param doorLeftPosition Position of the left door.
     * @param doorRightPosition Position of the right door.
     */
    public static void passThroughLongitudinal(Player player, GameObject obj, int doorLeftId, int doorRightId, Position doorLeftPosition, Position doorRightPosition) {
        passThroughLongitudinal(player, obj, doorLeftId, doorRightId, doorLeftPosition, doorRightPosition, true);
    }

    /**
     * Passes the player through a set of double doors going north to south/south to north.
     * @param player The player moving through the door.
     * @param obj The door game object.
     * @param doorLeftId Object ID for the left door.
     * @param doorRightId Object ID for the right door.
     * @param doorLeftPosition Position of the left door.
     * @param doorRightPosition Position of the right door.
     */
    public static void passThroughLongitudinal(Player player, GameObject obj, int doorLeftId, int doorRightId, Position doorLeftPosition, Position doorRightPosition, boolean openNorth) {
        Direction dir = player.getPosition().getY() == (openNorth ? obj.getPosition().getY() : obj.getPosition().getY() - 1) ? Direction.NORTH : Direction.SOUTH;
        World.startEvent(e -> {
            e.delay(1);
            // Temp objects used for the animation
            GameObject temp = new GameObject(doorLeftId, doorLeftPosition.getX(), doorLeftPosition.getY() + (openNorth ? 1 : -1), doorLeftPosition.getZ(), 0, 0);
            GameObject tempRemove = new GameObject(-1, doorLeftPosition, 0, 0);
            GameObject temp2 = new GameObject(doorRightId, doorRightPosition.getX(), doorRightPosition.getY() + (openNorth ? 1 : -1), doorRightPosition.getZ(), 0, 2);
            GameObject tempRemove2 = new GameObject(-1, doorRightPosition, 0, 0);
            // Naturally spawns for the gates, for respawning after the player walks through
            GameObject left = new GameObject(doorLeftId, doorLeftPosition, 0, openNorth ? 1 : 3);
            GameObject right = new GameObject(doorRightId, doorRightPosition, 0, openNorth ? 1 : 3);
            // Spawn temp objects
            tempRemove.spawn();
            temp.spawn();
            tempRemove2.spawn();
            temp2.spawn();
            // Move player
            player.getMovement().force(0, dir == Direction.NORTH ? 1 : -1, 0, 0, 1, 0, dir);
            e.delay(2);
            // Remove temp objects
            temp.remove();
            tempRemove.remove();
            temp2.remove();
            tempRemove2.remove();
            // Respawn gates
            left.spawn();
            right.spawn();
        });
    }
}

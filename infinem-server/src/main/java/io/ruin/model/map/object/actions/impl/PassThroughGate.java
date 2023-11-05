package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/4/2023
 */
public class PassThroughGate {

    static {
        // Piscatoris fishing colony entrance
        ObjectAction.register(12816, 3485, 3244, 0, "open", ((player, obj) -> passThroughLongitudinal(player, obj, 12817, 12816, new Position(3484, 3244), new Position(3485, 3244), false)));
        ObjectAction.register(12817, 3484, 3244, 0, "open", ((player, obj) -> passThroughLongitudinal(player, obj, 12817, 12816, new Position(3484, 3244), new Position(3485, 3244), false)));
   }

    /**
     * Passes the player through a gate going north to south/south to north.
     * @param player The player moving through the door.
     * @param obj The gate game object.
     * @param doorLeftId Object ID for the left gate.
     * @param doorRightId Object ID for the right gate.
     * @param doorLeftPosition Position of the left gate.
     * @param doorRightPosition Position of the right gate.
     */
    public static void passThroughLongitudinal(Player player, GameObject obj, int doorLeftId, int doorRightId, Position doorLeftPosition, Position doorRightPosition) {
        passThroughLongitudinal(player, obj, doorLeftId, doorRightId, doorLeftPosition, doorRightPosition, true);
    }

    /**
     * Passes the player through a gate going north to south/south to north.
     * @param player The player moving through the door.
     * @param obj The gate game object.
     * @param gateLeftId Object ID for the left gate.
     * @param gateRightId Object ID for the right gate.
     * @param gateLeftPosition Position of the left gate.
     * @param gateRightPosition Position of the right gate.
     */
    public static void passThroughLongitudinal(Player player, GameObject obj, int gateLeftId, int gateRightId, Position gateLeftPosition, Position gateRightPosition, boolean openNorth) {
        Direction dir = player.getPosition().getY() == (openNorth ? obj.getPosition().getY() : obj.getPosition().getY() - 1) ? Direction.NORTH : Direction.SOUTH;
        World.startEvent(e -> {
            e.delay(1);
            // Temp objects used for the animation
            GameObject temp = new GameObject(gateLeftId, gateRightPosition.getX(), gateRightPosition.getY() + (openNorth ? 2 : -2), gateRightPosition.getZ(), 0, 2);
            GameObject tempRemove = new GameObject(-1, gateLeftPosition, 0, 0);
            GameObject temp2 = new GameObject(gateRightId, gateRightPosition.getX(), gateRightPosition.getY() + (openNorth ? 1 : -1), gateRightPosition.getZ(), 0, 2);
            GameObject tempRemove2 = new GameObject(-1, gateRightPosition, 0, 0);
            // Naturally spawns for the gates, for respawning after the player walks through
            GameObject left = new GameObject(gateLeftId, gateLeftPosition, 0, openNorth ? 1 : 3);
            GameObject right = new GameObject(gateRightId, gateRightPosition, 0, openNorth ? 1 : 3);
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

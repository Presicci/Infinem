package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.PassableDoor;
import io.ruin.model.map.object.actions.impl.Stairs;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/7/2022
 */
public class DraynorManor {

    private static void openFrontDoor(Player player, GameObject obj) {
        Direction dir = Direction.NORTH;
        if (player.getPosition().getY() > obj.getPosition().getY()) {
            player.sendMessage("The doors won't open.");
            return;
        }
        World.startEvent(e -> {
            player.lock();
            e.delay(1);
            // Temp objects used for the animation
            GameObject tempGate = new GameObject(11446, 3108, 3354, 0, 0, 0);
            GameObject tempRemove = new GameObject(-1, 3108, 3353, 0, 0, 0);
            GameObject tempGate2 = new GameObject(11447, 3109, 3354, 0, 0, 2);
            GameObject tempRemove2 = new GameObject(-1, 3109, 3353, 0, 0, 0);
            // Naturally spawns for the gates, for respawning after the player walks through
            GameObject northGate = new GameObject(134, 3108, 3353, 0, 0, 1);    // Left
            GameObject southGate = new GameObject(135, 3109, 3353, 0, 0, 1);    // Right
            // Spawn temp objects
            tempRemove.spawn();
            tempGate.spawn();
            tempRemove2.spawn();
            tempGate2.spawn();
            // Move player
            player.getMovement().force(0, 1, 0, 0, 1, 0, dir);
            e.delay(2);
            // Remove temp objects
            tempGate.remove();
            tempRemove.remove();
            tempGate2.remove();
            tempRemove2.remove();
            // Respawn gates
            northGate.spawn();
            southGate.spawn();
            player.getTaskManager().doLookupByUUID(340, 1); // Enter Draynor Manor
            player.unlock();
        });
    }

    private static void pullLever(Player player, GameObject obj) {
        Direction dir = Direction.WEST;
        World.startEvent(e -> {
            player.lock();
            player.animate(834);
            e.delay(1);
            player.getMovement().force(0, 1, 0, 0, 1, 0, dir);
            e.delay(2);
            // Temp objects used for the animation
            GameObject tempBookcase = new GameObject(157, 3097, 3360, 0, 10, 0);
            GameObject tempRemove = new GameObject(-1, 3097, 3358, 0, 10, 0);
            GameObject tempBookcase2 = new GameObject(157, 3097, 3357, 0, 10, 0);
            GameObject tempRemove2 = new GameObject(-1, 3097, 3359, 0, 10, 0);
            // Naturally spawns for the gates, for respawning after the player walks through
            GameObject northBookcase = new GameObject(155, 3097, 3358, 0, 10, 0);    // South
            GameObject southBookcase = new GameObject(156, 3097, 3359, 0, 10, 0);    // North
            // Spawn temp objects
            tempRemove.spawn();
            tempBookcase.spawn();
            tempRemove2.spawn();
            tempBookcase2.spawn();
            // Move player
            player.getMovement().force(2, 0, 0, 0, 1, 0, dir);
            e.delay(2);
            // Remove temp objects
            tempBookcase.remove();
            tempRemove.remove();
            tempBookcase2.remove();
            tempRemove2.remove();
            // Respawn gates
            northBookcase.spawn();
            southBookcase.spawn();
            player.unlock();
        });
    }

    private static void openBookcase(Player player, GameObject obj) {
        if (player.getPosition().getX() < obj.getPosition().getX()) {
            return;
        }
        Direction dir = Direction.WEST;
        World.startEvent(e -> {
            player.lock();
            e.delay(1);
            // Temp objects used for the animation
            GameObject tempBookcase = new GameObject(157, 3097, 3360, 0, 10, 0);
            GameObject tempRemove = new GameObject(-1, 3097, 3358, 0, 10, 0);
            GameObject tempBookcase2 = new GameObject(157, 3097, 3357, 0, 10, 0);
            GameObject tempRemove2 = new GameObject(-1, 3097, 3359, 0, 10, 0);
            // Naturally spawns for the gates, for respawning after the player walks through
            GameObject northBookcase = new GameObject(155, 3097, 3358, 0, 10, 0);    // South
            GameObject southBookcase = new GameObject(156, 3097, 3359, 0, 10, 0);    // North
            // Spawn temp objects
            tempRemove.spawn();
            tempBookcase.spawn();
            tempRemove2.spawn();
            tempBookcase2.spawn();
            // Move player
            player.getMovement().force(-2, 0, 0, 0, 1, 0, dir);
            e.delay(2);
            // Remove temp objects
            tempBookcase.remove();
            tempRemove.remove();
            tempBookcase2.remove();
            tempRemove2.remove();
            // Respawn gates
            northBookcase.spawn();
            southBookcase.spawn();
            player.unlock();
        });
    }

    static {
        // Front door
        ObjectAction.register(131, 3107, 3367, 0, "open", ((player, obj) -> player.sendMessage("The door is locked.")));
        ObjectAction.register(134, 3108, 3353, 0, "open", (DraynorManor::openFrontDoor));
        ObjectAction.register(135, 3109, 3353, 0, "open", (DraynorManor::openFrontDoor));

        // Vampire Slayer dungeon
        ObjectAction.register(2616, 3115, 3357, 0, "walk-down", ((player, obj) -> Traveling.fadeTravel(player, new Position(3077, 9771))));
        ObjectAction.register(2617, 3077, 9768, 0, "walk-up", ((player, obj) -> Traveling.fadeTravel(player, new Position(3115, 3356))));

        // Central staircase
        Stairs.registerStair(11498, new Position(3108, 3362, 0), Direction.NORTH, 5);
        Stairs.registerStair(11499, new Position(3108, 3364, 1), Direction.SOUTH, 5);

        // Bookcases to ava
        Tile.getObject(155, 3097, 3358, 0).nearPosition = (p, obj) -> {
            return new Position(3098, 3358, 0);
        };
        Tile.getObject(156, 3097, 3359, 0).nearPosition = (p, obj) -> {
            return new Position(3098, 3359, 0);
        };
        ObjectAction.register(155, 3097, 3358, 0, "search", (DraynorManor::openBookcase));
        ObjectAction.register(156, 3097, 3359, 0, "search", (DraynorManor::openBookcase));
        ObjectAction.register(160, 3096, 3357, 0, "pull", (DraynorManor::pullLever));

        // Spiral staircases
        ObjectAction.register(11511, 3104, 3362, 1, "climb-up", ((player, obj) -> player.getMovement().teleport(3105, 3364, 2)));
        ObjectAction.register(9584, 3105, 3363, 2, "climb-down", ((player, obj) -> player.getMovement().teleport(3106, 3363, 1)));

        // Ava's room ladder
        ObjectAction.register(133, 3092, 3362, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(3117, 9753), false, true, false)));
        ObjectAction.register(132, 3117, 9754, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(3092, 3361), true, true, false)));

        // Back door
        ObjectAction.register(136, 3123, 3361, 0, "open", (player, obj) -> PassableDoor.passDoor(player, obj, Direction.SOUTH, -4, 11448));
    }
}

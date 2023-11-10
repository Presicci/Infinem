package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/25/2021
 */
public class Stairs {

    public static void registerSpiralStair(int objectId, Position position) {
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-up", (player, obj) -> climbUpSpiral(player));
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-down", (player, obj) -> climbDownSpiral(player));
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb", (player, obj) -> {
            player.dialogue(
                    new OptionsDialogue("Climb up or down the stairs?",
                            new Option("Climb up the stairs.", () -> climbUpSpiral(player)),
                            new Option("Climb down the stairs.", () -> climbDownSpiral(player))
                    ));
        });
    }

    private static void climbUpSpiral(Player player) {
        player.getMovement().teleport(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ() + 1);
    }

    private static void climbDownSpiral(Player player) {
        player.getMovement().teleport(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ() - 1);
    }

    public static void registerStair(int objectId, final int tiles, final int offset) {
        ObjectAction.register(objectId, "climb-up", (player, obj) ->
                climbUp(player, Direction.getFromObjectDirection(obj.direction + offset > 3 ? obj.direction + offset - 4 : obj.direction + offset < 0 ? obj.direction + offset + 4 : obj.direction + offset), tiles));
        ObjectAction.register(objectId, "climb-down", (player, obj) ->
                climbDown(player, Direction.getFromObjectDirection(obj.direction + offset > 3 ? obj.direction + offset - 4 : obj.direction + offset < 0 ? obj.direction + offset + 4 : obj.direction + offset), tiles));
    }

    public static void registerStair(int objectId, Position position, final Direction direction, final int tiles) {
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-up", (player, obj) -> climbUp(player, direction, tiles));
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-down", (player, obj) -> climbDown(player, direction, tiles));
    }

    private static void climbUp(Player player, Direction direction, int tiles) {
        climb(player, direction, tiles, true);
    }

    private static void climbDown(Player player, Direction direction, int tiles) {
        climb(player, direction, tiles, false);
    }

    private static void climb(Player player, Direction direction, int tiles, boolean climbUp) {
        Position pos = player.getPosition().copy();
        switch (direction) {
            case NORTH:
                pos.translate(0, tiles, climbUp ? 1 : -1);
                break;
            case SOUTH:
                pos.translate(0, -tiles, climbUp ? 1 : -1);
                break;
            case EAST:
                pos.translate(tiles, 0, climbUp ? 1 : -1);
                break;
            case WEST:
                pos.translate(-tiles, 0, climbUp ? 1 : -1);
                break;
        }
        player.getMovement().teleport(pos);
    }

    static {
        // Wizard's tower
        registerSpiralStair(12536, new Position(3103, 3159, 0));
        registerSpiralStair(12537, new Position(3103, 3159, 1));
        registerSpiralStair(12538, new Position(3104, 3160, 2));
        // Isle of souls crumbling tower
        registerSpiralStair(14735, new Position(2134, 2995, 0));
        registerSpiralStair(14736, new Position(2134, 2995, 1));
        registerSpiralStair(14737, new Position(2135, 2996, 2));
        // Dorgesh-kaan
        registerStair(22942, 3, 1);
        registerStair(22941, 3, -1);
        registerStair(22940, 3, 1);
        registerStair(22939, 3, -1);
        registerStair(22937, 4, -1);
        registerStair(22938, 4, 1);
        registerStair(22933, 3, 0);
        registerStair(22932, 3, 0);
        registerStair(22931, 3, 2);


    }
}

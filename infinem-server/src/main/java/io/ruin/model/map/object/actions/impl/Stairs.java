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

    public static void registerStair(int objectId, Position position, final Direction direction, final int tiles) {
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-up", (player, obj) -> climbUp(player, direction ,tiles));
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-down", (player, obj) -> climbDown(player, direction, tiles));
    }

    private static void climbUp(Player player, Direction direction, int tiles) {
        climb(player, direction, tiles, true);
    }

    private static void climbDown(Player player, Direction direction, int tiles) {
        climb(player, direction, tiles, false);
    }

    private static void climb(Player player, Direction direction, int tiles, boolean climbUp) {
        Position pos = player.getPosition();
        switch (direction) {
            case NORTH:
                pos.translate(tiles, 0, climbUp ? 1 : -1);
                break;
            case SOUTH:
                pos.translate(-tiles, 0, climbUp ? 1 : -1);
                break;
            case EAST:
                pos.translate(0, tiles, climbUp ? 1 : -1);
                break;
            case WEST:
                pos.translate(0, -tiles, climbUp ? 1 : -1);
                break;
        }
        player.getMovement().teleport(pos);
    }

    static {
        // Wizard's tower
        registerSpiralStair(12536, new Position(3103, 3159, 0));
        registerSpiralStair(12537, new Position(3103, 3159, 1));
        registerSpiralStair(12538, new Position(3104, 3160, 2));
    }
}

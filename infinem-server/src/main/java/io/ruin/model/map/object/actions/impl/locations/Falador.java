package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Stairs;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/27/2022
 */
public class Falador {

    static {
        // Houses south of party room
        Stairs.registerStair(24080, new Position(3045, 3364, 1), Direction.SOUTH, 4);
        Stairs.registerStair(24079, new Position(3045, 3363), Direction.NORTH, 4);
        ObjectAction.register(24075, 3048, 3352, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3049, 3354, 1));
        ObjectAction.register(24076, 3049, 3353, 1, "climb-down", (player, obj) -> player.getMovement().teleport(3049, 3354));

        // Above mining guild
        ObjectAction.register(24072, 3011, 3338, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3011, 3337, 1));
        ObjectAction.register(24074, 3011, 3338, 1, "climb-down", (player, obj) -> player.getMovement().teleport(3011, 3337));
        Stairs.registerStair(24077, new Position(3021, 3332), Direction.EAST, 4);
        Stairs.registerStair(24078, new Position(3022, 3332, 1), Direction.WEST, 4);
        Stairs.registerStair(24077, new Position(3018, 3343), Direction.EAST, 4);
        Stairs.registerStair(24078, new Position(3019, 3343, 1), Direction.WEST, 4);

        // Furnace building
        Stairs.registerStair(24080, new Position(2971, 3371, 1), Direction.SOUTH, 4);
        Stairs.registerStair(24079, new Position(2971, 3370), Direction.NORTH, 4);

        // Inn
        Stairs.registerStair(24080, new Position(2959, 3370, 1), Direction.SOUTH, 4);
        Stairs.registerStair(24079, new Position(2959, 3369), Direction.NORTH, 4);

        // White knights
        ObjectAction.register(24072, 2954, 3338, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2956, 3338, 1));
        ObjectAction.register(24074, 2955, 3338, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2956, 3338));
        ObjectAction.register(24072, 2960, 3338, 1, "climb-up", (player, obj) -> player.getMovement().teleport(2959, 3339, 2));
        ObjectAction.register(24074, 2960, 3339, 2, "climb-down", (player, obj) -> player.getMovement().teleport(2959, 3339, 1));
        ObjectAction.register(24072, 2957, 3338, 2, "climb-up", (player, obj) -> player.getMovement().teleport(2959, 3338, 3));
        ObjectAction.register(24074, 2958, 3338, 3, "climb-down", (player, obj) -> player.getMovement().teleport(2959, 3338, 2));
        ObjectAction.register(24067, 2968, 3347, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2968, 3348, 1));
        ObjectAction.register(24068, 2968, 3347, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2971, 3347));
        Stairs.registerStair(24078, new Position(2984, 3338, 2), Direction.SOUTH, 4);
        Stairs.registerStair(24077, new Position(2984, 3337, 1), Direction.NORTH, 4);
    }
}

package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/6/2023
 */
public class GnomeStronghold {

    static {
        // Misc
        ObjectAction.register(16675, 2418, 3417, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2418, 3416, 1));
        ObjectAction.register(16677, 2418, 3417, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2417, 3417, 0));
        ObjectAction.register(16675, 2416, 3445, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2415, 3446, 1));
        ObjectAction.register(16677, 2416, 3446, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2416, 3447, 0));
        ObjectAction.register(16675, 2401, 3449, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2400, 3450, 1));
        ObjectAction.register(16677, 2401, 3450, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2401, 3451, 0));
        ObjectAction.register(16675, 2395, 3474, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2396, 3476, 1));
        ObjectAction.register(16677, 2396, 3475, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2397, 3475, 0));
        ObjectAction.register(16675, 2376, 3489, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2378, 3489, 1));
        ObjectAction.register(16677, 2377, 3489, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2377, 3488, 0));
        ObjectAction.register(16675, 2396, 3501, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2396, 3500, 1));
        ObjectAction.register(16677, 2396, 3501, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2395, 3501, 0));
        ObjectAction.register(16675, 2384, 3506, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2384, 3505, 1));
        ObjectAction.register(16677, 2384, 3506, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2383, 3506, 0));
        ObjectAction.register(16675, 2389, 3512, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2388, 3513, 1));
        ObjectAction.register(16677, 2389, 3513, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2389, 3514, 0));
        ObjectAction.register(16675, 2398, 3512, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2397, 3513, 1));
        ObjectAction.register(16677, 2398, 3513, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2398, 3514, 0));
        ObjectAction.register(16675, 2440, 3404, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2440, 3403, 1));
        ObjectAction.register(16677, 2440, 3404, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2439, 3404, 0));
        ObjectAction.register(16675, 2420, 3471, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2421, 3473, 1));
        ObjectAction.register(16677, 2421, 3472, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2422, 3472, 0));
        ObjectAction.register(16675, 2413, 3488, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2412, 3489, 1));
        ObjectAction.register(16677, 2413, 3489, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2413, 3490, 0));
        ObjectAction.register(16675, 2417, 3490, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2418, 3492, 1));
        ObjectAction.register(16677, 2418, 3491, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2419, 3491, 0));
        ObjectAction.register(16675, 2455, 3417, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2457, 3417, 1));
        ObjectAction.register(16677, 2456, 3417, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2456, 3416, 0));
        ObjectAction.register(16675, 2461, 3416, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2460, 3417, 1));
        ObjectAction.register(16677, 2461, 3417, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2461, 3418, 0));
        ObjectAction.register(16675, 2475, 3400, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2475, 3399, 1));
        ObjectAction.register(16677, 2475, 3400, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2474, 3400, 0));
        ObjectAction.register(16675, 2485, 3402, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2485, 3401, 1));
        ObjectAction.register(16677, 2485, 3402, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2484, 3402, 0));
        ObjectAction.register(16675, 2488, 3407, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2489, 3409, 1));
        ObjectAction.register(16677, 2489, 3408, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2490, 3408, 0));
        ObjectAction.register(16675, 2479, 3408, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2479, 3407, 1));
        ObjectAction.register(16677, 2479, 3408, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2478, 3408, 0));
        // Bank
        ObjectAction.register(16675, 2444, 3414, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2445, 3416, 1));
        ObjectAction.register(16677, 2445, 3415, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2446, 3415, 0));
        ObjectAction.register(16675, 2445, 3434, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2445, 3433, 1));
        ObjectAction.register(16677, 2445, 3434, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2444, 3434, 0));
    }
}

package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Stairs;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/23/2021
 */
public class Burthorpe {

    static {
        // Main hall - Stairs down
        ObjectAction.register(4624, 2899, 3566, 0, "climb-down", (player, obj) -> player.getMovement().teleport(2207, 4938, 0));
        // Main hall - Stairs up
        ObjectAction.register(4626, 2897, 3566, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2898, 3569, 1));

        // Upstairs - Stairs down
        ObjectAction.register(4625, 2897, 3567, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2898, 3565, 0));

        // Games room - Stairs up
        ObjectAction.register(4622, 2207, 4935, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2899, 3565, 0));

        // Bar - Stairs up
        ObjectAction.register(15645, 2914, 3539, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2914, 3542, 1));

        // Bar - Stairs down
        ObjectAction.register(15648, 2914, 3540, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2914, 3538, 0));

        // Herblore house up
        ObjectAction.register(16671, 2898, 3428, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2898, 3427, 1));

        // Herblore house down
        ObjectAction.register(16673, 2898, 3428, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2898, 3427, 0));

        // Dwarf tunnel
        // Burthorpe down
        ObjectAction.register(57, 2876, 3480, 0, "climb-down", (player, obj) -> player.getMovement().teleport(2877, 9879, 0));

        // Burthorpe up
        ObjectAction.register(56, 2876, 9880, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2877, 3482, 0));

        // Catheryby down
        ObjectAction.register(55, 2820, 3484, 0, "climb-down", (player, obj) -> player.getMovement().teleport(2821, 9882, 0));

        // Catheryby up
        ObjectAction.register(54, 2820, 9883, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2821, 3486, 0));
    }
}

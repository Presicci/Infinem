package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/22/2023
 */
public class MosLeHarmlessCave {

    static {
        // Main entrance
        ObjectAction.register(3650, 3748, 2973, 0, "enter", (player, obj) -> player.getMovement().teleport(3748, 9373, 0));
        // Main exit
        ObjectAction.register(5553, 3749, 9373, 0, "exit", (player, obj) -> player.getMovement().teleport(3749, 2973, 0));
        // Back entrances
        ObjectAction.register(5270, 3814, 3062, 0, "climb-down", (player, obj) -> player.getMovement().teleport(3816, 9463, 0));
        ObjectAction.register(5270, 3829, 3062, 0, "climb-down", (player, obj) -> player.getMovement().teleport(3828, 9462, 0));
        // Back exits
        ObjectAction.register(5269, 3814, 9462, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3816, 3062, 0));
        ObjectAction.register(5269, 3829, 9462, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3831, 3062, 0));

    }
}

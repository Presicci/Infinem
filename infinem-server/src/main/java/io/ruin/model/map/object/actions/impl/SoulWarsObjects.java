package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/19/2023
 */
public class SoulWarsObjects {
    static {
        // To soul wars lobby
        ObjectAction.register(40474, 1, (player, obj) -> {
            player.getMovement().teleport(2206, 2857, player.getHeight());
        });
        // To edgeville
        ObjectAction.register(40476, 1, (player, obj) -> {
            player.getMovement().teleport(3082, 3476, player.getHeight());
        });
    }
}

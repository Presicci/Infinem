package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/19/2023
 */
public class SoulWarsObjects {
    static {
        // To soul wars lobby
        ObjectAction.register(40474, 1, (player, obj) -> {
            player.getMovement().teleport(2206, 2857, 0);
        });
        ObjectAction.register(40475, 1, (player, obj) -> {
            player.getMovement().teleport(2206, 2857, 0);
        });
        // To edgeville
        ObjectAction.register(40476, 2, (player, obj) -> {
            player.getMovement().teleport(3081, 3475, 0);
        });
        // To ferox
        ObjectAction.register(40476, 3, (player, obj) -> {
            player.getMovement().teleport(3158, 10027, 0);
        });

        ObjectAction.register(40476, 1, (player, obj) -> {
            player.dialogue(new OptionsDialogue("Where would you like to go?",
                            new Option("Edgeville.", () -> player.getMovement().teleport(3081, 3475)),
                            new Option("Ferox Enclave.", () -> player.getMovement().teleport(3158, 10027)),
                            new Option("Nowhere.")
                    )
            );
        });
    }
}

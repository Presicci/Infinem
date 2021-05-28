package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/25/2021
 */
public class Stairs {

    public static void registerStair(int objectId, Position position) {
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-up", (player, obj) -> climbUp(player));
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb-down", (player, obj) -> climbDown(player));
        ObjectAction.register(objectId, position.getX(), position.getY(), position.getZ(), "climb", (player, obj) -> {
            player.dialogue(
                    new OptionsDialogue("Climb up or down the stairs?",
                            new Option("Climb up the stairs.", () -> climbUp(player)),
                            new Option("Climb down the stairs.", () -> climbDown(player))
                    ));
        });
    }

    private static void climbUp(Player player) {
        player.getMovement().teleport(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ() + 1);
    }

    private static void climbDown(Player player) {
        player.getMovement().teleport(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ() + 1);
    }

    static {
        // Wizard's tower
        registerStair(12536, new Position(3103, 3159, 0));
        registerStair(12537, new Position(3103, 3159, 1));
        registerStair(12538, new Position(3104, 3160, 2));
    }
}

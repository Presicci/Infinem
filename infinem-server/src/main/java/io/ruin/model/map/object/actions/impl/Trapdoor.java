package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Trapdoor {

    public static void open(Player player, GameObject trapdoor, int openedId) {
        World.startEvent(event -> {
            trapdoor.setId(openedId);
            event.delay(300);
            trapdoor.setId(trapdoor.originalId);
        });

        player.sendMessage("You open the trapdoor.");
    }

    public static void climbDown(Player player, Position position) {
        player.sendMessage("You climb down through the trapdoor.");
        Ladder.climb(player, position, false, true, false);
    }

    private static void close(Player player, GameObject trapdoor) {
        trapdoor.setId(trapdoor.originalId);
        player.sendMessage("You close the trapdoor.");
    }

    public static void register() {

        /*
         * Edgeville dungeon
         */
        ObjectAction.register(1579, "open", (player, obj) -> open(player, obj, 1581));
        ObjectAction.register(1581, "climb-down", (player, obj) -> climbDown(player, new Position(3096, 9867)));
        ObjectAction.register(1581, "close", Trapdoor::close);

        /*
         * Rogues' Den
         */
        ObjectAction.register(7257, "enter", (player, obj) -> player.getMovement().teleport(3061, 4985, 1));
        ObjectAction.register(7258, "enter", (player, obj) -> player.getMovement().teleport(2906, 3537, 0));
    }

    static {
        register();
    }
}

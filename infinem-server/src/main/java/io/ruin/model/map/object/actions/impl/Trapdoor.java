package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public enum Trapdoor {
    CHAMPIONS_GUILD(10559, new Position(3190, 3355), new Position(3189, 9758, 0)),
    EDGEVILLE_DUNGEON(1581, new Position(3098, 3468), new Position(3096, 9867)),
    PATERDOMUS_DUNGEON(1581, new Position(3405, 3507), new Position(3405, 9906));

    private final int id;
    private final Position objectPos, destinationPos;

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
        Set<Integer> ids = new HashSet<>();
        for (Trapdoor trapdoor : values()) {
            ids.add(trapdoor.id);
        }
        for (int id : ids) {
            ObjectAction.register(id, "climb-down", (player, obj) -> {
                for (Trapdoor trapdoor : values()) {
                    if (obj.getPosition().equals(trapdoor.objectPos)) {
                        climbDown(player, trapdoor.destinationPos);
                    }
                }
            });
        }

        /*
         * Champion's guild
         */
        ObjectAction.register(10558, "open", (player, obj) -> open(player, obj, 10559));
        ObjectAction.register(10559, "close", Trapdoor::close);

        /*
         * Edgeville dungeon
         */
        ObjectAction.register(1579, "open", (player, obj) -> open(player, obj, 1581));
        ObjectAction.register(1581, "close", Trapdoor::close);

        /*
         * Ectofuntus
         */
        ObjectAction.register(16113, "open", (player, obj) -> open(player, obj, 16114));
        ObjectAction.register(16114, "climb-down", (player, obj) -> Ladder.climb(player, new Position(3669, 9888, 3), false, true, false));
        ObjectAction.register(16114, "close", Trapdoor::close);

        /*
         * Rogues' Den
         */
        ObjectAction.register(7257, "enter", (player, obj) -> player.getMovement().teleport(3061, 4985, 1));
        ObjectAction.register(7258, "enter", (player, obj) -> player.getMovement().teleport(2906, 3537, 0));

        // Evil dave's lair
        ObjectAction.register(12266, 1, (player, obj) -> player.getMovement().teleport(3077, 9893));
        ObjectAction.register(12265, "climb", (player, obj) -> player.getMovement().teleport(3078, 3493));

        /*
         * Mourner's tunnel
         */
        ObjectAction.register(8783, "open", (player, obj) -> {
            World.startEvent(e -> {
                obj.setId(8784);
                e.delay(2);
                obj.setId(8783);
            });
            climbDown(player, new Position(2044, 4649, 0));
        });
    }

    static {
        register();
    }
}

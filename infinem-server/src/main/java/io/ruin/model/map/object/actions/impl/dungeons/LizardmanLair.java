package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class LizardmanLair {

    public static void handlePosition30383(Player player) {
        if (player.getPosition().getX() == 1305 && player.getPosition().getY() == 9957) {
            Traveling.fadeTravel(player, new Position(1305, 9953, 0));
        } else {
            if (player.getPosition().getX() == 1318 && player.getPosition().getY() == 9960) {
                Traveling.fadeTravel(player, new Position(1318, 9956, 0));
            }
        }
    }

    public static void handlePosition30382(Player player) {
        if (player.getPosition().getX() == 1305 && player.getPosition().getY() == 9953) {
            Traveling.fadeTravel(player, new Position(1305, 9957, 0));
        } else {
            if (player.getPosition().getX() == 1318 && player.getPosition().getY() == 9956) {
                Traveling.fadeTravel(player, new Position(1318, 9960, 0));
            }
        }
    }

    public static void handlePosition30384(Player player) {
        if (player.getPosition().getX() == 1295 && player.getPosition().getY() == 9959) {
            Traveling.fadeTravel(player, new Position(1299, 9959, 0));
        } else {
            if (player.getPosition().getX() == 1319 && player.getPosition().getY() == 9966) {
                Traveling.fadeTravel(player, new Position(1323, 9966, 0));
            }
        }
    }

    public static void handlePosition30385(Player player) {
        if (player.getPosition().getX() == 1299 && player.getPosition().getY() == 9959) {
            Traveling.fadeTravel(player, new Position(1295, 9959, 0));
        } else {
            if (player.getPosition().getX() == 1323 && player.getPosition().getY() == 9966) {
                Traveling.fadeTravel(player, new Position(1319, 9966, 0));
            }
        }
    }

    static {
        ObjectAction.register(30383, "squeeze-through", (player, obj) -> handlePosition30383(player));
        ObjectAction.register(30383, "squeeze-through", (player, obj) -> handlePosition30383(player));

        ObjectAction.register(30382, "squeeze-through", (player, obj) -> handlePosition30382(player));
        ObjectAction.register(30382, "squeeze-through", (player, obj) -> handlePosition30382(player));

        ObjectAction.register(30384, "squeeze-through", (player, obj) -> handlePosition30384(player));
        ObjectAction.register(30384, "squeeze-through", (player, obj) -> handlePosition30384(player));

        ObjectAction.register(30385, "squeeze-through", (player, obj) -> handlePosition30385(player));
        ObjectAction.register(30385, "squeeze-through", (player, obj) -> handlePosition30385(player));

        // Entrance/Exit
        ObjectAction.register(30380, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(1305, 9973, 0)));
        ObjectAction.register(30381, "squeeze-through", (player, obj) -> Traveling.fadeTravel(player, new Position(1309, 3574, 0)));
    }
}


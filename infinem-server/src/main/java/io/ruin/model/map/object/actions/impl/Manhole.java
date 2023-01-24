package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/23/2023
 */
public class Manhole {

    private static final Position VARROCK = new Position(3237, 3458, 0),
    ARDY = new Position(2632, 3294, 0);

    /**
     * Has to be done this way because of how manholes work.
     */
    private static void teleport(Player player, GameObject gameObject) {
        if (gameObject.getPosition().equals(VARROCK)) {
            player.getMovement().teleport(3237, 9858, 0);
        } else if (gameObject.getPosition().equals(ARDY)) {
            player.getMovement().teleport(2632, 9695, 0);
        }
    }

    static {
        ObjectAction.register(881, "open", (player, obj) -> {
            obj.setId(882);
            player.sendFilteredMessage("You pull back the cover from over the manhole.");
        });
        ObjectAction.register(882, "close", (player, obj) -> {
            obj.setId(obj.originalId);
            player.sendFilteredMessage("You place the cover back over the manhole.");
        });
        ObjectAction.register(882, "climb-down", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(827);
            event.delay(1);
            teleport(player, obj);
            player.sendFilteredMessage("You climb down through the manhole.");
            player.unlock();
        }));
    }
}

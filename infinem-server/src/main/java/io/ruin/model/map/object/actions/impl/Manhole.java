package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/23/2023
 */
public class Manhole {

    static {
        ObjectAction.register(881, "open", (player, obj) -> {
            obj.setId(882);
            player.sendFilteredMessage("You pull back the cover from over the manhole.");
        });
        ObjectAction.register(882, "close", (player, obj) -> {
            obj.setId(obj.originalId);
            player.sendFilteredMessage("You place the cover back over the manhole.");
        });
        ObjectAction.register(882, 3237, 3458, 0, "climb-down", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getMovement().teleport(3237, 9858, 0);
            player.sendFilteredMessage("You climb down through the manhole.");
            player.unlock();
        }));
    }
}

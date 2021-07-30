package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 7/30/2021
 */
public class WildernessSlayerCave {

    static {
        // South exit
        ObjectAction.register(40389, 3384, 10050, 0, "exit", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getMovement().teleport(3260, 3663, 0);
            player.sendFilteredMessage("You exit the cave.");
            player.unlock();
        }));

        // South entrance
        ObjectAction.register(40388, 3259, 3664, 0, "walk-down", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getMovement().teleport(3385, 10052, 0);
            player.sendFilteredMessage("You walk down the stairs into the cave.");
            player.unlock();
        }));

        // North exit
        ObjectAction.register(40391, 3405, 10146, 0, "exit", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getMovement().teleport(3293, 3749, 0);
            player.sendFilteredMessage("You exit the cave.");
            player.unlock();
        }));

        // North entrance
        ObjectAction.register(40390, 3293, 3746, 0, "walk-down", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getMovement().teleport(3406, 10145, 0);
            player.sendFilteredMessage("You walk down the stairs into the cave.");
            player.unlock();
        }));
    }
}

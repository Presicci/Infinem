package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PassableDoor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2023
 */
public class ShiloVillage {

    static {
        // Climb over cart
        ObjectAction.register(2216, 2877, 2951, 0, 2, ((player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(839);
                e.delay(1);
                player.getMovement().teleport(player.getPosition().getX() > 2877 ? 2876 : 2880, 2952, 0);
                player.unlock();
            });
        }));
        // Front gate
        ObjectAction.register(2260, 2875, 2953, 0, "open", ((player, obj) -> PassableDoor.passDoor(player, obj, Direction.WEST, 0)));
        ObjectAction.register(2259, 2875, 2952, 0, "open", ((player, obj) -> PassableDoor.passDoor(player, obj, Direction.WEST, 2)));
        // Front wooden gate
        ObjectAction.register(2262, 2867, 2952, 0, "open", ((player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getMovement().teleport(player.getPosition().getX() == 2867 ? 2868 : 2867, 2952, 0);
                player.unlock();
            });
        }));
        ObjectAction.register(2261, 2867, 2953, 0, "open", ((player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getMovement().teleport(player.getPosition().getX() == 2867 ? 2868 : 2867, 2953, 0);
                player.unlock();
            });
        }));

        LoginListener.register(p -> {
            Config.SHILO_VILLAGE.set(p, 16);    // Sets up varp so that player can climb over cart
        });
    }
}

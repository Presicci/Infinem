package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/14/2022
 */
public class Slepe {

    static {
        ObjectAction.register(32637, 1, (player, obj) -> player.startEvent(event -> {
            player.getMovement().teleport(3738, 9703, 1);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        }));
    }
}

package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/30/2023
 */
public class Spade {

    static {
        ObjectAction.register(9662, "take", ((player, obj) -> {
            if (!player.getInventory().hasFreeSlots(1)) {
                player.sendMessage("You do not have enough inventory space to take this.");
                return;
            }
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getInventory().add(Items.SPADE);
                World.startEvent(event -> {
                    obj.setId(10626);
                    event.delay(50);
                    obj.restore();
                });
                player.unlock();
            });
        }));
    }
}

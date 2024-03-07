package io.ruin.model.map.object.actions.impl.locations.karamja;

import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/6/2024
 */
public class LeafyPalmTree {

    static {
        ObjectAction.register(2975, "shake", (player, obj) -> {
            player.lock();
            player.startEvent(e -> {
                player.animate(423);
                e.delay(1);
                player.getInventory().add(Items.PALM_LEAF);
                player.unlock();
            });
        });
    }
}

package io.ruin.model.map.object.actions.impl.zeah;

import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/10/2024
 */
public class ProtestBanners {

    static {
        ObjectAction.register(42466, 1, (player, obj) -> {
            if (player.getInventory().hasId(25822) || player.getEquipment().hasId(25822)) {
                player.dialogue(new PlayerDialogue("I've already taken one. Best leave some for the other protestors."));
                return;
            }
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.sendFilteredMessage("You take a banner.");
                player.getInventory().add(25822);
                player.unlock();
            });
        });
    }
}

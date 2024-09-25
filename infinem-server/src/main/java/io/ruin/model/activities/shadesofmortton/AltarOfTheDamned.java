package io.ruin.model.activities.shadesofmortton;

import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/24/2024
 */
public class AltarOfTheDamned {

    static {
        ItemObjectAction.register(25422, 41222, (player, item, obj) -> {
            Stat prayer = player.getStats().get(StatType.Prayer);
            if(prayer.currentLevel == prayer.fixedLevel) {
                player.sendMessage("You already have full prayer points.");
                return;
            }
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                item.remove();
                player.privateSound(2674);
                prayer.restore();
                player.sendMessage("You offer the bones to recharge your prayer.");
                player.unlock();
            });
        });
    }
}

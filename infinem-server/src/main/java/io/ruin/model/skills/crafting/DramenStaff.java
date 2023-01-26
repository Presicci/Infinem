package io.ruin.model.skills.crafting;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2023
 */
public class DramenStaff {

    static {
        ItemItemAction.register(Items.DRAMEN_BRANCH, Items.KNIFE, ((player, primary, secondary) -> {
            player.startEvent(e -> {
                if (player.getStats().get(StatType.Crafting).currentLevel < 31) {
                    player.sendMessage("You need a Crafting level of 31 to cut the branch.");
                    return;
                }
                player.animate(1248);
                e.delay(1);
                primary.setId(Items.DRAMEN_STAFF);
                player.sendMessage("You carefully cut the branch into a magical staff.");
            });
        }));
    }
}

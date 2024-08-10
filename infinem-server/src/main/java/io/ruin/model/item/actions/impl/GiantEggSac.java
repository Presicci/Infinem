package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/10/2024
 */
public class GiantEggSac {

    private static void openSac(Player player, Item sac) {
        sac.remove();
        player.getInventory().add(Items.RED_SPIDERS_EGGS_NOTE, 100);
        player.sendFilteredMessage("You slice open the sac and collect the eggs.");
    }

    static {
        ItemAction.registerInventory(23517, "cut-open", (player, item) -> {
            if (player.getInventory().hasId(Tool.KNIFE)) openSac(player, item);
            else player.sendMessage("You need a knife to cut open the egg sac.");
        });
        ItemAction.registerInventory(23517, "check-eggs", (player, item) -> player.sendMessage("The egg sac is full."));
        ItemItemAction.register(23517, Tool.KNIFE, (player, sac, secondary) -> openSac(player, sac));
    }
}

package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/24/2022
 */
public class Oyster {

    static {
        ItemAction.registerInventory(Items.OYSTER, "open", (player, item) -> {
            if (Random.rollDie(5)) {
                item.setId(Items.EMPTY_OYSTER);
                player.sendFilteredMessage("You crack open the oyster and find... nothing.");
            } else {
                item.setId(Items.OYSTER_PEARL);
                player.sendFilteredMessage("You crack open the oyster and find... a pearl!");
            }
        });
    }
}

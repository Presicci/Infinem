package io.ruin.model.item.actions.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/16/2021
 */
@AllArgsConstructor
public enum EmptyPot {

    POT_OF_FLOUR(1933);

    private final int id;

    static {
        for (EmptyPot pot : values()) {
            ItemAction.registerInventory(pot.id, "Empty", (player, item) -> {
                player.getInventory().remove(pot.id, 1);
                player.getInventory().add(Items.POT, 1);
                player.sendMessage("You empty the pot.");
            });
        }
    }
}

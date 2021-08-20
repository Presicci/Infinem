package io.ruin.model.item.actions.impl;

import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/20/2021
 */
public class Swamptoad {
    static {
        ItemAction.registerInventory(2150, "Remove-legs", (player, item) -> {
            player.getInventory().remove(2150, 1);
            player.getInventory().add(2152, 1);

        });
    }
}

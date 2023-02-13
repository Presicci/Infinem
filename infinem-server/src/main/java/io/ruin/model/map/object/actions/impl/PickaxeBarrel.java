package io.ruin.model.map.object.actions.impl;

import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/1/2023
 */
public class PickaxeBarrel {
    static {
        ObjectAction.register(41593, "take pickaxe", (player, obj) -> {
            if (!player.getInventory().hasFreeSlots(1)) {
                player.sendMessage("You don't have enough inventory space to take a pickaxe.");
                return;
            }
            if (player.getInventory().contains(Items.BRONZE_PICKAXE)) {
                player.sendMessage("You already have a pickaxe, no need to take another.");
                return;
            }
            player.animate(832);
            player.getInventory().add(Items.BRONZE_PICKAXE);
        });
    }
}

package io.ruin.model.map.object.actions.impl.varlamore;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/26/2024
 */
public class ChickenCoop {

    private static void search(Player player) {
        if (!player.getInventory().hasRoomFor(Items.EGG)) {
            player.sendMessage("You don't have enough inventory space to do that.");
            return;
        }
        player.animate(832);
        player.getInventory().addOrDrop(Items.EGG, 1);
    }

    static {
        ObjectAction.register(52822, "search", (player, obj) -> search(player));
    }
}

package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/2/2023
 */
public class Dung {

    private static void pickup(Player player, GameObject object) {
        if (!player.getInventory().contains(Items.BUCKET)) {
            player.sendMessage("You should really use a bucket for that.");
            return;
        }
        player.animate(827);
        player.getInventory().remove(Items.BUCKET, 1);
        player.getInventory().add(Items.UGTHANKI_DUNG);
        player.sendMessage("You very carefully scoop the dung up with the bucket.");
        if (!object.isRemoved())
            object.remove();
    }

    static {
        ObjectAction.register(6257, "pick-up", Dung::pickup);
        ItemObjectAction.register(1925, 6257, (player, item, obj) -> pickup(player, obj));
    }
}

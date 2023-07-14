package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
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

    private static void pickup(Player player, GameObject object, int productId, boolean removeForever) {
        if (!player.getInventory().contains(Items.BUCKET)) {
            player.sendMessage("You should really use a bucket for that.");
            return;
        }
        player.animate(827);
        player.getInventory().remove(Items.BUCKET, 1);
        player.getInventory().add(productId);
        player.sendMessage("You very carefully scoop the dung up with the bucket.");
        if (removeForever) {
            if (!object.isRemoved())
                object.remove();
        } else {
            World.startEvent(e -> {
                object.setId(-1);
                e.delay(15);
                object.restore();
            });
        }

    }

    static {
        ObjectAction.register(6257, "pick-up", (player, object) -> pickup(player, object, Items.UGTHANKI_DUNG, true));
        ObjectAction.register(33214, "pick-up", (player, object) -> pickup(player, object, 22590, false)); // Goat dung
        ItemObjectAction.register(1925, 6257, (player, item, obj) -> pickup(player, obj, Items.UGTHANKI_DUNG, true));
        ItemObjectAction.register(1925, 33214, (player, item, obj) -> pickup(player, obj, 22590, false));  // Goat dung
    }
}

package io.ruin.model.skills.smithing.blastfurnace;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/17/2024
 */
public class BlastFurnaceSink {

    private static void fill(Player player) {
        Item bucket = player.getInventory().findItem(Items.BUCKET);
        if (bucket == null) {
            player.sendMessage("You don't have any buckets to fill.");
            return;
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(832);
            e.delay(1);
            bucket.setId(Items.BUCKET_OF_WATER);
            player.sendMessage("You fill the bucket from the sink.");
            player.unlock();
        });
    }

    static {
        ObjectAction.register(9143, "fill-bucket", (player, obj) -> fill(player));
    }
}

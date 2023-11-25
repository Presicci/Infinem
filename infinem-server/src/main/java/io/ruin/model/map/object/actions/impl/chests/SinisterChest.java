package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/25/2023
 */
public class SinisterChest {

    public static final LootTable LOOT_TABLE = new LootTable().guaranteedItems(
                    new LootItem(Items.GRIMY_RANARR_WEED_NOTE, 3, 0),
                    new LootItem(Items.GRIMY_HARRALANDER_NOTE, 2, 0),
                    new LootItem(Items.GRIMY_IRIT_LEAF_NOTE, 1, 0),
                    new LootItem(Items.GRIMY_AVANTOE_NOTE, 1, 0),
                    new LootItem(Items.GRIMY_KWUARM_NOTE, 1, 0),
                    new LootItem(Items.GRIMY_TORSTOL_NOTE, 1, 0))
            .addTable(97,   // Nothing
                    new LootItem(-1, 1, 0)
            ).addTable(1,   // Toadflax
                    new LootItem(Items.GRIMY_TOADFLAX_NOTE, 2, 0)
            ).addTable(1, // Snapdragon
                    new LootItem(Items.GRIMY_SNAPDRAGON_NOTE, 2, 0)
            ).addTable(1, // Lantadyme
                    new LootItem(Items.GRIMY_LANTADYME_NOTE, 2, 0)
            );

    static {
        ObjectAction.register(377, "open", (player, obj) -> {
            Item sinisterKey = player.getInventory().findItem(Items.SINISTER_KEY);
            if (sinisterKey == null) {
                player.sendFilteredMessage("You need a sinister key to open this chest.");
                return;
            }

            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the chest with your key.");
                player.privateSound(51);
                player.animate(536);
                World.startEvent(e -> {
                    obj.setId(378);
                    e.delay(2);
                    obj.setId(obj.originalId);
                });
                sinisterKey.remove();
                List<Item> loot = LOOT_TABLE.rollItems(true);
                for(Item item : loot) {
                    if (item.getId() == -1) continue;
                    player.getInventory().addOrDrop(item.getId(), item.getAmount());
                }
                event.delay(1);
                player.unlock();
            });
        });
    }
}

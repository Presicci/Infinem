package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.List;

public class ElvenCrystalChest {

    public static final LootTable LOOT_TABLE = new LootTable().guaranteedItems(new LootItem(Items.UNCUT_DRAGONSTONE, 1, 0))
            .addTable(256,   // Crystal key halves
                    new LootItem(Items.COINS, 10000, 15000, 0),
                    new LootItem(Items.LOOP_HALF_OF_KEY, 1, 1),
                    new LootItem(Items.TOOTH_HALF_OF_KEY, 1, 1)
            ).addTable(128,   // Gems
                    new LootItem(Items.UNCUT_RUBY_NOTE, 8, 13, 0),
                    new LootItem(Items.UNCUT_DIAMOND_NOTE, 5, 8, 0)
            ).addTable(104,   // Crystal key
                    new LootItem(Items.CRYSTAL_KEY, 1, 0)
            ).addTable(80,   // Coins
                    new LootItem(Items.COINS, 30000, 50000, 0),
                    new LootItem(Items.CRYSTAL_SHARD, 8, 13, 0)
            ).addTable(68,   // Crystal shards
                    new LootItem(Items.CRYSTAL_SHARD, 20, 30, 0)
            ).addTable(68,   // Rune platelegs or plateskirt
                    new LootItem(Items.CRYSTAL_SHARD, 4, 6, 0),
                    new LootItem(Items.RUNE_PLATELEGS, 1, 1),
                    new LootItem(Items.RUNE_PLATESKIRT, 1, 1)
            ).addTable(68,   // Runes
                    new LootItem(Items.CHAOS_RUNE, 50, 100, 0),
                    new LootItem(Items.DEATH_RUNE, 50, 100, 0),
                    new LootItem(Items.COSMIC_RUNE, 50, 100, 0),
                    new LootItem(Items.NATURE_RUNE, 50, 100, 0),
                    new LootItem(Items.LAW_RUNE, 50, 100, 0)
            ).addTable(64,   // Yew seed
                    new LootItem(Items.YEW_SEED, 1, 0)
            ).addTable(64,   // Raw sharks
                    new LootItem(Items.RAW_SHARK_NOTE, 50, 100, 0)
            ).addTable(48,   // Gold ores
                    new LootItem(Items.GOLD_ORE_NOTE, 350, 500, 0)
            ).addTable(36,   // Runite ores
                    new LootItem(Items.RUNITE_ORE_NOTE, 7, 10, 0)
            ).addTable(28,   // Crystal acorn
                    new LootItem(23661, 1, 2, 0)
            ).addTable(12,   // Dragon items
                    new LootItem(Items.DRAGON_PLATELEGS, 1, 1),
                    new LootItem(Items.DRAGON_PLATESKIRT, 1, 1),
                    new LootItem(Items.SHIELD_LEFT_HALF, 1, 1)
            ).addTable(2,   // Dragonstone armour
                    new LootItem(24034, 1, 1),
                    new LootItem(24037, 1, 1),
                    new LootItem(24040, 1, 1),
                    new LootItem(24043, 1, 1),
                    new LootItem(24046, 1, 1)
            ).addTable(1,   // Onyx
                    new LootItem(Items.UNCUT_ONYX, 1, 0));

    static {
        ObjectAction.register(37342, 1, (player, obj) -> {
            Item crystalKey = player.getInventory().findItem(23951);
            if (crystalKey == null) {
                player.sendFilteredMessage("You need an enhanced crystal key to open this chest.");
                return;
            }

            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the chest with your key.");
                player.privateSound(51);
                player.animate(536);
                player.getInventory().remove(23951, 1);
                List<Item> loot = LOOT_TABLE.rollItems(true);
                for(Item item : loot) {
                    player.getInventory().addOrDrop(item.getId(), item.getAmount());
                    player.getCollectionLog().collect(item);
                }
                event.delay(1);
                player.unlock();
            });
        });
    }
}

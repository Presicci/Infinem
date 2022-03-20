package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;

public class CrystalChest {

    private static final LootTable FOOD = new LootTable()
            .addTable(34,   // Spinach Roll
                    new LootItem(Items.SPINACH_ROLL, 1, 0),
                    new LootItem(Items.COINS, 2000, 0)
            ).addTable(12,   // Runes
                    new LootItem(Items.AIR_RUNE, 50, 0),
                    new LootItem(Items.WATER_RUNE, 50, 0),
                    new LootItem(Items.EARTH_RUNE, 50, 0),
                    new LootItem(Items.FIRE_RUNE, 50, 0),
                    new LootItem(Items.BODY_RUNE, 50, 0),
                    new LootItem(Items.MIND_RUNE, 50, 0),
                    new LootItem(Items.CHAOS_RUNE, 10, 0),
                    new LootItem(Items.DEATH_RUNE, 10, 0),
                    new LootItem(Items.COSMIC_RUNE, 10, 0),
                    new LootItem(Items.NATURE_RUNE, 10, 0),
                    new LootItem(Items.LAW_RUNE, 10, 0)
            ).addTable(12,   // Gems
                    new LootItem(Items.RUBY, 2, 0),
                    new LootItem(Items.DIAMOND, 2, 0)
            ).addTable(12,   // Runite bars
                    new LootItem(Items.RUNITE_BAR, 3, 0)
            ).addTable(10,   // Crystalk key halves
                    new LootItem(Items.COINS, 750, 0),
                    new LootItem(Items.LOOP_HALF_OF_KEY, 1, 1),
                    new LootItem(Items.TOOTH_HALF_OF_KEY, 1, 1)
            ).addTable(10,   // Iron ores
                    new LootItem(Items.IRON_ORE_NOTE, 150, 0)
            ).addTable(10,   // Coal
                    new LootItem(Items.COAL_NOTE, 100, 0)
            ).addTable(8,   // Raw Swordfish
                    new LootItem(Items.COINS, 1000, 0),
                    new LootItem(Items.RAW_SWORDFISH, 5, 0)
            ).addTable(2,   // Adamant sq shield
                    new LootItem(Items.ADAMANT_SQ_SHIELD, 1, 0)
            ).addTable(12,   // Rune platelegs or plateskirt
                    new LootItem(Items.RUNE_PLATELEGS, 1, 1),
                    new LootItem(Items.RUNE_PLATESKIRT, 1, 1)
            ).addTable(17,   // "NOTHING"
                    new LootItem(Items.COINS, 5000, 0)
            );

    static {
        ObjectAction.register(172, "open", (player, obj) -> {
            Item crystalKey = player.getInventory().findItem(989);
            if (crystalKey == null) {
                player.sendFilteredMessage("You need a crystal key to open this chest.");
                return;
            }

            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the chest with your key.");
                player.privateSound(51);
                player.animate(536);
                World.startEvent(e -> {
                    obj.setId(173);
                    e.delay(2);
                    obj.setId(obj.originalId);
                });
                crystalKey.setId(1631); //dragonstone
                List<Item> loot = FOOD.rollItems(true);
                for(Item item : loot) {
                    player.getInventory().addOrDrop(item.getId(), item.getAmount());
                }
                event.delay(1);
                player.unlock();
            });
        });
    }
}

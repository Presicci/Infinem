package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public class LarransChest {

    private static final LootTable rareTable = new LootTable()
            .addTable(1,
                    new LootItem(24288, 1, 1),  // Hat
                    new LootItem(24291, 1, 1),  // Robe top
                    new LootItem(24294, 1, 1)   // Robe bottom
            );

    private static final LootTable bigTable = new LootTable()
            .addTable(1,
                    new LootItem(Items.UNCUT_DIAMOND_NOTE, 35, 45, 5),
                    new LootItem(Items.UNCUT_RUBY_NOTE, 35, 45, 5),
                    new LootItem(Items.COAL_NOTE, 450, 650, 5),
                    new LootItem(Items.GOLD_ORE_NOTE, 150, 250, 4),
                    new LootItem(Items.DRAGON_ARROWTIPS, 100, 250, 4),
                    new LootItem(Items.COINS, 75000, 175000, 3),
                    new LootItem(Items.IRON_ORE_NOTE, 500, 650, 3),
                    new LootItem(Items.RUNE_FULL_HELM_NOTE, 3, 5, 3),
                    new LootItem(Items.RUNE_PLATEBODY_NOTE, 2, 3, 3),
                    new LootItem(Items.RUNE_PLATELEGS_NOTE, 2, 3, 3),
                    new LootItem(Items.PURE_ESSENCE_NOTE, 4500, 7500, 3),
                    new LootItem(Items.RAW_LOBSTER_NOTE, 150, 525, 1),
                    new LootItem(Items.RAW_SHARK_NOTE, 150, 375, 1),
                    new LootItem(Items.RAW_MANTA_RAY_NOTE, 120, 240, 1),
                    new LootItem(Items.RUNITE_ORE_NOTE, 15, 20, 2),
                    new LootItem(Items.STEEL_BAR_NOTE, 350, 550, 2),
                    new LootItem(Items.MAGIC_LOGS_NOTE, 180, 220, 2),
                    new LootItem(Items.DRAGON_DART_TIP, 80, 200, 2),
                    new LootItem(Items.PALM_TREE_SEED, 3, 5, 1),
                    new LootItem(Items.MAGIC_SEED, 3, 4, 1),
                    new LootItem(22869, 3, 5, 1),   // Celastrus seed
                    new LootItem(22877, 3, 5, 1),   // Dragonfruit tree seed
                    new LootItem(22871, 1, 1),  // Redwood tree seed
                    new LootItem(Items.TORSTOL_SEED, 4, 6, 1),
                    new LootItem(Items.SNAPDRAGON_SEED, 4, 6, 1),
                    new LootItem(Items.RANARR_SEED, 4, 6, 1)
            );

    private static final LootTable smallTable = new LootTable()
            .addTable(1,
                    new LootItem(Items.UNCUT_DIAMOND_NOTE, 15, 25, 5),
                    new LootItem(Items.UNCUT_RUBY_NOTE, 20, 30, 5),
                    new LootItem(Items.COAL_NOTE, 282, 480, 5),
                    new LootItem(Items.GOLD_ORE_NOTE, 81, 179, 4),
                    new LootItem(Items.DRAGON_ARROWTIPS, 41, 182, 4),
                    new LootItem(Items.COINS, 40534, 114792, 3),
                    new LootItem(Items.IRON_ORE_NOTE, 300, 449, 3),
                    new LootItem(Items.RUNE_FULL_HELM_NOTE, 1, 3, 3),
                    new LootItem(Items.RUNE_PLATEBODY_NOTE, 1, 2, 3),
                    new LootItem(Items.RUNE_PLATELEGS_NOTE, 1, 2, 3),
                    new LootItem(Items.PURE_ESSENCE_NOTE, 3041, 5989, 3),
                    new LootItem(Items.RAW_LOBSTER_NOTE, 163, 342, 1),
                    new LootItem(Items.RAW_SHARK_NOTE, 126, 250, 1),
                    new LootItem(Items.RAW_MANTA_RAY_NOTE, 81, 168, 1),
                    new LootItem(Items.RUNITE_ORE_NOTE, 5, 10, 2),
                    new LootItem(Items.STEEL_BAR_NOTE, 253, 450, 2),
                    new LootItem(Items.MAGIC_LOGS_NOTE, 80, 120, 2),
                    new LootItem(Items.DRAGON_DART_TIP, 31, 149, 2),
                    new LootItem(Items.PALM_TREE_SEED, 1, 3, 1),
                    new LootItem(Items.MAGIC_SEED, 1, 2, 1),
                    new LootItem(22869, 1, 3, 1),   // Celastrus seed
                    new LootItem(22877, 1, 3, 1),   // Dragonfruit tree seed
                    new LootItem(22871, 1, 1),  // Redwood tree seed
                    new LootItem(Items.TORSTOL_SEED, 2, 4, 1),
                    new LootItem(Items.SNAPDRAGON_SEED, 2, 4, 1),
                    new LootItem(Items.RANARR_SEED, 2, 4, 1)
            );

    private static final int CHEST = 34831;
    private static final int BIGCHEST = 34832;

    private static final int LARRAN_KEY_ID = 23490;

    static {
        ObjectAction.register(CHEST, 1, ((player, obj) -> openChest(player, false)));
        ObjectAction.register(BIGCHEST, 1, ((player, obj) -> openChest(player, true)));
    }

    private static void openChest(Player player, boolean big) {
        Item larrenKey = player.getInventory().findItem(LARRAN_KEY_ID);
        if (larrenKey == null) {
            player.sendFilteredMessage("You need Larran's key to open this chest.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You unlock the chest with your key.");
            larrenKey.remove(1);
            player.animate(536);
            Item loot = big ? bigTable.rollItem() : smallTable.rollItem();
            player.getInventory().addOrDrop(loot);
            if (big && Random.rollDie(256)) {
                loot = rareTable.rollItem();
                player.getInventory().addOrDrop(loot);
                player.getTaskManager().doUnlockItemLookup(loot);
                player.getTaskManager().doDropGroupLookup(loot.getDef().name.toLowerCase());
            }
            event.delay(1);
            player.unlock();
        });
    }
}

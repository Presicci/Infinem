package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/18/2023
 * <a href="https://oldschool.runescape.wiki/w/Brimstone_chest">Wiki link</a>
 */
public class BrimstoneChest {

    private static final int BRIMSTONE_KEY = 23083;

    private static final PlayerCounter chestCounter = PlayerCounter.BRIMSTONE_CHESTS_OPENED;

    public static final LootTable LOOT_TABLE = new LootTable().addTable(0,
            new LootItem(Items.UNCUT_DIAMOND_NOTE, 25, 35, 80),
            new LootItem(Items.UNCUT_RUBY_NOTE, 25, 35, 80),
            new LootItem(Items.COAL_NOTE, 300, 500, 80),
            new LootItem(Items.COINS, 50000, 150000, 80),
            new LootItem(Items.GOLD_ORE_NOTE, 100, 200,64),
            new LootItem(Items.DRAGON_ARROWTIPS, 50, 200, 64),
            new LootItem(Items.IRON_ORE_NOTE, 350, 500, 48),
            new LootItem(Items.RUNE_FULL_HELM_NOTE, 2, 4, 48),
            new LootItem(Items.RUNE_PLATEBODY_NOTE, 1, 2, 48),
            new LootItem(Items.RUNE_PLATELEGS_NOTE, 1, 2, 48),
            new LootItem(Items.RAW_TUNA_NOTE, 80, 350, 48), // RAW FOOD ROLL
            new LootItem(Items.RUNITE_ORE_NOTE, 10, 15, 32),
            new LootItem(Items.STEEL_BAR_NOTE, 300, 500, 32),
            new LootItem(Items.MAGIC_LOGS_NOTE, 120, 160, 32),
            new LootItem(Items.DRAGON_DART_TIP, 40, 160, 32),
            new LootItem(Items.PALM_TREE_SEED, 2, 4, 16),
            new LootItem(Items.MAGIC_SEED, 2, 4, 16),
            new LootItem(22869, 2, 4, 16),
            new LootItem(22877, 1, 4, 16),
            new LootItem(22871, 1, 1, 16),
            new LootItem(Items.TORSTOL_SEED, 3, 5, 16),
            new LootItem(Items.SNAPDRAGON_SEED, 3, 5, 16),
            new LootItem(Items.RANARR_SEED, 3, 5, 16),
            new LootItem(Items.PURE_ESSENCE_NOTE, 3000, 6000, 16),
            new LootItem(22963, 1, 1, 5),
            new LootItem(23047, 1, 1, 1),
            new LootItem(23050, 1, 1, 1),
            new LootItem(23053, 1, 1, 1),
            new LootItem(23056, 1, 1, 1),
            new LootItem(23059, 1, 1, 1)
    );

    private static Item rollRawFood(Player player) {
        int fishingLevel = player.getStats().get(StatType.Fishing).currentLevel;
        if (fishingLevel >= 81) {
            return new Item(Items.RAW_MANTA_RAY_NOTE, Random.get(80, 160));
        }
        if (fishingLevel == 80) {
            return new Item(Items.RAW_SEA_TURTLE_NOTE, Random.get(80, 200));
        }
        if (fishingLevel >= 76) {
            return new Item(Items.RAW_SHARK_NOTE, Random.get(100, 250));
        }
        if (fishingLevel >= 62) {
            return new Item(Items.RAW_MONKFISH_NOTE, Random.get(100, 300));
        }
        if (fishingLevel >= 50) {
            return new Item(Items.RAW_SWORDFISH_NOTE, Random.get(100, 300));
        }
        if (fishingLevel >= 40) {
            return new Item(Items.RAW_LOBSTER_NOTE, Random.get(100, 350));
        }
        return new Item(Items.RAW_TUNA_NOTE, Random.get(100, 350));
    }

    private static void openChest(Player player) {
        Item brimstoneKey = player.getInventory().findItem(BRIMSTONE_KEY);
        if (brimstoneKey == null) {
            player.sendFilteredMessage("You need a Brimstone key to open the chest.");
            return;
        }
        if (player.getInventory().getFreeSlots() == 0 && brimstoneKey.getAmount() > 1) {
            player.sendMessage("You need a free inventory slot to open the chest!");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You unlock the chest with your key.");
            brimstoneKey.remove(1);
            player.animate(536);
            Item loot = LOOT_TABLE.rollItem();
            if (loot.getId() == Items.RAW_TUNA_NOTE)
                loot = rollRawFood(player);
            player.getInventory().addOrDrop(loot);
            player.getCollectionLog().collect(loot);
            chestCounter.increment(player, 1);
            event.delay(1);
            player.unlock();
        });
    }

    static {
        ObjectAction.register(34662, 1, (player, obj) -> {
            openChest(player);
        });
        ObjectAction.register(34662, 2, (player, obj) -> {
            player.sendMessage("Your Brimstone chest count is: " + chestCounter.get(player));
        });
    }
}

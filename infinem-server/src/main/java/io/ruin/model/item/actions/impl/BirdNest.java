package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

public enum BirdNest {

    /* Egg */
    RED_EGG(5070, 5076),
    GREEN_EGG(5071, 5078),
    BLUE_EGG(5072, 5077),

    /* Ring */
    RING(5074, -1),

    /* Seeds */
    SEED_ONE(5073, -1),
    SEED_TWO(7413, -1),
    SEED_THREE(13653, -1);

    public final int itemID, result;

    BirdNest(int itemID, int result) {
        this.result = result;
        this.itemID = itemID;
    }

    public static final LootTable RING_LOOT = new LootTable().addTable(1,
            new LootItem(1635, 1, 40), //Gold ring
            new LootItem(1637, 1, 30), //Sapphire ring
            new LootItem(1639, 1, 20),  //Emerald ring
            new LootItem(1641, 1, 9),  //Ruby ring
            new LootItem(1643, 1, 1)   //Diamond ring
    );

    public static final LootTable SEED_LOOT = new LootTable().addTable(1,
            new LootItem(5312, 1, 1000), //Acorn
            new LootItem(5283, 1, 800), //Apple tree seed
            new LootItem(5313, 1, 700), //Willow seed
            new LootItem(5284, 1, 600), //Banana tree seed
            new LootItem(5285, 1, 500), //Orange tree seed
            new LootItem(5286, 1, 300), //Curry tree seed
            new LootItem(5314, 1, 250),  //Maple seed
            new LootItem(5287, 1, 200),  //Pineapple seed
            new LootItem(5288, 1, 150),  //Papaya tree seed
            new LootItem(5315, 1, 130),  //Yew seed
            new LootItem(5289, 1, 110),  //Palm tree seed
            new LootItem(5290, 1, 100),  //Calquat tree seed
            new LootItem(5317, 1, 50),   //Spirit seed
            new LootItem(22877, 1, 30),  // Dragonfruit tree seed
            new LootItem(5316, 1, 35),  //Magic seed
            new LootItem(21486, 1, 20),  // Teak seed
            new LootItem(21488, 1, 20),  // Mahogany seed
            new LootItem(22869, 1, 15),  // Celastrus seed
            new LootItem(22871, 1, 10)  // Redwood tree seed
    );

    public static final LootTable WYSON_SEED_LOOT = new LootTable().addTable(1,
            new LootItem(Items.SWEETCORN_SEED, 6, 1020),
            new LootItem(Items.STRAWBERRY_SEED, 6, 1000),
            new LootItem(Items.ACORN, 1, 800),
            new LootItem(Items.LIMPWURT_SEED, 2, 800),
            new LootItem(Items.WATERMELON_SEED, 2, 700),
            new LootItem(22879, 2, 400),  // Snape grass seed
            new LootItem(Items.LANTADYME_SEED, 1, 300),
            new LootItem(Items.DWARF_WEED_SEED, 1, 300),
            new LootItem(Items.CADANTINE_SEED, 1, 240),
            new LootItem(21486, 1, 200),  // Teak seed
            new LootItem(21488, 1, 200),  // Mahogany seed
            new LootItem(Items.WILLOW_SEED, 1, 160),
            new LootItem(Items.PINEAPPLE_SEED, 1, 160),
            new LootItem(Items.CALQUAT_TREE_SEED, 1, 120),
            new LootItem(Items.PAPAYA_TREE_SEED, 1, 100),
            new LootItem(Items.MAPLE_SEED, 1, 60),
            new LootItem(Items.TORSTOL_SEED, 1, 40),
            new LootItem(Items.RANARR_SEED, 1, 40),
            new LootItem(Items.SNAPDRAGON_SEED, 1, 40),
            new LootItem(Items.YEW_SEED, 1, 40),
            new LootItem(Items.SPIRIT_SEED, 1, 40),
            new LootItem(Items.PALM_TREE_SEED, 1, 20),
            new LootItem(22877, 1, 20),  // Dragonfruit tree seed
            new LootItem(Items.MAGIC_SEED, 1, 20),
            new LootItem(22869, 1, 10),  // Celastrus seed
            new LootItem(22871, 1, 10)  // Redwood tree seed
    );

    private static void openNest(Player player, Item item, Item reward, String descriptiveName) {
        if (player.getInventory().isFull()) {
            player.sendMessage("You don't have enough inventory space to do that.");
            return;
        }
        item.setId(5075);
        player.getInventory().add(reward);
        PlayerCounter.OPENED_BIRDS_NESTS.increment(player, 1);
        player.sendMessage("You take " + descriptiveName + " out of the bird's nest.");
    }

    public static int getRandomNest() {
        int randomNest = Random.get(BirdNest.values().length - 1);
        return BirdNest.values()[randomNest].itemID;
    }

    static {
        for (BirdNest nest : values()) {
            if (nest.result != -1) {
                ItemAction.registerInventory(nest.itemID, "search", (player, item) -> {
                    Item reward = new Item(nest.result, 1);
                    openNest(player, item, reward, reward.getDef().descriptiveName.toLowerCase());
                });
            } else {
                ItemAction.registerInventory(nest.itemID, "search", (player, item) -> {
                    Item reward = nest == RING ? RING_LOOT.rollItem() : SEED_LOOT.rollItem();
                    openNest(player, item, reward, reward.getDef().descriptiveName.toLowerCase());
                });
            }
        }
        ItemAction.registerInventory(22800, "search", (player, item) -> {
            Item reward = WYSON_SEED_LOOT.rollItem();
            openNest(player, item, reward, reward.getDef().descriptiveName.toLowerCase());
        });
    }
}

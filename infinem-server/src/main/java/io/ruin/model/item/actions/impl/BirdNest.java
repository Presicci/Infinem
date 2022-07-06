package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.woodcutting.Tree;

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

    public static final LootTable ringNest = new LootTable().addTable(1,
            new LootItem(1635, 1, 40), //Gold ring
            new LootItem(1637, 1, 30), //Sapphire ring
            new LootItem(1639, 1, 20),  //Emerald ring
            new LootItem(1641, 1, 9),  //Ruby ring
            new LootItem(1643, 1, 1)   //Diamond ring
    );

    public static final LootTable seedNest = new LootTable().addTable(1,
            new LootItem(5312, 1, 100), //Acorn
            new LootItem(5283, 1, 80), //Apple tree seed
            new LootItem(5313, 1, 70), //Willow seed
            new LootItem(5284, 1, 60), //Banana tree seed
            new LootItem(5285, 1, 50), //Orange tree seed
            new LootItem(5286, 1, 30), //Curry tree seed
            new LootItem(5314, 1, 25),  //Maple seed
            new LootItem(5287, 1, 20),  //Pineapple seed
            new LootItem(5288, 1, 15),  //Papaya tree seed
            new LootItem(5315, 1, 13),  //Yew seed
            new LootItem(5289, 1, 11),  //Palm tree seed
            new LootItem(5290, 1, 10),  //Calquat tree seed
            new LootItem(5317, 1, 5),   //Spirit seed
            new LootItem(22877, 1, 3),  // Dragonfruit tree seed
            new LootItem(5316, 1, 3),  //Magic seed
            new LootItem(21486, 1, 2),  // Teak seed
            new LootItem(21488, 1, 2),  // Mahogany seed
            new LootItem(22869, 1, 2),  // Celastrus seed
            new LootItem(22871, 1, 1)  // Redwood tree seed
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
                    Item reward = nest == RING ? ringNest.rollItem() : seedNest.rollItem();
                    openNest(player, item, reward, reward.getDef().descriptiveName.toLowerCase());
                });
            }
        }
    }
}

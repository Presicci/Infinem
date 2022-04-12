package io.ruin.model.skills.farming.farming_contracts;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/4/2021
 */
public class SeedPack {
    //0-6 t1, 7-14 t2, 15-23 t3, 24-33 t4, 34-44 t5
    private static final int[] tierIndices = {0, 7, 15, 24, 34};

    private static final LootItem[] lowSeeds = {
            // Allotment
            new LootItem(5318, 8, 12, 2),   // Potato
            new LootItem(5319, 8, 12, 2),   // Onion
            new LootItem(5324, 8, 12, 2),   // Cabbage
            new LootItem(5322, 8, 12, 2),   // Tomato
            new LootItem(5320, 8, 12, 2),   // Sweetcorn
            new LootItem(5323, 8, 12, 2),    // Strawberry

            // Hops
            new LootItem(5305, 8, 14, 2),     // Barley
            new LootItem(5307, 6, 8, 2),     // Hammerstone
            new LootItem(5308, 6, 8, 2),     // Asgarnian
            new LootItem(5306, 8, 12, 2),     // Jute
            new LootItem(5309, 6, 8, 2),     // Yanillian seed
            new LootItem(5310, 6, 8, 2),      // Krandorian

            // Trees
            new LootItem(5312, 3, 5, 2),      // Acorn

            // Fruit
            new LootItem(5283, 3, 5, 2),      // Apple
            new LootItem(5284, 3, 5, 2),      // Banana
            new LootItem(5285, 3, 5, 2),      // Orange
            new LootItem(5286, 3, 5, 2),      // Curry

            // Bushes
            new LootItem(5101, 6, 8, 2),     // Redberry
            new LootItem(5102, 6, 8, 2),     // Cadavaberry
            new LootItem(5103, 6, 8, 2),     // Dewllberry
            new LootItem(5104, 6, 8, 2),      // Jangerberry

            // Flower
            new LootItem(5096, 8, 12, 2),     // Marigold
            new LootItem(5098, 8, 12, 2),     // Nasturtium
            new LootItem(5097, 8, 12, 2),     // Rosemary
            new LootItem(5099, 8, 12, 2),     // Woad

            // Herb
            new LootItem(5291, 3, 5, 2),     // Guam
            new LootItem(5292, 3, 5, 2),     // Marrentill
            new LootItem(5293, 3, 5, 2),     // Tarromin
            new LootItem(5294, 3, 5, 2),     // Harralander

            // Special
            new LootItem(5281, 4, 6, 1),   // Belladonna
            new LootItem(5282, 4, 6, 1)    // Mushroom
    };

    private static final LootItem[] mediumSeeds = {
            // Allotment
            new LootItem(5321, 8, 12, 2),   // Watermelon
            new LootItem(22879, 6, 8, 2),   // Snape grass;

            // Hops
            new LootItem(5311, 8, 12, 2),       // Wildblood

            // Trees
            new LootItem(5313, 2, 4, 1),      // Willow
            new LootItem(21486, 1, 3, 1),     // Teak

            // Fruit
            new LootItem(5287, 3, 5, 1),      // Pineapple

            // Bushes
            new LootItem(5105, 6, 8, 2),      // Whiteberry
            new LootItem(5106, 6, 8, 2),      // Poison ivy

            // Flower
            new LootItem(5100, 4, 8, 3),       // Limpwurt

            // Herb
            new LootItem(5296, 1, 3, 1),      // Toadflax
            new LootItem(5297, 2, 6, 3),      // Irit
            new LootItem(5298, 1, 3, 1),      // Avantoe
            new LootItem(5299, 1, 3, 1),      // Kwuarm
            new LootItem(5301, 1, 3, 1),       // Cadantine seed
            new LootItem(5302, 1, 3, 1),       // Lantadyme
            new LootItem(5303, 1, 3, 1),       // Dwarf weed

            // Special
            new LootItem(5280, 2, 6, 2),     // Cactus
            new LootItem(22873, 2, 6, 2),    // Potato cactus
            new LootItem(5290, 3, 6, 1)     // Calquat
    };

    private static final LootItem[] highSeeds = {
            // Trees
            new LootItem(21488, 1, 2, 3),     // Mahogany
            new LootItem(5314, 1, 2, 3),      // Maple
            new LootItem(5315, 1, 3),      // Yew
            new LootItem(5316, 1, 1),      // Magic

            // Fruit
            new LootItem(5288, 1, 3, 4),      // Papaya
            new LootItem(5289, 1, 2, 4),      // Palm
            new LootItem(22877, 1, 3),      // Palm

            // Herb
            new LootItem(5295, 1, 2, 3),      // Ranarr
            new LootItem(5300, 1, 3),      // Snapdragon
            new LootItem(5304, 1, 2),        // Torstol

            // Special
            new LootItem(22871, 1, 1),       // Redwood
            new LootItem(5317, 1, 1),       // Spirit
            new LootItem(22869, 1, 2),      // Celastrus
            new LootItem(22875, 1, 4)   // Hespori
    };

    private static final LootTable low = new LootTable().addTable(5, lowSeeds);
    private static final LootTable medium = new LootTable().addTable(5, mediumSeeds);
    private static final LootTable high = new LootTable().addTable(5, highSeeds);

    public static Item createSeedPack(int tier) {
        int charges = 5 + tier;
        int index = tierIndices[tier - 1];
        Item seedPack = new Item(22993);
        AttributeExtensions.setCharges(seedPack, charges + index);
        return seedPack;
    }

    private static void openSeedPack(Player player, Item item) {
        if (!AttributeExtensions.hasAttribute(item, AttributeTypes.CHARGES) || AttributeExtensions.getCharges(item) <= 0) {
            item.remove();
            return;
        }
        int charges = AttributeExtensions.getCharges(item);
        int tier = charges > tierIndices[4] ? 5 : charges > tierIndices[3] ? 4 : charges > tierIndices[2] ? 3 : charges > tierIndices[1] ? 2 : 1;
        int chargesRemaining = charges - tierIndices[tier - 1];
        LootTable seedTable = low;
        if ((chargesRemaining > 6 && Random.rollDie(11))
                || (chargesRemaining > 7 && Random.rollDie(5))
                || (chargesRemaining > 8 && Random.rollDie(3))
                || (chargesRemaining > 9)) {
            seedTable = high;
        } else if ((chargesRemaining > 3 && Random.rollDie(6))
                || (chargesRemaining > 4 && Random.rollDie(5))
                || (chargesRemaining > 5 && Random.rollDie(3))
                || (chargesRemaining > 6)) {
            seedTable = medium;
        }
        player.getInventory().addOrDrop(seedTable.rollItem());
        if ((charges - 1) == tierIndices[tier - 1]) {   // Ran out of charges
            item.remove();
        } else {    // Decrement charges by 1
            AttributeExtensions.setCharges(item, charges - 1);
        }
    }

    private static void checkSeedPack(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        int tier = charges > tierIndices[4] ? 5 : charges > tierIndices[3] ? 4 : charges > tierIndices[2] ? 3 : charges > tierIndices[1] ? 2 : 1;
        int chargesRemaining = charges - tierIndices[tier - 1];
        player.sendMessage("This seed pack has <col=ff0000>" + chargesRemaining + "</col> seeds left in it.");
    }

    static {
        ItemAction.registerInventory(22993, "take", SeedPack::openSeedPack);
        ItemAction.registerInventory(22993, "check", SeedPack::checkSeedPack);
    }
}

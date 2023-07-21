package io.ruin.model.activities.combat.bosses.hespori;

import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/30/2022
 */
public class HesporiLoot {

    /*
     * Each table is rolled once when the patch is cleared.
     */

    public static final LootTable ANIMA_SEEDS = new LootTable().addTable(1,
            new LootItem(22881, 1, 1),  // Attas seed
            new LootItem(22883, 1, 1),  // Iasor seed
            new LootItem(22885, 1, 1)   // Kronos seed
    );

    public static final LootTable NORMAL_SEEDS = new LootTable()
            .addTable(14,   // Pre roll
                    new LootItem(22994, 1, 1, 2860)   //Bottomless compost bucket
            )
            .addTable(24,   // Allotment seeds
                    new LootItem(5321, 10, 20, 2500),   //Watermelon seed
                    new LootItem(22879, 6, 16, 2500)   //Snape grass seed
            )
            .addTable(43,    // Flower seeds
                    new LootItem(22887, 8, 18, 6250),   //White lily seed
                    new LootItem(5100, 6, 14, 2500)   //Limpwurt seed
            )
            .addTable(12,   // Hop seeds
                    new LootItem(5311, 10, 20, 2500)   //Wildblood seed
            )
            .addTable(24,   // Bush seeds
                    new LootItem(5105, 10, 16, 2500),   //Whiteberry seed
                    new LootItem(5106, 8, 16, 2500)   //Poison ivy seed
            )
            .addTable(166,  // Herb seeds
                    new LootItem(5297, 2, 8, 3750),   //Irit seed
                    new LootItem(5298, 2, 5, 3750),   //Avantoe seed
                    new LootItem(5299, 2, 5, 3750),   //Kwuarm seed
                    new LootItem(5296, 2, 5, 3750),   //Toadflax seed
                    new LootItem(5301, 2, 5, 3750),   //Cadantine seed
                    new LootItem(5302, 2, 5, 3750),   //Lantadyme seed
                    new LootItem(5303, 2, 5, 3750),   //Dwarf weed seed
                    new LootItem(5295, 1, 2, 2500),   //Ranarr seed
                    new LootItem(5300, 1, 1, 2500),   //Snapdragon seed
                    new LootItem(5304, 1, 1, 2500)   //Torstol seed
            )
            .addTable(61,   // Tree seeds
                    new LootItem(5314, 2, 4, 5000),   //Maple seed
                    new LootItem(5313, 2, 5, 3750),   //Willow seed
                    new LootItem(5315, 1, 1, 2500),   //Yew seed
                    new LootItem(5316, 1, 1, 1250)   //Magic seed
            )
            .addTable(67,   // Fruit tree seeds
                    new LootItem(5287, 3, 6, 3750),   //Pineapple seed
                    new LootItem(5288, 1, 3, 3750),   //Papaya tree seed
                    new LootItem(5289, 1, 3, 3750),   //Palm tree seed
                    new LootItem(22877, 1, 1, 2500)   //Dragonfruit tree seed
            )
            .addTable(92,   // Special seeds
                    new LootItem(21486, 2, 5, 5000),   //Teak seed
                    new LootItem(21488, 1, 3, 3750),   //Mahogany seed
                    new LootItem(5280, 4, 14, 2500),   //Cactus seed
                    new LootItem(22873, 4, 14, 2500),   //Potato cactus seed
                    new LootItem(22869, 1, 1, 2500),   //Celastrus seed
                    new LootItem(5317, 1, 1, 1250),   //Spirit seed
                    new LootItem(22871, 1, 1, 1250)   //Redwood tree seed
            );
}

package io.ruin.model.item.loot;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.WebTable;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import lombok.AllArgsConstructor;

import java.text.DecimalFormat;
import java.util.*;

public class LootTable {

    @Expose public LootItem[] guaranteed;

    @Expose public List<ItemsTable> tables;

    public double totalWeight;

    /**
     * Methods used for creating tables @ runtime.
     */

    public LootTable guaranteedItems(LootItem... items) {
        guaranteed = items;
        return this;
    }
    public List<LootItem> getLootItems() {
        List<LootItem> items = Lists.newArrayList();
        for (ItemsTable table : tables) {
            items.addAll(Arrays.asList(table.items));
        }
        return items;
    }

    public LootTable addTable(int tableWeight, LootItem... tableItems) {
        return addTable(null, tableWeight, tableItems);
    }

    public LootTable addTable(String tableName, int tableWeight, LootItem... tableItems) {
        if(tables == null)
            tables = new ArrayList<>();
        tables.add(new ItemsTable(tableName, tableWeight, tableItems));
        if (tableName == null || !tableName.equalsIgnoreCase("tertiary")) {
            totalWeight += tableWeight;
        }
        return this;
    }

    /**
     * Methods pretty much specifically for npc drop tables.
     */

    public LootTable combine(LootTable table) {
        LootTable newTable = new LootTable();

        List<LootItem> newGuaranteed = new ArrayList<>();
        if(guaranteed != null)
            Collections.addAll(newGuaranteed, guaranteed);
        if(table.guaranteed != null)
            Collections.addAll(newGuaranteed, table.guaranteed);
        newTable.guaranteed = newGuaranteed.isEmpty() ? null : newGuaranteed.toArray(new LootItem[0]);

        List<ItemsTable> newTables = new ArrayList<>();
        if(tables != null)
            newTables.addAll(tables);
        if(table.tables != null)
            newTables.addAll(table.tables);
        newTable.tables = newTables.isEmpty() ? null : newTables;

        return newTable;
    }

    public void calculateWeight() {
        totalWeight = 0;
        if(tables != null) {
            for(ItemsTable table : tables) {
                if (!table.name.equalsIgnoreCase("tertiary")) {
                    totalWeight += table.weight;
                }
                table.totalWeight = 0;
                if(table.items != null) {
                    for(LootItem item : table.items)
                        table.totalWeight += item.weight;
                }
            }
        }
    }

    /**
     * Item selection
     */

    public Item rollItem() {
        List<Item> items = rollItems(false);
        return items == null ? null : items.get(0);
    }

    public List<Item> rollItems(boolean allowGuaranteed) {
        List<Item> items;
        if(allowGuaranteed && guaranteed != null) {
            items = new ArrayList<>(guaranteed.length + 1);
            for(LootItem item : guaranteed)
                items.add(item.toItem());
        } else {
            items = new ArrayList<>(1);
        }
        if(tables != null) {
            double tableRand = Random.get() * totalWeight;
            for(ItemsTable table : tables) {
                if((tableRand -= table.weight) <= 0) {
                    if(table.items != null) {
                        double itemsRand = Random.get() * table.totalWeight;
                        for(LootItem item : table.items) {
                            if(item.weight == 0) {
                                /* weightless item landed, add it and continue loop */
                                items.add(item.toItem());
                                continue;
                            }
                            if((itemsRand -= item.weight) <= 0) {
                                /* weighted item landed, add it and break loop */
                                items.add(item.toItem());
                                break;
                            }
                        }
                    } else {
                        for (CommonTables cTable : CommonTables.values()) {
                            if (cTable.title.equalsIgnoreCase(table.name)) {
                                double itemsRand = Random.get() * cTable.totalWeight;
                                for(LootItem item : cTable.items) {
                                    if(item.weight == 0) {
                                        /* weightless item landed, add it and continue loop */
                                        items.add(item.toItem());
                                        continue;
                                    }
                                    if((itemsRand -= item.weight) <= 0) {
                                        /* weighted item landed, add it and break loop */
                                        items.add(item.toItem());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return items.isEmpty() ? null : items;
    }

    public List<Item> allItems() {
        List<Item> items = new ArrayList<>();
        if(guaranteed != null) {
            for(LootItem item : guaranteed) {
                if (item != null)
                items.add(item.toItem());
            }
        }
        if(tables != null) {
            for(ItemsTable table : tables) {
                if(table.items != null) {
                    for(LootItem item : table.items) {
                        if (item != null)
                        items.add(item.toItem());
                    }
                }
            }
        }
        return items;
    }
    /*
     * Returns the weight of an item on a loot table
     */
    public int getWeight(Item item) {
        int weight = 100;
        List<LootItem> searchTable = getLootItems();
        for (LootItem itemSearch : searchTable) {
            if (itemSearch.id == item.getId()) {
                weight = itemSearch.weight;
            }
        }
        return weight;
    }

    public void calculate(String name) {
        DecimalFormat format = new DecimalFormat("0.###");
        WebTable tablesTable = new WebTable(name + " | Tables | Total weight = " + totalWeight);
        WebTable itemsTable = new WebTable(name + " | Items");
        for (ItemsTable table : tables) {
            double tableProbability = table.weight / totalWeight;
            tablesTable.newEntry().add("name", table.name)
                    .add("weight", table.weight)
                    .add("average (1 in X)", (int)(1 / (tableProbability)))
                    .add("probability", format.format(tableProbability))
                    .add("percentage", format.format(tableProbability * 100) + "%");
            if(table.items.length == 0) {
                int itemWeight = 1;
                double probabilityInTable = itemWeight / table.totalWeight;
                itemsTable.newEntry()
                        .add("table", table.name)
                        .add("id", -1)
                        .add("name", "Nothing")
                        .add("min amount", -1)
                        .add("max amount", -1)

                        .add("overall average (1 in X)", (int)(1 / (probabilityInTable * tableProbability)))
                        .add("weight in table", itemWeight)
                        .add("overall probability", format.format(probabilityInTable * tableProbability))
                        .add("overall percentage", format.format(probabilityInTable * tableProbability * 100) + "%")

                        .add("average in table (1 in X)", (int)(1 / (probabilityInTable)))
                        .add("probability in table", format.format(probabilityInTable))
                        .add("percentage in table", format.format(probabilityInTable * 100) + "%");
            } else {
                for(LootItem item : table.items) {
                    double probabilityInTable = item.weight / table.totalWeight;
                    itemsTable.newEntry()
                            .add("table", table.name)
                            .add("id", item.id)
                            .add("name", item.getName())
                            .add("min amount", item.min)
                            .add("max amount", item.max)

                            .add("overall average (1 in X)", (int) (1 / (probabilityInTable * tableProbability)))
                            .add("weight in table", item.weight)
                            .add("overall probability", format.format(probabilityInTable * tableProbability))
                            .add("overall percentage", format.format(probabilityInTable * tableProbability * 100) + "%")

                            .add("average in table (1 in X)", (int) (1 / (probabilityInTable)))
                            .add("probability in table", format.format(probabilityInTable))
                            .add("percentage in table", format.format(probabilityInTable * 100) + "%");

                }
            }
        }
        System.out.println("Tables: " + tablesTable.getURL());
        System.out.println("Items: " + itemsTable.getURL());
    }

    /**
     * A table of items unique to this table type.
     */

    public static final class ItemsTable {

        @Expose public final String name;

        @Expose public final int weight;

        @Expose public final LootItem[] items;

        public double totalWeight;

        public ItemsTable(String name, int weight, LootItem[] items) {
            this.name = name;
            this.weight = weight;
            this.items = items;
            for(LootItem item : items) {
                totalWeight += item.weight;
                if(ItemDef.get(item.id) == null)
                    System.err.println("!!@@@@@@@@@@@@@@@@@@@@@@: " + item.id);
            }
        }
    }


    /**
     * Common drop table that can be added to monsters table lists
     */
    @AllArgsConstructor
    public enum CommonTables {
        HERB(199, "herb drop", 128, new LootItem[] {
                new LootItem(199, 1, 32),   // Guam
                new LootItem(201, 1, 24),   // Marrentill
                new LootItem(203, 1, 18),   // Tarromin
                new LootItem(205, 1, 14),   // Harralander
                new LootItem(207, 1, 11),   // Ranarr
                new LootItem(259, 1, 8),    // Irit
                new LootItem(211, 1, 6),    // Avantoe
                new LootItem(213, 1, 5),    // Kwuarm
                new LootItem(215, 1, 4),    // Cadantine
                new LootItem(2485, 1, 3),   // Lantadyme
                new LootItem(217, 1, 3),    // Dwarf weed
        }),

        UNCOMMON_SEED(22879, "uncommon seed", 1048, new LootItem[] {
                new LootItem(5100, 1, 137), // Limpwurt
                new LootItem(5323, 1, 131), // Strawberry
                new LootItem(5292, 1, 125), // Marrentill
                new LootItem(5104, 1, 92),  // Jangerberry
                new LootItem(5293, 1, 85),  // Tarromin
                new LootItem(5311, 1, 82),  // Wildblood
                new LootItem(5321, 1, 63),  // Watermelon
                new LootItem(5294, 1, 56),  // Harralander
                new LootItem(22879, 1, 40), // Snape grass
                new LootItem(5295, 1, 39),  // Ranarr
                new LootItem(5105, 1, 34),  // Whiteberry
                new LootItem(5282, 1, 29),  // Mushroom
                new LootItem(5296, 1, 27),  // Toadflax
                new LootItem(5281, 1, 18),  // Belladonna
                new LootItem(5297, 1, 18),  // Irit
                new LootItem(5106, 1, 13),  // Poison ivy
                new LootItem(5298, 1, 12),  // Avantoe
                new LootItem(5280, 1, 12),  // Cactus
                new LootItem(5299, 1, 9),   // Kwuarm
                new LootItem(22873, 1, 8),  // Potato cactus
                new LootItem(5300, 1, 5),   // Snapdragon
                new LootItem(5301, 1, 4),   // Cadantine
                new LootItem(5302, 1, 3),   // Lantadyme
                new LootItem(5303, 1, 2),   // Dwarf weed
                new LootItem(5304, 1, 1)    // Torstol
        }),

        RARE_SEED(5304, "rare seed", 238, new LootItem[] {
                new LootItem(5296, 1, 47),   // Toadflax
                new LootItem(5297, 1, 32),   // Irit
                new LootItem(5281, 1, 31),   // Belladonna
                new LootItem(5298, 1, 22),   // Avantoe
                new LootItem(5106, 1, 22),   // Poison ivy
                new LootItem(5280, 1, 21),   // Cactus
                new LootItem(5299, 1, 15),   // Kwuarm
                new LootItem(22873, 1, 15),  // Potato cactus
                new LootItem(5300, 1, 10),   // Snapdragon
                new LootItem(5301, 1, 7),   // Cadantine
                new LootItem(5302, 1, 5),   // Lantadyme
                new LootItem(22879, 1, 4),  // Snape grass
                new LootItem(5303, 1, 3),   // Dwarf weed
                new LootItem(5304, 1, 2)   // Torstol
        }),

        GENERAL_SEED_0(5318, "general seed", 1008, new LootItem[] {
                new LootItem(5318, 4, 368),     // Potato
                new LootItem(5319, 4, 276),     // Onion
                new LootItem(5324, 4, 184),     // Cabbage
                new LootItem(5322, 3, 92),      // Tomato
                new LootItem(5320, 3, 46),      // Sweetcorn seed
                new LootItem(5323, 2, 23),      // Strawberry
                new LootItem(5321, 2, 11),      // Watermelon
                new LootItem(22879, 2, 8)       // Snape grass
        }),

        GENERAL_SEED_485(5305, "general seed485", 1000, new LootItem[] {
                new LootItem(5305, 4, 229),     // Barley
                new LootItem(5307, 3, 228),     // Hammerstone
                new LootItem(5308, 3, 172),     // Asgarnian
                new LootItem(5306, 2, 171),     // Jute
                new LootItem(5309, 2, 114),     // Yanillian seed
                new LootItem(5310, 2, 57),      // Krandorian
                new LootItem(5311, 1, 29)       // Wildblood
        }),

        GENERAL_SEED_728(5096, "general seed728", 1000, new LootItem[] {
                new LootItem(5096, 1, 376),     // Marigold
                new LootItem(5098, 1, 249),     // Nasturtium
                new LootItem(5097, 1, 161),     // Rosemary
                new LootItem(5099, 1, 119),     // Woad
                new LootItem(5100, 1, 95)       // Limpwurt
        }),

        GENERAL_SEED_850(5101, "general seed850", 1000, new LootItem[] {
                new LootItem(5101, 1, 400),     // Redberry
                new LootItem(5102, 1, 280),     // Cadavaberry
                new LootItem(5103, 1, 200),     // Dewllberry
                new LootItem(5104, 1, 80),      // Jangerberry
                new LootItem(5105, 1, 29),      // Whiteberry
                new LootItem(5106, 1, 11),      // Poison ivy
        }),

        GENERAL_SEED_947(5291, "general seed947", 1000, new LootItem[] {
                new LootItem(5291, 1, 320),     // Guam
                new LootItem(5292, 1, 218),     // Marrentill
                new LootItem(5293, 1, 149),     // Tarromin
                new LootItem(5294, 1, 101),     // Harralander
                new LootItem(5295, 1, 69),      // Ranarr
                new LootItem(5296, 1, 47),      // Toadflax
                new LootItem(5297, 1, 32),      // Irit
                new LootItem(5298, 1, 22),      // Avantoe
                new LootItem(5299, 1, 15),      // Kwuarm
                new LootItem(5300, 1, 10),      // Snapdragon
                new LootItem(5301, 1, 7),       // Cadantine seed
                new LootItem(5302, 1, 5),       // Lantadyme
                new LootItem(5303, 1, 3),       // Dwarf weed
                new LootItem(5304, 1, 2)        // Torstol
        }),

        GENERAL_SEED_995(5282, "general seed995", 1100, new LootItem[] {
                new LootItem(5282, 1, 500),     // Mushroom
                new LootItem(5281, 1, 300),     // Belladonna
                new LootItem(5280, 1, 200),     // Cactus
                new LootItem(22873, 1, 100)     // Potato cactus
        }),

        THREE_HERB_SEED(5313, "three-herb seed", 250, new LootItem[] {
                new LootItem(5295, 1, 30),      // Ranarr
                new LootItem(5300, 1, 28),      // Snapdragon
                new LootItem(5304, 1, 22),      // Torstol
                new LootItem(5321, 15, 21),     // Watermelon
                new LootItem(5313, 1, 20),      // Willow
                new LootItem(21488, 1, 18),     // Mahogany
                new LootItem(5314, 1, 18),      // Maple
                new LootItem(21486, 1, 18),     // Teak
                new LootItem(5315, 1, 18),      // Yew
                new LootItem(5288, 1, 14),      // Papaya
                new LootItem(5316, 1, 11),      // Magic
                new LootItem(5289, 1, 10),      // Palm
                new LootItem(5317, 1, 4),       // Spirit
                new LootItem(22877, 1, 6),      // Dragonfruit
                new LootItem(22869, 1, 4),      // Celastrus
                new LootItem(22871, 1, 4)       // Redwood
        }),

        USEFUL_HERB(212, "useful herb", 16, new LootItem[] {
                new LootItem(212, 1, 5),        // Avantoe
                new LootItem(3052, 1, 4),       // Snapdragon
                new LootItem(207, 1, 4),        // Ranarr
                new LootItem(219, 1, 3)         // Torstol
        }),

        ALLOTMENT_SEED(5282, "allotment seed", 128, new LootItem[] {
                new LootItem(5318, 1, 4, 64),       // Potato
                new LootItem(5319, 1, 3, 32),       // Onion
                new LootItem(5324, 1, 3, 16),       // Cabbage
                new LootItem(5322, 1, 2, 8),        // Tomato
                new LootItem(5320, 1, 2, 4),        // Sweetcorn
                new LootItem(5323, 1, 2),                        // Strawberry
                new LootItem(5321, 1, 1),                        // Watermelon
                new LootItem(22879, 1, 1),                       // Snape grass
        });

        public int itemId;
        public String title;
        public int totalWeight;
        public LootItem[] items;
    }
}
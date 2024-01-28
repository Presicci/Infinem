package io.ruin.model.item.loot;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.WebTable;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
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
                if (!table.name.equalsIgnoreCase("tertiary")
                        && !CommonTables.tertiaryTableNames.contains(table.name)) {
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

    public void checkForTertiaryWeight(String name) {
        if(tables != null) {
            for(ItemsTable table : tables) {
                if (table.name.equalsIgnoreCase("tertiary")) {
                    double percentage = ((double) table.weight / (double) totalWeight);
                    if (percentage > 0.5)
                        System.out.println(name + ": " + percentage);
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
                    double itemsRand = Random.get() * table.totalWeight;
                    for(LootItem item : table.items) {
                        if(item.weight == 0) {  // weightless item landed, add it and continue loop
                            items.add(item.toItem());
                            continue;
                        }
                        if((itemsRand -= item.weight) <= 0) { // weighted item landed, add it and break loop
                            if (item.id == -1) {    // -1 is nothing
                                break;
                            }
                            items.add(item.toItem());
                            break;
                        }
                    }
                    break;
                }
            }
            List<Item> tertiaryRolls = rollTertiary(null);
            if (tertiaryRolls != null)
                items.addAll(tertiaryRolls);
        }
        return items.isEmpty() ? null : items;
    }

    public List<Item> rollTertiary(List<Item> rolledItems) {
        List<Item> items = new ArrayList<>();
        List<Item> commonTableItems = new ArrayList<>(); // Doing this to prevent multiple side-tertiary rolls
        if(tables != null) {
            tableLoop : for(ItemsTable table : tables) {
                if (table.name != null && (table.name.equalsIgnoreCase("tertiary")
                        || CommonTables.tertiaryTableNames.contains(table.name))) {
                    if (Random.rollDie((int) (totalWeight / table.weight))) {
                        double itemsRand = Random.get() * table.totalWeight;
                        boolean commonTableRolled = CommonTables.tertiaryTableNames.contains(table.name);
                        for(LootItem item : table.items) {
                            if(item.weight == 0) {  // weightless item landed, break
                                if (commonTableRolled)
                                    commonTableItems.add(item.toItem());
                                else
                                    items.add(item.toItem());
                                break tableLoop;
                            }
                            if((itemsRand -= item.weight) <= 0) { // weighted item landed, add it and break loop
                                if (rolledItems != null) {
                                    for (Item i : rolledItems) {
                                        if (i.getId() == item.id)
                                            break tableLoop;
                                    }
                                }
                                if (item.id == -1)  // Quit out if we roll a empty slot
                                    break;
                                if (commonTableRolled)
                                    commonTableItems.add(item.toItem());
                                else
                                    items.add(item.toItem());
                                break;
                            }
                        }
                        if (!items.isEmpty()) { // Just in case
                            // Can roll multiple tertiaries in one drop
                            if (rolledItems != null)
                                rolledItems.addAll(items);
                            List<Item> recursiveItems = rollTertiary(rolledItems);
                            if (recursiveItems != null)
                                items.addAll(recursiveItems);
                        }
                    }
                    break;
                }
            }
        }
        items.addAll(commonTableItems);
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

    public void showDrops(Player player, String title) {
        int petId = -1, petAverage = 0;
        List<Integer[]> drops = new ArrayList<>();
        if(guaranteed != null) {
            for(LootItem item : guaranteed) {
                Integer[] drop = new Integer[5];
                drop[0] = item.id;
                drop[1] = item.broadcast == null ? 0 : (item.broadcast.ordinal() + 1);
                drop[2] = item.min;
                drop[3] = item.max;
                drop[4] = 1; //average 1/1
                drops.add(drop);
            }
        }
        if(tables != null) {
            for(LootTable.ItemsTable table : tables) {
                if(table != null) {
                    double tableChance;
                    if (totalWeight == 0) {
                        tableChance = 1;
                    } else {
                        tableChance = table.weight / totalWeight;
                    }
                    if(table.items.length == 0) {
                        //Nothing!
                        //nothingPercentage = tableChance * 100D;
                    } else {
                        for(LootItem item : table.items) {
                            if (item.id == 0 || item.id == -1) {
                                continue;
                            }
                            Integer[] drop = new Integer[5];
                            drop[0] = item.id;
                            drop[1] = item.broadcast == null ? 0 : (item.broadcast.ordinal() + 1);
                            drop[2] = item.min;
                            drop[3] = item.max;
                            if (item.weight == 0)
                                drop[4] = (int) Math.ceil(1D / tableChance);
                            else
                                drop[4] = (int) Math.ceil(1D / (tableChance * (item.weight / table.totalWeight)));
                            drops.add(drop);
                        }
                    }
                }
            }
        }
        //todo - some how generate this string in the constructor! ^^^ :)
        player.openInterface(InterfaceType.MAIN, 383);
        player.getPacketSender().sendDropTable(title, petId, petAverage, drops);
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
    public enum CommonTables {
        GENERAL_SEED_0(5318, "general seed", "General seed table",1008, new LootItem[] {
                new LootItem(5318, 4, 368),     // Potato
                new LootItem(5319, 4, 276),     // Onion
                new LootItem(5324, 4, 184),     // Cabbage
                new LootItem(5322, 3, 92),      // Tomato
                new LootItem(5320, 3, 46),      // Sweetcorn seed
                new LootItem(5323, 2, 23),      // Strawberry
                new LootItem(5321, 2, 11),      // Watermelon
                new LootItem(22879, 2, 8)       // Snape grass
        }),

        GENERAL_SEED_485(5305, "general seed485", "General seed table", 1000, new LootItem[] {
                new LootItem(5305, 4, 229),     // Barley
                new LootItem(5307, 3, 228),     // Hammerstone
                new LootItem(5308, 3, 172),     // Asgarnian
                new LootItem(5306, 2, 171),     // Jute
                new LootItem(5309, 2, 114),     // Yanillian seed
                new LootItem(5310, 2, 57),      // Krandorian
                new LootItem(5311, 1, 29)       // Wildblood
        }),

        GENERAL_SEED_728(5096, "general seed728", "General seed table",1000, new LootItem[] {
                new LootItem(5096, 1, 376),     // Marigold
                new LootItem(5098, 1, 249),     // Nasturtium
                new LootItem(5097, 1, 161),     // Rosemary
                new LootItem(5099, 1, 119),     // Woad
                new LootItem(5100, 1, 95)       // Limpwurt
        }),

        GENERAL_SEED_850(5101, "general seed850", "General seed table",1000, new LootItem[] {
                new LootItem(5101, 1, 400),     // Redberry
                new LootItem(5102, 1, 280),     // Cadavaberry
                new LootItem(5103, 1, 200),     // Dewllberry
                new LootItem(5104, 1, 80),      // Jangerberry
                new LootItem(5105, 1, 29),      // Whiteberry
                new LootItem(5106, 1, 11),      // Poison ivy
        }),

        GENERAL_SEED_947(5291, "general seed947", "General seed table",1000, new LootItem[] {
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

        GENERAL_SEED_995(5282, "general seed995", "General seed table", 1100, new LootItem[] {
                new LootItem(5282, 1, 500),     // Mushroom
                new LootItem(5281, 1, 300),     // Belladonna
                new LootItem(5280, 1, 200),     // Cactus
                new LootItem(22873, 1, 100)     // Potato cactus
        }),

        HERB(199, "herb drop", "Herb table",128, new LootItem[] {
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

        UNCOMMON_SEED(22879, "uncommon seed", "Uncommon seed table", 1048, new LootItem[] {
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

        RARE_SEED(5304, "rare seed", "Rare seed table", 238, new LootItem[] {
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

        TREE_HERB_SEED(5313, "tree-herb seed", "Tree-herb seed table", 250, new LootItem[] {
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

        USEFUL_HERB(212, "useful herb", "Useful herb table", 16, new LootItem[] {
                new LootItem(212, 1, 5),        // Avantoe
                new LootItem(3052, 1, 4),       // Snapdragon
                new LootItem(207, 1, 4),        // Ranarr
                new LootItem(219, 1, 3)         // Torstol
        }),

        ALLOTMENT_SEED(5282, "allotment seed", "Allotment seed table", 128, new LootItem[] {
                new LootItem(5318, 1, 4, 64),       // Potato
                new LootItem(5319, 1, 3, 32),       // Onion
                new LootItem(5324, 1, 3, 16),       // Cabbage
                new LootItem(5322, 1, 2, 8),        // Tomato
                new LootItem(5320, 1, 2, 4),        // Sweetcorn
                new LootItem(5323, 1, 2),                        // Strawberry
                new LootItem(5321, 1, 1),                        // Watermelon
                new LootItem(22879, 1, 1),                       // Snape grass
        }),

        TALISMAN(Items.AIR_TALISMAN, "talisman drop", "Talisman table", 28, new LootItem[] {
                new LootItem(Items.AIR_TALISMAN, 1, 1, 4),          // Air
                new LootItem(Items.BODY_TALISMAN, 1, 1, 4),         // Body
                new LootItem(Items.EARTH_TALISMAN, 1, 1, 4),        // Earth
                new LootItem(Items.FIRE_TALISMAN, 1, 1, 4),         // Fire
                new LootItem(Items.MIND_TALISMAN, 1, 1, 4),         // Mind
                new LootItem(Items.WATER_TALISMAN, 1, 1, 4),        // Water
                new LootItem(Items.COSMIC_TALISMAN, 1, 1, 2),       // Cosmic
                new LootItem(Items.CHAOS_TALISMAN, 1, 1, 1),        // Chaos
                new LootItem(Items.NATURE_TALISMAN, 1, 1, 1)        // Nature
        }),

        WILDERNESS_SLAYER_CAVE(true, 24614, "wilderness slayer", "Wildy slayer cave", 28, new LootItem[] {
                new LootItem(24613, 1, 10, 60),  // Blighted entangle sack
                new LootItem(24592, 1, 2, 50),   // Blighted anglerfish
                new LootItem(24589, 1, 2, 50),   // Blighted manta ray
                new LootItem(24595, 1, 2, 42),   // Blighted karambwan
                new LootItem(24615, 1, 10, 42),   // Blighted teleport spell sack
                new LootItem(24607, 1, 10, 42),   // Blighted ancient ice sack
                new LootItem(24621, 1, 10, 42),   // Blighted vengeance sack
                new LootItem(24598, 1, 21),   // Blighted super restore
                new LootItem(21802, 1, 21),   // Revenant cave teleport
                new LootItem(12777, 1, 4),   // Dareeyak teleport
                new LootItem(12776, 1, 4),   // Carrallanger teleport
                new LootItem(12781, 1, 4),   // Paddewwa teleport
                new LootItem(12775, 1, 4),   // Annakarl teleport
                new LootItem(12780, 1, 4),   // Lassar teleport
                new LootItem(12779, 1, 4),   // Kharyrll teleport
                new LootItem(12782, 1, 4),   // Senntisten teleport
                new LootItem(12778, 1, 4),   // Ghorrock teleport
                new LootItem(24336, 1, 4),   // Target teleport
                new LootItem(12786, 1, 4),   // Magic shortbow scroll
                new LootItem(12783, 1, 4),   // Ring of wealth scroll
                new LootItem(24585, 1, 1)   // Looting bag note
        }),

        SUPERIOR(20724, "superior", "Superior table", 8, new LootItem[] {
                new LootItem(20736, 1, 1, 3),        // Dust battlestaff
                new LootItem(20730, 1, 1, 3),        // Mist battlestaff
                new LootItem(20724, 1, 1, 1),        // Imbued heart
                new LootItem(21270, 1, 1, 1)         // Eternal gem
        });

        public int itemId;
        public String title, name;
        public int totalWeight;
        public LootItem[] items;
        public boolean tertiary;

        public static List<String> tertiaryTableNames = new ArrayList<>();

        CommonTables(int itemId, String title, String name, int totalWeight, LootItem[] items) {
            this(false, itemId, title, name, totalWeight, items);
        }

        CommonTables(boolean tertiary, int itemId, String title, String name, int totalWeight, LootItem[] items) {
            this.tertiary = tertiary;
            this.itemId = itemId;
            this.title = title;
            this.name = name;
            this.totalWeight = totalWeight;
            this.items = items;
        }

        static {
            for (CommonTables t : values()) {
                if (t.tertiary) tertiaryTableNames.add(t.title);
            }
        }
    }
}
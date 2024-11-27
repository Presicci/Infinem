package io.ruin.model.skills.fishing;

import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/26/2024
 */
public class FishingTables {

    public static final LootTable CIVITAS_ILLA_FORTIS = new LootTable().addTable(1,
            new LootItem(Items.BROKEN_GLASS, 1, 144),
            new LootItem(Items.OLD_BOOT, 1, 100),
            new LootItem(Items.BIG_FISHING_NET, 1, 100),
            new LootItem(Items.CASKET, 1, 100),
            new LootItem(29325, 1, 50),  // House keys
            new LootItem(Items.JADE, 1, 5),
            new LootItem(Items.RED_TOPAZ, 1, 1)
    );

    public static final LootTable BIG_NET = new LootTable().addTable(1,
            new LootItem(-1, 1, 25), // Keep fish
            new LootItem(Items.LEATHER_BOOTS, 1, 3),
            new LootItem(Items.LEATHER_GLOVES, 1, 3),
            new LootItem(Items.SEAWEED, 1, 3),
            new LootItem(Items.OYSTER, 1, 3),
            new LootItem(Items.CASKET, 1, 1)
    );
}

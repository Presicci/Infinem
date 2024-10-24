package io.ruin.model.activities.wilderness.bosses.callisto;

import io.ruin.cache.ItemID;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/23/2024
 */
public class CallistoDropTable {


    /**
     * REGULAR:
     * Things like Runes and ammunition resources etc.
     */
    public static final LootTable REGULAR = new LootTable().addTable(56,
            /**
             * Consumables
             */
            new LootItem(ItemID.DARK_CRAB, 50, 85, true),
            new LootItem(ItemID.SUPER_RESTORE4, 10, 85, true),

            /**
             * Weapons & Armour
             */
            new LootItem(ItemID.RUNE_PICKAXE, 5, 110, true),
            new LootItem(ItemID.RUNE_2H_SWORD, 3, 65, true),
            new LootItem(ItemID.MYSTIC_EARTH_STAFF, 4, 65, true),
            new LootItem(ItemID.MYSTIC_ROBE_TOP, 4, 65, true),
            new LootItem(ItemID.MYSTIC_ROBE_BOTTOM, 4, 65, true),
            new LootItem(ItemID.RUNE_KITESHIELD, 4, 65, true),
            new LootItem(ItemID.RUNE_PLATEBODY, 4, 65, true),

            /**
             * Runes and ammunition
             */
            new LootItem(ItemID.CHAOS_RUNE, 900, 100),
            new LootItem(ItemID.DEATH_RUNE, 700, 100),
            new LootItem(ItemID.BLOOD_RUNE, 500, 100),
            new LootItem(ItemID.SOUL_RUNE, 600, 100),
            new LootItem(ItemID.CANNONBALL, 600, 70),

            /**
             * Materials
             */
            new LootItem(ItemID.MAHOGANY_LOGS, 600, 100, true),
            new LootItem(ItemID.LIMPWURT_ROOT, 100, 85, true),
            new LootItem(ItemID.MAGIC_LOGS, 225, 85, true),
            new LootItem(ItemID.UNCUT_RUBY, 75, 85, true),
            new LootItem(ItemID.UNCUT_DIAMOND, 35, 65, true),
            new LootItem(ItemID.RED_DRAGONHIDE, 170, 65, true),
            new LootItem(ItemID.UNCUT_DRAGONSTONE, 5, 60, true),
            new LootItem(ItemID.COCONUT, 135, 60, true),
            new LootItem(ItemID.GRIMY_RANARR_WEED, 45, 30, true),
            new LootItem(ItemID.GRIMY_DWARF_WEED, 45, 30, true),
            new LootItem(ItemID.GRIMY_SNAPDRAGON, 45, 30, true),
            new LootItem(ItemID.GRIMY_TOADFLAX, 150, 30, true),

            /**
             * Other
             */
            new LootItem(ItemID.COINS_995, 50_000, 120),
            new LootItem(ItemID.SUPERCOMPOST, 225, 75, true),
            new LootItem(ItemID.RANARR_SEED, 11, 30),
            new LootItem(ItemID.SNAPDRAGON_SEED, 8, 30),
            new LootItem(ItemID.DRAGON_BONES, 75, 30, true),
            new LootItem(ItemID.WILDERNESS_CRABS_TELEPORT, 4, 50),
            new LootItem(ItemID.BLIGHTED_ANGLERFISH, 100, 30, true)
    );

    /**
     * Secondary Loot Table
     */
    public static final LootTable SECONDARY_SUPPLY_ROLL = new LootTable().addTable(1,
            new LootItem(ItemID.BLIGHTED_ANGLERFISH, 5, 6, 1),
            new LootItem(ItemID.BLIGHTED_KARAMBWAN, 5, 6, 1),
            new LootItem(ItemID.BLIGHTED_SUPER_RESTORE3, 3, 4, 1),
            new LootItem(ItemID.BLIGHTED_SUPER_RESTORE4, 3, 4, 1),
            new LootItem(ItemID.RANGING_POTION2, 2, 3, 1),
            new LootItem(ItemID.SUPER_COMBAT_POTION2, 2, 3, 1)
    );
    /**
     * UNIQUES:
     * Effective chance of 1/43 chance
     */
    public static final LootTable UNIQUE = new LootTable().addTable(1,
            new LootItem(ItemID.CLAWS_OF_CALLISTO, 1, 33),
            new LootItem(ItemID.DRAGON_2H_SWORD, 1, 22),
            new LootItem(ItemID.DRAGON_PICKAXE, 1, 22),
            new LootItem(ItemID.VOIDWAKER_HILT, 1, 18),
            new LootItem(ItemID.TYRANNICAL_RING, 1, 13)
    );

    public static Item rollUnique() {
        return UNIQUE.rollItem();
    }

    public static Item rollRegular() {
        return REGULAR.rollItem();
    }

    public static Item rollSecondary() {
        return SECONDARY_SUPPLY_ROLL.rollItem();
    }

}
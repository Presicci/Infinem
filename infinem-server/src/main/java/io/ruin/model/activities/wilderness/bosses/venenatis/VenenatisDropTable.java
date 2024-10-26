package io.ruin.model.activities.wilderness.bosses.venenatis;

import io.ruin.cache.ItemID;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/24/2024
 */
public class VenenatisDropTable {
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
            new LootItem(ItemID.RUNE_DART, 150, 65),
            new LootItem(ItemID.RUNE_KNIFE, 150, 40),
            new LootItem(ItemID.MYSTIC_AIR_STAFF, 4, 40, true),
            new LootItem(ItemID.RUNE_PLATELEGS, 4, 40, true),
            new LootItem(ItemID.RUNE_SQ_SHIELD, 4, 40, true),
            new LootItem(ItemID.DRAGON_DAGGER, 6, 40, true),

            /**
             * Runes and ammunition
             */
            new LootItem(ItemID.CHAOS_RUNE, 900, 100),
            new LootItem(ItemID.DEATH_RUNE, 700, 100),
            new LootItem(ItemID.BLOOD_RUNE, 500, 100),
            new LootItem(ItemID.DIAMOND_BOLTS_E, 300, 40),
            new LootItem(ItemID.CANNONBALL, 600, 70),

            /**
             * Materials
             */
            new LootItem(ItemID.GOLD_ORE, 675, 100, true),
            new LootItem(ItemID.LIMPWURT_ROOT, 60, 85, true),
            new LootItem(ItemID.ONYX_BOLT_TIPS, 150, 85),
            new LootItem(ItemID.MAGIC_LOGS, 225, 85, true),
            new LootItem(ItemID.RED_SPIDERS_EGGS, 500, 85, true),
            new LootItem(ItemID.UNCUT_RUBY, 75, 75, true),
            new LootItem(ItemID.UNCUT_DIAMOND, 35, 65, true),
            new LootItem(ItemID.BATTLESTAFF, 12, 65, true),
            new LootItem(ItemID.UNCUT_DRAGONSTONE, 5, 60, true),
            new LootItem(ItemID.UNICORN_HORN, 225, 60, true),
            new LootItem(ItemID.GRIMY_RANARR_WEED, 100, 30, true),
            new LootItem(ItemID.GRIMY_DWARF_WEED, 45, 30, true),
            new LootItem(ItemID.GRIMY_SNAPDRAGON, 45, 30, true),
            new LootItem(ItemID.GRIMY_TOADFLAX, 45, 30, true),

            /**
             * Other
             */
            new LootItem(ItemID.COINS_995, 50_000, 120),
            new LootItem(ItemID.SANFEW_SERUM4, 20, 85, true),
            new LootItem(ItemID.SUPERCOMPOST, 225, 75, true),
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
            new LootItem(ItemID.FANGS_OF_VENENATIS, 1, 34),
            new LootItem(ItemID.DRAGON_2H_SWORD, 1, 22),
            new LootItem(ItemID.DRAGON_PICKAXE, 1, 22),
            new LootItem(ItemID.VOIDWAKER_GEM, 1, 18),
            new LootItem(ItemID.TREASONOUS_RING, 1, 13)
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

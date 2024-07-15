package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/4/2022
 */
public enum EquipTask {
    SPINY_HELMET(29, Items.SPINY_HELMET),   // Equip a Spiny Helmet
    MITHRIL_WEAPON(59,     // Equip a Mithril Weapon
            Items.MITHRIL_DAGGER, Items.MITHRIL_DAGGER_P, Items.MITHRIL_DAGGER_P_2, Items.MITHRIL_DAGGER_P_3, Items.MITHRIL_AXE, Items.MITHRIL_PICKAXE,
            Items.MITHRIL_MACE, Items.MITHRIL_SWORD, Items.MITHRIL_SCIMITAR, Items.MITHRIL_HASTA, Items.MITHRIL_HASTA_P, Items.MITHRIL_HASTA_P_2,
            Items.MITHRIL_HASTA_P_3, Items.MITHRIL_HASTA_KP, Items.MITHRIL_LONGSWORD, Items.MITHRIL_WARHAMMER, Items.MITHRIL_BATTLEAXE, Items.MITHRIL_CLAWS,
            Items.MITHRIL_SPEAR, Items.MITHRIL_SPEAR_P, Items.MITHRIL_SPEAR_P_2, Items.MITHRIL_SPEAR_P_3, Items.MITHRIL_SPEAR_KP,
            Items.MITHRIL_HALBERD, Items.MITHRIL_2H_SWORD, Items.MITH_CROSSBOW, Items.MITHRIL_KNIFE, Items.MITHRIL_DART, Items.MITHRIL_THROWNAXE
    ),
    MAPLE_SHORTBOW(62, Items.MAPLE_SHORTBOW),   // Equip a Maple Shortbow
    ELEMENTAL_STAFF(63, Items.STAFF_OF_AIR, Items.STAFF_OF_EARTH, Items.STAFF_OF_FIRE, Items.STAFF_OF_WATER),     // Equip an Elemental Staff
    BUG_LANTERN(125, Items.LIT_BUG_LANTERN),    // Equip a Harpie Bug Lantern
    ADAMANT_WEAPON(137,     // Equip an Adamant Weapon
            Items.ADAMANT_DAGGER, Items.ADAMANT_DAGGER_P, Items.ADAMANT_DAGGER_P_2, Items.ADAMANT_DAGGER_P_3, Items.ADAMANT_AXE, Items.ADAMANT_PICKAXE,
            Items.ADAMANT_MACE, Items.ADAMANT_CANE, Items.ADAMANT_SWORD, Items.ADAMANT_SCIMITAR, Items.ADAMANT_HASTA, Items.ADAMANT_HASTA_P, Items.ADAMANT_HASTA_P_2,
            Items.ADAMANT_HASTA_P_3, Items.ADAMANT_HASTA_KP, Items.ADAMANT_LONGSWORD, Items.ADAMANT_WARHAMMER, Items.ADAMANT_BATTLEAXE, Items.ADAMANT_CLAWS,
            Items.ADAMANT_SPEAR, Items.ADAMANT_SPEAR_P, Items.ADAMANT_SPEAR_P_2, Items.ADAMANT_SPEAR_P_3, Items.ADAMANT_SPEAR_KP,
            Items.ADAMANT_HALBERD, Items.ADAMANT_2H_SWORD, Items.ADAMANT_CROSSBOW, Items.ADAMANT_KNIFE, Items.ADAMANT_DART, Items.ADAMANT_THROWNAXE
    ),
    YEW_SHORTBOW(144, Items.YEW_SHORTBOW),  // Equip a Yew Shortbow
    ELEMENTAL_BATTLESTAFF(145,      // Equip an Elemental Battlestaff or Mystic Staff
            Items.AIR_BATTLESTAFF, Items.WATER_BATTLESTAFF, Items.EARTH_BATTLESTAFF, Items.FIRE_BATTLESTAFF,
            Items.MYSTIC_AIR_STAFF, Items.MYSTIC_WATER_STAFF, Items.MYSTIC_EARTH_STAFF, Items.MYSTIC_FIRE_STAFF
    ),
    FANCY_BOOTS(341, Items.FANCY_BOOTS, Items.FIGHTING_BOOTS),    // Equip Some Fancy Boots or Fighting Boots
    DORGESHUUN_CROSSBOW(357, Items.DORGESHUUN_CROSSBOW),
    FIRE_CAPE(418, Items.FIRE_CAPE),    // Equip a Fire Cape
    INFERNAL_CAPE(429, Items.INFERNAL_CAPE, MaxCapeVariants.MaxCapes.INFERNAL.newCapeId),   // Equip an Infernal Cape
    DRAGON_DEFENDER(462, Items.DRAGON_DEFENDER),    // Equip a Dragon Defender
    AVAS_ASSEMBLER(523, 22109),             // Equip an Ava's Assembler
    MONKEY_BACKPACK(568, 19556),            // Equip a Monkey Backpack
    KARAMJA_MONKEY_BACKPACK(590, 24862),    // Equip a Karamja Monkey Backpack
    MANIACAL_MONKEY_BACKPACK(604, 24864),   // Equip a Maniacal Monkey Backpack
    KRUK_JR_BACKPACK(622, 24866),           // Equip a Kruk Jr. Monkey Backpack
    PRINCE_BACKPACK(628, 24867),            // Equip a Prince Awowogei Monkey Back
    SALVE_AMULET_E(729, Items.SALVE_AMULET_E, Items.SALVE_AMULET_EI, 25278, 26782),
    GOD_CAPE(857,       // Equip a God Cape
            Items.SARADOMIN_CAPE, Items.ZAMORAK_CAPE, Items.GUTHIX_CAPE,
            Items.IMBUED_SARADOMIN_CAPE, Items.IMBUED_ZAMORAK_CAPE, Items.IMBUED_GUTHIX_CAPE
    ),
    TEAM_CAPE(877,      // Equip a Team Cape
            Items.TEAM1_CAPE, Items.TEAM2_CAPE, Items.TEAM3_CAPE, Items.TEAM4_CAPE, Items.TEAM5_CAPE,
            Items.TEAM6_CAPE, Items.TEAM7_CAPE, Items.TEAM8_CAPE, Items.TEAM9_CAPE, Items.TEAM10_CAPE,
            Items.TEAM11_CAPE, Items.TEAM12_CAPE, Items.TEAM13_CAPE, Items.TEAM14_CAPE, Items.TEAM15_CAPE,
            Items.TEAM16_CAPE, Items.TEAM17_CAPE, Items.TEAM18_CAPE, Items.TEAM19_CAPE, Items.TEAM20_CAPE,
            Items.TEAM21_CAPE, Items.TEAM22_CAPE, Items.TEAM23_CAPE, Items.TEAM24_CAPE, Items.TEAM25_CAPE,
            Items.TEAM26_CAPE, Items.TEAM27_CAPE, Items.TEAM28_CAPE, Items.TEAM29_CAPE, Items.TEAM30_CAPE,
            Items.TEAM31_CAPE, Items.TEAM32_CAPE, Items.TEAM33_CAPE, Items.TEAM34_CAPE, Items.TEAM35_CAPE,
            Items.TEAM36_CAPE, Items.TEAM37_CAPE, Items.TEAM38_CAPE, Items.TEAM39_CAPE, Items.TEAM40_CAPE,
            Items.TEAM41_CAPE, Items.TEAM42_CAPE, Items.TEAM43_CAPE, Items.TEAM44_CAPE, Items.TEAM45_CAPE,
            Items.TEAM46_CAPE, Items.TEAM47_CAPE, Items.TEAM48_CAPE, Items.TEAM49_CAPE, Items.TEAM50_CAPE,
            Items.TEAM_CAPE_I, Items.TEAM_CAPE_X
    ),
    PROTEST_BANNER(948, 25822),     // Equip a Protest Banner
    ;

    private final int uuid;
    private final int[] itemIds;

    EquipTask(int uuid, int... itemIds) {
        this.itemIds = itemIds;
        this.uuid = uuid;
    }

    static {
        for (EquipTask task : values()) {
            for (int itemId : task.itemIds) {
                EquipAction.register(itemId, (player -> {
                    player.getTaskManager().doLookupByUUID(task.uuid, 1);
                }));
            }

        }
    }
}

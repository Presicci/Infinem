package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/4/2022
 */
public enum EquipTask {

    FIRE_CAPE(418, Items.FIRE_CAPE),    // Equip a Fire Cape
    INFERNAL_CAPE(429, Items.INFERNAL_CAPE, MaxCapeVariants.MaxCapes.INFERNAL.newCapeId),   // Equip an Infernal Cape
    SPINY_HELMET(29, Items.SPINY_HELMET),   // Equip a Spiny Helmet
    DRAGON_DEFENDER(462, Items.DRAGON_DEFENDER),    // Equip a Dragon Defender
    FANCY_BOOTS(341, Items.FANCY_BOOTS, Items.FIGHTING_BOOTS),    // Equip Some Fancy Boots or Fighting Boots
    BUG_LANTERN(125, Items.LIT_BUG_LANTERN),    // Equip a Harpie Bug Lantern
    MAPLE_SHORTBOW(62, Items.MAPLE_SHORTBOW),   // Equip a Maple Shortbow
    ELEMENTAL_STAFF(63, Items.STAFF_OF_AIR, Items.STAFF_OF_EARTH, Items.STAFF_OF_FIRE, Items.STAFF_OF_WATER),     // Equip an Elemental Staff
    ADAMANT_WEAPON(137,     // Equip an Adamant Weapon
            Items.ADAMANT_DAGGER, Items.ADAMANT_DAGGER_P, Items.ADAMANT_DAGGER_P_2, Items.ADAMANT_DAGGER_P_3, Items.ADAMANT_AXE, Items.ADAMANT_PICKAXE,
            Items.ADAMANT_MACE, Items.ADAMANT_CANE, Items.ADAMANT_SWORD, Items.ADAMANT_SCIMITAR, Items.ADAMANT_HASTA, Items.ADAMANT_HASTA_P, Items.ADAMANT_HASTA_P_2,
            Items.ADAMANT_HASTA_P_3, Items.ADAMANT_HASTA_KP, Items.ADAMANT_LONGSWORD, Items.ADAMANT_WARHAMMER, Items.ADAMANT_BATTLEAXE, Items.ADAMANT_CLAWS,
            Items.ADAMANT_SPEAR, Items.ADAMANT_SPEAR_P, Items.ADAMANT_SPEAR_P_2, Items.ADAMANT_SPEAR_P_3, Items.ADAMANT_SPEAR_KP,
            Items.ADAMANT_HALBERD, Items.ADAMANT_2H_SWORD, Items.ADAMANT_CROSSBOW, Items.ADAMANT_KNIFE, Items.ADAMANT_DART, Items.ADAMANT_THROWNAXE
    ),
    RUNITE_WEAPON(138,     // Equip a Rune Weapon
            Items.RUNE_DAGGER, Items.RUNE_DAGGER_P, Items.RUNE_DAGGER_P_2, Items.RUNE_DAGGER_P_3, Items.RUNE_AXE, Items.RUNE_PICKAXE,
            Items.RUNE_MACE, Items.RUNE_CANE, Items.RUNE_SWORD, Items.RUNE_SCIMITAR, Items.RUNE_HASTA, Items.RUNE_HASTA_P, Items.RUNE_HASTA_P_2,
            Items.RUNE_HASTA_P_3, Items.RUNE_HASTA_KP, Items.RUNE_LONGSWORD, Items.RUNE_WARHAMMER, Items.RUNE_BATTLEAXE, Items.RUNE_CLAWS,
            Items.RUNE_SPEAR, Items.RUNE_SPEAR_P, Items.RUNE_SPEAR_P_2, Items.RUNE_SPEAR_P_3, Items.RUNE_SPEAR_KP,
            Items.RUNE_HALBERD, Items.RUNE_2H_SWORD, Items.RUNE_CROSSBOW, Items.RUNE_KNIFE, Items.RUNE_DART, Items.RUNE_THROWNAXE
    ),
    YEW_SHORTBOW(144, Items.YEW_SHORTBOW),  // Equip a Yew Shortbow
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

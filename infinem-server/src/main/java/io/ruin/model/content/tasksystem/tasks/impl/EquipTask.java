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

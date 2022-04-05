package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/4/2022
 */
public enum EquipTask {

    FIRE_CAPE(Items.FIRE_CAPE, 418),
    INFERNAL_CAPE(Items.INFERNAL_CAPE, 429),
    SPINY_HELMET(Items.SPINY_HELMET, 29),
    DRAGON_DEFENDER(Items.DRAGON_DEFENDER, 462),
    FANCY_BOOTS(Items.FANCY_BOOTS, 341),
    FIGHTING_BOOTS(Items.FIGHTING_BOOTS, 341),
    ;

    private final int itemId, uuid;

    EquipTask(int itemId, int uuid) {
        this.itemId = itemId;
        this.uuid = uuid;
    }

    static {
        for (EquipTask task : values()) {
            EquipAction.register(task.itemId, (player -> {
                player.getTaskManager().doLookupByUUID(task.uuid, 1);
            }));
        }
    }
}

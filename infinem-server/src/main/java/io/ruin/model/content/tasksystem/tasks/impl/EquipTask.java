package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/4/2022
 */
public enum EquipTask {

    FIRE_CAPE(418, Items.FIRE_CAPE),
    INFERNAL_CAPE(429, Items.INFERNAL_CAPE),
    SPINY_HELMET(29, Items.SPINY_HELMET),
    DRAGON_DEFENDER(462, Items.DRAGON_DEFENDER),
    FANCY_BOOTS(341, Items.FANCY_BOOTS),
    FIGHTING_BOOTS(341, Items.FIGHTING_BOOTS),
    BUG_LANTERN(125, Items.LIT_BUG_LANTERN)
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

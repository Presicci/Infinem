package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/4/2022
 */
public class EquipTasks {

    static {
        EquipAction.register(Items.FIRE_CAPE, (player -> {
            player.getTaskManager().doLookupByUUID(418, 1);
        }));
        EquipAction.register(Items.INFERNAL_CAPE, (player -> {
            player.getTaskManager().doLookupByUUID(429, 1);
        }));
        EquipAction.register(Items.SPINY_HELMET, (player -> {
            player.getTaskManager().doLookupByUUID(29, 1);
        }));
    }
}

package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.item.Items;

public enum DropTask {
    TWISTED_BOW(989, Items.TWISTED_BOW);

    DropTask(int taskUuid, int... itemIds) {
        for (int itemId : itemIds) {
            ItemDefinition.get(itemId).custom_values.put("DROP_TASK", taskUuid);
        }
    }
}

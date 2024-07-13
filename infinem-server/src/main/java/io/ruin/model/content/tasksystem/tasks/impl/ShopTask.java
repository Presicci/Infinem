package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.def.ItemDefinition;

public enum ShopTask {
    CRYSTAL_CROWN(830, 23911);

    ShopTask(int uuid, int... itemIds) {
        for (int itemId : itemIds) {
            ItemDefinition.get(itemId).custom_values.put("SHOP_TASK", uuid);
        }
    }
}

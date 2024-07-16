package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.item.Items;

public enum ShopTask {
    DRAGON_SCIMITAR(579, Items.DRAGON_SCIMITAR),
    CRYSTAL_CROWN(830, 23911);

    ShopTask(int uuid, int... itemIds) {
        for (int itemId : itemIds) {
            ItemDefinition.get(itemId).custom_values.put("SHOP_TASK", uuid);
        }
    }
}

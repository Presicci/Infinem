package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.def.ItemDefinition;
import lombok.Getter;

import java.util.HashSet;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/10/2024
 */
@Getter
public enum DropAllTask {
    WILDERNESS_WEAPON(902, 22542, 22547, 22552),
    ;

    private final int taskUuid;
    private final int[] itemIds;

    public boolean hasCompleted(HashSet<Integer> collectedItems) {
        for (int itemId : itemIds) {
            if (!collectedItems.contains(itemId)) return false;
        }
        return true;
    }

    public void cleanupCollectedItems(HashSet<Integer> collectedItems) {
        for (int itemId : itemIds) {
            collectedItems.remove(itemId);
        }
    }

    DropAllTask(int taskUuid, int... itemIds) {
        this.taskUuid = taskUuid;
        this.itemIds = itemIds;
        for (int itemId : itemIds) {
            ItemDefinition.get(itemId).custom_values.put("DROPALL_TASK", this);
        }
    }
}

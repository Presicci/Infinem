package io.ruin.model.content.tasksystem.tasks;

import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/2/2024
 */
public class TaskCorrections {

    static {
        LoginListener.register(player -> {
            if (player.getCollectionLog().hasCollected(Items.FIGHTER_TORSO)) {
                player.getTaskManager().doLookupByUUID(597);
            }
            if (player.getTaskManager().hasCompletedTask(1003)) {
                if (PlayerCounter.MAHOGANY_HOMES_CONTRACTS.get(player) < 100) {
                    player.getTaskManager().removeTask("Complete 100 Expert Mahogany Homes Contracts", 1003, TaskArea.ASGARNIA, TaskDifficulty.ELITE);
                }
            }
        });
    }
}

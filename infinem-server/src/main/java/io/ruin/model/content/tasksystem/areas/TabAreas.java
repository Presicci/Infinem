package io.ruin.model.content.tasksystem.areas;

import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SlotAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/29/2024
 */
public class TabAreas {

    private static void openEntry(Player player, int slot) {
        AreaReward.openRewards(player, TaskArea.values()[slot]);
    }

    static {
        InterfaceHandler.register(Interface.ACHIEVEMENT, h -> {
            h.actions[2] = (SlotAction) TabAreas::openEntry;
        });
    }
}

package io.ruin.model.content.tasksystem.areas;

import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.content.tasksystem.tasks.inter.TaskInterface;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SlotAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/29/2024
 */
public class TabAreas {

    private static void openEntry(Player player, int slot) {
        AreaReward.openRewards(player, TaskArea.values()[slot]);
    }

    private static void openShopPreview(Player player, int slot) {
        AreaShop.viewShopPreview(player, TaskArea.values()[slot]);
    }

    private static void openTasks(Player player, int slot) {
        TaskInterface.openTaskInterface(player);
        TaskInterface.TIER_FILTER.set(player, 0);
        TaskInterface.TYPE_FILTER.set(player, 0);
        TaskInterface.AREA_FILTER.set(player, slot + 1);
        TaskInterface.COMPLETED_FILTER.set(player, 0);
    }

    static {
        InterfaceHandler.register(Interface.ACHIEVEMENT, h -> {
            h.actions[2] = (DefaultAction) (player, option, slot, itemId) -> {
                if (option == 1) openEntry(player, slot);
                else if (option == 2) openShopPreview(player, slot);
                else openTasks(player, slot);
            };
        });
    }
}

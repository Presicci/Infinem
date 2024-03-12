package io.ruin.model.content.tasksystem.tasks.inter;

import io.ruin.model.content.tasksystem.relics.inter.RelicInterface;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/30/2023
 */
public class TaskInterface {

    public static void openTaskInterface(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.TASKS);
        int currentPoints = Config.LEAGUE_POINTS.get(player);
        int pointsForCurrentTier = player.getRelicManager().pointsForCurrentRelic();
        int pointsForNextTier = player.getRelicManager().pointsForNextRelic();
        int fill = (int) (((double) (currentPoints - pointsForCurrentTier) / (pointsForNextTier - pointsForCurrentTier)) * 430);
        player.getPacketSender().sendClientScript(10062, "iii", currentPoints, pointsForNextTier, Math.min(fill, 430));
        player.getPacketSender().sendAccessMask(Interface.TASKS, 42, 0, 12, 2);
        player.getPacketSender().sendAccessMask(Interface.TASKS, 44, 0, 12, 2);
        player.getPacketSender().sendAccessMask(Interface.TASKS, 45, 0, 12, 2);
        player.getPacketSender().sendAccessMask(Interface.TASKS, 46, 0, 12, 2);
        player.getPacketSender().sendAccessMask(Interface.TASKS, 47, 0, 12, 2);
        player.getTaskManager().sendTasksToInterface();
    }

    static {
        InterfaceHandler.register(Interface.TASKS, (h -> {
            h.actions[9] = (OptionAction) (player, option) -> {
                if (option == 1) {
                    player.stringInput("Search:", search -> {
                        player.getTaskManager().searchString = search;
                        player.getTaskManager().sendTasksToInterface();
                    });
                } else {
                    player.getTaskManager().searchString = "";
                    player.getTaskManager().sendTasksToInterface();
                }
            };
            h.actions[37] = (SimpleAction) RelicInterface::open;
            h.actions[42] = (SlotAction) (player, slot) -> {
                Config.TASK_INTERFACE_AREA.set(player, slot - 1);
                player.getTaskManager().sendTasksToInterface();
            };
            h.actions[44] = (SlotAction) (player, slot) -> {
                Config.TASK_INTERFACE_SKILL.set(player, slot - 1);
                player.getTaskManager().sendTasksToInterface();
            };
            h.actions[45] = (SlotAction) (player, slot) -> {
                Config.TASK_INTERFACE_TIER.set(player, slot - 1);
                player.getTaskManager().sendTasksToInterface();
            };
            h.actions[46] = (SlotAction) (player, slot) -> {
                Config.TASK_INTERFACE_COMPLETED.set(player, slot - 1);
                player.getTaskManager().sendTasksToInterface();
            };
            h.actions[47] = (SlotAction) (player, slot) -> {
                Config.TASK_INTERFACE_SORT.set(player, slot - 1);
                player.getTaskManager().sendTasksToInterface();
            };
        }));
    }
}

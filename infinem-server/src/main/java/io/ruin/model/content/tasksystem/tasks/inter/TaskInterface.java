package io.ruin.model.content.tasksystem.tasks.inter;

import io.ruin.model.content.tasksystem.relics.inter.RelicInterface;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.journal.JournalTab;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/30/2023
 */
public class TaskInterface {

    public static final Config TIER_FILTER = Config.varpbit(10033, true);
    public static final Config TYPE_FILTER = Config.varpbit(10037, true);
    public static final Config AREA_FILTER = Config.varpbit(11692, true);
    public static final Config COMPLETED_FILTER = Config.varpbit(10034, true);

    public static void openTaskInterface(Player player) {
        player.getPacketSender().sendClientScript(10203, "s", player.getTaskManager().generateInProgressString());
        player.openInterface(InterfaceType.WORLD_MAP, 657);
        player.getPacketSender().sendAccessMask(657, 6, 9, 18, AccessMasks.ClickOp1);   // Navigation buttons
        player.getPacketSender().sendAccessMask(657, 27, 0, 16, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(657, 35, 0, 6, AccessMasks.ClickOp1);   // Tier filter dropdown
        player.getPacketSender().sendAccessMask(657, 36, 0, 25, AccessMasks.ClickOp1);  // Type filter dropdown
        player.getPacketSender().sendAccessMask(657, 37, 0, 12, AccessMasks.ClickOp1);  // Area filter dropdown
        player.getPacketSender().sendAccessMask(657, 39, 0, 3, AccessMasks.ClickOp1);   // Completed filter dropdown
    }

    private static void close(Player player) {
        player.closeInterface(InterfaceType.WORLD_MAP);
        player.getPacketSender().sendClientScript(101, "i", 11);
    }

    private static void navigation(Player player, int slot) {
        if (slot == 10) {   // Info

        } else if (slot == 14) {
            JournalTab.setTab(player, JournalTab.Tab.ACHIEVEMENT);
        } else if (slot == 16) {
            RelicInterface.open(player);
        }
    }

    public static void openOldInterface(Player player) {
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
        /*InterfaceHandler.register(Interface.TASKS, (h -> {
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
        }));*/
        InterfaceHandler.register(657, (h -> {
            h.actions[3] = (SimpleAction) TaskInterface::close;
            h.actions[6] = (SlotAction) TaskInterface::navigation;
            h.actions[35] = (SlotAction) (player, slot) -> TIER_FILTER.set(player, slot - 1);
            h.actions[36] = (SlotAction) (player, slot) -> TYPE_FILTER.set(player, slot - 1);
            h.actions[37] = (SlotAction) (player, slot) -> AREA_FILTER.set(player, slot - 1);
            h.actions[39] = (SlotAction) (player, slot) -> COMPLETED_FILTER.set(player, slot - 1);
        }));
    }
}

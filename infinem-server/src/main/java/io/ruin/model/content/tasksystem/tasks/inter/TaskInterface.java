package io.ruin.model.content.tasksystem.tasks.inter;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/30/2023
 */
public class TaskInterface {

    private static void handleToolsClick(Player player, int slot) {
        if (slot == 30)
            player.stringInput("Search:", search -> {
                player.getTaskManager().searchString = search;
                player.getTaskManager().openTaskInterface();
            });
        if (slot == 35) {
            handleDropdownButton(player, 5, (byte) 1);
        }
        if (slot == 40) {
            handleDropdownButton(player, 6, (byte) 2);
        }
        if (slot == 45) {
            handleDropdownButton(player, 7, (byte) 3);
        }
        if (slot == 50) {
            handleDropdownButton(player, 8, (byte) 4);
        }
        if (slot == 55) {
            handleDropdownButton(player, 9, (byte) 5);
        }
    }

    private static void handleFilterClick(Player player, int childId, int slot) {
        int index = (slot/2) - 1;
        switch (childId) {
            case 5:
                player.getTaskManager().setRegionFilter((byte) index);
                break;
            case 6:
                player.getTaskManager().setSkillFilter((byte) index);
                break;
            case 7:
                player.getTaskManager().setTierFilter((byte) index);
                break;
            case 8:
                player.getTaskManager().setCompletedFilter((byte) index);
                break;
            case 9:
                player.getTaskManager().setSortBy((byte) index);
                break;
        }
        hideAllPopups(player);
        player.getTaskManager().taskFilterDropdownOpen = 0;
        player.getTaskManager().openTaskInterface();
    }

    private static void handleDropdownButton(Player player, int childId, byte index) {
        if (player.getTaskManager().taskFilterDropdownOpen == index) {
            player.getTaskManager().taskFilterDropdownOpen = 0;
            player.getPacketSender().setHidden(383, childId, true);
            return;
        }
        hideAllPopups(player);
        player.getPacketSender().setHidden(383, childId, false);
        player.getTaskManager().taskFilterDropdownOpen = index;
    }

    private static void hideAllPopups(Player player) {
        player.getPacketSender().setHidden(383, 5, true);
        player.getPacketSender().setHidden(383, 6, true);
        player.getPacketSender().setHidden(383, 7, true);
        player.getPacketSender().setHidden(383, 8, true);
        player.getPacketSender().setHidden(383, 9, true);
    }

    private static void handlePopulClick(Player player, int slot) {

    }

    static {
        InterfaceHandler.register(383, (h -> {
            h.actions[1] = (SlotAction) TaskInterface::handleToolsClick;
            h.actions[2] = (SlotAction) TaskInterface::handlePopulClick;
            h.actions[5] = (SlotAction) (player, slot) -> handleFilterClick(player, 5, slot);
            h.actions[6] = (SlotAction) (player, slot) -> handleFilterClick(player, 6, slot);
            h.actions[7] = (SlotAction) (player, slot) -> handleFilterClick(player, 7, slot);
            h.actions[8] = (SlotAction) (player, slot) -> handleFilterClick(player, 8, slot);
            h.actions[9] = (SlotAction) (player, slot) -> handleFilterClick(player, 9, slot);
        }));
    }
}

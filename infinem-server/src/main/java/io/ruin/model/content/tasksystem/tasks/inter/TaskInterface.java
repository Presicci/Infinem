package io.ruin.model.content.tasksystem.tasks.inter;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SlotAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/30/2023
 */
public class TaskInterface {

    private static void handleClick(Player player, int slot) {
        if (slot == 30)
            player.stringInput("Search:", search -> player.getTaskManager().openTaskInterface(search));
    }

    static {
        InterfaceHandler.register(383, (h -> {
            h.actions[1] = (SlotAction) TaskInterface::handleClick;
        }));
    }
}

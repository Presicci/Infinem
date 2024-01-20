package io.ruin.model.content.tasksystem.inter;

import io.ruin.model.content.tasksystem.relics.inter.RelicInterface;
import io.ruin.model.content.tasksystem.tasks.inter.TaskInterface;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.journal.JournalTab;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/20/2024
 */
public class TabTask {

    public static void refresh(Player player) {
        JournalTab.setTab(player, JournalTab.Tab.TASK);
    }

    static {
        InterfaceHandler.register(Interface.TASK_TAB, h -> {
            //h.actions[20] = (SimpleAction) player ->
            h.actions[24] = (SimpleAction) TaskInterface::openTaskInterface;
            h.actions[28] = (SimpleAction) player -> JournalTab.setTab(player, JournalTab.Tab.ACHIEVEMENT);
            h.actions[32] = (SimpleAction) RelicInterface::open;
        });
    }
}

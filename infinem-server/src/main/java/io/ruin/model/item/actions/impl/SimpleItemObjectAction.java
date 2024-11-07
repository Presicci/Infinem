package io.ruin.model.item.actions.impl;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/6/2024
 */
@AllArgsConstructor
public enum SimpleItemObjectAction {
    BROKEN_ZOMBIE_AXE(28813, "anvil", (player, item, object) -> {
        if (!player.getStats().check(StatType.Smithing, 70, "repair that")) return;
        if (!SmithBar.hasHammer(player)) {
            player.dialogue(new MessageDialogue("You need a hammer to repair the axe."));
            return;
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(SmithBar.getHammerAnim(player));
            e.delay(2);
            item.setId(28810);
            player.dialogue(new MessageDialogue("You skillfully repair the axe."));
            player.unlock();
        });
    });

    private final int itemId;
    private final String objectName;
    private final ItemObjectAction action;

    static {
        for (SimpleItemObjectAction a : values()) {
            ItemObjectAction.register(a.itemId, a.objectName, a.action);
        }
    }
}
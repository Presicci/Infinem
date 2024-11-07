package io.ruin.model.item.actions.impl;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/6/2024
 */
@AllArgsConstructor
public enum SimpleItemAction {
    BROKEN_ZOMBIE_AXE(28813, 2, (player, item) -> {
        if (player.getStats().get(StatType.Smithing).currentLevel < 70) {
            player.dialogue(new ItemDialogue().one(28813, "The axe isn't in a great state, but it should be possible to repair it. However, you unfortunately don't feel you have the skills needed to do so yourself. To repair the axe, you'll need 70 Smithing."));
        } else {
            player.dialogue(new ItemDialogue().one(28813, "The axe isn't in a great state, but it should be possible to repair it. You reckon you have the skills needed to do so yourself. You'll just need a hammer and an anvil."));
        }
    });

    private final int itemId, optionIndex;
    private final ItemAction itemAction;

    static {
        for (SimpleItemAction a : values()) {
            ItemAction.registerInventory(a.itemId, a.optionIndex, a.itemAction);
        }
    }
}

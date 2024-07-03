package io.ruin.model.entity.npc.actions;

import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
public enum ItemOnNPCDialogue {
    AMY(7417,
            new ItemDialogue("That's a plank, you'll need a lot of those.", Items.PLANK, Items.OAK_PLANK, Items.TEAK_PLANK, Items.MAHOGANY_PLANK),
            new ItemDialogue("That's a saw. It's pretty useful.", Items.SAW),
            new ItemDialogue("That's a fascinating looking saw, it looks like it would break though.", Items.CRYSTAL_SAW),
            new ItemDialogue("Oh you have have one of my saws, I hope you enjoy it!", 24880),
            new ItemDialogue("That's a hammer. It's pretty useful.", Items.HAMMER)
    )
    ;

    ItemOnNPCDialogue(int npcId, ItemDialogue... itemDialogues) {
        for (ItemDialogue itemDialogue : itemDialogues) {
            for (int itemId : itemDialogue.itemIds) {
                ItemNPCAction.register(itemId, npcId, (player, item, npc) -> player.dialogue(new NPCDialogue(npcId, itemDialogue.dialogue)));
            }
        }
    }

    private static class ItemDialogue {
        private final String dialogue;
        private final int[] itemIds;

        private ItemDialogue(String dialogue, int... itemIds) {
            this.dialogue = dialogue;
            this.itemIds = itemIds;
        }
    }
}

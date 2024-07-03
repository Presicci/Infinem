package io.ruin.model.entity.npc.actions;

import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.actions.ItemNPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
public enum ItemOnNPCDialogue {
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

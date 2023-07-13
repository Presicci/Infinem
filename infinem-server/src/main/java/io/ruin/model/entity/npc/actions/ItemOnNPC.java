package io.ruin.model.entity.npc.actions;

import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
public enum ItemOnNPC {

    GOAT_POO_UGGTHANKI(8452, Items.UGTHANKI_DUNG, (player, item, npc) -> player.dialogue(new NPCDialogue(npc, "You t'ink I want that? It not even from goat!"))),
    GOAT_POO_GOAT(8452, 22590, (player, item, npc) -> player.dialogue(new NPCDialogue(npc, "Look, human, it were one time. Just once. Let a troll move on, okay?"))),
    GOAT_POO_BUCKET(8452, Items.BUCKET, (player, item, npc) -> player.dialogue(new NPCDialogue(npc, "Look, human, this not funny. You need goat poo in bucket, go find goat pen!")));

    ItemOnNPC(int npcId, int itemId, ItemNPCAction action) {
        ItemNPCAction.register(itemId, npcId, action);
    }
}

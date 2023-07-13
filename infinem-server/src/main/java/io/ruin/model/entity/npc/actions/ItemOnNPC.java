package io.ruin.model.entity.npc.actions;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
public enum ItemOnNPC {
    SNOWFLAKE_GOAT_DUNG(8431, 22590, (player, item, npc) -> player.dialogue(new NPCDialogue(npc, "No thank you, [player name], I do not need more poo in a bucket from you."))),
    SNOWFLAKE_BASALT(8431, 22603, (player, item, npc) -> {
        int amt = player.getInventory().getAmount(22603);
        player.getInventory().remove(22603, amt);
        player.getInventory().add(22604, amt);
        player.dialogue(new ItemDialogue().one(22603, "Snowflake converts your " + (amt > 1 ? "items" : "item") + " to " + (amt > 1 ? "banknotes" : "a banknote") + "."));
    }),
    GOAT_POO_UGGTHANKI(8452, Items.UGTHANKI_DUNG, (player, item, npc) -> player.dialogue(new NPCDialogue(npc, "You t'ink I want that? It not even from goat!"))),
    GOAT_POO_GOAT(8452, 22590, (player, item, npc) -> player.dialogue(new NPCDialogue(npc, "Look, human, it were one time. Just once. Let a troll move on, okay?"))),
    GOAT_POO_BUCKET(8452, Items.BUCKET, (player, item, npc) -> player.dialogue(new NPCDialogue(npc, "Look, human, this not funny. You need goat poo in bucket, go find goat pen!")));

    ItemOnNPC(int npcId, int itemId, ItemNPCAction action) {
        ItemNPCAction.register(itemId, npcId, action);
    }
}

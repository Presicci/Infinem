package io.ruin.model.entity.npc.actions.varlamore;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.skills.prayer.Bone;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/20/2024
 */
public class Virilis {

    private static void unnote(Player player, Item item, NPC npc) {
        ItemDefinition def = item.getDef();
        if (!def.isNote()) {
            player.dialogue(new NPCDialogue(npc, "I'm afraid I can only exchange bank notes for real items."));
            return;
        }
        if (Bone.get(def.notedId) == null) {
            player.dialogue(new NPCDialogue(npc, "I only exchange bones that you can bless at Ralos' altar."));
            return;
        }
        if (player.getInventory().getAmount(995) < 10) {
            player.dialogue(new NPCDialogue(npc, "I require 10 coins for exchanging each banknote."));
            return;
        }
        if (player.getInventory().isFull()) {
            player.dialogue(new NPCDialogue(npc, "Your inventory is too full."));
            return;
        }
        int amt = Math.min(Math.min(item.getAmount(), player.getInventory().getFreeSlots()), player.getInventory().getAmount(995) / 10);
        player.getInventory().remove(995, amt * 10);
        player.getInventory().remove(item.getId(), amt);
        player.getInventory().add(def.notedId, amt);
    }

    static {
        ItemNPCAction.register(13346, Virilis::unnote);
    }
}

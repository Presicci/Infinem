package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
public class LootingBagNote {
    private static int LOOTING_BAG = 11941;

    static {
        ItemAction.registerInventory(24585, "read", LootingBagNote::readNote);
    }

    private static void readNote(Player player, Item item) {
        player.dialogue(
                new ItemDialogue().one(LOOTING_BAG, "The bank of Gielinor promises to pay the bearer,<br>" +
                        " on demand, one Looting Bag, provided the bearer is<br> eligible to receive one.<br> Use this note on a bank booth to exchange it.")
        );
    }

    public static void exchangeNote(Player player, Item item, NPC npc) {
        if (hasLootingBag(player)) {
            if (npc != null) {
                player.dialogue(new NPCDialogue(npc, "You already have one of those."));
            } else {
                player.dialogue(new MessageDialogue("You already have one of those."));
            }
            return;
        }
        if (!player.getInventory().hasRoomFor(LOOTING_BAG)) {
            if (npc != null) {
                player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space to claim that."));
            } else {
                player.dialogue(new MessageDialogue("You don't have enough inventory space to claim that."));
            }
            return;
        }
        item.remove(1);
        player.getInventory().add(LOOTING_BAG, 1);
        player.dialogue(new ItemDialogue().one(LOOTING_BAG, "You exchange the promissory note."));
    }

    private static boolean hasLootingBag(Player p) {
        return p.getInventory().contains(11941) || p.getInventory().contains(22586) || p.getBank().contains(11941) || p.getBank().contains(22586);
    }
}

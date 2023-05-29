package io.ruin.model.entity.npc.actions;

import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.actions.ItemNPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/29/2023
 */
public class Phials {
    static {
        ItemNPCAction.register(1614, ((player, item, npc) -> {
            ItemDef def = item.getDef();
            if (def.isNote()) {
                ItemDef normal = def.fromNote();
                int amount = item.getAmount();
                int freeSpace = player.getInventory().getFreeSlots();
                if (amount == freeSpace + 1)
                    freeSpace += 1;
                if (freeSpace == 0) {
                    player.dialogue(new NPCDialogue(npc, "Free up some inventory space before you try to exchange banknotes."));
                    return;
                }
                if (freeSpace < amount)
                    amount = freeSpace;
                int amountOfCoins = player.getInventory().getAmount(995);
                if (amountOfCoins < 5) {
                    player.dialogue(new NPCDialogue(npc, "That'll cost 5 coins per banknote that you want me to exchange."));
                    return;
                }
                if (amount * 5 > amountOfCoins) {
                    amount = (int) Math.floor((double) amountOfCoins / 5);
                }
                item.remove(amount);
                player.getInventory().remove(995, amount * 5);
                player.getInventory().add(normal.id, amount);
                player.dialogue(new NPCDialogue(npc, "Here is " + Color.RED.wrap(amount + "") + " items in exchange for " + Color.RED.wrap((amount * 5) + "") + " coins."));
            } else {
                player.dialogue(new NPCDialogue(npc, "I can only help you if you provide me with banknotes."));
            }
        }));
    }
}

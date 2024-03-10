package io.ruin.model.entity.npc.actions.desert;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/9/2024
 */
public class Shantay {

    static {
        NPCAction.register(4642, "buy-pass", (player, npc) -> {
            int coins = player.getInventory().getAmount(995);
            if (coins < 5) {
                player.dialogue(new NPCDialogue(npc, "You need 5 coins to purchase a pass."));
                return;
            }
            if (!player.getInventory().hasRoomFor(Items.SHANTAY_PASS) && coins > 5) {
                player.dialogue(new NPCDialogue(npc, "You need more space to hold a pass."));
                return;
            }
            player.getInventory().remove(995, 5);
            player.getInventory().add(Items.SHANTAY_PASS, 1);
            player.dialogue(new NPCDialogue(npc, "Thank you for your business."));
        });
    }
}

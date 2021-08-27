package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.DialogueAnimations;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.actions.ItemNPCAction;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/2/2020
 */
public class FlaxKeeper {

    private static final int FLAX = 1779, NOTED_BOWSTRING = 1778, FLAX_KEEPER = 5522;

    private static void convert(Player player) {
        int amountToConvert = player.getInventory().getAmount(FLAX);
        int moneyCarried = player.getInventory().getAmount(995);
        if (amountToConvert <= 0) {
            player.dialogue(new NPCDialogue(FLAX_KEEPER, "You do not have any flax for me to exchange.").animate(588));
            return;
        }
        if (amountToConvert > (moneyCarried / 50)) {
            amountToConvert = moneyCarried / 50;
        }
        if (amountToConvert <= 0) {
            player.dialogue(new NPCDialogue(FLAX_KEEPER, "No way I am doing that for free!  Spinning flax is some of the worst content in this game.  All I am asking for is 50 gp...").animate(DialogueAnimations.SAD));
            return;
        }
        player.getInventory().remove(FLAX, amountToConvert);
        player.getInventory().remove(995, amountToConvert * 50);
        player.getInventory().add(NOTED_BOWSTRING, amountToConvert);
        player.dialogue(new NPCDialogue(FLAX_KEEPER, "Thank you! Come again.").animate(557));
    }

    static {
        NPCAction.register(FLAX_KEEPER, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "Hello there!  If you need me to, I can exchange flax for noted bowstring at 50 gp per.").animate(557));
        });
        NPCAction.register(FLAX_KEEPER, "Exchange", (player, npc) -> convert(player));
        ItemNPCAction.register(FLAX, FLAX_KEEPER, (player, item, npc) -> convert(player));
    }
}

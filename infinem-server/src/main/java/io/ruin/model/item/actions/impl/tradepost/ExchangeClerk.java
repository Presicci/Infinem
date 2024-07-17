package io.ruin.model.item.actions.impl.tradepost;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.ItemSet;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/17/2024
 */
public class ExchangeClerk {

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Welcome to the Grand Exchange. Would you like to trade now, or exchange item sets?"),
                new OptionsDialogue(
                        new Option("I'd like to set up trade offers please.", () -> player.getTradePost().openViewOffers()),
                        new Option("Can you help me with item sets?", () -> ItemSet.open(player)),
                        new Option("I'm fine, thanks.", new PlayerDialogue("I'm fine, thanks."))
                )
        );
    }

    static {
        NPCAction.register(2148, "talk-to", ExchangeClerk::dialogue);
        NPCAction.register(2149, "talk-to", ExchangeClerk::dialogue);
        NPCAction.register(2150, "talk-to", ExchangeClerk::dialogue);
        NPCAction.register(2151, "talk-to", ExchangeClerk::dialogue);
    }
}

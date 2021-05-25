package io.ruin.model.entity.npc.actions.tzhaar;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class TzHaarMejRoh {

    static {
        NPCAction.register(2185, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Can I help you JalYt-Xil-" + player.getName() + "?"),
                new OptionsDialogue(
                        new Option("What do you have to trade?", NPCDef.get(2185).shops.get(0)::open),
                        new Option("No I'm fine thanks.", () -> player.dialogue(new PlayerDialogue("No I'm fine thanks.")))
                )
        ));
    }
}

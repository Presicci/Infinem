package io.ruin.model.entity.npc.actions.tzhaar;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class TzHaarHurLek {

    static {
        NPCAction.register(2184, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Can I help you JalYt-Xil-" + player.getName() + "?"),
                new OptionsDialogue(
                        new Option("What do you have to trade?", NPCDefinition.get(2184).shops.get(0)::open),
                        new Option("No I'm fine thanks.", () -> player.dialogue(new PlayerDialogue("No I'm fine thanks.")))
                )
        ));
    }
}

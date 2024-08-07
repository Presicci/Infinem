package io.ruin.model.entity.npc.actions.home;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/7/2024
 */
public class VoteManager {

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Greetings, " + player.getName() + ". I am the vote manager here at Infinem. How can I help you?"),
                new OptionsDialogue(
                        new Option("How do I vote?", new NPCDialogue(npc, "Head over to www.infinem.net/vote/ and follow the instructions. Once you've voted on however many lists as you want, come back in game and type ::claim.")),
                        new Option("How do I claim my vote tickets?", new NPCDialogue(npc, "Once you've voted on however many lists as you want, come back in game and type ::claim.")),
                        new Option("Open the vote ticket shop", () -> npc.openShop(player))
                )
        );
    }

    static {
        NPCAction.register(15000, "talk-to", VoteManager::dialogue);
    }
}

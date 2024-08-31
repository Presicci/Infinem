package io.ruin.model.entity.npc.actions.home;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.services.Votes;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/7/2024
 */
public class VoteManager {

    private static final String VOTE_URL = World.type.getWebsiteUrl() + "/vote";

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Greetings, " + player.getName() + ". I am the vote manager here at Infinem. How can I help you?"),
                new OptionsDialogue(
                        new Option("How do I vote?",
                                new NPCDialogue(npc, "Head over to www.infinem.net/vote/ and follow the instructions. Once you've voted on however many lists as you want, come back in game and type ::claim."),
                                new OptionsDialogue("Would you like to open our voting page?",
                                        new Option("Yes", () -> player.openUrl("Voting Page", VOTE_URL)),
                                        new Option("No", player::closeDialogue)
                                )
                        ),
                        new Option("Claim votes", () -> Votes.claim(player, npc)),
                        new Option("Open the vote ticket shop", () -> npc.openShop(player))
                )
        );
    }

    static {
        NPCAction.register(15000, "talk-to", VoteManager::dialogue);
        NPCAction.register(15000, "claim-votes", Votes::claim);
    }
}

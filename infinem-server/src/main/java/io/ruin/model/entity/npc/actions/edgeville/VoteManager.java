package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.Random;
import io.ruin.utility.Color;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.services.Votes;
import io.ruin.utility.Broadcast;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.process.event.EventWorker.startEvent;

public class VoteManager {

    private static final String VOTE_URL = World.type.getWebsiteUrl() + "/vote";

    private static int voteMysteryBoxesClaimed = 0;

    static {
        NPCAction.register(1815, "cast-votes", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Would you like to open our voting page?",
                            new Option("Yes", () -> player.openUrl("Voting Page", VOTE_URL)),
                            new Option("No", player::closeDialogue)
                    )
            );
        });
        NPCAction.register(1815, "claim-votes", (player, npc) -> {
            Votes.claim(player, npc, (claimed, runelocus) -> {
                if(claimed == -1 || runelocus == -1) {
                    player.dialogue(new NPCDialogue(npc, "Error claiming votes, please try again."));
                    return;
                }
                if(claimed == 0 && runelocus == 0) {
                    player.dialogue(new NPCDialogue(npc, "No unclaimed votes found."));
                    return;
                }
                if (runelocus > 0) {
                    player.getInventory().add(4067, runelocus * 5);
                    player.getInventory().addOrDrop(new Item(621, 2));
                }

                if (claimed > 0) {
                    player.getInventory().add(4067, claimed * 3);
                }

                player.claimedVotes += (claimed + runelocus);
                player.dialogue(new NPCDialogue(npc, "You've successfully claimed " + (claimed + runelocus) + " vote" + ((claimed + runelocus) > 1 ? "s" : "") + "!"));
                player.sendFilteredMessage(Color.COOL_BLUE.wrap("You receive " + (claimed * 3) + " vote ticket" + ((claimed + runelocus) > 1 ? "s" : "") + " for voting."));
            });
        });
        startEvent(e -> {
            while(true) {
                e.delay(3000); //30 minutes
                if(voteMysteryBoxesClaimed > 1) {
                    Broadcast.WORLD.sendNews(Icon.ANNOUNCEMENT, "Another " + voteMysteryBoxesClaimed + " players have claimed their FREE Double Experience Scroll! Type ::vote and claim yours now!");
                    voteMysteryBoxesClaimed = 0;
                }
            }
        });
    }

}

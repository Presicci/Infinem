package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import kilim.analysis.Utils;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/8/2023
 */
public class SimonTempleton {

    private static final int PYRAMID_TOP_REWARD = 10_000;

    private static final int SIMON = 5786;

    static {
        NPCAction.register(SIMON, "talk-to", ((player, npc) -> {
            if (player.getSpokenToNPCSet().contains(SIMON)) {
                dialogue(player, npc);
            } else {
                firstTime(player, npc);
            }
        }));
    }

    private static void dialogue(Player player, NPC npc) {
        if (player.getInventory().hasId(Items.PYRAMID_TOP)) {
            player.dialogue(
                    new NPCDialogue(SIMON, "G'day, mate. Got any new pyramid artefacts for me?"),
                    new PlayerDialogue("No, I haven't."),
                    new NPCDialogue(SIMON, "Well, keep my offer in mind if you do find one."),
                    new PlayerDialogue("I will. Goodbye, Simon."),
                    new NPCDialogue(SIMON, "Bye, mate.")
            );
        } else {
            player.dialogue(
                    new NPCDialogue(SIMON, "G'day, mate. Got any new pyramid artefacts for me?"),
                    new OptionsDialogue(
                            new Option("Sell them.", () -> {
                                int amt = player.getInventory().getAmount(Items.PYRAMID_TOP);
                                player.getInventory().remove(Items.PYRAMID_TOP, amt);
                                player.getInventory().add(995, PYRAMID_TOP_REWARD);
                                player.dialogue(
                                        new ItemDialogue().one(Items.PYRAMID_TOP, "You hand over the artefact" + ((amt > 1) ? "s" : "") + " and Simon hands you " + NumberUtils.formatNumber((long) amt * PYRAMID_TOP_REWARD) + " coins."),
                                        new NPCDialogue(SIMON, "Ripper! Thanks a bundle, mate! Thanks to you I can fulfill me contract. You're a true blue! The boss will be pleased."),
                                        new PlayerDialogue("Glad I was able to help... but who is your boss? I thought you worked for the museum?"),
                                        new NPCDialogue(SIMON, "Mind your own bizzo, mate. But if you get any more, you know where I am!")
                                );
                            }),
                            new Option("Keep them.", () -> {
                                player.dialogue(
                                        new PlayerDialogue("I have some, but I want to keep them for now."),
                                        new NPCDialogue(SIMON, "Careful, mate, people might come looking for a thing like that!"),
                                        new PlayerDialogue("Thanks for the advice, but I'll hang onto it for now."),
                                        new NPCDialogue(SIMON, "Bye, cobber.")
                                );
                            })
                    )
            );
        }
    }

    private static void firstTime(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Hello."),
                new NPCDialogue(SIMON, "G'day, mate. I'm Simon - Simon Templeton. Who are you?"),
                new PlayerDialogue("I'm " + player.getName() + ". What are you doing all the way out here?"),
                new NPCDialogue(SIMON, "I've been contracted to retrieve artefacts from the top of this magnificent pyramid."),
                new PlayerDialogue("So, why are you down here and not up there then?"),
                new NPCDialogue(SIMON, "Well, it's me back - an old injury I picked up in Sophanem has come back to haunt me. I was working for the Museum of Varrock, and the gold I had been given wasn't enough to pay for the carpet rides, let"),
                new NPCDialogue(SIMON, "alone any decent equipment. But that was a long time ago... I can't even lift a chisel right now. There's no way I can climb all the way up there."),
                new PlayerDialogue("How on earth did you get down the cliff face, then?"),
                new NPCDialogue(SIMON, "That's what set off the injury. I was fine before I scrabbled down that pesky slope. I think it will be a while before I can climb back up again; I must be getting old."),
                new NPCDialogue(SIMON, "Hang on a second... You look like you're pretty agile and up for a challenge. How about you retrieve it for me?"),
                new NPCDialogue(SIMON, "I'll tell you what, I'll give you 10000 coins for every artefact you get for me."),
                new ActionDialogue(() -> {
                    player.getSpokenToNPCSet().add(SIMON);
                    player.dialogue(
                            new PlayerDialogue("I'll see what I can do."),
                            new NPCDialogue(SIMON, "Good on ya, mate!")
                    );
                })
        );
    }
}

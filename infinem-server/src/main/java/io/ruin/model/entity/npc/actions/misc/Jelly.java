package io.ruin.model.entity.npc.actions.misc;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/18/2022
 */
public class Jelly {

    private static final int JELLY = 7518;

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(JELLY, "Welcome to Jelly's Kurask cave."),
                new ActionDialogue(() -> options(player, npc))
        );
    }

    private static void options(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Who are you?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Who are you?"),
                                    new NPCDialogue(JELLY, "I am Jelly."),
                                    new NPCDialogue(JELLY, "When Jelly was just a baby troll, parents left him in Slayer cave... " +
                                            "But Jelly is strong, Jelly learn to survive in the cave, and one day Jelly will become great Slayer master!"),
                                    new ActionDialogue(() -> options(player, npc))
                            );
                        }),
                        new Option("Why is this area private?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Why is this area private?"),
                                    new NPCDialogue(JELLY, "When the Kurasks finded new treasures, many humans come to the Slayer cave to hunt them... " +
                                            "No Kurasks left for Jelly to train on, so Jelly makes a private area just for training. These be Jelly's very own Kurask!"),
                                    new NPCDialogue(JELLY, "Not just any human gets to train up here, you has to be on a Kurask Slayer task."),
                                    new ActionDialogue(() -> options(player, npc))
                            );
                        }),
                        new Option("What kind of a name is Jelly?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("What kind of a name is Jelly?"),
                                    new NPCDialogue(JELLY, "Jelly grew up in this cave, there not much choice around for a hungry troll..."),
                                    new ActionDialogue(() -> options(player, npc))
                            );
                        }),
                        new Option("Thanks, goodbye.")
                )
        );
    }

    static {
        NPCAction.register(JELLY, "talk-to", Jelly::dialogue);
    }
}

package io.ruin.model.entity.npc.actions;

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
 * Created on 11/4/2023
 */
public class CartConductor {
    private static final int[] KELDAGRIM = { 2385, 2386, 2387, 2388, 2392 };
    private static final int[] NON_KELDAGRIM = {
            2390,   // White wolf mountain
            2391    // Ice mountain
    };

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Yes [sir/m'am]? Can I help you at all?"),
                new OptionsDialogue(
                        new Option("Where can you take me?",
                                new PlayerDialogue("Where can you take me?"),
                                new NPCDialogue(npc, "This cart will take you to Keldagrim."),
                                new PlayerDialogue("Thank you.")
                        ),
                        new Option("I have to go.", new PlayerDialogue("I have to go."), new NPCDialogue(npc, "Just remember, wherever you go, you go there faster through Keldagrim Carts."))
                )
        );
    }

    private static void keldagrimDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Yes [sir/m'am]? Can I help you at all?"),
                new ActionDialogue(() -> keldagrimOptions(player, npc))
        );
    }

    private static void keldagrimOptions(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("What is this place?",
                                new PlayerDialogue("What is this place?"),
                                new NPCDialogue(npc, "You're in the main waiting area of Keldagrim Carts- station."),
                                new PlayerDialogue("Cart-station? What goes on here then?"),
                                new NPCDialogue(npc, "Keldagrim is the hub of all the traffic of the dwarven realm. Almost every dwarven outpost has railtracks leading to Keldagrim, connecting them all for easy travel and transportation of goods."),
                                new PlayerDialogue("Amazing! And what powers the carts? Magic?"),
                                new NPCDialogue(npc, "Oh, no no, we dwarves don't use magic. No, all of this is powered by steam engines, developed during the past few centuries after the Consortium came to power."),
                                new PlayerDialogue("Thanks for the info."),
                                new ActionDialogue(() -> keldagrimOptions(player, npc))
                        ),
                        new Option("Where can you take me?",
                                new PlayerDialogue("Where can you take me?"),
                                new NPCDialogue(npc, "Track 1 will take you to Ice Mountain. Track 3 will take you to the Grand Exchange. Track 4 will take you to White Wolf Mountain."),
                                new ActionDialogue(() -> keldagrimOptions(player, npc))
                        ),
                        new Option("I have to go.", new PlayerDialogue("I have to go."), new NPCDialogue(npc, "Just remember, wherever you go, you go there faster through Keldagrim Carts."))
                )
        );
    }

    private static void tickets(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("I'd like to buy a cart ticket."),
                new NPCDialogue(npc, "Oh no need! These carts are currently in the experimental phase. We would never charge for you testing them out."),
                new PlayerDialogue("Experimental? Are they safe."),
                new NPCDialogue(npc, "They wouldn't be experimental if they were. Have a safe ride!")
        );
    }

    static {
        for (int id : KELDAGRIM) {
            NPCAction.register(id, "talk-to", CartConductor::keldagrimDialogue);
            NPCAction.register(id, "tickets", CartConductor::tickets);
        }
        for (int id : NON_KELDAGRIM) {
            NPCAction.register(id, "talk-to", CartConductor::dialogue);
            NPCAction.register(id, "tickets", CartConductor::tickets);
        }
    }
}

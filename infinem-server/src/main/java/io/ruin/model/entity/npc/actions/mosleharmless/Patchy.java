package io.ruin.model.entity.npc.actions.mosleharmless;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
public class Patchy {

    public final static int PATCHY = 1053;

    private static void noThanks(Player player, NPC npc){
        player.dialogue(
                new PlayerDialogue("No thanks!"),
                new NPCDialogue(npc, "Alright matey! Just come back if you need anything sewed up.")
        );
    }

    static {
        NPCAction.register(PATCHY, "Sew", (player, npc) -> {
            player.dialogue(SewableItems.getDialogue(player, npc));
        });
        NPCAction.register(PATCHY, "Talk-To" ,(player, npc) ->{
            player.dialogue(
                    new NPCDialogue(npc, "Ahoy matey, do you need anything sewed together?"),
                    new PlayerDialogue("Uhhmmm, how much does it cost?"),
                    new NPCDialogue(npc, "Only 100,000 coins."),
                    new OptionsDialogue(
                            new Option("No thanks!", () -> noThanks(player, npc)),
                            new Option("Sure!", () -> player.dialogue(SewableItems.getDialogue(player, npc)))
                    )
            );
        });
    }
}

package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/10/2024
 */
public class BrotherTranquility {

    private static void talkTo(Player player, NPC npc) {
        if (npc.getAbsX() < 3710) { // Mos Le'Harmless
            player.dialogue(
                    new NPCDialogue(npc, "Would you like me to take you to Harmony?"),
                    new OptionsDialogue(
                            new Option("Yes, please.", () -> transport(player, npc)),
                            new Option("Not at the moment, thanks.", new PlayerDialogue("Not at the moment, thanks."))
                    )
            );
        } else {    // Harmony
            player.dialogue(
                    new NPCDialogue(npc, "Do you want me to take you back to Mos Le'Harmless??"),
                    new OptionsDialogue(
                            new Option("Yes, please.", () -> transport(player, npc)),
                            new Option("Not at the moment, thanks.", new PlayerDialogue("Not at the moment, thanks."))
                    )
            );
        }
    }

    private static void transport(Player player, NPC npc) {
        if (npc.getAbsX() < 3710) { // Mos Le'Harmless
            ModernTeleport.teleport(player, new Position(3787, 2822, 0));
            player.getTaskManager().doLookupByUUID(712);    // Visit Harmony Island
        } else {    // Harmony
            ModernTeleport.teleport(player, new Position(3679, 2964, 0));
        }
    }

    static {
        NPCAction.register(550, "talk-to", BrotherTranquility::talkTo);
        NPCAction.register(550, "transport", BrotherTranquility::transport);
    }
}

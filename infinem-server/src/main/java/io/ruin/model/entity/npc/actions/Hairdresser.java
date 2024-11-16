package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.Style;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.makeover.MakeoverInterface;
import io.ruin.model.inter.handlers.makeover.MakeoverType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import lombok.val;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/12/2023
 */
public class Hairdresser {

    private static final int PRICE = 0;

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Good afternoon [madam/sir]. In need of a haircut are we? Or perhaps a shave? Currently we're offering this service for " + (PRICE == 0 ? "free!" : NumberUtils.formatNumber(PRICE) + " coins.")),
                new OptionsDialogue(
                        new Option("I'd like a haircut please.",
                                new PlayerDialogue("I'd like a haircut please."),
                                new NPCDialogue(npc, "Please select the hairstyle you would like from this brochure. I'll even throw in a free recolour."),
                                new ActionDialogue(() -> MakeoverInterface.open(player, MakeoverType.HAIR, npc))),
                        new Option("I'd like a shave please.",
                                new PlayerDialogue("I'd like a shave please."),
                                new NPCDialogue(npc, "Please select the facial hair you would like from this brochure. I'll even throw in a free recolour."),
                                new ActionDialogue(() -> MakeoverInterface.open(player, MakeoverType.FACIAL_HAIR, npc))),
                        new Option("No thank you.",
                                new PlayerDialogue("No thank you."),
                                new NPCDialogue(npc, "Very well. Come back if you change your mind."))
                )
        );
    }

    static {
        NPCAction.register(1305, "talk-to", Hairdresser::dialogue);
        NPCAction.register(1305, "haircut", ((player, npc) -> {
            player.dialogue(new OptionsDialogue("What would you like?",
                    new Option("Haircut", () -> MakeoverInterface.open(player, MakeoverType.HAIR, npc)),
                    new Option("Shave", () -> MakeoverInterface.open(player, MakeoverType.FACIAL_HAIR, npc))
            ));
        }));
        NPCAction.register(12116, "talk-to", Hairdresser::dialogue);
        NPCAction.register(12116, "haircut", (player, npc) -> MakeoverInterface.open(player, MakeoverType.HAIR, npc));
        NPCAction.register(12116, "shave", (player, npc) -> MakeoverInterface.open(player, MakeoverType.FACIAL_HAIR, npc));
        NPCAction.register(13042, "talk-to", Hairdresser::dialogue);
        NPCAction.register(13042, "haircut", (player, npc) -> MakeoverInterface.open(player, MakeoverType.HAIR, npc));
        NPCAction.register(13042, "shave", (player, npc) -> MakeoverInterface.open(player, MakeoverType.FACIAL_HAIR, npc));
    }
}

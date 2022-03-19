package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class MonkOfEntrana {

    private static void dialogueToEntrana(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc.getId(), "Would you like a ride over to Entrana?"),
                new OptionsDialogue(
                        new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2834, 3335))),
                        new Option("No")
                )
        );
    }

    private static void travelToEntrana(Player player) {
        Traveling.fadeTravel(player, new Position(2834, 3335));
    }

    private static void dialogueToSarim(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc.getId(), "Would you like a ride over to Port Sarim?"),
                new OptionsDialogue(
                        new Option("Sure", () -> Traveling.fadeTravel(player, new Position(3048, 3234))),
                        new Option("No")
                )
        );
    }

    private static void travelToSarim(Player player) {
        Traveling.fadeTravel(player, new Position(3048, 3234));
    }

    static {
        NPCAction.register(1167, "talk-to", MonkOfEntrana::dialogueToEntrana);
        NPCAction.register(1167, "take-boat", ((player, npc) -> travelToEntrana(player)));
        NPCAction.register(1166, "talk-to", MonkOfEntrana::dialogueToEntrana);
        NPCAction.register(1166, "take-boat", ((player, npc) -> travelToEntrana(player)));
        NPCAction.register(1165, "talk-to", MonkOfEntrana::dialogueToEntrana);
        NPCAction.register(1165, "take-boat", ((player, npc) -> travelToEntrana(player)));

        NPCAction.register(1169, "talk-to", MonkOfEntrana::dialogueToSarim);
        NPCAction.register(1169, "take-boat", ((player, npc) -> travelToSarim(player)));
        NPCAction.register(1168, "talk-to", MonkOfEntrana::dialogueToSarim);
        NPCAction.register(1168, "take-boat", ((player, npc) -> travelToSarim(player)));
        NPCAction.register(1170, "talk-to", MonkOfEntrana::dialogueToSarim);
        NPCAction.register(1170, "take-boat", ((player, npc) -> travelToSarim(player)));

        ObjectAction.register(2412, 3048, 3233, 0, "cross", (player, obj) -> travelToEntrana(player));
        ObjectAction.register(2414, 2834, 3334, 0, "cross", (player, obj) -> travelToSarim(player));
    }
}

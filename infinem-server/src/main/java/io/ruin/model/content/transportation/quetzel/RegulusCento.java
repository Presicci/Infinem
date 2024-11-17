package io.ruin.model.content.transportation.quetzel;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/16/2024
 */
public class RegulusCento {

    private static void travel(Player player, Destination destination) {
        QuetzelTransportSystem.teleport(player, destination.position);
    }

    private static void customizeRenu(Player player) {
        player.dialogue(new OptionsDialogue("Customize Renu",
                new Option("Green", () -> QuetzelTransportSystem.RENU.set(player, 2)),
                new Option("Orange", () -> QuetzelTransportSystem.RENU.set(player, 3)),
                new Option("Blue", () -> QuetzelTransportSystem.RENU.set(player, 4)),
                new Option("Cyan", () -> QuetzelTransportSystem.RENU.set(player, 5)),
                new Option("Green & Orange", () -> QuetzelTransportSystem.RENU.set(player, 6))
        ));
    }

    private static void dialogue(Player player, NPC npc, Destination destination) {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Travel to " + destination.name, () -> travel(player, destination)));
        if (destination == Destination.VARROCK) options.add(new Option("Customize Renu", () -> customizeRenu(player)));
        options.add(new Option("No thanks", new PlayerDialogue("No thanks."), new NPCDialogue(npc, "Very well.")));
        player.dialogue(
                new NPCDialogue(npc, "Nilsal, adventurer. Do you wish to travel to " + destination.name + "?"),
                new OptionsDialogue(options)
        );
    }

    static {
        NPCAction.register(12884, "talk-to", (player, npc) -> dialogue(player, npc, Destination.FORTIS));
        NPCAction.register(12885, "talk-to", (player, npc) -> dialogue(player, npc, Destination.VARROCK));
        NPCAction.register(12884, "travel", (player, npc) -> travel(player, Destination.FORTIS));
        NPCAction.register(12885, "travel", (player, npc) -> travel(player, Destination.VARROCK));
    }

    @AllArgsConstructor
    private enum Destination {
        VARROCK("Varrock", new Position(3280, 3412)),
        FORTIS("Fortis", new Position(1703, 3140));

        private final String name;
        private final Position position;
    }
}

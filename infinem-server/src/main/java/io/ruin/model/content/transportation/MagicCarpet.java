package io.ruin.model.content.transportation;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/26/2021
 */
public class MagicCarpet {

    // 20 Shanty pass
    // 17 uzer, camp, pollniv
    // 22 nardah, soph, menaphos
    // 21 Pollniv
    private static final Position northPollniv = new Position(3349, 3003);
    private static final Position southPollniv = new Position(3351, 2942);
    private static final Position camp = new Position(3180, 3045);
    private static final Position uzer = new Position(3469, 3113);
    private static final Position shantyPass = new Position(3308, 3110);
    private static final Position menaphos = new Position(3245, 2813);
    private static final Position sophanem = new Position(3285, 2813);
    private static final Position nardah = new Position(3401, 2916);

    private static void travel(Player player, Position destination) {
        Traveling.fadeTravel(player, destination);
        if (destination.equals(sophanem))
            player.getTaskManager().doLookupByUUID(641);    // Take a Carpet Ride from Pollnivneach to Sophanem
    }

    static {
        NPCAction.register(17, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> travel(player, northPollniv)),
                            new Option("Bedabin Camp", () -> travel(player, camp)),
                            new Option("Uzer", () -> travel(player, uzer))
                    ));
        });

        NPCAction.register(17, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> travel(player, northPollniv)),
                            new Option("Bedabin Camp", () -> travel(player, camp)),
                            new Option("Uzer", () -> travel(player, uzer))
                    ));
        });

        NPCAction.register(20, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Shanty Pass", () -> travel(player, shantyPass))
                    ));
        });

        NPCAction.register(20, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Shanty Pass", () -> travel(player, shantyPass))
                    ));
        });

        NPCAction.register(18, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> travel(player, southPollniv))
                    ));
        });

        NPCAction.register(18, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> travel(player, southPollniv))
                    ));
        });

        NPCAction.register(22, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Nardah", () -> travel(player, nardah)),
                            new Option("Sophanem", () -> travel(player, sophanem)),
                            new Option("Menaphos", () -> travel(player, menaphos))
                    ));
        });

        NPCAction.register(22, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Nardah", () -> travel(player, nardah)),
                            new Option("Sophanem", () -> travel(player, sophanem)),
                            new Option("Menaphos", () -> travel(player, menaphos))
                    ));
        });
    }
}

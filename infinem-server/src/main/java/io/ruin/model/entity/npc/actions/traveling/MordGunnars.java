package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class MordGunnars {
    static {
        NPCAction.register(1900, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(1900, "Would you like a ride over to Jatizso?"),
                    new OptionsDialogue(
                            new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2420, 3781, 0))),
                            new Option("No")
                    )
            );
        });
        NPCAction.register(1900, "jatizso", (player, npc) -> Traveling.fadeTravel(player, new Position(2420, 3781, 0)));
        NPCAction.register(1940, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(1940, "Would you like a ride over to Rellekka?"),
                    new OptionsDialogue(
                            new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2644, 3709, 0))),
                            new Option("No")
                    )
            );
        });
        NPCAction.register(1940, "rellekka", (player, npc) -> Traveling.fadeTravel(player, new Position(2644, 3709, 0)));
    }
}
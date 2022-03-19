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
public class Miscellania {
    static {
        NPCAction.register(3936, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(3936, "Would you like a ride over to Miscecllania?"),
                    new OptionsDialogue(
                            new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2581, 3845, 0))),
                            new Option("No")
                    )
            );
        });
        NPCAction.register(3936, "travel", (player, npc) -> Traveling.fadeTravel(player, new Position(2581, 3845, 0)));
        NPCAction.register(3680, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(3680, "Would you like a ride over to Rellekka?"),
                    new OptionsDialogue(
                            new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2629, 3692, 0))),
                            new Option("No")
                    )
            );
        });
        NPCAction.register(3680, "travel", (player, npc) -> Traveling.fadeTravel(player, new Position(2629, 3692, 0)));
    }
}

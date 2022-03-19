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
public class Jarvald {
    static {
        NPCAction.register(5937, "talk-to", (player, npc) -> {
            if (npc.getPosition().equals(2550, 3758)) { // To Rellekka
                player.dialogue(
                        new NPCDialogue(5937, "Would you like a ride over to Rellekka?"),
                        new OptionsDialogue(
                                new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2620, 3685, 0))),
                                new Option("No")
                        )
                );
            } else {    // To waterbirth
                player.dialogue(
                        new NPCDialogue(5937, "Would you like a ride over to Waterbirth Island?"),
                        new OptionsDialogue(
                                new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2549, 3758, 0))),
                                new Option("No")
                        )
                );
            }
        });
        NPCAction.register(5937, "travel", (player, npc) -> {
            if (npc.getPosition().equals(2550, 3758)) { // To Rellekka
                Traveling.fadeTravel(player, new Position(2620, 3685, 0));
            } else {    // To waterbirth
                Traveling.fadeTravel(player, new Position(2549, 3758, 0));
            }
        });
    }
}

package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class Torfinn {
    static {
        NPCAction.register(8131, "talk-to", (player, npc) -> {
            if (npc.getPosition().equals(2640, 3696)) { // To Ungael
                player.dialogue(
                        new NPCDialogue(8131, "Would you like a ride over to Ungael?"),
                        new OptionsDialogue(
                                new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2277, 4034, 0))),
                                new Option("No")
                        )
                );
            } else {    // From Ungael
                player.dialogue(
                        new NPCDialogue(8131, "Would you like a ride over to Rellekka?"),
                        new OptionsDialogue(
                                new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2640, 3695, 0))),
                                new Option("No")
                        )
                );
            }
        });
        // Boat
        ObjectAction.register(31989, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(2640, 3697, 0))));
    }
}

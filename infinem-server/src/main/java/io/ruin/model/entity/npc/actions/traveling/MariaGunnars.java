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
public class MariaGunnars {
    static {
        NPCAction.register(1883, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(1883, "Would you like a ride over to Neitiznot?"),
                    new OptionsDialogue(
                            new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2311, 3782, 0))),
                            new Option("No")
                    )
            );
        });
        NPCAction.register(1883, "neitiznot", (player, npc) -> Traveling.fadeTravel(player, new Position(2311, 3782, 0)));
        NPCAction.register(1882, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(1882, "Would you like a ride over to Rellekka?"),
                    new OptionsDialogue(
                            new Option("Sure", () -> Traveling.fadeTravel(player, new Position(2644, 3710, 0))),
                            new Option("No")
                    )
            );
        });
        NPCAction.register(1882, "rellekka", (player, npc) -> Traveling.fadeTravel(player, new Position(2644, 3710, 0)));
    }
}

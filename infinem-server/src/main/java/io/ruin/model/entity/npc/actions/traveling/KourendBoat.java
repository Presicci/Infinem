package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2023
 */
public class KourendBoat {

    static {
        // Port sarim
        NPCAction.register(8630, "talk-to", ((player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc.getId(), "Would you like me to take you to Port Piscarilius or Land's End?"),
                    new OptionsDialogue(
                            new Option("Port Piscarilius", () -> Traveling.fadeTravel(player, 1824, 3691, 0)),
                            new Option("Land's End", () -> Traveling.fadeTravel(player, 1504, 3399, 0)),
                            new Option("No")
                    )
            );
        }));
        NPCAction.register(8630, "port piscarilius", (player, npc) -> Traveling.fadeTravel(player, 1824, 3691, 0));
        NPCAction.register(8630, "land's end", (player, npc) -> Traveling.fadeTravel(player, 1504, 3399, 0));

        // Land's end
        NPCAction.register(7471, "talk-to", ((player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc.getId(), "Would you like me to take you to Port Sarim or Port Piscarilius?"),
                    new OptionsDialogue(
                            new Option("Port Sarim", () -> Traveling.fadeTravel(player, 3055, 3245, 0)),
                            new Option("Port Piscarilius", () -> Traveling.fadeTravel(player, 1824, 3691, 0)),
                            new Option("No")
                    )
            );
        }));
        NPCAction.register(7471, "port piscarilius", (player, npc) -> Traveling.fadeTravel(player, 1824, 3691, 0));
        NPCAction.register(7471, "port sarim", (player, npc) -> Traveling.fadeTravel(player, 3055, 3245, 0));

        // Port piscarilius
        NPCAction.register(2147, "talk-to", ((player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc.getId(), "Would you like me to take you to Port Sarim or Land's End?"),
                    new OptionsDialogue(
                            new Option("Port Sarim", () -> Traveling.fadeTravel(player, 3055, 3245, 0)),
                            new Option("Land's End", () -> Traveling.fadeTravel(player, 1504, 3399, 0)),
                            new Option("No")
                    )
            );
        }));
        NPCAction.register(2147, "port sarim", (player, npc) -> Traveling.fadeTravel(player, 3055, 3245, 0));
        NPCAction.register(2147, "land's end", (player, npc) -> Traveling.fadeTravel(player, 1504, 3399, 0));
    }
}

package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/7/2022
 */
public class BargeGuard {

    private static void travel(Player player) {
        player.getTaskManager().doLookupByUUID(333, 1); // Travel to Fossil Island
        Traveling.fadeTravel(player, new Position(3725, 3807));
    }

    static {
        NPCAction.register(8012, "talk-to", ((player, npc) -> {
            player.dialogue(
                    new NPCDialogue(8012, "Would you like a ride over to Fossil island?"),
                    new OptionsDialogue(
                            new Option("Sure!", () -> travel(player)),
                            new Option("Nope")
                    )
            );
        }));
        NPCAction.register(8012, "board", (player, npc) -> travel(player));
        NPCAction.register(8012, "quick-travel", (player, npc) -> travel(player));
    }
}

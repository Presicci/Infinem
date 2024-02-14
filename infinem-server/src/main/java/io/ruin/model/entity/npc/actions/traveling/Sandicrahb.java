package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/14/2024
 */
public class Sandicrahb {

    public static void pay(Player player, NPC npc) {
        if (player.getInventory().getAmount(995) < 10000) {
            player.dialogue(
                    new PlayerDialogue("I don't have that sort of money!"),
                    new NPCDialogue(npc, "Then you do not belong on my island."),
                    new PlayerDialogue("Fine!"),
                    new NPCDialogue(npc, "Bye bye bwana.")
            );
        } else {
            player.dialogue(
                    new OptionsDialogue(
                            new Option("Ok, here's the ridiculous sum of money...", () -> {
                                player.startEvent(event -> {
                                    player.dialogue(new PlayerDialogue("Ok, here's the ridiculous sum of money..."));
                                    event.waitForDialogue(player);
                                    player.lock();
                                    player.getInventory().remove(995, 10000);
                                    Traveling.fadeTravel(player, new Position(1779, 3417, 0));
                                });
                            }),
                            new Option("Never mind.", new PlayerDialogue("Never mind."))
                    )
            );
        }
    }

    static {
        NPCAction.register(7483, "travel", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Want me to take you to Crabclaw Isle? It'll cost you 10,000 coins."),
                new ActionDialogue(() -> pay(player, npc))
        ));
        NPCAction.register(7484, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Would you like to travel back to the mainland?"),
                new OptionsDialogue("Travel back",
                        new Option("Yes", () -> Traveling.fadeTravel(player, new Position(1783, 3458, 0))),
                        new Option("No")
                )
        ));
        NPCAction.register(7484, "travel", (player, npc) -> Traveling.fadeTravel(player, new Position(1783, 3458, 0)));
    }
}

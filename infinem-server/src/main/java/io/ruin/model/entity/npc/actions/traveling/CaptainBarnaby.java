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
 * Created on 5/28/2023
 */
public class CaptainBarnaby {
    private static final int ARDOUGNE_BARNABY = 9250;
    private static final int BRIMHAVEN_BARNABY = 8764;
    private static final int RIMMINGTON_BARNABY = 8763;

    private static final Position ARDOUGNE = new Position(2683, 3271);
    private static final Position BRIMHAVEN = new Position(2772, 3234);
    private static final Position RIMMINGTON = new Position(2915, 3225);

    private static void travel(Player player, Position destination) {
        player.getInventory().remove(995, 30);
        Traveling.fadeTravel(player, destination);
    }

    private static void fastTravel(Player player, NPC npc, Position destination) {
        if (player.getInventory().hasItem(995, 30)) {
            travel(player, destination);
        } else {
            player.dialogue(new NPCDialogue(npc, "Come back when you've got 30 coins for me."));
        }
    }

    private static void dialogue(Player player, NPC npc, String destinationOneName, Position destinationOne, String destinationTwoName, Position destinationTwo) {
        player.dialogue(
                new NPCDialogue(npc, "Hello there. Would you like to travel somewhere? I can take you to " + destinationOneName + " or " + destinationTwoName + " for 30 coins."),
                new OptionsDialogue(
                        new Option("I'd like to go to " + destinationOneName + ".", () -> {
                            player.dialogue(
                                    new PlayerDialogue("I'd like to go to " + destinationOneName + "."),
                                    new ActionDialogue(() -> {
                                        if (player.getInventory().hasItem(995, 30)) {
                                            travel(player, destinationOne);
                                        } else {
                                            player.dialogue(new NPCDialogue(npc, "Come back when you've got 30 coins for me."));
                                        }
                                    })
                            );
                        }),
                        new Option("I'd like to go to " + destinationTwoName + ".", () -> {
                            player.dialogue(
                                    new PlayerDialogue("I'd like to go to " + destinationTwoName + "."),
                                    new ActionDialogue(() -> {
                                        if (player.getInventory().hasItem(995, 30)) {
                                            travel(player, destinationTwo);
                                        } else {
                                            player.dialogue(new NPCDialogue(npc, "Come back when you've got 30 coins for me."));
                                        }
                                    })
                            );
                        }),
                        new Option("I'm good thanks.", () -> player.dialogue(new PlayerDialogue("I'm good thanks.")))
                )
        );
    }

    static {
        NPCAction.register(ARDOUGNE_BARNABY, "talk-to", ((player, npc) -> dialogue(player, npc, "Rimmington", RIMMINGTON, "Brimhaven", BRIMHAVEN)));
        NPCAction.register(ARDOUGNE_BARNABY, "rimmington", ((player, npc) -> fastTravel(player, npc, RIMMINGTON)));
        NPCAction.register(ARDOUGNE_BARNABY, "brimhaven", ((player, npc) -> fastTravel(player, npc, BRIMHAVEN)));
        NPCAction.register(BRIMHAVEN_BARNABY, "talk-to", ((player, npc) -> dialogue(player, npc, "Ardougne", ARDOUGNE, "Rimmington", RIMMINGTON)));
        NPCAction.register(BRIMHAVEN_BARNABY, "ardougne", ((player, npc) -> fastTravel(player, npc, ARDOUGNE)));
        NPCAction.register(BRIMHAVEN_BARNABY, "rimmington", ((player, npc) -> fastTravel(player, npc, RIMMINGTON)));
        NPCAction.register(RIMMINGTON_BARNABY, "talk-to", ((player, npc) -> dialogue(player, npc, "Ardougne", ARDOUGNE, "Brimhaven", BRIMHAVEN)));
        NPCAction.register(RIMMINGTON_BARNABY, "ardougne", ((player, npc) -> fastTravel(player, npc, ARDOUGNE)));
        NPCAction.register(RIMMINGTON_BARNABY, "brimhaven", ((player, npc) -> fastTravel(player, npc, BRIMHAVEN)));
    }
}

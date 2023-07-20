package io.ruin.model.content.transportation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public class ShiloVillageCart {

    private static void payFare(Player player, int npc) {
        if (player.getInventory().hasItem(995, 200)) {
            player.startEvent(e -> {
                player.lock();
                player.getPacketSender().fadeOut();
                player.getInventory().remove(995, 200);
                player.dialogue(new MessageDialogue("You hop into the cart and the driver urges the horses on. You take a taxing journey through the jungle to " + (npc == 5357 ? "Brimhaven" : "Shilo Village") + ". " +
                        "You feel tired from the journey, but at least you didn't have to walk all that distance.").hideContinue());
                e.delay(5);
                player.getMovement().teleport(npc == 5357 ? new Position(2776, 3212) : new Position(2832, 2954));
                player.getPacketSender().fadeIn();
                player.closeDialogue();
                player.unlock();
            });
        } else {
            player.dialogue(new NPCDialogue(npc, "Sorry, but it looks as if you don't have enough money. Come and see me when you have enough for the ride."));
        }
    }

    private static void dialogue(Player player, int npc) {
        player.dialogue(
                new NPCDialogue(npc, "I am offering a cart ride to " + (npc == 5357 ? "Brimhaven" : "Shilo Village") + ", if you're interested. It will cost 200 coins. Is that okay?"),
                new OptionsDialogue(
                        new Option("Yes please, I'd like to go to " + (npc == 5357 ? "Brimhaven" : "Shilo Village") + ".", () -> player.dialogue(
                                new PlayerDialogue("Yes please, I'd like to go to " + (npc == 5357 ? "Brimhaven" : "Shilo Village") + "."),
                                new ActionDialogue(() -> payFare(player, npc))
                        )),
                        new Option("No thanks.", () -> player.dialogue(
                                new PlayerDialogue("No thanks."),
                                new NPCDialogue(npc, "Okay, Bwana, let me know if you change your mind.")
                        ))
                )
        );
    }

    static {
        // Brimhaven
        NPCAction.register(5356, "talk-to", ((player, npc) -> dialogue(player, npc.getId())));
        NPCAction.register(5356, "pay-fare", ((player, npc) -> payFare(player, npc.getId())));
        ObjectAction.register(2230, 2777, 3211, 0, "board", ((player, obj) -> dialogue(player, 5356)));
        ObjectAction.register(2230, 2777, 3211, 0, "pay-fare", ((player, obj) -> payFare(player, 5356)));
        // Shilo Village
        NPCAction.register(5357, "talk-to", ((player, npc) -> dialogue(player, npc.getId())));
        NPCAction.register(5357, "pay-fare", ((player, npc) -> payFare(player, npc.getId())));
        ObjectAction.register(2265, 2831, 2952, 0, "board", ((player, obj) -> dialogue(player, 5357)));
        ObjectAction.register(2265, 2831, 2952, 0, "pay-fare", ((player, obj) -> payFare(player, 5357)));
    }
}

package io.ruin.model.content.transportation;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.TileTrigger;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/9/2023
 */
public class TrainStation {

    private static final Position KELDAGRIM_CART_1 = new Position(2442, 5533, 0);
    private static final Position KELDAGRIM_CART_2 = new Position(2442, 5522, 0);
    private static final Position DORGESH_KAAN_CART_1 = new Position(2481, 5538, 0);
    private static final Position DORGESH_KAAN_CART_2 = new Position(2481, 5549, 0);

    private static void travel(Player player, Position destination) {
        player.lock();
        player.getMovement().reset();
        player.startEvent(e -> {
            player.lock();
            player.getPacketSender().fadeOut();
            player.getInventory().remove(995, 200);
            player.dialogue(new MessageDialogue("The train speeds out the station and through the elaborate tunnel network. You arrive at your destination in no time.").hideContinue());
            e.delay(5);
            player.getMovement().teleport(destination);
            player.getPacketSender().fadeIn();
            player.closeDialogue();
            player.getTaskManager().doLookupByUUID(928);    // Ride a Train
            player.unlock();
        });
    }

    private static void attemptTravel(Player player, Position position) {
        player.lock();
        player.getMovement().reset();
        player.dialogue(new OptionsDialogue("Would you like to board the train?",
                new Option("Yes", () -> travel(player, position)),
                new Option("No", player::unlock)
        ));
    }

    static {
        TileTrigger.registerPlayerTrigger(KELDAGRIM_CART_1, 1, p -> attemptTravel(p, DORGESH_KAAN_CART_1.relative(1, 0)));
        TileTrigger.registerPlayerTrigger(KELDAGRIM_CART_2, 1, p -> attemptTravel(p, DORGESH_KAAN_CART_2.relative(1, 0)));
        TileTrigger.registerPlayerTrigger(DORGESH_KAAN_CART_1, 1, p -> attemptTravel(p, KELDAGRIM_CART_1.relative(-1, 0)));
        TileTrigger.registerPlayerTrigger(DORGESH_KAAN_CART_2, 1, p -> attemptTravel(p, KELDAGRIM_CART_2.relative(-1, 0)));
    }
}

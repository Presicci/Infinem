package io.ruin.model.map.object.actions.impl.locations.prifddinas;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

public class GrandLibrary {

    static {
        // Leaving
        ObjectAction.register(36615, 1, (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(1);
            player.dialogue(
                    new MessageDialogue("Welcome to the Tower of Voices.")
            );
            player.getMovement().teleport(3257, 6082, 2);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        }));
        // Entering
        ObjectAction.register(36614, 1, (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(1);
            player.dialogue(
                    new MessageDialogue("Welcome to the Grand Library.")
            );
            player.getMovement().teleport(3232, 12534, 0);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        }));
    }
}

package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/3/2024
 */
public class UndergroundPass {

    private static void traverse(Player player, Position destination) {
        player.lock();
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            player.dialogue(new MessageDialogue("Oh no. Back into the underground pass...").hideContinue());
            e.delay(4);
            player.dialogue(new MessageDialogue("You stumble about in the dreadful tunnels...").hideContinue());
            e.delay(4);
            player.dialogue(new MessageDialogue("You fail the same agility obstacle six times...").hideContinue());
            e.delay(4);
            player.dialogue(new MessageDialogue("You mourn the death of an innocent unicorn...").hideContinue());
            e.delay(4);
            player.dialogue(new MessageDialogue("You fail a different agility obstacle three times...").hideContinue());
            e.delay(4);
            player.dialogue(new MessageDialogue("Out of breath, you barely make it to the other end of the tunnel."));
            e.delay(1);
            player.getMovement().teleport(destination);
            player.getPacketSender().fadeIn();
            player.unlock();
        });

    }

    static {
        // Tiranwn -> West ardy
        ObjectAction.register(4006, 2313, 3215, 0, "enter", (player, obj) -> traverse(player, new Position(2435, 3315, 0)));
        // West ardy -> Tiranwn
        ObjectAction.register(3213, 2433, 3313, 0, "enter", (player, obj) -> traverse(player, new Position(2312, 3216, 0)));
    }
}

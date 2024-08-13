package io.ruin.model.map.object.actions.impl.transportation;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/12/2024
 */
public class DarkmeyerBoat {

    private static void travel(Player player, Position destination, String firstMessage, String secondMessage) {
        player.startEvent(e -> {
            player.lock();
            player.getPacketSender().fadeOut();
            player.dialogue(new MessageDialogue(firstMessage).hideContinue());
            e.delay(5);
            player.getMovement().teleport(destination);
            player.getPacketSender().fadeIn();
            player.dialogue(new MessageDialogue(secondMessage));
            player.unlock();
        });
    }

    static {
        ObjectAction.register(12944, 1, (player, obj) -> travel(player, new Position(3627, 3333, 0), "You push the boat into the water and board it...", "Somehow you end up in Darkmeyer. Lovely."));
        ObjectAction.register(39170, 1, (player, obj) -> travel(player, new Position(3523, 3180, 0), "You crawl through the cracked wall...", "Well now you're in Burgh de Rott."));
    }
}

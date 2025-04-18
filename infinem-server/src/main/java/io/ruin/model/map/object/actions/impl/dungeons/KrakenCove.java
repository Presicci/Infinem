package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.utility.Color;
import io.ruin.model.activities.combat.pvminstance.InstanceDialogue;
import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

public class KrakenCove {

    static {
        /*
         * Entrance/exit
         */
        ObjectAction.register(30177, 2277, 3611, 0, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2276, 9988, 0);
            player.unlock();
        }));
        ObjectAction.register(30178, 2276, 9987, 0, "leave", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2278, 3610, 0);
            player.unlock();
        }));

        /*
         * Kraken boss
         */
        ObjectAction.register(537, 2280, 10017, 0, "enter", (player, obj) -> {
            if(player.krakenWarning) {
                player.dialogue(
                        new MessageDialogue(Color.DARK_RED.wrap("WARNING!") +
                                "<br> This is the lair of the Kraken boss. <br> Are you sure you want to enter?").lineHeight(24),
                        new OptionsDialogue("Enter the boss area?",
                                new Option("Yes.", () -> player.getMovement().teleport(2280, 10022)),
                                new Option("Yes, and don't warn me again.", () -> {
                                    player.getMovement().teleport(2280, 10022);
                                    player.krakenWarning = false;
                                }),
                                new Option("No.", player::closeDialogue)
                        )
                );
            } else {
                player.getMovement().teleport(2280, 10022);
            }
        });
        ObjectAction.register(537, 2280, 10017, 0, "private", (player, obj) -> InstanceDialogue.open(player, InstanceType.KRAKEN));
        ObjectAction.register(538,"use", (player, obj) -> player.getMovement().teleport(2280, 10016));
    }
}

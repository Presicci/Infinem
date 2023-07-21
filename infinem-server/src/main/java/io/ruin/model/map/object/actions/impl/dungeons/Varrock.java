package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.activities.combat.pvminstance.PVMInstance;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class Varrock {

    private static void enterBryo(Player player) {
        boolean hasKey = player.getInventory().hasId(22374);
        if (!hasKey) {
            player.dialogue(new ItemDialogue().one(22374, "You need a Mossy key to enter Bryophyta's lair."));
        } else {
            player.getInventory().remove(22374, 1);
            PVMInstance.enterTimeless(player, InstanceType.BRYOPHYTA);
        }
    }

    static {
        /**
         * Ladders
         */
        ObjectAction.register(11806, 3237, 9858, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3236, 3458, 0, true, true, false));
        ObjectAction.register(17385, 3230, 9904, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3230, 3505, 0, true, true, false));

        /**
         * Hole
         */
        ObjectAction.register(7142, 3230, 3504, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3229, 9904, 0, false, true, false));

        /**
         * Bryophyta
         */
        ObjectAction.register(32534, "open", (player, obj) -> {
            player.dialogue(new OptionsDialogue("Enter Bryophyta's Lair?",
                    new Option("Yes", () -> enterBryo(player)),
                    new Option("No", player::closeDialogue)));
        });

        ObjectAction.register(32535, "clamber", (player, obj) -> {
            Traveling.fadeTravel(player, 3174, 9900, 0);
        });

        ObjectAction.register(32536, "take-axe", (player, obj) -> {
            if (!player.getInventory().hasFreeSlots(1)) {
                player.sendMessage("You don't haven enough inventory space.");
            } else {
                player.getInventory().add(1351, 1);
                obj.setId(5582);
                player.sendMessage("You take the axe from the log.");
            }
        });
    }
}

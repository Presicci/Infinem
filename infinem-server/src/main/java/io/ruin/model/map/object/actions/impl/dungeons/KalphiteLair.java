package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.activities.combat.pvminstance.InstanceDialogue;
import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class KalphiteLair {

    static {
        ObjectAction.register(3832, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3509, 9496, 2, true, true, false);
        });
        ObjectAction peekAction = (player, obj) -> {
            int players = (int) Region.get(13972).players.stream().filter(p -> p.getHeight() == 0).count();
            if (players == 0)
                player.sendMessage("It doesn't look like there's anyone down there.");
            else
                player.sendMessage("It looks like there " + (players > 1 ? "are" : "is") + " " + players + " adventurer" + (players > 1 ? "s" : "") + " down there.");
        };
        ObjectAction.register(23609, 2, peekAction);
        ObjectAction.register(29705, "peek", peekAction);
        ObjectAction.register(29705, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.KALPHITE_QUEEN));

        ObjectAction.register(16465, 1, (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 86, "use this shortcut")) {
                return;
            }
            if (player.isAt(3500, 9510)) {
                player.getMovement().teleport(3506, 9505, 2);
            } else {
                player.getMovement().teleport(3500, 9510, 2);
            }
        });
        ObjectAction.register(23596, 1, (player, obj) -> player.sendMessage("Looks like this tunnel is blocked."));
        ObjectAction.register(3829, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3226, 3108, 0, true, true, false);
        });
    }

}

package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class KalphiteLair {

    static {
        ObjectAction.register(3832, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3509, 9496, 2, true, true, false);
        });
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
        ObjectAction.register(3829, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3226, 3108, 0, true, true, false);
        });
    }

}

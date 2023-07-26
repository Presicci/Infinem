package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/1/2022
 */
public class DorgeshKaanMine {

    public static void squeezeThrough(Player player, Direction direction) {
        player.startEvent((event) -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.getMovement().force(direction == Direction.EAST ? 2 : -2, 0, 0, 0, 5, 95, direction);
            player.animate(749);
            event.delay(1);
            player.unlock();
        });
    }

    static {
        // Castle cellar
        ObjectAction.register(17385, 3209, 9616, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3210, 3216, 0, true, true, false));
        ObjectAction.register(14880, 3209, 3216, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3210, 9616, 0, false, true, false));
        // Squeeze through hole
        ObjectAction.register(6898, 1, ((player, obj) -> squeezeThrough(player, Direction.EAST)));
        ObjectAction.register(6899, 1, ((player, obj) -> squeezeThrough(player, Direction.WEST)));
        // Dorgesh-Kaan entrance
        ObjectAction.register(6919, "open", ((player, obj) -> Traveling.fadeTravel(player, new Position(2747, 5374))));
        ObjectAction.register(6920, "open", ((player, obj) -> Traveling.fadeTravel(player, new Position(2747, 5374))));
        ObjectAction.register(22945, "open", ((player, obj) -> Traveling.fadeTravel(player, new Position(3318, 9602))));
    }
}

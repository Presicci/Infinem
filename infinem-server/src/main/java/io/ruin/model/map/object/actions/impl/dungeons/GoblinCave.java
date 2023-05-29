package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public class GoblinCave {

    static {
        Tile.getObject(2, 2622, 3392, 0).walkTo = Position.of(2624, 3391, 0);
        ObjectAction.register(2, 2622, 3392, 0, "enter", ((player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(2796);
                e.delay(1);
                player.resetAnimation();
                player.getMovement().teleport(2620, 9796, 0);
                player.unlock();
            });
        }));
        ObjectAction.register(13, 2621, 9796, 0, "climb-over", (player, obj) -> Ladder.climb(player, new Position(2624, 3391), true, true, false));
    }
}

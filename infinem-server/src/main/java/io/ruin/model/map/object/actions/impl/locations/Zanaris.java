package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/11/2024
 */
public class Zanaris {

    private static void staticFairyRing(Player player, GameObject fairyRing, Position destination) {
        player.startEvent(event -> {
            player.lock();
            if (fairyRing != null && !player.isAt(fairyRing.x, fairyRing.y)) {
                player.stepAbs(fairyRing.x, fairyRing.y, StepType.FORCE_WALK);
                event.delay(1);
            }
            event.delay(1);
            player.animate(3265, 30);
            player.graphics(569);
            event.delay(3);
            player.getMovement().teleport(destination);
            player.animate(3266);
            event.delay(1);
            player.unlock();
        });
    }

    static {
        ObjectAction.register(12003, "use", ((player, obj) -> staticFairyRing(player, obj, new Position(3262, 3167, 0))));
        ObjectAction.register(12094, "use", ((player, obj) -> staticFairyRing(player, obj, new Position(3201, 3169, 0))));
    }
}

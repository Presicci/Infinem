package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/17/2023
 */
public class MountainCamp {

    static {
        ObjectAction.register(5847, 2760, 3658, 0, "climb-over", (player, obj) -> {
            Direction dir = player.getPosition().getY() > obj.getPosition().getY() ? Direction.SOUTH : Direction.NORTH;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(839);
                player.getMovement().force(0, dir == Direction.SOUTH ? -3 : 3, 0, 0, 0, 60, dir);
                e.delay(2);
                player.getStats().addXp(StatType.Agility, 0.5, true);
                player.unlock();
            });
        });
    }
}

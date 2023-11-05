package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/4/2023
 */
public enum DelayedTravelObject {
    CHASOM_OF_TEARS_ENTRANCE(6659, new Position(3225, 9539), new Position(3219, 9532, 2), 2, 2796);

    DelayedTravelObject(int objectId, Position position, Position destination, int delay, int animation) {
        ObjectAction.register(objectId, position, 1, ((player, obj) -> {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(animation);
                e.delay(delay);
                player.getMovement().teleport(destination);
                player.unlock();
            });
        }));
    }
}

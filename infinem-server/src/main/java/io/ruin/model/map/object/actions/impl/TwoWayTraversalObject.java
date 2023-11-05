package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/5/2023
 */
public enum TwoWayTraversalObject {
    LUMBRIDGE_SWAMP_CAVE_TO_DORGESH_KAAN_CAVES(6912, new Position(3224, 9601), 4, 2, 2796, false);

    TwoWayTraversalObject(int objectId, Position position, int distance, int delay, int animation, boolean horizontal) {
        ObjectAction.register(objectId, position, 1, ((player, obj) -> {
            int x = horizontal ? (player.getAbsX() + (player.getAbsX() > obj.getPosition().getX() ? -distance : distance)) : player.getAbsX();
            int y = !horizontal ? (player.getAbsY() + (player.getAbsY() > obj.getPosition().getY() ? -distance : distance)) : player.getAbsY();
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(animation);
                e.delay(delay);
                player.getMovement().teleport(x, y, player.getHeight());
                player.unlock();
            });
        }));
    }
}

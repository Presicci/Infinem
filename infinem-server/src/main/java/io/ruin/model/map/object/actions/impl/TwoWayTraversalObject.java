package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/5/2023
 */
public enum TwoWayTraversalObject {
    ELEMENTAL_WORKSHOP_WALL_1(26114, new Position(2710, 3495), 1, -1, false),
    ELEMENTAL_WORKSHOP_WALL_2(26115, new Position(2709, 3495), 1, -1, false),
    APE_ATOLL_GATE_1(4787, new Position(2719, 2766), 2, -1, false),
    APE_ATOLL_GATE_2(4788, new Position(2721, 2766), 2, -1, false),
    SOURHOG_CAVE(40331, new Position(3156, 9704), 2, 839, false),
    LUMBRIDGE_SWAMP_CAVE_TO_DORGESH_KAAN_CAVES(6912, new Position(3224, 9601), 4, 2, 2796, false);

    TwoWayTraversalObject(int objectId, Position position, int distance, int animation, boolean horizontal) {
        ObjectAction.register(objectId, position, 1, ((player, obj) -> {
            Direction direction = horizontal ? player.getAbsX() > obj.getPosition().getX() ? Direction.WEST : Direction.EAST : player.getAbsY() > obj.getPosition().getY() ? Direction.SOUTH : Direction.NORTH;
            int xDiff = direction == Direction.WEST ? -distance : direction == Direction.EAST ? distance : 0;
            int yDiff = direction == Direction.SOUTH ? -distance : direction == Direction.NORTH ? distance : 0;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                if (animation != -1) {
                    player.animate(animation);
                    player.getMovement().force(xDiff, yDiff, 0, 0, 5, 80, direction);
                    e.delay(distance);
                } else {
                    player.getMovement().teleport(player.getPosition().relative(xDiff, yDiff));
                }
                player.unlock();
            });
        }));
    }

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

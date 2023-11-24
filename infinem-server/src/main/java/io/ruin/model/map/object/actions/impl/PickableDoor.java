package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public enum PickableDoor {
    YANILLE_DUNGEON(11728, new Position(2601, 9482), 82, 5, true);

    PickableDoor(int objectId, Position objectPos, int levelRequirement, double experience, boolean vertical) {
        ObjectAction.register(objectId, objectPos, "pick-lock", (player, obj) -> {
            if (!player.getStats().check(StatType.Thieving, levelRequirement, "pick this")) return;
            player.startEvent(e -> {
                while (true) {
                    player.animate(537);
                    player.sendFilteredMessage("You attempt to pick the lock on the door.");
                    e.delay(5);
                    if (Random.rollPercent(50)) {
                        player.sendFilteredMessage("You pick the lock on the door.");
                        player.getStats().addXp(StatType.Thieving, experience, true);
                        Direction walkDirection = vertical ? player.getAbsY() < obj.y ? Direction.NORTH : Direction.SOUTH : player.getAbsX() < obj.x ? Direction.EAST : Direction.WEST;
                        PassableDoor.passDoor(player, obj, walkDirection, -4, new Position(walkDirection == Direction.EAST ? -2 : 0, walkDirection == Direction.NORTH ? -2 : 0), obj.id);
                        break;
                    }
                }
            });
        });
    }
}

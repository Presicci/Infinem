package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.item.Items;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public enum PickableDoor {
    ARDY_10_COIN(11719, new Position(2674, 3305), Direction.NORTH, 1, 3.8),
    ARDY_NATURE_RUNE(11720, new Position(2674, 3304), Direction.SOUTH, 16, 15),
    YANILLE_DUNGEON(11728, new Position(2601, 9482), Direction.SOUTH, 82, 5);

    PickableDoor(int objectId, Position objectPos, Direction openDirection, int levelRequirement, double experience) {
        ObjectAction.register(objectId, objectPos, "pick-lock", (player, obj) -> {
            if (!player.getStats().check(StatType.Thieving, levelRequirement, "pick this")) return;
            player.startEvent(e -> {
                while (true) {
                    boolean hasLockpick = player.getInventory().hasId(Items.LOCKPICK);
                    player.animate(537);
                    player.sendFilteredMessage("You attempt to pick the lock on the door.");
                    e.delay(5);
                    if (Random.rollPercent(hasLockpick ? 75 : 50)) {
                        player.sendFilteredMessage("You pick the lock on the door.");
                        player.getStats().addXp(StatType.Thieving, experience, true);
                        PassableDoor.passDoor(player, obj, openDirection, 0, null, obj.id);
                        break;
                    }
                }
            });
        });
    }
}

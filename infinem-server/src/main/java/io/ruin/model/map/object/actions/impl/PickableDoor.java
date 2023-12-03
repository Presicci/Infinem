package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public enum PickableDoor {
    PORT_SARIL_CELL_DOOR_1(9563, new Position(3012, 3195), Direction.EAST, 1, 0.0),
    PORT_SARIL_CELL_DOOR_2(9563, new Position(3012, 3192), Direction.EAST, 1, 0.0),
    PORT_SARIL_CELL_DOOR_3(9563, new Position(3012, 3189), Direction.EAST, 1, 0.0),
    PORT_SARIL_CELL_DOOR_4(9565, new Position(3018, 3187), Direction.NORTH, 1, 1.0, new Bounds(3017, 3188, 3020, 3190, 0)),
    PORT_SARIL_CELL_DOOR_5(9565, new Position(3018, 3182), Direction.SOUTH, 1, 1.0, new Bounds(3017, 3179, 3020, 3181, 0)),
    ARDY_10_COIN(11719, new Position(2674, 3305), Direction.NORTH, 1, 3.8, new Bounds(2673, 3306, 2675, 3306, 0)),
    ROSE_HOUSE(40178, new Position(2610, 3316), Direction.EAST, 13, 15, new Bounds(2611, 3315, 2611, 3318, 0)),
    ARDY_NATURE_RUNE(11720, new Position(2674, 3304), Direction.SOUTH, 16, 15, new Bounds(2673, 3303, 2675, 3303, 0)),
    AXE_HUT_1(11726, new Position(3190, 3957), Direction.NORTH, 23, 25, true, new Bounds(3187, 3958, 3194, 3962, 0)),
    AXE_HUT_2(11726, new Position(3191, 3963), Direction.SOUTH, 23, 25, true, new Bounds(3187, 3958, 3194, 3962, 0)),
    PIRATE_HALL_1(11727, new Position(3044, 3956), Direction.EAST, 39, 35, true, new Bounds(3038, 3949, 3044, 3959, 0)),
    PIRATE_HALL_2(11727, new Position(3041, 3959), Direction.NORTH, 39, 35, true, new Bounds(3038, 3949, 3044, 3959, 0)),
    PIRATE_HALL_3(11727, new Position(3038, 3956), Direction.WEST, 39, 35, true, new Bounds(3038, 3949, 3044, 3959, 0)),
    CHAOS_DRUID_TOWER(11723, new Position(2565, 3356), Direction.WEST, 46, 37.5, new Bounds(2564, 3355, 2564, 3357, 0)),
    GRUBBY_CHEST(34840, new Position(1798, 9925), Direction.WEST, 57, 10, new Bounds(1797, 9924, 1797, 9926, 0)),
    PALADIN_DOOR_1(11724, new Position(2572, 3288, 1), Direction.SOUTH, 61, 50, new Bounds(2571, 3287, 2573, 3287, 1)),
    PALADIN_DOOR_2(11724, new Position(2572, 3305, 1), Direction.SOUTH, 61, 50, new Bounds(2571, 3306, 2573, 3306, 1)),
    YANILLE_DUNGEON(11728, new Position(2601, 9482), Direction.SOUTH, 82, 5);

    PickableDoor(int objectId, Position objectPos, Direction openDirection, int levelRequirement, double experience) {
        this(objectId, objectPos, openDirection, levelRequirement, experience, false);
    }

    PickableDoor(int objectId, Position objectPos, Direction openDirection, int levelRequirement, double experience, boolean lockpickRequired) {
        this(objectId, objectPos, openDirection, levelRequirement, experience, lockpickRequired, null);
    }

    PickableDoor(int objectId, Position objectPos, Direction openDirection, int levelRequirement, double experience, Bounds insideBounds) {
        this(objectId, objectPos, openDirection, levelRequirement, experience, false, insideBounds);
    }

    PickableDoor(int objectId, Position objectPos, Direction openDirection, int levelRequirement, double experience, boolean lockpickRequired, Bounds insideBounds) {
        ObjectAction.register(objectId, objectPos, "open", (player, obj) -> {
            if (insideBounds != null && insideBounds.inBounds(player)) {
                PassableDoor.passDoor(player, obj, openDirection, 0, null, obj.id);
            } else {
                player.dialogue(new MessageDialogue("The door is locked."));
            }
        });
        ObjectAction.register(objectId, objectPos, "pick-lock", (player, obj) -> {
            if (!player.getStats().check(StatType.Thieving, levelRequirement, "pick this")) return;
            if (lockpickRequired && !player.getInventory().hasId(Items.LOCKPICK)) {
                player.sendMessage("You need a lockpick to pick the lock.");
                return;
            }
            player.startEvent(e -> {
                boolean vertical = openDirection == Direction.NORTH || openDirection == Direction.SOUTH;
                if ((vertical && player.getAbsX() != obj.x) || (!vertical && player.getAbsY() != obj.y)) {
                    player.getRouteFinder().routeAbsolute(vertical ? obj.x : player.getAbsX(), vertical ? player.getAbsY() : obj.y);
                    e.delay(2);
                    player.face(obj);
                }
                if (insideBounds != null && insideBounds.inBounds(player)) {
                    PassableDoor.passDoor(player, obj, openDirection, 0, null, obj.id);
                    return;
                }
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

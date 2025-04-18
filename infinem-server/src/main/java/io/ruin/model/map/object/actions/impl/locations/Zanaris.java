package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PassableDoor;

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
            player.getTaskManager().doLookupByUUID(332);    // Enter Zanaris
            player.animate(3266);
            event.delay(1);
            player.unlock();
        });
    }

    private static void zanarisDoorEntrance(Player player, GameObject door) {
        Item dramenStaff = player.getEquipment().findFirst(772, 9084);
        boolean hasFairyMushroom = player.getEquipment().hasId(25102) || player.getInventory().hasId(25102);
        if(dramenStaff == null && !hasFairyMushroom) {
            PassableDoor.passDoor(player, door, Direction.WEST);
            return;
        }
        GameObject rotatedDoor = new GameObject(door.id, door.getPosition(), door.type, 1);
        rotatedDoor.spawn();
        staticFairyRing(player, door, new Position(2452, 4472, 0));
        World.startEvent(e -> {
            e.delay(2);
            door.spawn();
        });
    }

    static {
        ObjectAction.register(12003, "use", (player, obj) -> staticFairyRing(player, obj, new Position(3262, 3167, 0)));
        ObjectAction.register(12094, "use", (player, obj) -> staticFairyRing(player, obj, new Position(3201, 3169, 0)));
        ObjectAction.register(2406, 3202, 3169, 0, "open", Zanaris::zanarisDoorEntrance);
        ItemObjectAction.register(Items.RAW_CHICKEN, 12093, (player, item, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(3265);
                item.remove();
                e.delay(2);
                player.getMovement().teleport(2460, 4356, 0);
                player.getTaskManager().doLookupByUUID(993);    // Enter the Evil Chicken's Lair
                player.unlock();
            });
        });
        ObjectAction.register(12260, 2459, 4354, 0, "use", (player, obj) -> player.getMovement().teleport(2453, 4476, 0));
    }
}

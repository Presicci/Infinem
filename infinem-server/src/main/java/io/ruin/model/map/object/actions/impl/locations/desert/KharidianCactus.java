package io.ruin.model.map.object.actions.impl.locations.desert;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.environment.Desert;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.Event;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/9/2024
 */
public class KharidianCactus {

    private static int getTool(Player player) {
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (weapon != null && weapon.getDef().sharpWeapon)
            return weapon.getId();
        if (!player.getInventory().hasId(Items.KNIFE)) {
            player.sendMessage("You need a knife or sharp weapon equipped to cut the cactus.");
            return -1;
        }
        return Items.KNIFE;
    }

    private static void cut(Player player, GameObject object) {
        int tool = getTool(player);
        if (tool == -1) return;
        cut(player, tool, object);
    }

    private static void cut(Player player, int toolId, GameObject object) {
        Item waterskin;
        for (int index = 0; index < Desert.WATERSKINS.length - 1; index++) {
            waterskin = player.getInventory().findItem(Desert.WATERSKINS[index]);
            if (waterskin != null) {
                Item finalWaterskin = waterskin;
                player.startEvent(e -> {
                    player.animate(toolId == Items.KNIFE ? 911 : player.getCombat().weaponType.attackAnimation);
                    e.delay(1);
                    finalWaterskin.setId(Items.WATERSKIN_4);
                    player.sendMessage("You top up your skin with water from the cactus.");
                    player.getStats().addXp(StatType.Woodcutting, 10.0, true);
                    player.getTaskManager().doLookupByUUID(640);    // Cut a Cactus in the Kharidian Desert
                    Event event = World.startEvent(we -> {
                        object.setId(2671);
                        we.delay(100);
                        object.setId(2670);
                    });
                });
                return;
            }
        }
        player.sendMessage("You don't have any waterskins to fill.");
    }

    static {
        ObjectAction.register(2670, "cut", KharidianCactus::cut);
        ItemObjectAction.register(Items.KNIFE, 2670, (player, item, obj) -> cut(player, Items.KNIFE, obj));
    }
}

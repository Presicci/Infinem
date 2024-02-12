package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/12/2024
 */
public class Darkmeyer {

    private static final Config eastShotcutConfig = Config.varpbit(10449, true);
    private static final Config eastShotcutConfig2 = Config.varpbit(10450, true);

    private static void setupShortcut(Player player) {
        if (eastShotcutConfig.get(player) == 1) {
            player.sendMessage("You've already attached a rope to the wall.");
            return;
        }
        if (player.getInventory().getAmount(24790) < 2) {
            player.sendMessage("You need two long ropes to setup the shortcut.");
            return;
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(827);
            e.delay(1);
            player.getInventory().remove(24790, 2);
            eastShotcutConfig.set(player, 1);
            eastShotcutConfig2.set(player, 1);
            player.unlock();
        });
    }

    private static void scaleShortcut(Player player, Position destination) {
        if (eastShotcutConfig.get(player) == 0 || eastShotcutConfig2.get(player) == 0) {
            player.sendMessage("You need a couple long ropes to scale the wall.");
            return;
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(828);
            e.delay(1);
            player.getMovement().teleport(destination);
            player.unlock();
        });
    }

    static {
        ItemObjectAction.register(24790, 39542, (player, item, obj) -> setupShortcut(player));
        ItemObjectAction.register(24790, 39541, (player, item, obj) -> setupShortcut(player));
        ObjectAction.register(39541, 1, (player, obj) -> scaleShortcut(player, new Position(3667, 3375, 0)));
        ObjectAction.register(39542, 1, (player, obj) -> scaleShortcut(player, new Position(3673, 3375, 0)));
    }
}

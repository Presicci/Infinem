package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.crafting.SpinningWheel;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public class BrokenSpinningWheel {

    private static final Config SPINNING_WHEEL = Config.varpbit(11716, true);

    static {
        ObjectAction.register(40767, 1, (player, obj) -> {
            if (SPINNING_WHEEL.get(player) == 1)
                return;
            if (player.getStats().get(StatType.Construction).currentLevel < 5) {
                player.dialogue(new MessageDialogue("You need a construction level of 5 to repair the spinning wheel."));
                return;
            }
            if (!player.getInventory().hasId(Items.HAMMER)) {
                player.dialogue(new MessageDialogue("You need a hammer to repair the spinning wheel."));
                return;
            }
            Inventory inventory = player.getInventory();
            if (inventory.getAmount(Items.PLANK) < 5
                    || !inventory.contains(Items.BALL_OF_WOOL)
                    || !inventory.contains(Items.IRON_BAR)) {
                player.dialogue(new MessageDialogue("You need 5 planks, a ball of wool, and an iron bar to repair the spinning wheel."));
                return;
            }
            player.lock();
            player.startEvent(e -> {
                player.animate(3676);
                e.delay(1);
                inventory.remove(Items.PLANK, 5);
                inventory.remove(Items.BALL_OF_WOOL, 1);
                inventory.remove(Items.IRON_BAR, 1);
                player.getStats().addXp(StatType.Construction, 200, true);
                SPINNING_WHEEL.set(player, 1);
                player.dialogue(new MessageDialogue("You manage to fully repair the spinning wheel to working order."));
                player.getTaskManager().doLookupByUUID(925);    // Repair the Spinning Wheel on the Isle of Souls
                player.unlock();
            });
        });
        ObjectAction.register(40767, 2, (player, obj) -> {
            if (SPINNING_WHEEL.get(player) == 0)
                return;
            SpinningWheel.spinningOptions(player);
        });
    }
}

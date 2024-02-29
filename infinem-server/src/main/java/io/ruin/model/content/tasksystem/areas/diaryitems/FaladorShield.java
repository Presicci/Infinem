package io.ruin.model.content.tasksystem.areas.diaryitems;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DailyResetListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/29/2024
 */
public class FaladorShield {

    private static final String KEY = "FALADOR_SHIELD";
    private static final int[] ITEM_IDS = { Items.FALADOR_SHIELD_1, Items.FALADOR_SHIELD_2, Items.FALADOR_SHIELD_3, Items.FALADOR_SHIELD_4 };
    private static final double[] RESTORE = { .25, .5, 1, 1 };

    private static void restorePrayer(Player player, Item item) {
        int uses = player.getAttributeIntOrZero(KEY);
        int max = item.getId() == Items.FALADOR_SHIELD_4 ? 2 : 1;
        if (uses >= max) {
            player.timeTillDailyReset("You've already used your prayer restore" + (max == 2 ? "s" : "") + " for the day.<br><br>");
            return;
        }
        Stat prayer = player.getStats().get(StatType.Prayer);
        if (!prayer.isDrained()) {
            player.sendMessage("You already have full prayer.");
            return;
        }
        prayer.restore(0, RESTORE[item.getId() - 13117]);
        player.incrementNumericAttribute(KEY, 1);
        player.sendMessage("The shield restores your prayer.");
    }

    private static void check(Player player, Item item) {
        int uses = player.getAttributeIntOrZero(KEY);
        int max = item.getId() == Items.FALADOR_SHIELD_4 ? 2 : 1;
        if (uses < max) {
            player.dialogue(new MessageDialogue("You have yet to use your prayer restore" + (max == 2 ? "s" : "") + " for the day."));
        } else {
            player.timeTillDailyReset();
        }
    }

    static {
        for (int id : ITEM_IDS) {
            ItemAction.registerInventory(id, "recharge-prayer", FaladorShield::restorePrayer);
            ItemAction.registerEquipment(id, "recharge prayer", FaladorShield::restorePrayer);
            ItemAction.registerInventory(id, "check", FaladorShield::check);
            ItemAction.registerEquipment(id, "check", FaladorShield::check);
        }
        DailyResetListener.register(player -> player.removeAttribute(KEY));
    }
}

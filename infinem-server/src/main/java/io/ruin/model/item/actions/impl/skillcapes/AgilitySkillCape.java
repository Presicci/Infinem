package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventRunnable;
import io.ruin.process.tickevent.TickEventType;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class AgilitySkillCape {

    protected static void staminaBoost(Player player) {
        if (!available(player.lastAgilityCapeBoost)) {
            player.sendMessage("You have already made use of that today.");
            return;
        }
        player.getMovement().restoreEnergy(100);
        Config.STAMINA_POTION.set(player, 1);
        player.addTickEvent(new TickEvent(
                TickEventType.STAMINA_POTION,
                100,
                () -> {
                    player.sendMessage("<col=8f4808>Your stamina potion has expired.");
                    player.privateSound(2672, 3, 0);
                    Config.STAMINA_POTION.set(player, 0);
                },
                new TickEventRunnable(17, () -> {
                    player.sendMessage("<col=8f4808>Your stamina potion is about to expire.");
                    player.privateSound(3120, 3, 0);
                })
        ));
        player.getPacketSender().sendWidget(Widget.STAMINA, 60);
        player.sendMessage("You activate the effect of your Agility cape.");
        player.lastAgilityCapeBoost = System.currentTimeMillis();
    }

    private static boolean available(long lastUsed) {
        if (lastUsed <= 0) {
            return true;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTimeInMillis(lastUsed);
        int lastUsedDay = cal.get(Calendar.DAY_OF_MONTH);
        return System.currentTimeMillis() > lastUsed && lastUsedDay != currentDay;
    }

    static {
        ItemAction.registerInventory(9771, "Stamina Boost", (player, item) -> staminaBoost(player));
        ItemAction.registerInventory(9772, "Stamina Boost", (player, item) -> staminaBoost(player));
    }
}

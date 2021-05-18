package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class AgilitySkillCape {

    protected static void staminaBoost(Player p, Item item) {
        if (!available(p.lastAgilityCapeBoost)) {
            p.sendMessage("You have already made use of that today.");
            return;
        }

        p.getMovement().restoreEnergy(100);
        Config.STAMINA_POTION.set(p, 1);
        p.staminaTicks = 100; // One minute
        p.getPacketSender().sendWidget(Widget.STAMINA, 60);
        p.sendMessage("You activate the effect of your Agility cape.");
        p.lastAgilityCapeBoost = System.currentTimeMillis();
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
        ItemAction.registerInventory(9771, "Stamina Boost", AgilitySkillCape::staminaBoost);
        ItemAction.registerInventory(9772, "Stamina Boost", AgilitySkillCape::staminaBoost);
    }
}

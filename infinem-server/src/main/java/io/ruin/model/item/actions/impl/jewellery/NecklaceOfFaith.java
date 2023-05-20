package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/19/2023
 */
public class NecklaceOfFaith {

    public static void check(Player player) {
        if (player.getHp() <= player.getMaxHp() * 0.20 && !player.getCombat().isDead()) {
            Item necklace = player.getEquipment().get(Equipment.SLOT_AMULET);
            if (necklace == null || necklace.getId() != 21157)
                return;
            necklace.remove();
            player.getStats().get(StatType.Prayer).restore(0, 0.1);
            player.sendFilteredMessage("Your necklace of faith restores some prayer and crumbles to dust.");
        }
    }
}

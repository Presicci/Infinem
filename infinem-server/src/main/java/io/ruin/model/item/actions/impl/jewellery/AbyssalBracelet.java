package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/30/2024
 */
public class AbyssalBracelet {

    private static final int[] BRACELET_IDS = { 11095, 11097, 11099, 11101, 11103, -1 };

    public static boolean test(Player player) {
        for (int index = 0; index < 5; index++) {
            int id = BRACELET_IDS[index];
            Item bracelet = player.getEquipment().findItem(id);
            if (bracelet == null) continue;
            bracelet.setId(BRACELET_IDS[index + 1]);
            player.sendMessage("Your abyssal bracelet protects you from the abyss.");
            if (index + 1 == 5) {
                player.sendMessage("<col=7F00FF>Your abyssal bracelet crumbles to dust.");
            } else {
                player.sendMessage("<col=7F00FF>Your abyssal bracelet has " + (5 - (index + 1)) + " charges left.");
            }
            return true;
        }
        return false;
    }
}

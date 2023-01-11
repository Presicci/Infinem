package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.equipment.EquipAction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * Enum of items that skull the player when equipped and prevent the skull from going away.
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/11/2023
 */
@AllArgsConstructor
public enum SkullingItem {
    AMULET_OF_AVARICE(22557),
    CAPE_OF_SKULLS(23351);

    private final int itemId;

    public static boolean canSkullDeplete(Player player) {
        if (Arrays.stream(SkullingItem.values()).anyMatch(skullingItem -> player.getEquipment().contains(skullingItem.itemId))) {
            return false;
        }
        return true;
    }

    static {
        for (SkullingItem item : SkullingItem.values()) {
            EquipAction.register(item.itemId, (player -> player.getCombat().skullNormal(2000)));    // Skull for 20 mins
        }
    }
}

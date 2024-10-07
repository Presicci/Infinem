package io.ruin.model.content.transmog;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/4/2024
 */
@AllArgsConstructor
public enum TransmogFilter {
    ALL(null),
    WEAPON(TransmogSlot.WEAPON),
    SHIELD(TransmogSlot.SHIELD),
    HEAD(TransmogSlot.HAT),
    CHEST(TransmogSlot.CHEST),
    LEGS(TransmogSlot.LEGS),
    HANDS(TransmogSlot.HANDS),
    FEET(TransmogSlot.FEET),
    BACK(TransmogSlot.CAPE),
    NECK(TransmogSlot.AMULET),
    RING(TransmogSlot.RING),
    AMMO(TransmogSlot.AMMO);

    private final TransmogSlot slot;

    private static final Config FILTER = Config.varpbit(16014, false);

    public static void selectFilter(Player player, int index) {
        FILTER.set(player, index);
    }

    public static void selectFilter(Player player, TransmogSlot slot) {
        for (TransmogFilter filter : values()) {
            if (filter.slot == slot) {
                FILTER.set(player, filter.ordinal());
                TransmogInterface.sendTransmogList(player);
            }
        }
    }

    public static TransmogSlot getFilteredSlot(Player player) {
        int filter = FILTER.get(player);
        return TransmogFilter.values()[filter].slot;
    }
}

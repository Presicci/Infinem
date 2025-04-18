package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class PrayerSkillCape {
    private static final int CAPE = StatType.Prayer.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Prayer.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Prayer.masterCapeId;

    public static boolean wearingPrayerCape(Player player) {
        int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return cape == CAPE || cape == TRIMMED_CAPE || cape == MASTER_CAPE || MaxCapeVariants.isMaxCape(cape);
    }

    public static boolean hasPrayerCape(Player player) {
        Inventory inv = player.getInventory();
        return inv.contains(CAPE) || inv.contains(TRIMMED_CAPE) || inv.contains(MASTER_CAPE) || MaxCapeVariants.has(player);
    }
}

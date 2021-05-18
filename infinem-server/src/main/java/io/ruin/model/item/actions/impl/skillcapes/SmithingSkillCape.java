package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;


public class SmithingSkillCape {

    private static final int CAPE = StatType.Smithing.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Smithing.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Smithing.masterCapeId;

    public static boolean wearingSmithingCape(Player player) {
        int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return cape == CAPE || cape == TRIMMED_CAPE || cape == 13342 || cape == MASTER_CAPE;
    }
}

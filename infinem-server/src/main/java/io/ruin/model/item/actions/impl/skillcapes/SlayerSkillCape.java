package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class SlayerSkillCape {

    private static final int CAPE = StatType.Slayer.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Slayer.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Slayer.masterCapeId;

    public static boolean wearingSlayerCape(Player player) {
        int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return cape == CAPE || cape == TRIMMED_CAPE || cape == 13342 || cape == MASTER_CAPE;
    }
}

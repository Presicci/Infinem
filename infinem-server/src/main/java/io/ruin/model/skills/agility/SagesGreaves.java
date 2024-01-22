package io.ruin.model.skills.agility;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/21/2024
 */
public class SagesGreaves {

    private static final int GREAVES = 28771;

    public static void tickGreaves(Player player) {
        if (player.getEquipment().getId(Equipment.SLOT_FEET) != GREAVES) return;
        if (!player.getRelicManager().hasRelicEnalbed(Relic.TRICKSTER)) return;
        if (!player.addTickEvent(new TickEvent(TickEventType.SAGES_GREAVES, 10))) return;
        player.getStats().addXp(StatType.Agility, (player.getStats().get(StatType.Agility).currentLevel) * 0.66, true);
    }
}

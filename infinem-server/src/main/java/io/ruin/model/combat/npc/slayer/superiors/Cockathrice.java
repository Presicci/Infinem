package io.ruin.model.combat.npc.slayer.superiors;

import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class Cockathrice extends Superior {

    private static final StatType[] DRAIN = { StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic };

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1))
            basicAttack();
        if (target.player != null
                && target.player.getEquipment().getId(Equipment.SLOT_SHIELD) != 4156
                && target.player.getEquipment().getId(Equipment.SLOT_SHIELD) != 24266) {
            for (StatType statType : DRAIN) {
                target.player.getStats().get(statType).drain(8);
            }
            target.player.sendMessage("<col=ff0000>The cockatrice's piercing gaze drains your stats!");
            target.player.sendMessage("<col=ff0000>A mirror shield can protect you from this attack.");
        }
        return true;
    }
}

package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.item.Items;
import io.ruin.model.stat.StatType;

import java.util.function.BiFunction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/2/2021
 */
public class PearlBoltEffect extends BoltEffect {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if (hit.attacker.player == null)
            return false;
        if(!Random.rollPercent(getProcChange(target, hit)))
            return false;
        int newDamage = hit.damage;
        if (target.player != null) {
            boolean hasFire = target.player.getEquipment().hasAtLeastOneOf(Items.FIRE_CAPE, Items.STAFF_OF_FIRE, Items.FIRE_BATTLESTAFF, Items.MYSTIC_FIRE_STAFF);
            boolean hasWater = target.player.getEquipment().hasAtLeastOneOf(Items.STAFF_OF_WATER, Items.WATER_BATTLESTAFF, Items.MYSTIC_WATER_STAFF);
            if (hasWater)
                return false;
            newDamage += (int) (hit.attacker.player.getStats().get(StatType.Ranged).currentLevel * (hasFire ? 0.06 : 0.05));
        } else if (target.npc != null) {
            boolean fireMonster = target.npc.getDef().hasCustomValue("FIERY");
            newDamage += (int) (hit.attacker.player.getStats().get(StatType.Ranged).currentLevel * (fireMonster ? 0.06 : 0.05));
        }
        target.graphics(750);
        hit.fixedDamage(newDamage);
        return true;
    }

    @Override
    protected int getBaseChance(Entity target) {
        return 6;
    }
}

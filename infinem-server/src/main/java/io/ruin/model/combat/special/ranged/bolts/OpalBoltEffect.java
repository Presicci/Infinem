package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.stat.StatType;

import java.util.function.BiFunction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/2/2021
 */
public class OpalBoltEffect extends BoltEffect {

    public OpalBoltEffect() {
        super(false);
    }

    public OpalBoltEffect(boolean alwaysTrigger) {
        super(alwaysTrigger);
    }

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(getProcChange(target, hit)))
            return false;
        int newDamage = hit.damage + (int) (hit.attacker.player != null ? hit.attacker.player.getStats().get(StatType.Ranged).currentLevel * 0.10D : 0);
        target.graphics(749);
        hit.fixedDamage(newDamage);
        target.hit(hit);
        return true;
    }

    @Override
    protected int getBaseChance(Entity target) {
        return 5;
    }
}

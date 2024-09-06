package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

public class DiamondBoltEffect extends BoltEffect {

    public DiamondBoltEffect() {
        super(false);
    }

    public DiamondBoltEffect(boolean alwaysTrigger) {
        super(alwaysTrigger);
    }

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(getProcChange(target, hit)))
            return false;
        target.graphics(758);
        target.hit(hit.boostDamage(0.15).ignoreDefence());
        return true;
    }

    @Override
    protected int getBaseChance(Entity target) {
        return target.player != null ? 5 :  10;
    }
}
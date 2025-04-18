package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

public class OnyxBoltEffect extends BoltEffect {

    public OnyxBoltEffect() {
        super(false);
    }

    public OnyxBoltEffect(boolean alwaysTrigger) {
        super(alwaysTrigger);
    }

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(getProcChange(target, hit)))
            return false;
        int damage = target.hit(hit.boostDamage(0.20));
        int heal = (int) (damage * 0.25);
        if(heal > 0) {
            target.graphics(753);
            hit.attacker.incrementHp(heal);
        }
        return true;
    }

    @Override
    protected int getBaseChance(Entity target) {
        return target.player != null ? 10 : 11;
    }
}
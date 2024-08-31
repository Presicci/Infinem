package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

public class EmeraldBoltEffect extends BoltEffect {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(getProcChange(target)))
            return false;
        target.poison(5);
        target.graphics(752);
        target.hit(hit);
        return true;
    }

    @Override
    protected int getBaseChance(Entity target) {
        return target.player != null ? 54 : 55;
    }
}
package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

import java.util.function.BiFunction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/2/2021
 */
public class TopazBoltEffect extends BoltEffect {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(getProcChange(target)) || target.player == null)
            return false;
        target.graphics(757);
        ((Player) target).getStats().get(StatType.Magic).drain(1);
        return true;
    }

    @Override
    protected int getBaseChance(Entity target) {
        return target.player != null ? 4 : 0;
    }
}

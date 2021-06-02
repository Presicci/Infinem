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
public class JadeBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent( // Factor the players agility level in
                target.player != null ? Math.min(6, (int) Math.floor((99 - target.player.getStats().get(StatType.Agility).currentLevel) / 11))
                        : 6))
            return false;
        target.graphics(755);
        target.startEvent(e -> {
           target.lock();
           e.delay(2);
           target.unlock();
        });
        return true;
    }
}

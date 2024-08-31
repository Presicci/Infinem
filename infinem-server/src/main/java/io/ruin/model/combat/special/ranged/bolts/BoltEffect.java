package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.model.combat.Hit;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/31/2024
 */
public abstract class BoltEffect implements BiFunction<Entity, Hit, Boolean> {

    protected abstract int getBaseChance(Entity target);

    protected int getProcChange(Entity target, Hit hit) {
        int baseChance = getBaseChance(target);
        if (target.player == null && hit.attacker.player != null && hit.attacker.player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE)) {
            baseChance = (int) Math.floor(baseChance * 1.5);
        }
        return baseChance;
    }
}

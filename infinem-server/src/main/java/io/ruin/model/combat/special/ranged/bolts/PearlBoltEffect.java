package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.stat.StatType;

import java.util.function.BiFunction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/2/2021
 */
public class PearlBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    private static final String[] fieryMonsters = {
            "fire giant", "pyrefiend", "fire elemental", "bronze dragon", "iron dragon", "steel dragon", "mithril dragon", "adamant dragon", "rune dragon",
            "blue dragon", "green dragon", "red dragon", "black dragon", "king black dragon", "lava dragon"
    };

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(6))
            return false;
        int newDamage = hit.damage + (int) (hit.attacker.player != null ? hit.attacker.player.getStats().get(StatType.Ranged).currentLevel * 0.5D : 0);
        for (String name : fieryMonsters) {
            if (target.npc != null && NPCDef.get(((NPC) target).getId()).name.equalsIgnoreCase(name)) {
                newDamage *= 1.3;   // 30% damage boost against fire monsters
            }
        }
        target.graphics(750);
        hit.fixedDamage(newDamage);
        return true;
    }
}

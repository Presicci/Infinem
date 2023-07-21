package io.ruin.model.combat.npc.slayer.superiors.nechryarch;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class RangeDeathSpawn extends NPCCombat {

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(5);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(5))
            return false;
        npc.animate(info.attack_animation);
        target.hit(new Hit(null, info.attack_style).randDamage(info.max_damage).ignorePrayer());
        return true;
    }

    @Override
    public boolean multiCheck(Entity entity) {
        return true;
    }
}

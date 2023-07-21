package io.ruin.model.combat.npc.slayer.superiors.nechryarch;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class MeleeDeathSpawn extends NPCCombat {

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
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

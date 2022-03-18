package io.ruin.model.activities.miscpvm.basic;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/18/2022
 */
public class BasicArcherCombat extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(9, 40, 40, 30, 25, 8, 16, 64);

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(6);
    }

    @Override
    public boolean attack() {
        if(withinDistance(6)) {
            projectileAttack(PROJECTILE, info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }

}
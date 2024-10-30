package io.ruin.model.combat.npc.wilderness;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/29/2024
 */
public class EscapeCaveSpider extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(2362, 5, 36, 10, 40, 5, 15, 0);

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
package io.ruin.model.activities.combat.bosses.sarachnis;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class SarachnisSpawn extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(1682, 43, 31, 51, 56, 10, 16, 64);


    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        npc.animate(npc.getDef().combatInfo.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if(hit.damage > 0) {
                t.graphics(1683, 124, delay);
            } else {
                t.graphics(85, 124, delay);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }
}

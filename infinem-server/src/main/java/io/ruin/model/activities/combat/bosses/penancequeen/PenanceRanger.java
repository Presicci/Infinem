package io.ruin.model.activities.combat.bosses.penancequeen;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/27/2024
 */
public class PenanceRanger extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(866, 60, 31, 0, 56, 10, 16, 64);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        rangeAttack();
        return true;
    }

    private void rangeAttack() {
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
        hit.postDamage(t -> {
            t.graphics(865);
        });
    }
}

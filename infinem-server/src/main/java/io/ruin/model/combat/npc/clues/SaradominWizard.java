package io.ruin.model.combat.npc.clues;

import io.ruin.model.combat.npc.basic.BasicPoisonCombat;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;


/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/25/2022
 */
public class SaradominWizard extends BasicPoisonCombat {

    private static final Projectile PROJ = new Projectile(64, 0);

    @Override
    public void init() {
        npc.forceText("For Saradomin!");
    }

    @Override
    public void follow() {
        if (withinDistance(2)) {
            follow(1);
        } else {
            follow(8);
        }
    }

    @Override
    public boolean attack() {
        if (withinDistance(1) && target.player != null && !target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
            return super.attack();
        }
        if (!withinDistance(8))
            return false;
        npc.animate(811);
        int delay = PROJ.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).clientDelay(delay).randDamage(20);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                t.graphics(76, 128, 0);
                t.privateSound(1659);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }
}

package io.ruin.model.combat.npc.clues;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/23/2022
 */
public class AncientWizard extends NPCCombat {

    private static final Projectile BLOODBLITZ = new Projectile(374, 43, 0, 51, 56, 10, 16, 64);

    @Override
    public void init() {
        npc.forceText(Random.get(1, 2) == 1 ? "For Zaros!" : "For Zaros!");
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        npc.publicSound(222, 1, 0);
        npc.animate(1978);
        int delay = BLOODBLITZ.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).clientDelay(delay).randDamage(info.max_damage);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                t.graphics(375);
                t.privateSound(104);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }
}

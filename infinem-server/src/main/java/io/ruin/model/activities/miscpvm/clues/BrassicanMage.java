package io.ruin.model.activities.miscpvm.clues;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/22/2022
 */
public class BrassicanMage extends NPCCombat {

    // TODO CHANGE TO CABBAGE GFX
    private static final Projectile EARTH_WAVE = new Projectile(165, 43, 31, 30, 56, 10, 16, 64);

    @Override
    public void init() {
        npc.forceText(Random.get(Arrays.asList("Rest in cabbage!", "Vegetate!", "Grow up or leave.")));
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        npc.graphics(164, 92, 0);
        npc.publicSound(222, 1, 0);
        npc.animate(1167);
        int delay = EARTH_WAVE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).ignorePrayer().clientDelay(delay).randDamage(info.max_damage);;
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                t.graphics(166, 124, 0);
                t.privateSound(223);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }
}

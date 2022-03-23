package io.ruin.model.activities.miscpvm.basic;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/23/2022
 */
public class BasicPoisonCombat extends NPCCombat {

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            npc.animate(info.attack_animation);
            Hit hit = new Hit(npc, info.attack_style, null).randDamage(info.max_damage);
            hit.postDefend((entity) -> {    // 25% chance on successful attack to poison
                if (entity.player != null && !hit.isBlocked() && Random.rollDie(4)) {
                    entity.player.poison(6);
                }
            });
            target.hit(hit);
            return true;
        }
        return false;
    }

}
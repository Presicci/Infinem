package io.ruin.model.combat.npc;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.npc.magic.NPCCombatSpells;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/19/2023
 */
public class Catablepon extends NPCCombat {
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
        if (Random.get(5) == 0) {
            weaken();
        } else {
            basicAttack();
        }
        return true;
    }

    private void weaken() {
        NPCCombatSpells weaken = NPCCombatSpells.WEAKEN;
        npc.animate(4272);
        npc.graphics(weaken.getCastGfxId(), weaken.getCastGfxHeight(), 0);
        npc.publicSound(weaken.getCastSoundId(), weaken.getCastSoundType(), 0);
        int delay = weaken.getProjectile().send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(0)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if (weaken.getOnHitAction() != null) {
                if (!hit.isBlocked()) {
                    t.graphics(weaken.getHitGfxId(), weaken.getHitGfxHeight(), 0);
                    t.publicSound(weaken.getHitSound(), 1, 0);
                    weaken.getOnHitAction().accept(t);
                } else {
                    t.graphics(85, 124, 0);
                    t.publicSound(227, 1, 0);
                }
                hit.hide();
            }
        });
        target.hit(hit);
    }
}

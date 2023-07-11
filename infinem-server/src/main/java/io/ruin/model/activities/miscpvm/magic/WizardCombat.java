package io.ruin.model.activities.miscpvm.magic;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2023
 */
public abstract class WizardCombat extends NPCCombat {
    protected NPCCombatSpells standardCast;
    protected NPCCombatSpells altCast;

    protected int altCastChance = 3;

    @Override
    public void init() {}

    @Override
    public void follow() {
        if (standardCast != null)
            follow(8);
        else {
            follow(1);
        }
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (altCast != null && Random.get(altCastChance) == 1) {
            cast(altCast);
        } else if (standardCast != null){
            cast(standardCast);
        } else {
            if (!withinDistance(1))
                return false;
            basicAttack();
            return true;
        }
        return true;
    }

    protected boolean playCastEffects() {
        return true;
    }

    protected void onCast(boolean standard) {}

    private void cast(NPCCombatSpells spell) {
        if (playCastEffects()) {
            npc.animate(spell.getAnimId());
            npc.graphics(spell.getCastGfxId(), spell.getCastGfxHeight(), 0);
        } else {
            npc.animate(npc.getCombat().getInfo().attack_animation);
        }
        npc.publicSound(spell.getCastSoundId(), spell.getCastSoundType(), 0);
        int delay = spell.getProjectile().send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(spell.getOnHitAction() != null ? 0 : info.max_damage)
                .clientDelay(delay);
        onCast(spell == standardCast);
        hit.postDamage(t -> {
            if (spell.getOnHitAction() != null) {
                if (!hit.isBlocked()) {
                    t.graphics(spell.getHitGfxId(), spell.getHitGfxHeight(), 0);
                    t.publicSound(spell.getHitSound(), 1, 0);
                    spell.getOnHitAction().accept(t);
                } else {
                    t.graphics(85, 124, 0);
                    t.publicSound(227, 1, 0);
                }
                hit.hide();
            } else {
                if(hit.isBlocked()) {
                    t.graphics(85, 124, 0);
                    t.publicSound(227, 1, 0);
                    hit.hide();
                } else {
                    t.graphics(spell.getHitGfxId(), spell.getHitGfxHeight(), 0);
                    t.publicSound(spell.getHitSound(), 1, 0);
                }
            }
        });
        target.hit(hit);
    }
}

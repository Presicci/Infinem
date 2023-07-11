package io.ruin.model.activities.miscpvm.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/10/2023
 */
public class SkeletonMage83 extends WizardCombat {
    @Override
    protected boolean playCastEffects() {
        return false;
    }

    @Override
    protected int getCastAnimation() {
        return npc.getCombat().getInfo().attack_animation;
    }

    @Override
    public void init() {
        standardCast = NPCCombatSpells.FIRE_STRIKE;
        altCast = NPCCombatSpells.VULNERABILITY;
        altCastChance = 5;
    }
}

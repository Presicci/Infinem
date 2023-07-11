package io.ruin.model.activities.miscpvm.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/10/2023
 */
public class SkeletonMage16 extends WizardCombat {
    @Override
    protected boolean playCastEffects() {
        return false;
    }

    @Override
    protected int getCastAnimation() {
        return 5523;
    }

    @Override
    protected void onCast(boolean standard) {
        npc.forceText("I infect your body with rot...");
    }

    @Override
    public void init() {
        altCast = NPCCombatSpells.SKELETON_MAGE_WEAKEN;
        altCastChance = 6;
    }
}

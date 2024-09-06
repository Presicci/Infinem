package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/6/2024
 */
public class UndeadDruid extends WizardCombat {
    @Override
    protected boolean playCastEffects() {
        return false;
    }

    @Override
    protected int getCastAnimation() {
        return 5579;
    }

    @Override
    public void init() {
        standardCast = NPCCombatSpells.EARTH_STRIKE;
    }
}

package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/9/2023
 */
public class EarthStrikeMage extends WizardCombat {
    @Override
    public void init() {
        standardCast = NPCCombatSpells.EARTH_STRIKE;
        useBaseAnimation = true;
    }

    @Override
    protected boolean playCastEffects() {
        return false;
    }
}
package io.ruin.model.activities.miscpvm.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2023
 */
public class DarkWizard extends WizardCombat {
    @Override
    public void init() {
        standardCast = NPCCombatSpells.EARTH_STRIKE;
        altCast = NPCCombatSpells.WEAKEN;
        altCastChance = 3;
    }
}

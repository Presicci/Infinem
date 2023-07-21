package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2023
 */
public class DarkWizardLevelSeven extends WizardCombat {
    @Override
    public void init() {
        standardCast = NPCCombatSpells.WATER_STRIKE;
        altCast = NPCCombatSpells.CONFUSE;
        altCastChance = 3;
    }
}

package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/13/2023
 */
public class ChaosDruid extends WizardCombat {
    @Override
    public void init() {
        altCast = NPCCombatSpells.CONFUSE;
        altCastChance = 3;
    }
}

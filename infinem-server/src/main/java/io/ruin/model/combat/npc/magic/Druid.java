package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/14/2023
 */
public class Druid extends WizardCombat {
    @Override
    public void init() {
        altCast = NPCCombatSpells.DRUID_FAKE_ENTANGLE;
        altCastChance = 3;
    }
}

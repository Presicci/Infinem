package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/9/2023
 */
public class ScarabMage extends WizardCombat {
    @Override
    public void init() {
        standardCast = NPCCombatSpells.SCARAB_MAGE_SPELL;
    }
}
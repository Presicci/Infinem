package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/17/2023
 */
public class Wizard extends WizardCombat {
    @Override
    public void init() {
        standardCast = NPCCombatSpells.FIRE_STRIKE;
    }
}
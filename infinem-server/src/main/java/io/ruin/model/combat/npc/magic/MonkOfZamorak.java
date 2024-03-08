package io.ruin.model.combat.npc.magic;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/8/2024
 */
public class MonkOfZamorak extends WizardCombat {

    @Override
    protected int getSpellMaxHit() {
        return (int) Math.ceil(info.max_damage * 2.5);
    }

    @Override
    public void init() {
        altCast = NPCCombatSpells.FLAMES_OF_ZAMORAK;
        altCastChance = 5;
    }
}

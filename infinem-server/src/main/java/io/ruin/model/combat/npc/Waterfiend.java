package io.ruin.model.combat.npc;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.npc.magic.NPCCombatSpells;
import io.ruin.model.combat.npc.magic.WizardCombat;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
public class Waterfiend extends WizardCombat {

    private static final Projectile ICE_ARROW = new Projectile(16, 23, 36, 41, 51, 5, 15, 11);

    @Override
    protected int getCastAnimation() {
        return 7820;
    }

    @Override
    public void init() {
        standardCast = NPCCombatSpells.WATER_BLAST;
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(2)) {
            cast(standardCast);
        } else {
            rangedAttack();
        }
        return true;
    }

    private void rangedAttack() {
        projectileAttack(ICE_ARROW, 7820, AttackStyle.MAGICAL_RANGED, info.max_damage);
    }
}

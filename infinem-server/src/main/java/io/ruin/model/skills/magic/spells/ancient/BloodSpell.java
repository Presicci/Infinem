package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.combat.SetEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.skills.magic.spells.TargetSpell;

public abstract class BloodSpell extends TargetSpell {

    @Override
    protected void afterHit(Hit hit, Entity target) {
        if(hit.damage > 0) {
            int healAmount = hit.damage / 4;
            if (hit.attacker.player != null) {
                int bloodbarkPieceCount = SetEffect.BLOODBARK.numberOfPieces(hit.attacker.player);
                if (bloodbarkPieceCount > 0) {
                    healAmount = (int) (healAmount * (1D + (bloodbarkPieceCount * 0.02)));
                }
            }
            hit.attacker.incrementHp(healAmount);
        }
    }

}
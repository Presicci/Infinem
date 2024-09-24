package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.combat.SetEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.skills.magic.spells.TargetSpell;

public abstract class BloodSpell extends TargetSpell {

    @Override
    protected void afterHit(Hit hit, Entity target) {
        if(hit.damage > 0) {
            double healPercentage = 0.25;
            if (hit.attacker.player != null) {
                int bloodbarkPieceCount = SetEffect.BLOODBARK.numberOfPieces(hit.attacker.player);
                if (bloodbarkPieceCount > 0) {
                    healPercentage += bloodbarkPieceCount * 0.02;
                }
            }
            int healAmount = (int) (hit.damage * healPercentage);
            hit.attacker.incrementHp(healAmount);
        }
    }

}
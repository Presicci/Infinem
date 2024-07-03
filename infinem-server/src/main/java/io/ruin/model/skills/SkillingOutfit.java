package io.ruin.model.skills;

import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
@Getter
public enum SkillingOutfit {
    ;

    private final StatType statType;
    private final double setBonus;
    private final SkillingOutfitPiece[] pieces;

    SkillingOutfit(StatType statType, double setBonus, SkillingOutfitPiece... pieces) {
        this.statType = statType;
        this.setBonus = setBonus;
        this.pieces = pieces;
    }

    public double getExperienceBonus(Player player) {
        double bonus = 1D;
        int equippedPieces = 0;
        for (SkillingOutfitPiece piece : pieces) {
            if (player.getEquipment().hasId(piece.itemId)) {
                bonus += piece.experienceBonus;
                ++equippedPieces;
            }
        }
        if (setBonus > 0D && equippedPieces >= pieces.length) {
            bonus += setBonus;
        }
        return bonus;
    }

    public static double getExperienceBonus(Player player, StatType statType) {
        double bonus = 1D;
        for (SkillingOutfit outfit : values()) {
            if (outfit.statType == statType) {
                bonus += outfit.getExperienceBonus(player);
            }
        }
        return bonus;
    }

    @Getter
    @AllArgsConstructor
    private static class SkillingOutfitPiece {
        private final int itemId;
        private final double experienceBonus;
    }
}

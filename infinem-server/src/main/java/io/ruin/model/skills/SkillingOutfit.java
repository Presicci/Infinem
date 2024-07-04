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
    CONSTRUCTION(StatType.Construction, 0.5,
            new SkillingOutfitPiece(0.4, 24872),
            new SkillingOutfitPiece(0.8, 24874),
            new SkillingOutfitPiece(0.6, 24876),
            new SkillingOutfitPiece(0.2, 24878)
    );

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
        piece: for (SkillingOutfitPiece piece : pieces) {
            for (int itemId : piece.itemIds) {
                if (player.getEquipment().hasId(itemId)) {
                    bonus += piece.experienceBonus;
                    ++equippedPieces;
                    continue piece;
                }
            }
        }
        if (setBonus > 0D && equippedPieces >= pieces.length) {
            bonus += setBonus;
        }
        player.sendMessage(bonus + "");
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
    private static class SkillingOutfitPiece {
        private final double experienceBonus;
        private final int[] itemIds;

        private SkillingOutfitPiece(double experienceBonus, int... itemIds) {
            this.experienceBonus = experienceBonus;
            this.itemIds = itemIds;
        }
    }
}

package io.ruin.model.skills;

import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
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
    ),
    GRACEFUL(StatType.Agility, 0.4, // 3% total boost
            // Normal, purple, blue, yellow, red, green, white, dark blue, black, brown
            new SkillingOutfitPiece(0.35, 11850, 13580, 13592, 13604, 13616, 13628, 13668, 21063, 24745, 25071),            // Hood
            new SkillingOutfitPiece(0.4, 11852, 13582, 13594, 13606, 13618, 13630, 13670, 21066, 24748, 25074),             // Cape
            new SkillingOutfitPiece(0.55, 11854, 13584, 13596, 13608, 13620, 13632, 13672, 21069, 24751, 25077),            // Top
            new SkillingOutfitPiece(0.6, 11856, 13586, 13598, 13610, 13622, 13634, 13674, 21072, 24754, 25080),             // Legs
            new SkillingOutfitPiece(0.3, 11858, 13588, 13600, 13612, 13624, 13636, 13676, 21075, 24757, 25083),             // Gloves
            new SkillingOutfitPiece(0.4, 11860, 13590, 13602, 13614, 13626, 13638, 13678, 21078, 24760, 25086)              // Boots
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

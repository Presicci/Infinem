package io.ruin.model.skills;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Items;
import io.ruin.model.stat.StatType;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
@Getter
public enum SkillingOutfit {
    CONSTRUCTION(StatType.Construction, 0.028,
            new SkillingOutfitPiece(0.016, 24872),
            new SkillingOutfitPiece(0.024, 24874),
            new SkillingOutfitPiece(0.02, 24876),
            new SkillingOutfitPiece(0.012, 24878)
    ),
    GRACEFUL(StatType.Agility, 0.016, // 5% total boost
            // Normal, arceuus, port piscarilius, lovakengj, shayzien, hosidius, kourend, brimhaven agility, hallowed sepulchre, trailblazer
            new SkillingOutfitPiece(Config.GRACEFUL_HOOD, 0.012, 11850, 13580, 13592, 13604, 13616, 13628, 13668, 21063, 24745, 25071, 27446, 30047),            // Hood
            new SkillingOutfitPiece(Config.GRACEFUL_CAPE, 0.014, 11852, 13582, 13594, 13606, 13618, 13630, 13670, 21066, 24748, 25074, 27449, 30050),             // Cape
            new SkillingOutfitPiece(Config.GRACEFUL_TORSO, 0.02, 11854, 13584, 13596, 13608, 13620, 13632, 13672, 21069, 24751, 25077, 27452, 30053),            // Top
            new SkillingOutfitPiece(Config.GRACEFUL_LEGS, 0.016, 11856, 13586, 13598, 13610, 13622, 13634, 13674, 21072, 24754, 25080, 27455, 30056),             // Legs
            new SkillingOutfitPiece(Config.GRACEFUL_GLOVES, 0.01, 11858, 13588, 13600, 13612, 13624, 13636, 13676, 21075, 24757, 25083, 27458, 30059),             // Gloves
            new SkillingOutfitPiece(Config.GRACEFUL_BOOTS, 0.012, 11860, 13590, 13602, 13614, 13626, 13638, 13678, 21078, 24760, 25086, 27461, 30062)              // Boots
    ),
    PROSPECTOR(StatType.Mining, 0.028,
            new SkillingOutfitPiece(0.016, Items.PROSPECTOR_HELMET, 25549),
            new SkillingOutfitPiece(0.024, Items.PROSPECTOR_JACKET, Items.VARROCK_ARMOUR_4, 25551),
            new SkillingOutfitPiece(0.02, Items.PROSPECTOR_LEGS, 25553),
            new SkillingOutfitPiece(0.012, Items.PROSPECTOR_BOOTS, 25555)
    ),
    LUMBERJACK(StatType.Woodcutting, 0.028,
            new SkillingOutfitPiece(0.016, Items.LUMBERJACK_HAT),
            new SkillingOutfitPiece(0.024, Items.LUMBERJACK_TOP),
            new SkillingOutfitPiece(0.02, Items.LUMBERJACK_LEGS),
            new SkillingOutfitPiece(0.012, Items.LUMBERJACK_BOOTS)
    ),
    ANGLER(StatType.Fishing, 0.028,
            new SkillingOutfitPiece(0.016, Items.ANGLER_HAT),
            new SkillingOutfitPiece(0.024, Items.ANGLER_TOP),
            new SkillingOutfitPiece(0.02, Items.ANGLER_WADERS),
            new SkillingOutfitPiece(0.012, Items.ANGLER_BOOTS)
    ),
    ZEALOT(StatType.Prayer, 0.028,
            new SkillingOutfitPiece(0.016, 25438),
            new SkillingOutfitPiece(0.024, 25434),
            new SkillingOutfitPiece(0.02, 25436),
            new SkillingOutfitPiece(0.012, 25440)
    ),
    ROGUE(StatType.Thieving, 0.022,
            new SkillingOutfitPiece(0.016, Items.ROGUE_MASK),
            new SkillingOutfitPiece(0.02, Items.ROGUE_TOP),
            new SkillingOutfitPiece(0.018, Items.ROGUE_TROUSERS),
            new SkillingOutfitPiece(0.012, Items.ROGUE_GLOVES),
            new SkillingOutfitPiece(0.012, Items.ROGUE_BOOTS)
    ),
    PYROMANCER(StatType.Firemaking, 0.028,
            new SkillingOutfitPiece(0.016, Items.PYROMANCER_HOOD),
            new SkillingOutfitPiece(0.024, Items.PYROMANCER_GARB),
            new SkillingOutfitPiece(0.02, Items.PYROMANCER_ROBE),
            new SkillingOutfitPiece(0.012, Items.PYROMANCER_BOOTS)
    )
    ;

    private final StatType statType;
    private final double setBonus;
    private final SkillingOutfitPiece[] pieces;

    SkillingOutfit(StatType statType, double setBonus, SkillingOutfitPiece... pieces) {
        this.statType = statType;
        this.setBonus = setBonus;
        this.pieces = pieces;
    }

    public int countSetPiecesForInterface(Player player) {
        int count = 0;
        for (SkillingOutfitPiece piece : pieces) {
            boolean found = false;
            for (int itemId : piece.itemIds) {
                if (player.getEquipment().hasId(itemId)) {
                    if (piece.pieceConfig != null) piece.pieceConfig.set(player, 1);
                    found = true;
                    ++count;
                    break;
                }
            }
            if (!found && piece.pieceConfig != null) piece.pieceConfig.set(player, 0);
        }
        return count;
    }

    public boolean hasPieces(Player player) {
        for (SkillingOutfitPiece piece : pieces) {
            boolean found = false;
            for (int itemId : piece.itemIds) {
                if (player.getEquipment().hasId(itemId)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
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
        return bonus;
    }

    public static double getExperienceBonus(Player player, StatType statType) {
        double bonus = 1D;
        for (SkillingOutfit outfit : values()) {
            if (outfit.statType == statType) {
                bonus *= outfit.getExperienceBonus(player);
            }
        }
        return bonus;
    }

    @Getter
    private static class SkillingOutfitPiece {
        private final Config pieceConfig;
        private final double experienceBonus;
        private final int[] itemIds;

        private SkillingOutfitPiece(double experienceBonus, int... itemIds) {
            this.pieceConfig = null;
            this.experienceBonus = experienceBonus;
            this.itemIds = itemIds;
        }

        private SkillingOutfitPiece(Config config, double experienceBonus, int... itemIds) {
            this.pieceConfig = config;
            this.experienceBonus = experienceBonus;
            this.itemIds = itemIds;
        }
    }
}

package io.ruin.model.entity.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/3/2024
 */
@Getter
@AllArgsConstructor
public enum DonatorBenefits {
    DEFAULT(800),
    SAPPHIRE(850),
    EMERALD(900),
    RUBY(950),
    DIAMOND(1000);

    private final int bankSize;

    public static DonatorBenefits getBenefits(Player player) {
        if (player.isDiamond()) return DIAMOND;
        else if (player.isRuby()) return RUBY;
        else if (player.isEmerald()) return EMERALD;
        else if (player.isSapphire()) return SAPPHIRE;
        else return DEFAULT;
    }
}

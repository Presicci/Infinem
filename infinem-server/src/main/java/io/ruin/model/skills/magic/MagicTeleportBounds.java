package io.ruin.model.skills.magic;

import io.ruin.model.map.Bounds;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
@Getter
@AllArgsConstructor
public enum MagicTeleportBounds {
    // Modern Spellbook
    VARROCK(new Bounds(3211, 3422, 3214, 3424, 0)),
    VARROCK_GE(new Bounds(3163, 3475, 3166, 3478, 0)),
    LUMBRIDGE(new Bounds(3221, 3218, 3224, 3219, 0)),
    FALADOR(new Bounds(2964, 3378, 2966, 3379, 0)),
    CAMELOT(new Bounds(2756, 3476, 2759, 3480, 0)),
    CAMELOT_SEERS(new Bounds(2726, 3485, 2727, 3486, 0)),
    ARDOUGNE(new Bounds(2659, 3304, 2664, 3308, 0)),
    WATCHTOWER(new Bounds(2546, 3112, 2547, 3113, 2)),
    WATCHTOWER_YANILLE(new Bounds(2580, 3096, 2584, 3100, 0)),
    TROLLHEIM(new Bounds(2890, 3678, 2893, 3680, 0)),
    APE_ATOLL(new Bounds(2784, 2785, 2785, 2786, 0)),
    KOUREND(new Bounds(1644, 3672, 1642, 3674, 0)),

    // Ancient Spellbook
    PADDEWWA(new Bounds(3098, 9881, 3095, 9884, 0)),
    SENNTISTEN(new Bounds(3317, 3335, 3321, 3338, 0)),
    KHARYLL(new Bounds(3490, 3471, 3493, 3472, 0)),
    LASSAR(new Bounds(3004, 3470, 3007, 3473, 0)),
    DAREEYAK(new Bounds(2965, 3964, 2969, 3697, 0)),
    CARRALLANGAR(new Bounds(3162, 3663, 3164, 3665, 0)),
    ANNAKARL(new Bounds(3287, 3886, 3288, 3887, 0)),
    GHORROCK(new Bounds(2970, 3871, 2975, 3875, 0)),
    ;

    private final Bounds bounds;
}

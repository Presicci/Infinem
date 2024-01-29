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

    // Lunar Spellbook
    MOONCLAN(new Bounds(2112, 3914, 2114, 3917, 0)),
    OURANIA(new Bounds(2466, 3245, 2469, 3248, 0)),
    WATERBIRTH(new Bounds(2545, 3753, 2548, 3757, 0)),
    BARBARIAN(new Bounds(2541, 3566, 2544, 3570, 0)),
    KHAZARD(new Bounds(2636, 3166, 2639, 3168, 0)),
    FISHING_GUILD(new Bounds(2609, 3390, 2614, 3393, 0)),
    CATHERBY(new Bounds(2801, 3447, 2804, 3451, 0)),
    ICE_PLATEAU(new Bounds(2972, 3938, 2975, 3940, 0)),

    // Arceuus Spellbook
    ARCEUUS_LIBRARY(new Bounds(1631, 3835, 1634, 3838, 0)),
    DRAYNOR_MANOR(new Bounds(3108, 3350, 3109, 3351, 0)),
    BATTLEFRONT(new Bounds(1345, 3740, 1347, 3741, 0)),
    MIND_ALTAR(new Bounds(2979, 3509, 2980, 3510, 0)),
    SALVE_GRAVEYARD(new Bounds(3433, 3460, 3435, 3462, 0)),
    FENKENSTRAINS_CASTLE(new Bounds(3547, 3528, 3549, 3529, 0)),
    WEST_ARDOUGNE(new Bounds(2499, 3291, 2501, 3292, 0)),
    HARMONY_ISLAND(new Bounds(3796, 2864, 3798, 2866, 0)),
    CEMETARY(new Bounds(2979, 3763, 2981, 3763, 0)),
    BARROWS(new Bounds(3564, 3313, 3566, 3315, 0)),
    APE_ATOLL_DUNGEON(new Bounds(2768, 2702, 2769, 2704, 0));

    private final Bounds bounds;
}

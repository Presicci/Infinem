package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
@Getter
@AllArgsConstructor
public enum JewelleryTeleportBounds {
    // Amulet of Glory
    EDGEVILLE(new Bounds(3086, 3494, 3088, 3499, 0)),
    KARAMJA(new Bounds(2916, 3174, 2919, 3176, 0)),
    DRAYNOR_VILLAGE(new Bounds(3104, 3249, 3105, 3253, 0)),
    AL_KHARID(new Bounds(3291, 3162, 3295, 3163, 0)),

    // Ring of Wealth
    MISCELLANIA(new Bounds(2535, 3860, 2538, 3863, 0)),
    GRAND_EXCHANGE(new Bounds(3162, 3478, 3167, 3479, 0)),
    FALADOR(new Bounds(2994, 3374, 2997, 3376, 0)),
    DONDAKAN(new Bounds(2829, 10164, 2833, 10167, 0)),

    // Burning Amulet
    CHAOS_TEMPLE(new Bounds(3231, 3635, 3235, 3637, 0)),
    BANDIT_CAMP(new Bounds(3035, 3649, 3038, 3651, 0)),
    LAVA_MAZE(new Bounds(3026, 3837, 3029, 3840, 0)),

    // Combat Bracelet
    WARRIORS_GUILD(new Bounds(2881, 3548, 2884, 3551, 0)),
    CHAMPIONS_GUILD(new Bounds(3189, 3366, 3192, 3369, 0)),
    EDGEVILLE_MONASTERY(new Bounds(3051, 3486, 3053, 3489, 0)),
    RANGING_GUILD(new Bounds(2653, 3439, 2656, 3443, 0)),

    // Digsite Pendant
    DIGSITE(new Bounds(3337, 3443, 3344, 3444, 0)),
    HOUSE_ON_THE_HILL(new Bounds(3763, 3868, 3765, 3870, 1)),
    LITHKREN(new Bounds(1565, 5072, 1570, 5077, 0)),

    // Games Necklace
    BURTHORPE(new Bounds(2897, 3551, 2900, 3555, 0)),
    BARBARIAN_OUTPOST(new Bounds(2518, 3570, 2520, 3572, 0)),
    CORPOREAL_BEAST(new Bounds(2967, 4382, 2969, 4385, 2)),
    TEARS_OF_GUTHIX(new Bounds(3242, 9498, 3246, 9501, 2)),
    WINTERTODT_CAMP(new Bounds(1623, 3935, 1625, 3939, 0)),

    // Necklace of Passage
    WIZARDS_TOWER(new Bounds(3112, 3176, 3115, 3180, 0)),
    THE_OUTPOST(new Bounds(2431, 3346, 2433, 3349, 0)),
    EAGLES_EYRIE(new Bounds(3403, 3156, 3408, 3159, 0)),

    // Ring of Dueling
    DUEL_ARENA(new Bounds(3313, 3233, 3317, 3236, 0)),
    CASTLE_WARS(new Bounds(2441, 3088, 2443, 3091, 0)),
    FEROX_ENCLAVE(new Bounds(3150, 3634, 3151, 3635, 0)),

    // Skills Necklace
    FISHING_GUILD(new Bounds(2610, 3390, 2614, 3393, 0)),
    MINING_GUILD(new Bounds(3047, 9761, 3050, 9763, 0)),
    CRAFTING_GUILD(new Bounds(2932, 3292, 2934, 3295, 0)),
    COOKING_GUILD(new Bounds(3142, 3438, 3145, 3439, 0)),
    WOODCUTTING_GUILD(new Bounds(1658, 3503, 1664, 3506, 0)),
    FARMING_GUILD(new Bounds(1247, 3716, 1250, 3720, 0)),

    // Slayer Ring
    SLAYER_TOWER(new Bounds(3422, 3535, 3424, 3537, 0)),
    FREMENNIK_SLAYER_DUNGEON(new Bounds(2799, 9997, 2801, 10000, 0)),
    STRONGHOLD_SLAYER_CAVE(new Bounds(2432, 3420, 2434, 3422, 0)),
    DARK_BEASTS(new Bounds(2029, 4637, 2031, 4639, 0)),
    TARNS_LAIR(new Bounds(3184, 4599, 3186, 4599, 0)),

    // Hazelmere's signet ring
    KELDAGRIM(new Bounds(2853, 10198, 2857, 10200, 0)),

    // Desert amulet
    NARDAH(new Bounds(3417, 2916, 3423, 2920, 0)),
    NARDAH_SHRINE(new Bounds(3424, 2927, 3429, 2929, 0)),
    KALPHITE_CAVE(new Bounds(3321, 3120, 3324, 3124, 0)),

    // Fremennik sea boots
    RELLEKKA(new Bounds(2640, 3672, 2646, 3681, 0)),

    // Kandarin headgear
    SHERLOCK(new Bounds(2727, 3409, 2731, 3413, 0)),
    ;

    private final Bounds bounds;
}

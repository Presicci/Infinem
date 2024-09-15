package io.ruin.model.skills.fishing;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Position;

import java.util.*;

public enum FishingArea {
    KARAMBWAN_RIVER(
            new Position(2899, 3119, 0),
            new Position(2912, 3119, 0),
            new Position(2896, 3120, 0)
    ),
    LUMBRIDGE_TUTORIAL(
            new Position(3246, 3156, 0),
            new Position(3246, 3155, 0),
            new Position(3245, 3152, 0),
            new Position(3244, 3150, 0),
            new Position(3242, 3148, 0),
            new Position(3241, 3148, 0),
            new Position(3240, 3147, 0),
            new Position(3240, 3146, 0),
            new Position(3242, 3143, 0),
            new Position(3238, 3141, 0)
    ),
    LUMBRIDGE_RIVER(
            new Position(3238, 3251, 0),
            new Position(3238, 3252, 0),
            new Position(3238, 3253, 0),
            new Position(3238, 3254, 0),
            new Position(3238, 3255, 0),
            new Position(3238, 3256, 0),
            new Position(3239, 3244, 0),
            new Position(3239, 3241, 0)
    ),
    DRAYNOR(
            new Position(3086, 3227, 0),
            new Position(3086, 3228, 0),
            new Position(3085, 3230, 0),
            new Position(3085, 3231, 0)
    ),
    BARBARIAN_VILLAGE(
            new Position(3104, 3424, 0),
            new Position(3104, 3425, 0),
            new Position(3110, 3433, 0),
            new Position(3110, 3434, 0),
            new Position(3110, 3432, 0)
    ),
    CATHERBY(
            new Position(2836, 3431, 0),
            new Position(2837, 3431, 0),
            new Position(2838, 3431, 0),
            new Position(2839, 3431, 0),
            new Position(2840, 3431, 0),

            new Position(2844, 3429, 0),
            new Position(2845, 3429, 0),
            new Position(2846, 3429, 0),

            new Position(2853, 3423, 0),
            new Position(2854, 3423, 0),
            new Position(2855, 3423, 0),

            new Position(2859, 3426, 0),
            new Position(2860, 3426, 0)
    ),
    PISCATORIS(
            new Position(2321, 3702, 0),
            new Position(2317, 3702, 0),
            new Position(2312, 3704, 0),
            new Position(2310, 3704, 0),
            new Position(2307, 3701, 0),
            new Position(2307, 3700, 0),
            new Position(2326, 3700, 0),
            new Position(2327, 3700, 0),
            new Position(2332, 3703, 0),
            new Position(2336, 3703, 0),
            new Position(2337, 3703, 0),
            new Position(2340, 3702, 0),
            new Position(2341, 3702, 0),
            new Position(2342, 3702, 0),
            new Position(2343, 3702, 0),
            new Position(2344, 3702, 0),
            new Position(2345, 3702, 0),
            new Position(2346, 3702, 0),
            new Position(2347, 3702, 0),
            new Position(2348, 3702, 0),
            new Position(2349, 3702, 0)
    ),
    FISHING_GUILD(
            new Position(2598, 3419, 0),
            new Position(2599, 3419, 0),
            new Position(2600, 3419, 0),
            new Position(2602, 3419, 0),
            new Position(2603, 3419, 0),
            new Position(2604, 3419, 0),
            new Position(2605, 3420, 0),
            new Position(2605, 3421, 0),
            new Position(2604, 3422, 0),
            new Position(2603, 3422, 0),
            new Position(2602, 3422, 0),
            new Position(2601, 3422, 0),
            new Position(2601, 3423, 0),
            new Position(2602, 3423, 0),
            new Position(2603, 3423, 0),
            new Position(2604, 3423, 0),
            new Position(2605, 3424, 0),
            new Position(2605, 3425, 0),
            new Position(2604, 3426, 0),
            new Position(2603, 3426, 0),
            new Position(2602, 3426, 0),
            new Position(2598, 3423, 0),
            new Position(2598, 3422, 0),
            new Position(2602, 3411, 0),
            new Position(2602, 3412, 0),
            new Position(2602, 3413, 0),
            new Position(2602, 3414, 0),
            new Position(2602, 3415, 0),
            new Position(2602, 3416, 0),
            new Position(2603, 3417, 0),
            new Position(2604, 3417, 0),
            new Position(2605, 3416, 0),
            new Position(2606, 3416, 0),
            new Position(2607, 3416, 0),
            new Position(2608, 3416, 0),
            new Position(2609, 3416, 0),
            new Position(2610, 3416, 0),
            new Position(2611, 3416, 0),
            new Position(2612, 3415, 0),
            new Position(2612, 3414, 0),
            new Position(2611, 3413, 0),
            new Position(2610, 3413, 0),
            new Position(2609, 3413, 0),
            new Position(2608, 3413, 0),
            new Position(2607, 3413, 0),
            new Position(2606, 3413, 0),
            new Position(2605, 3413, 0),
            new Position(2612, 3412, 0),
            new Position(2612, 3411, 0),
            new Position(2608, 3410, 0),
            new Position(2607, 3410, 0),
            new Position(2606, 3410, 0)
    ),
    RESOURCE_AREA(
            new Position(3181, 3926, 0),
            new Position(3183, 3926, 0),
            new Position(3185, 3926, 0),
            new Position(3187, 3927, 0)
    ),
    PVP_WORLD_37S(
            new Position(3347, 3814, 0),
            new Position(3350, 3817, 0),
            new Position(3360, 3802, 0),
            new Position(3359, 3802, 0)
    ),
    ZEAH_ANGLER(
            new Position(1836, 3771, 0),
            new Position(1833, 3769, 0),
            new Position(1827, 3770, 0),
            new Position(1826, 3770, 0),
            new Position(1840, 3776, 0),
            new Position(1831, 3767, 0)
    ),
    BARBARIAN(
            new Position(2504, 3497, 0),
            new Position(2500, 3509, 0),
            new Position(2500, 3506, 0),
            new Position(2506, 3493, 0),
            new Position(2500, 3512, 0),
            new Position(2500, 3510, 0)
    ),
    INFERNAL_EEL_EAST(
            new Position(2536, 5086, 0),
            new Position(2539, 5088, 0),
            new Position(2540, 5088, 0),
            new Position(2541, 5088, 0)
    ),
    INFERNAL_EEL_SOUTH_AND_WEST(
            new Position(2479, 5078, 0),
            new Position(2478, 5078, 0),
            new Position(2477, 5078, 0),
            new Position(2446, 5104, 0)
    ),
    KARAMJA(
            new Position(2921, 3178, 0),
            new Position(2923, 3179, 0),
            new Position(2923, 3180, 0),
            new Position(2924, 3181, 0),
            new Position(2926, 3179, 0),
            new Position(2926, 3176, 0),
            new Position(2926, 3180, 0),
            new Position(2925, 3181, 0)
    ),
    AL_KHARID(
            new Position(3267, 3148, 0),
            new Position(3266, 3148, 0),
            new Position(3276, 3140, 0),
            new Position(3275, 3140, 0)
    ),
    MORT_MYRE_SWAMP_WEST(
            new Position(3443, 3272, 0),
            new Position(3437, 3272, 0),
            new Position(3439, 3276, 0),
            new Position(3441, 3271, 0),
            new Position(3440, 3281, 0)
    ),
    MORT_MYRE_SWAMP_NORTH_WEST(
            new Position(3424, 3409, 0),
            new Position(3425, 3407, 0),
            new Position(3428, 3405, 0)
    ),
    MORT_MYRE_SWAMP_NORTH_WEST_2(
            new Position(3431, 3415, 0),
            new Position(3435, 3417, 0),
            new Position(3434, 3417, 0)
    ),
    MORT_MYRE_SPAWP_NORTH(
            new Position(3478, 3431, 0),
            new Position(3479, 3430, 0),
            new Position(3479, 3435, 0),
            new Position(3483, 3438, 0)
    ),
    MORT_MYRE_SPAWP_NORTH_2(
            new Position(3489, 3445, 0),
            new Position(3487, 3446, 0)
    ),
    BURGH_DE_ROTT(
            new Position(3476, 3191, 0),
            new Position(3479, 3189, 0),
            new Position(3486, 3184, 0),
            new Position(3489, 3184, 0),
            new Position(3490, 3183, 0),
            new Position(3496, 3178, 0),
            new Position(3497, 3175, 0),
            new Position(3499, 3178, 0)
    ),
    BURGH_DE_ROTT_2(
            new Position(3509, 3180, 0),
            new Position(3512, 3178, 0),
            new Position(3518, 3177, 0)
    ),
    BURGH_DE_ROTT_3(
            new Position(3528, 3171, 0),
            new Position(3526, 3169, 0),
            new Position(3528, 3167, 0),
            new Position(3531, 3171, 0),
            new Position(3538, 3179, 0),
            new Position(3540, 3178, 0),
            new Position(3545, 3181, 0)
    ),
    BURGH_DE_ROTT_4(
            new Position(3559, 3176, 0),
            new Position(3562, 3174, 0),
            new Position(3569, 3176, 0),
            new Position(3574, 3178, 0)
    ),
    MUSA_POINT(
            new Position(2996, 3157, 0),
            new Position(2996, 3158, 0),
            new Position(2996, 3159, 0)
    ),
    MUSA_POINT_2(
            new Position(2986, 3176, 0),
            new Position(2990, 3169, 0),
            new Position(2985, 3179, 0)
    ),
    SHILO_VILLAGE(
            new Position(2855, 2974, 0),
            new Position(2860, 2972, 0),
            new Position(2852, 2973, 0),
            new Position(2845, 2971, 0),
            new Position(2854, 2977, 0),
            new Position(2855, 2977, 0),
            new Position(2859, 2976, 0)
    ),
    KARAMBWANJI(
            new Position(2807, 3021, 0),
            new Position(2791, 3019, 0),
            new Position(2801, 3010, 0)
    ),
    ENTRANA_DOCK(
            new Position(2879, 3335, 0),
            new Position(2879, 3334, 0),
            new Position(2876, 3331, 0),
            new Position(2877, 3331, 0),
            new Position(2875, 3331, 0),
            new Position(2879, 3338, 0),
            new Position(2879, 3339, 0),
            new Position(2877, 3342, 0),
            new Position(2876, 3342, 0),
            new Position(2875, 3342, 0)
    ),
    ENTRANA_RIVER(
            new Position(2845, 3356, 0),
            new Position(2843, 3359, 0),
            new Position(2842, 3359, 0),
            new Position(2840, 3356, 0),
            new Position(2847, 3361, 0),
            new Position(2849, 3361, 0)
    ),
    SHAYZIEN(
            new Position(1591, 3564, 0),
            new Position(1586, 3566, 0),
            new Position(1585, 3566, 0),
            new Position(1584, 3566, 0),
            new Position(1581, 3563, 0),
            new Position(1581, 3562, 0),
            new Position(1581, 3561, 0),
            new Position(1580, 3559, 0),
            new Position(1580, 3558, 0),
            new Position(1580, 3557, 0)
    ),
    KOUREND_WOODLAND_1(
            new Position(1485, 3432, 0),
            new Position(1485, 3431, 0),
            new Position(1485, 3433, 0),
            new Position(1493, 3442, 0),
            new Position(1494, 3443, 0)
    ),
    KOUREND_WOODLAND_2(
            new Position(1539, 3415, 0),
            new Position(1541, 3417, 0),
            new Position(1542, 3417, 0),
            new Position(1544, 3418, 0),
            new Position(1546, 3418, 0),
            new Position(1535, 3414, 0),
            new Position(1534, 3414, 0),
            new Position(1525, 3415, 0)
    ),
    ISLE_OF_SOULS_SOUTH(
            new Position(2164, 2782, 0),
            new Position(2163, 2782, 0),
            new Position(2162, 2782, 0),
            new Position(2161, 2782, 0),
            new Position(2166, 2781, 0)
    ),
    ISLE_OF_SOULS_WEST( // Custom
            new Position(2089, 2884, 0),
            new Position(2089, 2883, 0),
            new Position(2089, 2882, 0),
            new Position(2086, 2890, 0),
            new Position(2085, 2893, 0),
            new Position(2085, 2894, 0)
    ),
    ISLE_OF_SOULS_NORTH(
            new Position(2286, 2974, 0),
            new Position(2287, 2974, 0),
            new Position(2278, 2976, 0),
            new Position(2277, 2976, 0),
            new Position(2276, 2976, 0)
    ),
    ISLE_OF_SOULS_EAST(
            new Position(2288, 2847, 0),
            new Position(2288, 2848, 0),
            new Position(2280, 2839, 0),
            new Position(2280, 2838, 0),
            new Position(2280, 2837, 0),
            new Position(2280, 2836, 0)
    ),
    APE_ATOLL_WEST(
            new Position(2694, 2706, 0),
            new Position(2699, 2702, 0),
            new Position(2700, 2702, 0),
            new Position(2707, 2698, 0),
            new Position(2714, 2695, 0)
    ),
    APE_ATOLL_EAST(
            new Position(2771, 2734, 0),
            new Position(2771, 2733, 0),
            new Position(2777, 2740, 0),
            new Position(2780, 2741, 0),
            new Position(2788, 2741, 0)
    ),
    CORSAIR_COVE(
            new Position(2510, 2838, 0),
            new Position(2509, 2838, 0),
            new Position(2515, 2838, 0),
            new Position(2516, 2838, 0),
            new Position(2505, 2835, 0)
    ),
    FELDIP_HILLS(
            new Position(2454, 2893, 0),
            new Position(2453, 2892, 0),
            new Position(2455, 2890, 0),
            new Position(2458, 2890, 0),
            new Position(2457, 2893, 0)
    ),
    GNOME_VILLAGE(
            new Position(2468, 3157, 0),
            new Position(2472, 3156, 0),
            new Position(2474, 3153, 0),
            new Position(2461, 3150, 0),
            new Position(2461, 3151, 0),
            new Position(2465, 3156, 0),
            new Position(2478, 3164, 0),
            new Position(2462, 3145, 0)
    ),
    NORTH_ARDY(
            new Position(2566, 3370, 0),
            new Position(2562, 3374, 0),
            new Position(2561, 3374, 0)
    ),
    BAXTORIAN_FALLS_SOUTH(
            new Position(2534, 3403, 0),
            new Position(2537, 3406, 0),
            new Position(2533, 3410, 0),
            new Position(2530, 3412, 0),
            new Position(2527, 3412, 0),
            new Position(2507, 3421, 0)
    ),
    RELEKKA_NET(
            new Position(2633, 3688, 0),
            new Position(2633, 3689, 0),
            new Position(2633, 3690, 0),
            new Position(2633, 3691, 0),
            new Position(2633, 3692, 0),
            new Position(2633, 3693, 0)
    ),
    RELEKKA_CAGE(
            new Position(2642, 3694, 0),
            new Position(2642, 3695, 0),
            new Position(2642, 3696, 0),
            new Position(2642, 3697, 0),
            new Position(2642, 3698, 0)
    ),
    RELEKKA_HARPOON(
            new Position(2648, 3708, 0),
            new Position(2647, 3708, 0),
            new Position(2646, 3708, 0),
            new Position(2645, 3708, 0),
            new Position(2648, 3711, 0)
    ),
    BARBARIAN_OUTPOST(
            new Position(2516, 3574, 0),
            new Position(2516, 3575, 0),
            new Position(2515, 3571, 0),
            new Position(2514, 3568, 0),
            new Position(2511, 3562, 0),
            new Position(2507, 3554, 0),
            new Position(2498, 3547, 0)
    ),
    PISCARILIUS(
            new Position(1771, 3798, 0),
            new Position(1770, 3798, 0),
            new Position(1769, 3798, 0),
            new Position(1765, 3796, 0),
            new Position(1763, 3796, 0),
            new Position(1761, 3796, 0),
            new Position(1762, 3796, 0),
            new Position(1750, 3802, 0),
            new Position(1749, 3802, 0),
            new Position(1748, 3802, 0),
            new Position(1747, 3802, 0)
    ),
    HOSIDIUS(
            new Position(1840, 3595, 0),
            new Position(1828, 3613, 0),
            new Position(1827, 3605, 0),
            new Position(1819, 3604, 0),
            new Position(1819, 3603, 0),
            new Position(1819, 3601, 0),
            new Position(1842, 3618, 0),
            new Position(1842, 3620, 0),
            new Position(1838, 3608, 0),
            new Position(1838, 3607, 0),
            new Position(1828, 3614, 0),
            new Position(1828, 3602, 0),
            new Position(1828, 3601, 0),
            new Position(1828, 3600, 0),
            new Position(1828, 3598, 0),
            new Position(1838, 3606, 0),
            new Position(1819, 3602, 0)
    ),
    HOSIDIUS_SOUTH(
            new Position(1673, 3469, 0),
            new Position(1674, 3469, 0),
            new Position(1675, 3469, 0),
            new Position(1676, 3469, 0),
            new Position(1684, 3472, 0),
            new Position(1685, 3472, 0),
            new Position(1688, 3469, 0),
            new Position(1690, 3467, 0),
            new Position(1668, 3475, 0),
            new Position(1668, 3474, 0),
            new Position(1668, 3473, 0),
            new Position(1672, 3469, 0),
            new Position(1676, 3489, 0),
            new Position(1676, 3490, 0),
            new Position(1678, 3492, 0)
    ),
    FARMING_GUILD_NORTH(
            new Position(1206, 3746, 0),
            new Position(1206, 3747, 0),
            new Position(1209, 3753, 0),
            new Position(1199, 3735, 0),
            new Position(1199, 3736, 0),
            new Position(1199, 3737, 0),
            new Position(1204, 3730, 0)
    ),
    FARMING_GUILD_MID(
            new Position(1221, 3714, 0),
            new Position(1221, 3713, 0),
            new Position(1220, 3717, 0),
            new Position(1220, 3710, 0)
    ),
    FARMING_GUILD_SOUTH(
            new Position(1209, 3689, 0),
            new Position(1209, 3688, 0),
            new Position(1210, 3685, 0),
            new Position(1211, 3680, 0),
            new Position(1206, 3695, 0)
    ),
    FARMING_GUILD_EAST(
            new Position(1271, 3707, 0),
            new Position(1272, 3707, 0),
            new Position(1273, 3707, 0),
            new Position(1267, 3703, 0),
            new Position(1264, 3697, 0)
    ),
    WATSON_HOUSE(
            new Position(1647, 3557, 0),
            new Position(1649, 3558, 0),
            new Position(1650, 3558, 0),
            new Position(1644, 3558, 0)
    ),
    WEST_HOSIDIUS(
            new Position(1715, 3610, 0),
            new Position(1715, 3611, 0),
            new Position(1715, 3612, 0),
            new Position(1715, 3613, 0)
    ),
    KOUREND_PARK(
            new Position(1720, 3683, 0),
            new Position(1722, 3684, 0),
            new Position(1722, 3685, 0),
            new Position(1721, 3686, 0),
            new Position(1720, 3686, 0)
    ),
    TREE_GNOME_STRONGHOLD(
            new Position(2382, 3414, 0),
            new Position(2382, 3415, 0),
            new Position(2389, 3423, 0),
            new Position(2395, 3417, 0),
            new Position(2383, 3412, 0)
    ),
    TIRANWN_BAIT_WEST(
            new Position(2215, 3248, 0),
            new Position(2217, 3245, 0),
            new Position(2210, 3237, 0),
            new Position(2212, 3239, 0),
            new Position(2210, 3243, 0),
            new Position(2225, 3243, 0),
            new Position(2222, 3241, 0)
    ),
    TIRANWN_BAIT_EAST(
            new Position(2264, 3258, 0),
            new Position(2265, 3258, 0),
            new Position(2266, 3253, 0),
            new Position(2267, 3253, 0),
            new Position(2273, 3247, 0),
            new Position(2274, 3248, 0),
            new Position(2276, 3243, 0),
            new Position(2275, 3240, 0),
            new Position(2282, 3244, 0)
    ),
    ZUL_ANDRA(
            new Position(2192, 3070, 0),
            new Position(2199, 3066, 0),
            new Position(2182, 3067, 0),
            new Position(2186, 3070, 0)
    ),
    PRIF_SOUTH(
            new Position(2164, 3280, 0),
            new Position(2165, 3285, 0),
            new Position(2165, 3286, 0),
            new Position(2165, 3268, 0),
            new Position(2165, 3266, 0)
    ),
    PRIF_NORTH(
            new Position(2166, 3347, 0),
            new Position(2165, 3374, 0),
            new Position(2163, 3365, 0),
            new Position(2165, 3373, 0)
    ),
    GWENITH(
            new Position(2225, 3429, 0),
            new Position(2226, 3430, 0),
            new Position(2235, 3433, 0),
            new Position(2234, 3441, 0),
            new Position(2228, 3428, 0),
            new Position(2228, 3429, 0),
            new Position(2225, 3428, 0),
            new Position(2236, 3445, 0),
            new Position(2227, 3430, 0)
    ),
    JATIZSO(
            new Position(2411, 3780, 0),
            new Position(2417, 3783, 0),
            new Position(2401, 3781, 0),
            new Position(2404, 3778, 0),
            new Position(2414, 3788, 0),
            new Position(2419, 3789, 0),
            new Position(2422, 3789, 0),
            new Position(2427, 3792, 0),
            new Position(2414, 3783, 0),
            new Position(2401, 3782, 0),
            new Position(2401, 3780, 0),
            new Position(2403, 3778, 0),
            new Position(2412, 3786, 0),
            new Position(2421, 3789, 0),
            new Position(2420, 3789, 0),
            new Position(2424, 3791, 0)
    ),
    SINCLAIR_MANSION(
            new Position(2716, 3530, 0),
            new Position(2714, 3532, 0),
            new Position(2714, 3533, 0),
            new Position(2726, 3524, 0),
            new Position(2727, 3524, 0),
            new Position(2728, 3524, 0)
    ),
    RUINS_OF_UNKAH(
            new Position(3142, 2802, 0),
            new Position(3142, 2805, 0),
            new Position(3142, 2806, 0),
            new Position(3143, 2809, 0),
            new Position(3140, 2797, 0),
            new Position(3140, 2796, 0),
            new Position(3140, 2795, 0)
    ),
    BANDIT_CAMP(
            new Position(3050, 3704, 0),
            new Position(3054, 3706, 0)
    ),
    LAVA_MAZE(
            new Position(3074, 3838, 0),
            new Position(3077, 3836, 0),
            new Position(3078, 3836, 0),
            new Position(3079, 3836, 0),
            new Position(3070, 3839, 0)
    ),
    TAVERLY_DUNGEON(
            new Position(2887, 9766, 0),
            new Position(2888, 9766, 0),
            new Position(2889, 9766, 0),
            new Position(2890, 9766, 0),
            new Position(2891, 9766, 0),
            new Position(2884, 9765, 0)
    ),
    LUMBRIDGE_SWAMP_CAVE_WEST(
            new Position(3154, 9544, 0),
            new Position(3156, 9542, 0),
            new Position(3157, 9542, 0),
            new Position(3153, 9545, 0),
            new Position(3152, 9545, 0)
    ),
    LUMBRIDGE_SWAMP_CAVE_EAST(
            new Position(3245, 9570, 0),
            new Position(3246, 9569, 0),
            new Position(3247, 9572, 0),
            new Position(3248, 9572, 0),
            new Position(3249, 9571, 0),
            new Position(3249, 9568, 0)
    ),
    DORGESH_KAAN_DUNGEON_WEST(
            new Position(2694, 5226, 0),
            new Position(2694, 5225, 0),
            new Position(2694, 5224, 0),
            new Position(2694, 5223, 0)
    ),
    DORGESH_KAAN_DUNGEON_EAST(
            new Position(2746, 5230, 0),
            new Position(2747, 5228, 0),
            new Position(2747, 5227, 0)
    ),
    CAMDOZAAL_BAIT(
            new Position(2921, 5808, 0),
            new Position(2921, 5807, 0),
            new Position(2927, 5813, 0),
            new Position(2927, 5814, 0)
    ),
    WATER_RAVINE_DUNGEON(
            new Position(3371, 9576, 0),
            new Position(3371, 9577, 0),
            new Position(3371, 9578, 0),
            new Position(3369, 9581, 0),
            new Position(3361, 9567, 0),
            new Position(3360, 9567, 0),
            new Position(3359, 9567, 0),
            new Position(3356, 9566, 0),
            new Position(3355, 9566, 0),
            new Position(3354, 9566, 0),
            new Position(3362, 9588, 0),
            new Position(3359, 9589, 0),
            new Position(3356, 9590, 0)
    ),
    MOUNT_QUIDAMORTEM(
            new Position(1253, 3542, 0),
            new Position(1252, 3542, 0),
            new Position(1261, 3542, 0),
            new Position(1266, 3541, 0),
            new Position(1267, 3541, 0),
            new Position(1272, 3546, 0),
            new Position(1272, 3547, 0)
    ),
    HEMENSTER(
            new Position(2632, 3428, 0),
            new Position(2632, 3427, 0),
            new Position(2632, 3426, 0),
            new Position(2632, 3425, 0),
            new Position(2629, 3420, 0),
            new Position(2627, 3415, 0),
            new Position(2630, 3435, 0),
            new Position(2637, 3443, 0),
            new Position(2637, 3444, 0)
    )
    ;

    private final ArrayDeque<Position> freePositions;
    private static final List<NPC> spots = new ArrayList<>();

    FishingArea(Position... positions) {
        List<Position> list = Arrays.asList(positions);
        Collections.shuffle(list);
        freePositions = new ArrayDeque<>(list);
    }

    private void move(NPC npc) {
        if (freePositions.isEmpty())
            return;
        Position next = freePositions.pop();
        Position prev = npc.getPosition();
        freePositions.addLast(prev.copy());
        npc.getMovement().teleport(next);
    }

    private void add(int npcId, int count) {
        for (int i = 0; i < count; i++) {
            NPC npc = new NPC(npcId);
            npc.fishingArea = this;
            npc.spawn(freePositions.pop());
            spots.add(npc);
        }
    }

    /**
     * World event that moves fishing spots every 350 to 450 ticks.
     * Spots have a 50% chance of moving, could tune this in the future.
     */
    private static void fishingSpotTimer() {
        World.startEvent(e -> {
            while (true) {
                e.delay(Random.get(350, 400));   // 3.5 - 4.0 minute delay
                for (NPC npc : spots) {
                    if (npc.fishingArea != null) {
                        npc.fishingArea.move(npc);
                    }
                }
            }
        });
    }

    static {
        AL_KHARID.add(FishingSpot.NET_BAIT, 2);
        KARAMBWAN_RIVER.add(FishingSpot.KARAMBWAN_SPOT, 2);
        LUMBRIDGE_TUTORIAL.add(FishingSpot.NET_BAIT, 3);
        LUMBRIDGE_RIVER.add(FishingSpot.LURE_BAIT, 2);
        DRAYNOR.add(FishingSpot.NET_BAIT, 2);
        BARBARIAN_VILLAGE.add(FishingSpot.LURE_BAIT, 2);
        CATHERBY.add(FishingSpot.NET_BAIT, 2);
        CATHERBY.add(FishingSpot.BIG_NET_HARPOON, 3);
        CATHERBY.add(FishingSpot.CAGE_HARPOON, 2);
        FISHING_GUILD.add(FishingSpot.CAGE_HARPOON, 11);
        FISHING_GUILD.add(FishingSpot.BIG_NET_HARPOON, 9);
        RESOURCE_AREA.add(FishingSpot.CAGE, 2);
        ZEAH_ANGLER.add(FishingSpot.BAIT, 3);
        BARBARIAN.add(FishingSpot.USE_ROD, 4);
        INFERNAL_EEL_EAST.add(FishingSpot.INFERNO_EEL, 2);
        INFERNAL_EEL_SOUTH_AND_WEST.add(FishingSpot.INFERNO_EEL, 2);
        PISCATORIS.add(FishingSpot.SMALL_NET_HARPOON, 5);
        KARAMJA.add(FishingSpot.CAGE_HARPOON, 3);
        KARAMJA.add(FishingSpot.NET_BAIT, 3);
        PVP_WORLD_37S.add(FishingSpot.CAGE, 3);
        MORT_MYRE_SWAMP_WEST.add(FishingSpot.SLIMY_EEL, 2);
        MORT_MYRE_SWAMP_NORTH_WEST.add(FishingSpot.SLIMY_EEL, 1);
        MORT_MYRE_SWAMP_NORTH_WEST_2.add(FishingSpot.SLIMY_EEL, 1);
        MORT_MYRE_SPAWP_NORTH.add(FishingSpot.SLIMY_EEL, 2);
        MORT_MYRE_SPAWP_NORTH_2.add(FishingSpot.SLIMY_EEL, 1);
        BURGH_DE_ROTT.add(FishingSpot.BIG_NET_HARPOON, 4);
        BURGH_DE_ROTT_2.add(FishingSpot.BIG_NET_HARPOON, 1);
        BURGH_DE_ROTT_3.add(FishingSpot.BIG_NET_HARPOON, 4);
        BURGH_DE_ROTT_4.add(FishingSpot.BIG_NET_HARPOON, 2);
        MUSA_POINT.add(FishingSpot.NET_BAIT, 2);
        MUSA_POINT_2.add(FishingSpot.NET_BAIT, 2);
        SHILO_VILLAGE.add(FishingSpot.LURE_BAIT, 4);
        KARAMBWANJI.add(FishingSpot.KARAMBWANJI, 2);
        ENTRANA_DOCK.add(FishingSpot.NET_BAIT, 4);
        ENTRANA_RIVER.add(FishingSpot.LURE_BAIT, 3);
        SHAYZIEN.add(FishingSpot.LURE_BAIT, 3);
        KOUREND_WOODLAND_1.add(FishingSpot.CAGE_HARPOON, 3);
        KOUREND_WOODLAND_2.add(FishingSpot.NET_BAIT, 3);
        ISLE_OF_SOULS_SOUTH.add(FishingSpot.NET_BAIT, 3);
        ISLE_OF_SOULS_WEST.add(FishingSpot.CAGE_HARPOON, 2);
        ISLE_OF_SOULS_NORTH.add(FishingSpot.CAGE_HARPOON, 3);
        ISLE_OF_SOULS_EAST.add(FishingSpot.BIG_NET_HARPOON, 3);
        APE_ATOLL_EAST.add(FishingSpot.BIG_NET_HARPOON, 3);
        APE_ATOLL_WEST.add(FishingSpot.BIG_NET_HARPOON, 3);
        CORSAIR_COVE.add(FishingSpot.NET_BAIT, 3);
        FELDIP_HILLS.add(FishingSpot.CAGE_HARPOON, 3);
        GNOME_VILLAGE.add(FishingSpot.LURE_BAIT, 5);
        NORTH_ARDY.add(FishingSpot.LURE_BAIT, 2);
        BAXTORIAN_FALLS_SOUTH.add(FishingSpot.LURE_BAIT, 4);
        RELEKKA_NET.add(FishingSpot.NET_BAIT, 4);
        RELEKKA_CAGE.add(FishingSpot.CAGE_HARPOON, 2);
        RELEKKA_HARPOON.add(FishingSpot.BIG_NET_HARPOON, 2);
        BARBARIAN_OUTPOST.add(FishingSpot.NET_BAIT, 4);
        PISCARILIUS.add(FishingSpot.NET_BAIT, 3);
        PISCARILIUS.add(FishingSpot.CAGE_HARPOON, 2);
        HOSIDIUS.add(FishingSpot.NET_BAIT, 2);
        HOSIDIUS.add(FishingSpot.CAGE_HARPOON, 3);
        HOSIDIUS.add(FishingSpot.BIG_NET_HARPOON, 3);
        HOSIDIUS_SOUTH.add(FishingSpot.NET_BAIT, 3);
        HOSIDIUS_SOUTH.add(FishingSpot.CAGE_HARPOON, 2);
        HOSIDIUS_SOUTH.add(FishingSpot.BIG_NET_HARPOON, 2);
        FARMING_GUILD_NORTH.add(FishingSpot.BIG_NET_HARPOON, 3);
        FARMING_GUILD_MID.add(FishingSpot.BIG_NET_HARPOON, 2);
        FARMING_GUILD_SOUTH.add(FishingSpot.BIG_NET_HARPOON, 2);
        FARMING_GUILD_EAST.add(FishingSpot.LURE_BAIT, 3);
        WATSON_HOUSE.add(FishingSpot.LURE_BAIT, 2);
        WEST_HOSIDIUS.add(FishingSpot.LURE_BAIT, 2);
        KOUREND_PARK.add(FishingSpot.LURE_BAIT, 2);
        TREE_GNOME_STRONGHOLD.add(FishingSpot.LURE_BAIT, 3);
        TIRANWN_BAIT_WEST.add(FishingSpot.LURE_BAIT, 3);
        TIRANWN_BAIT_EAST.add(FishingSpot.LURE_BAIT, 4);
        ZUL_ANDRA.add(FishingSpot.SACRED_EEL, 2);
        PRIF_SOUTH.add(FishingSpot.BIG_NET_HARPOON, 3);
        PRIF_NORTH.add(FishingSpot.CAGE_HARPOON, 1);
        PRIF_NORTH.add(FishingSpot.BIG_NET_HARPOON, 1);
        GWENITH.add(FishingSpot.CAGE_HARPOON, 3);
        GWENITH.add(FishingSpot.BIG_NET_HARPOON, 3);
        JATIZSO.add(FishingSpot.CAGE_HARPOON, 4);
        JATIZSO.add(FishingSpot.BIG_NET_HARPOON, 4);
        SINCLAIR_MANSION.add(FishingSpot.LURE_BAIT, 3);
        RUINS_OF_UNKAH.add(FishingSpot.CAGE_HARPOON, 3);
        BANDIT_CAMP.add(FishingSpot.NET_BAIT, 1);
        LAVA_MAZE.add(FishingSpot.LAVA_EEL, 2);
        TAVERLY_DUNGEON.add(FishingSpot.LAVA_EEL, 2);
        LUMBRIDGE_SWAMP_CAVE_WEST.add(FishingSpot.SWAMP_NET_BAIT, 2);
        LUMBRIDGE_SWAMP_CAVE_EAST.add(FishingSpot.SWAMP_NET_BAIT, 2);
        DORGESH_KAAN_DUNGEON_WEST.add(FishingSpot.SWAMP_NET_BAIT, 2);
        DORGESH_KAAN_DUNGEON_EAST.add(FishingSpot.SWAMP_NET_BAIT, 2);
        CAMDOZAAL_BAIT.add(FishingSpot.SWAMP_NET_BAIT, 2);
        WATER_RAVINE_DUNGEON.add(FishingSpot.LURE_BAIT, 6);
        MOUNT_QUIDAMORTEM.add(FishingSpot.USE_ROD, 4);
        HEMENSTER.add(FishingSpot.HEMENSTER, 5);
        FishingArea.fishingSpotTimer();
    }
}
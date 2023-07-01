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
            new Position(3240, 3141, 0),
            new Position(3239, 3141, 0),
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
    INFERNAL_EEL(
            new Position(2540, 5088, 0),
            new Position(2539, 5088, 0),
            new Position(2541, 5088, 0),
            new Position(2536, 5086, 0),
            new Position(2478, 5078, 0),
            new Position(2477, 5078, 0),
            new Position(2479, 5078, 0),
            new Position(2448, 5014, 0)
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
    DONATOR_ZONE(
            new Position(3828, 2869, 0),
            new Position(3830, 2869, 0),
            new Position(3832, 2869, 0),
            new Position(3833, 2869, 0),
            new Position(3835, 2869, 0),
            new Position(3836, 2870, 0),
            new Position(3835, 2872, 0),
            new Position(3833, 2872, 0),
            new Position(3831, 2872, 0),
            new Position(3829, 2872, 0),
            new Position(3828, 2872, 0),
            new Position(3826, 2872, 0)
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
    HOME(
            new Position(2039, 3607, 0),
            new Position(2039, 3606, 0),
            new Position(2039, 3605, 0),
            new Position(2039, 3604, 0),
            new Position(2039, 3603, 0),
            new Position(2039, 3602, 0),
            new Position(2044, 3597, 0),
            new Position(2044, 3596, 0),
            new Position(2044, 3595, 0),
            new Position(2044, 3594, 0),
            new Position(2044, 3593, 0),
            new Position(2044, 3592, 0),
            new Position(2044, 3591, 0),
            new Position(2044, 3590, 0),
            new Position(2044, 3589, 0),
            new Position(2044, 3588, 0)
    );

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
                e.delay(Random.get(350, 450));   // 3.5 - 4.5 minute delay
                for (NPC npc : spots) {
                    if (npc.fishingArea != null && Random.rollDie(2, 1)) {
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
        INFERNAL_EEL.add(FishingSpot.INFERNO_EEL, 4);
        PISCATORIS.add(FishingSpot.SMALL_NET_HARPOON, 5);
        KARAMJA.add(FishingSpot.CAGE_HARPOON, 3);
        KARAMJA.add(FishingSpot.NET_BAIT, 3);
        DONATOR_ZONE.add(FishingSpot.NET_BAIT, 1);
        DONATOR_ZONE.add(FishingSpot.LURE_BAIT, 1);
        DONATOR_ZONE.add(FishingSpot.CAGE_HARPOON, 2);
        DONATOR_ZONE.add(FishingSpot.BIG_NET_HARPOON, 2);
        PVP_WORLD_37S.add(FishingSpot.CAGE, 3);
        HOME.add(FishingSpot.BIG_NET_HARPOON, 2);
        HOME.add(FishingSpot.KARAMBWAN_SPOT, 1);
        HOME.add(FishingSpot.CAGE_HARPOON, 1);
        HOME.add(FishingSpot.SMALL_NET_HARPOON, 2);
        HOME.add(FishingSpot.LURE_BAIT, 1);
        HOME.add(FishingSpot.NET_BAIT, 1);
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
        FishingArea.fishingSpotTimer();
    }
}
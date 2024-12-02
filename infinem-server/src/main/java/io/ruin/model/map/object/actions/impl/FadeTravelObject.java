package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
public enum FadeTravelObject {
    CAL_TORUM_ENTRANCE(51375, new Position(1435, 3129), new Position(1440, 9509, 1)),
    CAL_TORUM_EXIT(51375, new Position(1439, 9507, 1), new Position(1435, 3128)),

    CIVITAS_ALTAR_UP_1(50860, new Position(1691, 9492), new Position(1692, 3087, 0)),
    CIVITAS_ALTAR_UP_2(50860, new Position(1691, 9480), new Position(1692, 3086, 0)),
    CIVITAS_ALTAR_DOWN_1(50859, new Position(1692, 3088), new Position(1694, 9492, 0)),
    CIVITAS_ALTAR_DOWN_2(50859, new Position(1692, 3084), new Position(1694, 9481, 0)),

    ROOST_UP(51643, new Position(1559, 3045), new Position(1556, 3046, 2), 828),
    ROOST_DOWN(51644, new Position(1556, 3047, 2), new Position(1559, 3046), 827),
    THE_BURROW_EXIT(51642, new Position(1557, 9448), new Position(1558, 3047, 0)),

    KUALTI_BARRACKS_DOWN(52641, new Position(1640, 3165), new Position(1640, 9567, 0)),
    KUALTI_BARRACKS_UP(52642, new Position(1640, 9564), new Position(1640, 3164, 0)),

    HALLOWED_SEPULCRE_ENTRANCE(38574, new Position(3654, 3385), new Position(2400, 5969, 0)),
    HALLOWED_SEPULCRE_EXIT(38601, new Position(2399, 5966), new Position(3654, 3384, 0)),

    BLAST_FURNACE_IN(9084, new Position(2930, 10196), new Position(1939, 4958)),
    BLAST_FURNACE_OUT(9138, new Position(1939, 4956), new Position(2931, 10196)),

    HOLLOWS_OUT(5054, new Position(3477, 9846), new Position(3495, 3465, 0), 828),
    HOLLOWS_IN(5055, new Position(3495, 3464), new Position(3477, 9845, 0), 827),
    HOLLOWS_MYRE_OUT(5056, new Position(3500, 9812), new Position(3509, 3449, 0)),
    HOLLOWS_MYRE_OUT_2(5057, new Position(3501, 9812), new Position(3510, 3449, 0)),
    HOLLOWS_MYRE_IN(5060, new Position(3510, 3447), new Position(3501, 9811, 0)),
    HOLLOWS_MYRE_IN_2(5061, new Position(3509, 3447), new Position(3500, 9811, 0)),

    GLOUGH_LAB_ENTRANCE(28813, new Position(2510, 9213, 1), new Position(2911, 9094)),
    GLOUGH_LAB_EXIT(28683, new Position(2911, 9093), new Position(2510, 9212, 1)),
    IORWERTH_DUNGEON_ENTRANCE(36690, new Position(3224, 6043), new Position(3225, 12445)),
    IORWERTH_DUNGEON_EXIT(36691, new Position(3224, 12446), new Position(3225, 6046)),
    ICETROLL_CAVE_WEST_ENTRANCE(21585, new Position(2316, 3894), new Position(2420, 10279, 1), 2796),
    ICETROLL_CAVE_ENTRANCE(21584, new Position(2401, 3889), new Position(2394, 10300, 1), 2796),
    ICETROLL_CAVE_EXIT(21598, new Position(2394, 10301, 1), new Position(2401, 3888, 0), 2796),
    PORTAL_OF_HEROES(31621, new Position(2455, 2853, 2), new Position(2904, 3511, 0)),
    PORTAL_OF_CHAMPIONS(31618, new Position(2456, 2856, 2), new Position(3191, 3364, 0)),
    PORTAL_OF_LEGENDS(31622, new Position(2459, 2853, 2), new Position(2729, 3348, 0)),
    WATER_RAVINE_DUNGEON_EXIT(10417, new Position(3347, 9533), new Position(3372, 3130, 0)),
    CAMDOZAAL_ENTRANCE(41357, new Position(2999, 3493), new Position(2952, 5762, 0)),
    CAMDOZAAL_EXIT(41446, new Position(2951, 5761), new Position(2998, 3494, 0)),
    DORGESH_TO_KALPHITE(22656, new Position(2710, 5206), new Position(3514, 9520, 2), 2796),
    JORMUNGAND_PRISON_ENTRANCE(37433, new Position(2464, 4011), new Position(2461, 10417, 0)),
    JORMUNGAND_PRISON_EXIT(37411, new Position(2460, 10418), new Position(2465, 4010, 0)),
    WIZARDS_GUILD_PORTAL_TO_WIZARDS_TOWER(2156, new Position(2596, 3087, 2), new Position(3109, 3165, 0)),
    WIZARDS_GUILD_PORTAL_TO_DARK_WIZARDS_TOWER(2157, new Position(2590, 3081, 2), new Position(2907, 3337, 0)),
    WIZARDS_GUILD_PORTAL_TO_SORCERORS_TOWER(2158, new Position(2585, 3087, 2), new Position(2702, 3403, 0)),
    HAUNTED_MINE_ENTRANCE(4913, new Position(3440, 3232), new Position(3436, 9637, 0), 2796),
    HAUNTED_MINE_EXIT(4920, new Position(3437, 9637), new Position(3441, 3232), 2796),
    TARNS_LAIR_ENTRANCE(20822, new Position(3424, 9661), new Position(3166, 4547, 0)),
    TARNS_LAIR_EXIT(20482, new Position(3166, 4546), new Position(3424, 9660, 0)),
    TRAIN_STATION_DORGESH_KAAN_ENTRANCE(23052, new Position(2695, 5277, 1), new Position(2488, 5536, 0)),
    TRAIN_STATION_DORGESH_KAAN_EXIT(23285, new Position(2489, 5536), new Position(2696, 5277, 1)),
    TRAIN_STATION_KELDAGRIM_ENTRANCE(23287, new Position(2942, 10179), new Position(2435, 5535, 0)),
    TRAIN_STATION_KELDAGRIM_EXIT(23286, new Position(2434, 5535), new Position(2941, 10179, 0)),
    MORTON_BOAT(6969, new Position(3523, 3284), new Position(3498, 3380)),
    MORT_MYRE_BOAT(6970, new Position(3498, 3377), new Position(3522, 3285)),
    PETERDOMUS_BASEMENT_EAST_ENTRANCE(3432, new Position(3422, 3485), new Position(3440, 9887), 827),
    PETERDOMUS_BASEMENT_EAST_EXIT(3443, new Position(3440, 9886), new Position(3423, 3485)),
    WEISS_MINE_ENTRANCE(33234, new Position(2867, 3939), new Position(2845, 10351)),
    WEISS_MINE_EXIT(33261, new Position(2844, 10352), new Position(2869, 3941)),
    WEISS_BOAT_SHORTCUT_CAVE(33329, new Position(2858, 3966), new Position(2854, 3941)),
    WEISS_BOAT_SHORTCUT_HOLE(33227, new Position(2853, 3943), new Position(2859, 3968)),
    WEISS_BOAT_TO(21176, new Position(2708, 3732), new Position(2850, 3968)),
    WEISS_BOAT_FROM(21177, new Position(2843, 3967), new Position(2707, 3735)),
    ISLE_OF_SOULS_DUNGEON_ENTRANCE(40736, new Position(2308, 2918), new Position(2167, 9308), 2796),
    ISLE_OF_SOULS_DUNGEON_EXIT(40737, new Position(2168, 9307), new Position(2310, 2919), 2796),
    GIANTS_DEN_ENTRANCE(42248, new Position(1421, 3587), new Position(1432, 9913), 2796),
    GIANTS_DEN_EXIT(42247, new Position(1431, 9914), new Position(1420, 3588), 2796),
    BRINE_RAT_CAVERN_EXIT_1(23158, new Position(2689, 10124), new Position(2729, 3734)),
    BRINE_RAT_CAVERN_EXIT_2(23157, new Position(2689, 10125), new Position(2729, 3734));

    FadeTravelObject(int objectId, Position objectPos, Position destination) {
        ObjectAction.register(objectId, objectPos, 1, ((player, obj) -> Traveling.fadeTravel(player, destination)));
    }

    FadeTravelObject(int objectId, Position objectPos, Position destination, int animation) {
        ObjectAction.register(objectId, objectPos, 1, ((player, obj) -> {
            player.animate(animation);
            Traveling.fadeTravel(player, destination);
        }));
    }
}

package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
public enum FadeTravelObject {
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

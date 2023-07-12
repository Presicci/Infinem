package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
public enum FadeTravelObject {
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

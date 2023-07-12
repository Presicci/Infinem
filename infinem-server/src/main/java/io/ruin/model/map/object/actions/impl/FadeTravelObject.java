package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
public enum FadeTravelObject {
    BRINE_RAT_CAVERN_EXIT_1(23158, new Position(2689, 10124), new Position(2729, 3734)),
    BRINE_RAT_CAVERN_EXIT_2(23157, new Position(2689, 10125), new Position(2729, 3734));

    FadeTravelObject(int objectId, Position objectPos, Position destination) {
        ObjectAction.register(objectId, objectPos, 1, ((player, obj) -> Traveling.fadeTravel(player, destination)));
    }
}

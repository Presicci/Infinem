package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class ExperimentsCave {

    static{
        ObjectAction.register(5167, 3578, 3527, 0, "push", ((player, obj) -> Traveling.fadeTravel(player, new Position(3577, 9927, 0))));
        ObjectAction.register(17387, 3578, 9927, 0, "climb-up", ((player, obj) -> Traveling.fadeTravel(player, new Position(3578, 3526, 0))));
    }
}

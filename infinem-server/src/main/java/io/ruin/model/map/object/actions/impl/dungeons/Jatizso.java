package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class Jatizso {

    static{
        // Mine entrance
        ObjectAction.register(21578, 2406, 10188,0, "climb-up", ((player, obj) -> Traveling.fadeTravel(player, new Position(2397, 3814,0))));
        ObjectAction.register(21455, 2397, 3812,0, "climb-down", ((player, obj) -> Traveling.fadeTravel(player, new Position(2406, 10190,0))));
    }
}

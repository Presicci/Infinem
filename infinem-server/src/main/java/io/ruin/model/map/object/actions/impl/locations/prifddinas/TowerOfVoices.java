package io.ruin.model.map.object.actions.impl.locations.prifddinas;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class TowerOfVoices {

    static {
        ObjectAction.register(36390, 1, (player, obj) -> Traveling.fadeTravel(player, new Position(3263, 6078, 0)));
        ObjectAction.register(36387, 1, (player, obj) -> Traveling.fadeTravel(player, new Position(3268, 6082, 2)));
    }
}

package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/16/2023
 */
public class Sophanem {

    static {
        // Front gate
        ObjectAction.register(44038, 3284, 2809, 0, "open",
                ((player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 44036, 44038, new Position(3283, 2809), new Position(3284, 2809))));
        ObjectAction.register(44036, 3283, 2809, 0, "open",
                ((player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 44036, 44038, new Position(3283, 2809), new Position(3284, 2809))));
    }
}

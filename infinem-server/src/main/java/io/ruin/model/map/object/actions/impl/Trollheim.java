package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/22/2023
 */
public class Trollheim {

    static {
        ObjectAction.register(3785, 2916, 3629, 0, "open", (player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 3785, 3786, new Position(2916, 3629), new Position(2917, 3629), false));
        ObjectAction.register(3786, 2917, 3629, 0, "open", (player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 3785, 3786, new Position(2916, 3629), new Position(2917, 3629), false));
        ObjectAction.register(3783, 2897, 3619, 0, "open", (player, obj) -> DoubleDoor.passThroughLateral(player, obj, 3783, 3782, new Position(2897, 3619), new Position(2897, 3618)));
        ObjectAction.register(3782, 2897, 3618, 0, "open", (player, obj) -> DoubleDoor.passThroughLateral(player, obj, 3783, 3782, new Position(2897, 3619), new Position(2897, 3618)));
    }
}

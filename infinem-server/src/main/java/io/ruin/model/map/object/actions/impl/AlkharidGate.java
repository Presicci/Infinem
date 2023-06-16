package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class AlkharidGate {
    static {
        ObjectAction.register(2883, 3268, 3228, 0, "open", ((player, obj) -> {
            DoubleDoor.passThroughLateral(player, obj, 2883, 2882, new Position(3268, 3228), new Position(3268, 3227));
            player.getTaskManager().doLookupByUUID(637, 1);
        }));
        ObjectAction.register(2882, 3268, 3227, 0, "open", ((player, obj) -> {
            DoubleDoor.passThroughLateral(player, obj, 2883, 2882, new Position(3268, 3228), new Position(3268, 3227));
            player.getTaskManager().doLookupByUUID(637, 1);
        }));
    }
}

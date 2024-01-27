package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class AlkharidGate {

    // Makes the gate object only have open as option 1
    private static final Config PRICE_ALI_RESCUE = Config.varp(273, false).defaultValue(100);

    static {
        ObjectAction.register(44599, 3268, 3228, 0, 1, ((player, obj) -> {
            DoubleDoor.passThroughLateral(player, obj, 44599, 44598, new Position(3268, 3228), new Position(3268, 3227));
            player.getTaskManager().doLookupByUUID(637, 1);
        }));
        ObjectAction.register(44598, 3268, 3227, 0, 1, ((player, obj) -> {
            DoubleDoor.passThroughLateral(player, obj, 44599, 44598, new Position(3268, 3228), new Position(3268, 3227));
            player.getTaskManager().doLookupByUUID(637, 1);
        }));
    }
}

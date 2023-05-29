package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public class RellekkaHunterArea {

    static {
        // East stairs
        ObjectAction.register(19690, 2726, 3802, 0, "ascend", ((player, obj) -> player.getMovement().teleport(2726, 3805, 1)));
        ObjectAction.register(19691, 2726, 3803, 1, "descend", ((player, obj) -> player.getMovement().teleport(2726, 3801, 0)));
        // West stairs
        ObjectAction.register(19690, 2715, 3799, 0, "ascend", ((player, obj) -> player.getMovement().teleport(2715, 3802, 1)));
        ObjectAction.register(19691, 2715, 3800, 1, "descend", ((player, obj) -> player.getMovement().teleport(2716, 3798, 0)));
    }
}

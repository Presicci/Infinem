package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/27/2023
 */
public class Curtain {

    static {
        // Pollniv
        ObjectAction.register(1534, "close", (player, obj) -> obj.setId(1533));
        ObjectAction.register(1533, "open", (player, obj) -> obj.setId(1534));
        // Rellekka
        ObjectAction.register(4251, 1, (player, obj) -> obj.setId(4250));
        ObjectAction.register(4250, 1, (player, obj) -> obj.setId(4251));
    }
}

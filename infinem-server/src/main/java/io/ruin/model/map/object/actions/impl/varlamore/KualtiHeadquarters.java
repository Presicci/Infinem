package io.ruin.model.map.object.actions.impl.varlamore;

import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/1/2024
 */
public class KualtiHeadquarters {

    static {
        ObjectAction.register(52821, 1646, 9564, 0, "open", (player, obj) -> player.sendMessage("It's locked."));
    }
}

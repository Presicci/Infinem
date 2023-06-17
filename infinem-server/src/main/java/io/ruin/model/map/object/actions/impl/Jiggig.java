package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.shortcut.CrumblingWall;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/16/2023
 */
public class Jiggig {

    static {
        // Entrance
        ObjectAction.register(6879, 2456, 3049, 0, 1, ((player, obj) -> CrumblingWall.shortcut(player, obj, 0)));
        ObjectAction.register(6878, 2456, 3048, 0, 1, ((player, obj) -> CrumblingWall.shortcut(player, obj, 0)));
    }
}

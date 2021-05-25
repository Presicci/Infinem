package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Stairs;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/25/2021
 */
public class Lumbridge {

    static {

        /**
         * Staircases
         */
        Stairs.registerStair(16671, new Position(3204, 3207, 0));
        Stairs.registerStair(16672, new Position(3204, 3207, 1));
        Stairs.registerStair(16673, new Position(3204, 3207, 2));

        Stairs.registerStair(16671, new Position(3204, 3229, 0));
        Stairs.registerStair(16672, new Position(3204, 3229, 1));
        Stairs.registerStair(16673, new Position(3204, 3229, 2));

    }
}

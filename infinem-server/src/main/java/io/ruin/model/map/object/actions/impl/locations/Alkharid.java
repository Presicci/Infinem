package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.impl.Stairs;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class Alkharid {

    static {
        Stairs.registerSpiralStair(16671, new Position(3312, 3185, 0));
        Stairs.registerSpiralStair(16673, new Position(3313, 3185, 1));
    }
}

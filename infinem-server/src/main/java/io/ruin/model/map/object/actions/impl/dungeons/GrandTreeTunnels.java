package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public class GrandTreeTunnels {

    static {
        // Main entrance
        ObjectAction.register(2446, 2463, 3497, 0, "open", ((player, obj) -> Ladder.climb(player, new Position(2464, 9897), false, true, false)));
        // Main exit
        ObjectAction.register(17385, 2463, 9897, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2464, 3497), true, true, false)));
        // Alt entrance
        ObjectAction.register(2444, 2487, 3464, 2, 1, ((player, obj) -> Ladder.climb(player, new Position(2491, 9864), false, true, false)));
        // Alt exit
        ObjectAction.register(17028, 2492, 9864, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2487, 3465, 2), true, true, false)));
    }
}

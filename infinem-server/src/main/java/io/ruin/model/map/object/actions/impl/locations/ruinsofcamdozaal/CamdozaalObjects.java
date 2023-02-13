package io.ruin.model.map.object.actions.impl.locations.ruinsofcamdozaal;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/2/2023
 */
public class CamdozaalObjects {

    // vb 12063 40
    static {
        // Entrance and exit
        ObjectAction.register(41357, "enter", (player, obj) -> Traveling.fadeTravel(player, 2952, 5762, 0));
        ObjectAction.register(41446, "exit", (player, obj) -> Traveling.fadeTravel(player, 2998, 3494, 0));
    }
}

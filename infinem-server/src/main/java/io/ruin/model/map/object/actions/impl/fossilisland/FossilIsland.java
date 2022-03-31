package io.ruin.model.map.object.actions.impl.fossilisland;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/31/2022
 */
public class FossilIsland {

    static {
        // To bank check island
        ObjectAction.register(30915, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3768, 3898))));
        // From bank check island
        ObjectAction.register(30919, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3734, 3893))));
        // Dive to underwater
        ObjectAction.register(30919, "dive", ((player, obj) -> Traveling.fadeTravel(player, new Position(3731, 10281, 1))));
        // Climb to island
        ObjectAction.register(30948, "climb", ((player, obj) -> Traveling.fadeTravel(player, new Position(3768, 3898))));
    }
}

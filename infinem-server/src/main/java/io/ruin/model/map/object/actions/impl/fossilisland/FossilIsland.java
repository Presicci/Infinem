package io.ruin.model.map.object.actions.impl.fossilisland;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/31/2022
 */
public class FossilIsland {

    static {
        // House on the hill stairs
        ObjectAction.register(30681, 3754, 3868, 0, "climb", (player, obj) -> player.getMovement().teleport(3757, 3869, 1));
        ObjectAction.register(30682, 3754, 3868, 1, "descend", (player, obj) -> player.getMovement().teleport(3753, 3869, 0));
        // To bank check island
        ObjectAction.register(30915, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3768, 3898))));
        // From bank check island
        ObjectAction.register(30919, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3734, 3893))));
        // Dive to underwater
        ObjectAction.register(30919, "dive", ((player, obj) -> Traveling.fadeTravel(player, new Position(3731, 10281, 1))));
        // Climb to island
        ObjectAction.register(30948, "climb", ((player, obj) -> Traveling.fadeTravel(player, new Position(3768, 3898))));
        // Rowboat to mainland
        ObjectAction.register(30914, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3362, 3445))));
    }
}

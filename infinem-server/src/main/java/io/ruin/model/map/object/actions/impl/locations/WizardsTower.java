package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.Trapdoor;

public class WizardsTower {

    static {
        // Trapdoor down
        ObjectAction.register(2147, 3104, 3162, 0, "climb-down", (player, obj) -> Trapdoor.climbDown(player, new Position(3104, 9576, 0)));
        // Ladder back up
        ObjectAction.register(2148, 3103, 9576, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3105, 3162, 0, true, true, false));
    }
}

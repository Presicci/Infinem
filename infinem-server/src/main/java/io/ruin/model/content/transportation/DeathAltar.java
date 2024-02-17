package io.ruin.model.content.transportation;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.TileTrigger;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/17/2024
 */
public class DeathAltar {
    static {
        TileTrigger.registerPlayerTrigger(new Position(1915, 4638, 0), 3, p -> Traveling.fadeTravel(p, 1863, 4639, 0));
        TileTrigger.registerPlayerTrigger(new Position(1864, 4638, 0), 3, p -> Traveling.fadeTravel(p, 1920, 4639, 0));
    }
}

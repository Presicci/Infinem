package io.ruin.model.map;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.Spade;

import java.util.function.Consumer;

public enum DigArea {
    BRINE_RAT_CAVERN_ENTRANCE(new Bounds(2746, 3730, 2750, 3733, 0), (player) -> Traveling.fadeTravel(player, new Position(2706, 10134)));

    DigArea(Bounds bounds, Consumer<Player> consumer) {
        Spade.registerDig(bounds, consumer);
    }
}

package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class Neitiznot {

    static {
        ObjectAction.register(21308, 2343, 3821, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2343, 3829, 0))));
        ObjectAction.register(21309, 2343, 3828, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2343, 3820, 0))));
        ObjectAction.register(21306, 2317, 3824, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2317, 3832, 0))));
        ObjectAction.register(21307, 2317, 3831, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2317, 3823, 0))));
        ObjectAction.register(21310, 2314, 3840, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2314, 3848, 0))));
        ObjectAction.register(21311, 2314, 3847, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2314, 3839, 0))));
        ObjectAction.register(21313, 2355, 3847, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2355, 3839, 0))));
        ObjectAction.register(21312, 2355, 3840, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2355, 3848, 0))));
        ObjectAction.register(21314, 2378, 3840, 0, "cross-bridge", ((player, obj) -> walkBridge(player, new Position(2378, 3848, 0))));
        ObjectAction.register(21315, 2378, 3847, 0, "walk-across", ((player, obj) -> walkBridge(player, new Position(2378, 3839, 0))));
    }

    private static void walkBridge(Player player, Position destination) {
        player.lock();
        player.startEvent(e -> {
            int distance = destination.distance(player.getPosition());
            player.stepAbs(destination.getX(), destination.getY(), StepType.FORCE_WALK);
            e.delay(distance + 1);
            player.unlock();
        });
    }
}

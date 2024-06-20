package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/20/2024
 */
public class ArandorGate {

    private static final GameObject EAST_CLOSED_GATE = new GameObject(3944, new Position(2386, 3334, 0), 10, 2);
    private static final GameObject WEST_CLOSED_GATE = new GameObject(3945, new Position(2384, 3334, 0), 10, 0);
    private static final GameObject EAST_OPEN_GATE = new GameObject(3946, new Position(2387, 3333, 0), 10, 2);
    private static final GameObject WEST_OPEN_GATE = new GameObject(3947, new Position(2384, 3333, 0), 10, 2);

    static {
        // East gate
        ObjectAction.register(3944, "enter", (player, obj) -> {
            boolean north = player.getAbsY() < obj.y;
            player.startEvent(e -> {
                player.lock();
                if (player.getAbsX() != 2386) {
                    player.stepAbs(2386, player.getAbsY(), StepType.FORCE_WALK);
                    e.delay(1);
                    player.face(obj);
                    e.delay(1);
                }
                new GameObject(-1, new Position(2384, 3334, 0), 10, 0).spawn();
                new GameObject(-1, new Position(2386, 3334, 0), 10, 0).spawn();
                EAST_OPEN_GATE.spawn();
                WEST_OPEN_GATE.spawn();
                player.stepAbs(player.getAbsX(), player.getAbsY() + ((north ? 1 : -1) * 2), StepType.FORCE_WALK);
                e.delay(3);
                EAST_OPEN_GATE.remove();
                WEST_OPEN_GATE.remove();
                EAST_CLOSED_GATE.spawn();
                WEST_CLOSED_GATE.spawn();
                player.unlock();
            });
        });
        // West gate
        ObjectAction.register(3945, "enter", (player, obj) -> {
            boolean north = player.getAbsY() < obj.y;
            player.startEvent(e -> {
                player.lock();
                if (player.getAbsX() != 2385) {
                    player.stepAbs(2385, player.getAbsY(), StepType.FORCE_WALK);
                    e.delay(1);
                    player.face(obj);
                    e.delay(1);
                }
                new GameObject(-1, new Position(2384, 3334, 0), 10, 0).spawn();
                new GameObject(-1, new Position(2386, 3334, 0), 10, 0).spawn();
                WEST_CLOSED_GATE.remove();
                EAST_OPEN_GATE.spawn();
                WEST_OPEN_GATE.spawn();
                player.stepAbs(player.getAbsX(), player.getAbsY() + ((north ? 1 : -1) * 2), StepType.FORCE_WALK);
                e.delay(3);
                EAST_OPEN_GATE.remove();
                WEST_OPEN_GATE.remove();
                EAST_CLOSED_GATE.spawn();
                WEST_CLOSED_GATE.spawn();
                player.unlock();
            });
        });
    }
}

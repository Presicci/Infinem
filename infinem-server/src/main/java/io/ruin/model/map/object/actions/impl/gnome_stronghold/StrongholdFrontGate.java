package io.ruin.model.map.object.actions.impl.gnome_stronghold;

import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/19/2024
 */
public class StrongholdFrontGate {

    private static final GameObject CLOSED_GATE = new GameObject(190, new Position(2459, 3383, 0), 10, 2);
    private static final GameObject EAST_OPEN_GATE = new GameObject(192, new Position(2462, 3383, 0), 10, 0);
    private static final GameObject WEST_OPEN_GATE = new GameObject(191, new Position(2459, 3383, 0), 10, 2);
    private static final int centeredX = 2461;

    static {
        ObjectAction.register(190, "open", (player, obj) -> {
            boolean north = player.getAbsY() < obj.y;
            player.startEvent(e -> {
                player.lock();
                if (player.getAbsX() != centeredX) {
                    player.stepAbs(centeredX, player.getAbsY(), StepType.FORCE_WALK);
                    e.delay(1);
                    player.face(obj);
                    e.delay(1);
                }
                EAST_OPEN_GATE.spawn();
                WEST_OPEN_GATE.spawn();
                player.stepAbs(player.getAbsX(), player.getAbsY() + ((north ? 1 : -1) * 3), StepType.FORCE_WALK);
                e.delay(3);
                EAST_OPEN_GATE.remove();
                WEST_OPEN_GATE.remove();
                CLOSED_GATE.spawn();
                player.unlock();
            });
        });
    }
}

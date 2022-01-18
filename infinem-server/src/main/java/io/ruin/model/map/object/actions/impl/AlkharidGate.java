package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class AlkharidGate {

    private static void openAlkharidGate(Player player, GameObject obj) {
        Direction dir = player.getPosition().getX() == obj.getPosition().getX() ? Direction.WEST : Direction.EAST;
        World.startEvent(e -> {
            e.delay(1);
            GameObject tempGate = new GameObject(2883, 3267, 3228, 0, 0, 1);
            GameObject tempRemove = new GameObject(-1, 3268, 3228, 0, 0, 0);
            GameObject tempGate2 = new GameObject(2882, 3267, 3227, 0, 0, 3);
            GameObject tempRemove2 = new GameObject(-1, 3268, 3227, 0, 0, 0);
            GameObject northGate = new GameObject(2883, 3268, 3228, 0, 0, 0);
            GameObject southGate = new GameObject(2882, 3268, 3227, 0, 0, 0);
            tempRemove.spawn();
            tempGate.spawn();
            tempRemove2.spawn();
            tempGate2.spawn();
            player.getMovement().force(dir == Direction.EAST ? 1 : -1, 0, 0, 0, 1, 0, dir);
            e.delay(2);
            tempGate.remove();
            tempRemove.remove();
            tempGate2.remove();
            tempRemove2.remove();
            northGate.spawn();
            southGate.spawn();
        });
    }

    static {
        ObjectAction.register(2883, 3268, 3228, 0, "open", AlkharidGate::openAlkharidGate);
        ObjectAction.register(2882, 3268, 3227, 0, "open", AlkharidGate::openAlkharidGate);
    }
}

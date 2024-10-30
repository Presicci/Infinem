package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
public class LizardmenTemple {

    // These are supposed to have cooldowns, stored in the objects VB
    private static void passThroughBarrier(Player player, GameObject obj) {
        boolean right = obj.x < player.getAbsX();
        boolean above = obj.y < player.getAbsY();
        switch (obj.direction) {
            case 0:
                teleportPlayer(player, player.getAbsX(), player.getAbsY() + (above ? -2 : 2));
                break;
            case 3:
            case 1:
                teleportPlayer(player, player.getAbsX() + (right ? -2 : 2), player.getAbsY());
                break;
        }
    }

    private static void teleportPlayer(Player player, int x, int y) {
        if(player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y);
            event.delay(1);
            player.unlock();
        });
    }

    static {
        for (int objId : Arrays.asList(34642, 34643, 34644, 34645, 34646)) {
            ObjectAction.register(objId, 1, LizardmenTemple::passThroughBarrier);
        }
    }
}

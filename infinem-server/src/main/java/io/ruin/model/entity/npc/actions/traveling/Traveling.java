package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;

/**
 * @author ReverendDread on 7/19/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Traveling {

    public static void fadeTravel(Player player, Position positon, Runnable onComplete) {
        fadeTravel(player, positon.getX(), positon.getY(), positon.getZ(), onComplete);
    }

    public static void fadeTravel(Player player, Position positon) {
        fadeTravel(player, positon.getX(), positon.getY(), positon.getZ(), null);
    }

    public static void fadeTravel(Player player, int x, int y, int z) {
        fadeTravel(player, x, y, z, null);
    }

    public static void fadeTravel(Player player, int x, int y, int z, Runnable onComplete) {
        player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(x, y, z);
            //player.getPacketSender().clearFade();
            player.getPacketSender().fadeIn();
            player.unlock();
            if (onComplete != null)
                onComplete.run();
        });
    }

}

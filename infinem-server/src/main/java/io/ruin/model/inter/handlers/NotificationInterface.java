package io.ruin.model.inter.handlers;

import io.ruin.api.utils.Tuple;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/20/2024
 */
public class NotificationInterface {

    @Getter
    @AllArgsConstructor
    public static class Notification {
        private int color;
        private String title, text;
    }

    public static void sendNotification(Player player, int color, String title, String text) {
        if (player.isVisibleInterface(660)) {
            player.queuedPopups.add(new Notification(color, title, text));
            return;
        }
        showNotification(player, color, title, text);
    }

    private static void showNotification(Player player, int color, String title, String text) {
        World.startEvent(e -> {});
        World.startEvent(e -> {
            player.openInterface(InterfaceType.POPUP_NOTIFICATION_OVERLAY, 660);
            player.getPacketSender().sendClientScript(3343, "iss", color, title, text);
            e.delay(12);
            List<Notification> queuedPopups = player.queuedPopups;
            if (!queuedPopups.isEmpty()) {
                Notification nextNotification = queuedPopups.remove(0);
                showNotification(player, nextNotification.getColor(), nextNotification.getTitle(), nextNotification.getText());
            } else {
                player.closeInterface(InterfaceType.POPUP_NOTIFICATION_OVERLAY);
            }
        });
    }
}

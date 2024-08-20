package io.ruin.utility;

import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.central.CentralClient;

import java.util.function.BiConsumer;

public enum Broadcast {
    // Sent to friends.
    FRIENDS(Icon.GREEN_INFO_BADGE, (player, message) -> CentralClient.sendGlobalMessage(player.getUserId(), message)),
    // Sent to everyone in the world.
    WORLD(Icon.BLUE_INFO_BADGE, (player, message) -> World.players.forEach(p -> {
        //If the player has toggle off announcement broadcasts don't send.
        if (!p.broadcastHotspot && (message.contains("[Hotspot]"))) {
            return;
        }
        //If the player has toggle off tournament broadcasts don't send.
        if (!p.broadcastTournaments && message.contains("[Tournament]")) {
            return;
        }
        if (!p.broadcastBossEvent && message.contains("[World Boss]")) {
            return;
        }
        p.sendMessage(message);
    })),
    INFORMATION(Icon.INFO, (player, message) -> World.players.forEach(p -> {
        if (Config.INFORMATION_BROADCASTS.get(player) == 1) p.sendMessage(message);
    })),
    SKILL(Icon.SKILL_ICON, (player, message) -> World.players.forEach(p -> {
        if (Config.SKILLING_BROADCASTS.get(player) == 1) p.sendMessage(message);
    })),
    // Sent to everyone in the world and as a notification.
    WORLD_NOTIFICATION(Icon.BLUE_INFO_BADGE, (player, message) -> World.players.forEach(p -> {
        p.sendMessage(message);
        p.sendNotification(message);
    })),
    // 99 messages and 200m, use to filter
    // Global broadcasts are sent to everyone in the game, and to discord.
    GLOBAL(Icon.YELLOW_INFO_BADGE, (player, message) -> CentralClient.sendGlobalMessage(-1, message));

    private final Icon newsIcon;


    private final BiConsumer<Player, String> consumer;

    Broadcast(Icon newsIcon, BiConsumer<Player, String> consumer) {
        this.newsIcon = newsIcon;
        this.consumer = consumer;
    }

    /**
     * Send the given message as is.
     */

    public void sendPlain(String message) {
        sendPlain(null, message);
    }

    public void sendPlain(Player player, String message) {
        //Player only required to be set when this == FRIENDS.
        consumer.accept(player, message);
    }

    /**
     * Send the given message with a news portion added to it.
     */

    public void sendNews(String message) {
        sendNews(null, null, null, message);
    }

    public void sendNews(Player player, String message) {
        sendNews(player, null, null, message);
    }

    public void sendNews(Player player, Icon overrideIcon, String message) {
        sendNews(player, overrideIcon, null, message);
    }

    public void sendNews(Icon overrideIcon, String title, String message) {
        sendNews(null, overrideIcon, title, message);
    }

    public void sendNews(Icon overrideIcon, String message) {
        sendNews(null, overrideIcon, null, message);
    }

    public void sendNews(Player player, Icon overrideIcon, String title, String message) {
        //Player only required to be set when this == FRIENDS.
      consumer.accept(player, (overrideIcon == null ? newsIcon.tag() : overrideIcon.tag()) + "<col=f4d03f> " +
             (title == null ? "" : title + ":") + " <col=1e44b3>" + message);
    }

}
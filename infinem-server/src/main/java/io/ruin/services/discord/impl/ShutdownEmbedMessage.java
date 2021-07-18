package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.content.activities.event.impl.eventboss.EventBossType;
import io.ruin.model.World;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.*;

public class ShutdownEmbedMessage {

    public static void sendDiscordMessage(String shutdownMessage) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.WEBHOOK_URL);
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle("Shutdown Announcement");
            embedMessage.setDescription(shutdownMessage);
            embedMessage.setColor(26280);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://static.runelite.net/cache/item/icon/" + 13307 + ".png");
            embedMessage.setThumbnail(thumbnail);
            /*
             * Footer
             */
            //Footer footer = new Footer();
            //footer.setText(World.type.getWorldName());
            //embedMessage.setFooter(footer);

            /*
             * Fire the message
             */
            message.setEmbeds(embedMessage);
            webhook.sendMessage(message.toJson());
        } catch (Exception e) {
            ServerWrapper.logError("Failed to send discord embed", e);
        }
    }

}

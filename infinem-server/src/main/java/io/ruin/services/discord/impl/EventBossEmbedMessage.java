package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.content.activities.event.impl.eventboss.EventBossType;
import io.ruin.model.World;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Constants;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Image;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/13/2020
 */
public class EventBossEmbedMessage {
    public static void sendDiscordMessage(EventBossType boss, String location) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.WEBHOOK_URL);
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle("An event boss has spawned!");
            embedMessage.setDescription(location);
            embedMessage.setColor(14230283);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://oldschool.runescape.wiki/images/a/a1/Skull_%28status%29_icon.png?fa6d8");
            embedMessage.setThumbnail(thumbnail);

            /*
             * Image
             */
            Image image = new Image();
            image.setUrl(boss.getEmbedUrl());
            embedMessage.setImage(image);

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

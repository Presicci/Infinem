package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.model.stat.StatType;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Constants;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/2/2024
 */
public class SkillAchievementEmbedMessage {

    public static void sendDiscordMessage(String title, String description, StatType statType) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.SKILL_ACHIEVEMENT_WEBHOOK_URL);
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle(title);
            embedMessage.setDescription(description);
            embedMessage.setColor(16520212);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://oldschool.runescape.wiki/images/" + StringUtils.capitalizeFirst(statType.name().toLowerCase()) + "_icon.png");
            embedMessage.setThumbnail(thumbnail);

            /*
             * Fire the message
             */
            message.setEmbeds(embedMessage);
            webhook.sendMessage(message.toJson());
        } catch (Exception e) {
            ServerWrapper.logError("Failed to send discord embed", e);
        }
    }

    public static void sendMaxMessage(String title, String description) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.SKILL_ACHIEVEMENT_WEBHOOK_URL);
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle(title);
            embedMessage.setDescription(description);
            embedMessage.setColor(14759147);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://oldschool.runescape.wiki/images/Max_cape_detail.png");
            embedMessage.setThumbnail(thumbnail);

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

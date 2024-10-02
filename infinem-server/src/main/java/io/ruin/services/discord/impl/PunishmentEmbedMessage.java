package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Constants;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/2/2024
 */
public class PunishmentEmbedMessage {

    public static void sendDiscordMessage(String playerName, String punishment) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.PUNISHMENT_URL);
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle(playerName);
            embedMessage.setDescription(punishment);
            embedMessage.setColor(16119064);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://preview.redd.it/rp9543s44n6b1.png?width=960&crop=smart&auto=webp&s=98c5afe04b4dbe64ffd3a0c981dd3e53167b42b8");
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

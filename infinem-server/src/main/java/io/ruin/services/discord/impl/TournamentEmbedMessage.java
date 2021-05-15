package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Constants;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Footer;
import io.ruin.services.discord.util.Message;
import net.dv8tion.jda.api.requests.restaction.WebhookAction;

public class TournamentEmbedMessage {

    public static void sendDiscordMessage(String presetName, String minutes) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.WEBHOOK_URL);
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle("Tournament System");
            embedMessage.setDescription("The " + presetName + " tournament will begin in **" + minutes + " minutes**. Login and type ::tournament to join!");
            embedMessage.setColor(26280);

            /*
             * Footer
             */
            Footer footer = new Footer();
            footer.setText(World.type.getWorldName());
            embedMessage.setFooter(footer);

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

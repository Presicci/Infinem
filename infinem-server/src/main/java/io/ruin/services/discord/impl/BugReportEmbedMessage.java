package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.*;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
public class BugReportEmbedMessage {
    public static void sendDiscordMessage(Player player, String description, String replication) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.BUG_REPORT_WEBHOOK);
            Message message = new Message();

            List<String> localPlayers = new ArrayList<>();
            player.localPlayers().forEach(p -> localPlayers.add(p.getName()));
            StringBuilder inventoryItems = new StringBuilder();
            for (Item item : player.getInventory().getItems()) {
                if (item == null) continue;
                inventoryItems.append(item.getId());
                inventoryItems.append(", ");
                inventoryItems.append(item.getAmount());
                inventoryItems.append("\t\t(");
                inventoryItems.append(ItemDef.get(item.getId()).name);
                inventoryItems.append(")\n");
            }
            StringBuilder equipmentItems = new StringBuilder();
            for (Item item : player.getEquipment().getItems()) {
                if (item == null) continue;
                equipmentItems.append(item.getId());
                equipmentItems.append(", ");
                equipmentItems.append(item.getAmount());
                equipmentItems.append("\t\t(");
                equipmentItems.append(ItemDef.get(item.getId()).name);
                equipmentItems.append(")\n");
            }

            Embed embedMessage = new Embed();
            embedMessage.setTitle(player.getName() + " has reported a bug");
            embedMessage.setDescription(
                            description + "\n\n"
                            + replication + "\n\n"
                            + "x:" + player.getAbsX() + ", y:" + player.getAbsY() + ", z:" + player.getHeight() + "\n\n"
                            + "Local Players:\n" + Arrays.toString(localPlayers.toArray(new String[0])) + "\n\n"
                            + "Inventory:\n" + inventoryItems + "\n"
                            + "Equipment:\n" + equipmentItems
            );
            embedMessage.setColor(14230283);

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

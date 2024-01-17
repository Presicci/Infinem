package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Constants;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
public class AbuseReportEmbedMessage {

    @AllArgsConstructor
    private enum ReportType {
        OFFENSIVE_LANGUAGE(15),
        SCAMMING(14),
        REAL_WORLD_LAWS(20),
        BUG_EXPLOIT(3),
        STAFF_IMPERSONATION(4),
        ENCOURAGING_RULE_BREAKING(8),
        MACROING(6),
        OFFENSIVE_NAME(18),
        ADVERTISING(10),
        BUYING_OR_SELLING_SERVICES(5),
        ASKING_FOR_CONTACT_INFORMATION(12),
        SOLICITATION(16),
        GAMBLING(21),
        DISRUPTIVE_BEHAVIOUR(17),
        REAL_LIFE_THREATS(19);

        private final int index;

        private static ReportType get(int index) {
            for (ReportType type : values()) {
                if (type.index == index) return type;
            }
            return null;
        }
    }

    public static void sendDiscordMessage(Player player, Player reportedPlayer, int reportTypeIndex) {
        if (!World.isLive()){
            return;
        }
        try {
            Webhook webhook = new Webhook(Constants.ABUSE_REPORT_WEBHOOK);
            Message message = new Message();

            ReportType reportType = ReportType.get(reportTypeIndex);
            List<String> localPlayers = new ArrayList<>();
            reportedPlayer.localPlayers().forEach(p -> localPlayers.add(p.getName()));
            StringBuilder inventoryItems = new StringBuilder();
            for (Item item : reportedPlayer.getInventory().getItems()) {
                if (item == null) continue;
                inventoryItems.append(item.getId());
                inventoryItems.append(", ");
                inventoryItems.append(item.getAmount());
                inventoryItems.append("\t\t(");
                inventoryItems.append(ItemDef.get(item.getId()).name);
                inventoryItems.append(")\n");
            }
            StringBuilder equipmentItems = new StringBuilder();
            for (Item item : reportedPlayer.getEquipment().getItems()) {
                if (item == null) continue;
                equipmentItems.append(item.getId());
                equipmentItems.append(", ");
                equipmentItems.append(item.getAmount());
                equipmentItems.append("\t\t(");
                equipmentItems.append(ItemDef.get(item.getId()).name);
                equipmentItems.append(")\n");
            }
            StringBuilder sentMessages = new StringBuilder();
            for (String sentMessage : reportedPlayer.sentMessages) {
                sentMessages.append(sentMessage);
                sentMessages.append("\n");
            }

            Embed embedMessage = new Embed();
            if (reportType == null) {
                embedMessage.setTitle("Report error!");
                embedMessage.setDescription("Report of type:" + reportTypeIndex + " has improper index.\nReport sent by " + player.getName() + " of " + reportedPlayer.getName());
            } else {
                embedMessage.setTitle(player.getName() + " has reported " + reportedPlayer.getName() + " for " + reportType.name().toLowerCase().replace("_", " "));
                embedMessage.setDescription(
                                "Sent Messages:\n" + sentMessages + "\n"
                                + "x:" + reportedPlayer.getAbsX() + ", y:" + reportedPlayer.getAbsY() + ", z:" + reportedPlayer.getHeight() + "\n\n"
                                + "Local Players:\n" + Arrays.toString(localPlayers.toArray(new String[0])) + "\n\n"
                                + "Inventory:\n" + inventoryItems + "\n"
                                + "Equipment:\n" + equipmentItems
                );
            }

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
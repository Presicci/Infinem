package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.utils.IPMute;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;
import io.ruin.utility.ClientPacketHolder;

import java.util.Arrays;
import java.util.List;

@ClientPacketHolder(packets = {ClientPacket.MESSAGE_PUBLIC})
public class ChatHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (IPMute.isIPMuted(player.getIp()) || Punishment.isMuted(player)) {
            if (!player.shadowMute) {
                player.sendMessage("You're muted and can't talk.");
                return;
            }
            return;
        }
        int type = in.readUnsignedByte();
        int color = in.readUnsignedByte();
        int effect = in.readUnsignedByte();
        byte[] pattern = null;
        if (color >= 13 && color <= 20) {
            pattern = new byte[color - 12];
            in.readBytes(pattern);
        }
        int length = in.readSmart();
        byte[] compressed = new byte[length];
        in.readBytes(compressed);

        String message = Huffman.decompress(compressed, length);
        if (message.isEmpty()) {
            /* how does this even happen? */
            return;
        }

        /*
         * If player mentions any of these words don't send it.
         */
        List<String> badWords = Arrays.asList("ikov", "roatz", "alora", "nigger");
        for (String word : badWords) {
            if (message.toLowerCase().contains(word)) {
                return;
            }
        }
        player.sentMessages.add(message);
        if (player.sentMessages.size() > 20) player.sentMessages.poll();
        if (type == 2) {
            message = message.substring(1);
            CentralClient.sendClanMessage(player.getUserId(), player.getClientGroupId(), message);
            Loggers.logClanChat(player.getUserId(), player.getName(), player.getIp(), message);
            return;
        }
        if (message.length() >= 3) {
            char c1 = message.charAt(0);
            char c2 = message.charAt(1);
            if ((c1 == ':' && c2 == ':') || (c1 == ';' && c2 == ';')) {
                CommandHandler.handle(player, message.substring(2));
                return;
            }
            if (c1 == '!' && player.isAdmin()) {
                player.forceText(message.substring(1));
                return;
            }
        }
        player.getChatUpdate().set(color, effect, player.getClientGroupId(), type, message, pattern);
        Loggers.logPublicChat(player.getUserId(), player.getName(), player.getIp(), message, type, effect);
    }

}
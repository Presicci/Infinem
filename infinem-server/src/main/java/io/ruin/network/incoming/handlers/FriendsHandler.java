package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.protocol.Protocol;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;
import io.ruin.utility.ClientPacketHolder;

@ClientPacketHolder(packets = {
        ClientPacket.IGNORELIST_ADD, ClientPacket.FRIENDLIST_ADD, ClientPacket.FRIENDLIST_DEL,
        ClientPacket.IGNORELIST_DEL, ClientPacket.MESSAGE_PRIVATE, ClientPacket.FRIENDCHAT_SETRANK})
public class FriendsHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String name;
        if (opcode == ClientPacket.FRIENDCHAT_SETRANK.packetId) {
            /**
             * Rank friend
             */
            int rank = in.readByte();
            name = in.readString();
            CentralClient.sendClanRank(player.getUserId(), name, Math.abs(rank));
            return;
        }
        name = in.readString();
        if (opcode == ClientPacket.FRIENDLIST_ADD.packetId) {
            /**
             * Add friend
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 1);
            return;
        }
        if (opcode == ClientPacket.FRIENDLIST_DEL.packetId) {
            /**
             * Delete friend
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 2);
            return;
        }
        if (opcode == ClientPacket.IGNORELIST_ADD.packetId) {
            /**
             * Add ignore
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 3);
            return;
        }
        if (opcode == ClientPacket.IGNORELIST_DEL.packetId) {
            /**
             * Delete ignore
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 4);
            return;
        }
        if (opcode == ClientPacket.MESSAGE_PRIVATE.packetId) {
            /**
             * Private message
             */
            int len = in.readSmart();
            byte[] compressed = new byte[len];
            in.readBytes(compressed);

            String message = Huffman.decompress(compressed, compressed.length);
            System.out.println(name + ": " + message);
            if (Punishment.isMuted(player)) {
                if (player.shadowMute)
                    player.getPacketSender().write(Protocol.outgoingPm(name, message));
                else
                    player.sendMessage("You're muted and can't talk.");
                return;
            }
            player.sentMessages.add(message);
            if (player.sentMessages.size() > 20) player.sentMessages.poll();
            CentralClient.sendPrivateMessage(player.getUserId(), player.getClientGroupId(), name, message);
            Loggers.logPrivateChat(player.getUserId(), player.getName(), player.getIp(), name, message);
            return;
        }
    }

}
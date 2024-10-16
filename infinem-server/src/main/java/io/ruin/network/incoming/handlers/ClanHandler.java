package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;

@ClientPacketHolder(packets = {ClientPacket.FRIENDCHAT_JOIN_LEAVE, ClientPacket.FRIENDCHAT_KICK})
public class ClanHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String username = in.readString();
        if (opcode == ClientPacket.FRIENDCHAT_JOIN_LEAVE.packetId) {
            /**
             * Join / Leave
             */
            CentralClient.sendClanRequest(player.getUserId(), username);
            return;
        }
        if (opcode == ClientPacket.FRIENDCHAT_KICK.packetId) {
            /**
             * Kick
             */
            CentralClient.sendClanKick(player.getUserId(), username);
            return;
        }
    }

}
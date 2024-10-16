package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;

@ClientPacketHolder(packets = {ClientPacket.CHAT_SETFILTER})
public class PrivacyHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int publicSetting = in.readByte();
        int privateSetting = in.readByte();
        int tradeSetting = in.readByte();
        CentralClient.sendPrivacy(player.getUserId(), privateSetting);
    }

}
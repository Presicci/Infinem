package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;

@ClientPacketHolder(packets = {ClientPacket.CLOSE_MODAL})
public class CloseInterfaceHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        player.closeInterfaces();
        player.getPacketSender().sendClientScript(2158);
    }

}
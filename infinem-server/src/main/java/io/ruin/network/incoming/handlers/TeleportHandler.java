package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.ClientPacket;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.ClientPacketHolder;

@ClientPacketHolder(packets = {ClientPacket.TELEPORT})
public class TeleportHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int z = in.readUnsignedByteNeg();
        in.readMInt();
        int y = in.readUnsignedLEShort();
        int x = in.readUnsignedLEShort();
        player.getMovement().teleport(x, y, z);
    }

}

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
        in.readLEInt();
        int z = in.readByteAdd();
        int x = in.readUnsignedLEShort();
        int y = in.readUnsignedLEShort();


        player.getMovement().teleport(x, y, z);
    }

}

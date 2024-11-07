package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/6/2024
 */
@ClientPacketHolder(packets = {ClientPacket.MAP_BUILD_COMPLETE})
public class RegionUpdateHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        player.getPacketSender().hideGroundItemOptions(false);
        player.getPacketSender().hideNPCOptions(false);
        player.getPacketSender().hideObjectOptions(false);
    }

}
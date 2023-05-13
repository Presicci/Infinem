package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {63})//@IdHolder(ids = {7})
public class TeleportHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int y = in.readShort();
        int unknown = in.readInt1(); // should always be 0, dunno what it is.
        int z = in.readByteA();
        int x = in.readShortA();

        player.getMovement().teleport(x, y, z);
    }

}

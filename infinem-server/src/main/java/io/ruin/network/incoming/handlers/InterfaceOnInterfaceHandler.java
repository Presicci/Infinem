package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.DebugMessage;

public class InterfaceOnInterfaceHandler {

    @ClientPacketHolder(packets = {ClientPacket.IF_BUTTONT})
    public static final class OnInterface implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int toItemId = in.readUnsignedShortAdd();
            int toSlot = in.readUnsignedShortAdd();
            int fromItemId = in.readUnsignedLEShort();
            int toInterfaceHash = in.readIMInt();
            int fromInterfaceHash = in.readInt();
            int fromSlot = in.readUnsignedLEShortAdd();
            handleAction(player, fromInterfaceHash, fromSlot, fromItemId, toInterfaceHash, toSlot, toItemId);
        }
    }

    private static void handleAction(Player player, int fromInterfaceHash, int fromSlot, int fromItemId, int toInterfaceHash, int toSlot, int toItemId) {
        if (player.isLocked())
            return;
        player.resetActions(true, false, true);

        int fromInterfaceId = fromInterfaceHash >> 16;
        int fromChildId = fromInterfaceHash & 0xffff;
        if (fromChildId == 65535)
            fromChildId = -1;
        if (fromSlot == 65535)
            fromSlot = -1;
        if (fromItemId == 65535)
            fromItemId = -1;

        int toInterfaceId = toInterfaceHash >> 16;
        int toChildId = toInterfaceHash & 0xffff;
        if (toChildId == 65535)
            toChildId = -1;
        if (toSlot == 65535)
            toSlot = -1;
        if (toItemId == 65535)
            toItemId = -1;

        if (player.debug) {
            player.sendFilteredMessage("[InterfaceOnInterface]");
            DebugMessage debug = new DebugMessage()
                    .add("inter", fromInterfaceId)
                    .add("child", fromChildId)
                    .add("slot", fromSlot)
                    .add("item", fromItemId);
            player.sendFilteredMessage("    From: " + debug.toString());
            debug = new DebugMessage()
                    .add("inter", toInterfaceId)
                    .add("child", toChildId)
                    .add("slot", toSlot)
                    .add("item", toItemId);
            player.sendFilteredMessage("    To: " + debug.toString());
        }

        InterfaceAction action = InterfaceHandler.getAction(player, fromInterfaceId, fromChildId);
        if (action != null)
            action.handleOnInterface(player, fromSlot, fromItemId, toInterfaceId, toChildId, toSlot, toItemId);
    }

}
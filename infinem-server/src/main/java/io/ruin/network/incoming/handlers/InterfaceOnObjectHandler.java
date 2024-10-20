package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.DebugMessage;

public class InterfaceOnObjectHandler {

    @ClientPacketHolder(packets = {ClientPacket.OPLOCT})
    public static final class FromInterface implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int ctrlRun = in.readUnsignedByteAdd();
            int objectId = in.readUnsignedShortAdd();
            int itemId = in.readUnsignedShort();
            int objectY = in.readUnsignedLEShort();
            int objectX = in.readUnsignedLEShort();
            int interfaceHash = in.readLEInt();
            int slot = in.readUnsignedLEShort();
            handleAction(player, interfaceHash, slot, itemId, objectId, objectX, objectY, ctrlRun);
        }
    }

    private static void handleAction(Player player, int interfaceHash, int slot, int itemId, int objectId, int objectX, int objectY, int ctrlRun) {
        if (player.debug) {
            DebugMessage debug = new DebugMessage()
                    .add("interfaceHash", interfaceHash)
                    .add("slot", slot)
                    .add("itemId", itemId)
                    .add("objectId", objectId)
                    .add("objectX", objectX)
                    .add("objectY", objectY);
            player.sendFilteredMessage("[ObjectAction] " + debug.toString());
        }
        if (player.isLocked())
            return;
        player.resetActions(true, true, true);
        if (objectId == -1)
            return;
        GameObject gameObject = Tile.getObject(objectId, objectX, objectY, player.getPosition().getZ());
        if (gameObject == null)
            return;
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeObject(gameObject, () -> action(player, interfaceHash, slot, itemId, gameObject));
    }

    private static void action(Player player, int interfaceHash, int slot, int itemId, GameObject gameObject) {
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceHash);
        if (action == null)
            return;
        if (slot == 65535)
            slot = -1;
        if (itemId == 65535)
            itemId = -1;
        action.handleOnObject(player, slot, itemId, gameObject);
    }

}
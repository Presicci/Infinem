package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.ground.GroundItemAction;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.DebugMessage;

@ClientPacketHolder(packets = {
        ClientPacket.OPOBJ1, ClientPacket.OPOBJ2, ClientPacket.OPOBJ3,
        ClientPacket.OPOBJ4, ClientPacket.OPOBJ5, ClientPacket.OPOBJ6})
public class GroundItemActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (player.isLocked())
            return;
        player.resetActions(true, true, true);
        int option = OPTIONS[opcode];
        if (option == 1) {
            int x = in.readUnsignedLEShortAdd();
            int y = in.readUnsignedLEShort();
            int id = in.readUnsignedShortAdd();
            int ctrlRun = in.readByteSub();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 2) {
            int id = in.readUnsignedLEShort();
            int y = in.readUnsignedLEShort();
            int x = in.readUnsignedLEShort();
            int ctrlRun = in.readByteNeg();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 3) {
            int ctrlRun = in.readByteNeg();
            int id = in.readUnsignedShort();
            int x = in.readUnsignedLEShortAdd();
            int y = in.readUnsignedShortAdd();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 4) {
            int ctrlRun = in.readByteAdd();
            int x = in.readUnsignedLEShortAdd();
            int y = in.readUnsignedLEShortAdd();
            int id = in.readUnsignedLEShortAdd();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 5) {
            int id = in.readUnsignedShort();
            int x = in.readUnsignedShort();
            int y = in.readUnsignedShortAdd();
            int ctrlRun = in.readByteSub();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 6) {
            int y = in.readShort();
            int x = in.readLEShortAdd();
            int id = in.readLEShort();
            ItemDefinition def = ItemDefinition.get(id);
            if (def != null) {
                if (player.debug) {
                    DebugMessage debug = new DebugMessage()
                            .add("id", id);
                    player.sendFilteredMessage("[GroundItemAction] " + debug.toString());
                }
                def.examine(player);
            } else {
                if (player.debug) {
                    player.sendFilteredMessage("[GroundItemAction] Ground item with ID: " + id + " is null.");
                }
            }
        }
        player.sendFilteredMessage("Unhandled ground item action: option=" + option + " opcode=" + opcode);
    }

    private static void handleAction(Player player, int option, int groundItemId, int x, int y, int ctrlRun) {
        int z = player.getHeight();
        Tile tile = Tile.get(x, y, z, false);
        if (tile == null)
            return;
        GroundItem groundItem = tile.getPickupItem(groundItemId, player.getUserId());
        if (groundItem == null)
            return;
        ItemDefinition def = ItemDefinition.get(groundItem.id);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeGroundItem(groundItem, distance -> {
            int i = option - 1;
            if (i < 0 || i >= 5)
                return;
            if (player.debug) {
                DebugMessage debug = new DebugMessage()
                        .add("option", option)
                        .add("id", groundItemId)
                        .add("name", def.name)
                        .add("x", x)
                        .add("y", y);
                System.out.println(debug);
                player.sendFilteredMessage("[GroundItemAction] " + debug.toString());
            }
            if (option == def.pickupOption) {
                groundItem.pickup(player, distance);
                return;
            }
            GroundItemAction action;
            if (def.groundActions != null && (action = def.groundActions[i]) != null) {
                action.handle(player, groundItem, distance);
                return;
            }
            player.sendMessage("Nothing interesting happens.");
        });
    }

}
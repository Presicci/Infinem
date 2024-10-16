package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.def.ObjectDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.TricksterAgility;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.DebugMessage;

import java.util.Arrays;

@ClientPacketHolder(packets = {
        ClientPacket.OPLOC1, ClientPacket.OPLOC2, ClientPacket.OPLOC3,
        ClientPacket.OPLOC4, ClientPacket.OPLOC5, ClientPacket.OPLOCE})
public class ObjectActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (player.isLocked())
            return;
        player.resetIdle();
        int option = OPTIONS[opcode];
        if (option != 6)
            player.resetActions(true, true, true);
        if (option == 1) {
            int x = in.readUnsignedLEShortAdd();
            int id = in.readUnsignedShort();
            int y = in.readUnsignedShort();
            int ctrlRun = in.readByteSub();
            player.removeTemporaryAttribute(TricksterAgility.KEY);
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 2) {
            int y = in.readUnsignedShort();
            int id = in.readUnsignedLEShortAdd();
            int ctrlRun = in.readByteNeg();
            int x = in.readUnsignedLEShort();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 3) {
            int y = in.readUnsignedShort();
            int id = in.readUnsignedLEShort();
            int ctrlRun = in.readByteSub();
            int x = in.readUnsignedLEShortAdd();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 4) {
            int y = in.readUnsignedShort();
            int x = in.readUnsignedLEShort();
            int ctrlRun = in.readByteNeg();
            int id = in.readUnsignedLEShortAdd();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 5) {
            int y = in.readUnsignedLEShortAdd();
            int ctrlRun = in.readByteAdd();
            int x = in.readUnsignedLEShort();
            int id = in.readUnsignedLEShortAdd();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if (option == 6) {
            int id = in.readUnsignedShort();
            ObjectDefinition def = ObjectDefinition.get(id);
            if (def != null) {
                if (player.debug) {
                    DebugMessage debug = new DebugMessage()
                            .add("id", id)
                            .add("name", def.name)
                            .add("options", Arrays.toString(def.options))
                            .add("varpbitId", def.varpBitId)
                            .add("varpId", def.varpId);
                    player.sendFilteredMessage("[ObjectAction] " + debug.toString());
                }
                def.examine(player);
            } else {
                if (player.debug) {
                    player.sendFilteredMessage("[ObjectAction] Object with ID: " + id + " is null.");
                }
            }
            return;
        }
        player.sendFilteredMessage("Unhandled object action: option=" + option + " opcode=" + opcode);
    }

    public static void handleAction(Player player, int option, int objectId, int objectX, int objectY, int ctrlRun) {
        System.out.println(objectId + ": " + objectX + ", " + objectY);
        if (objectId == -1) {
            if (player.debug) {
                player.sendFilteredMessage("[ObjectAction] ObjectId is -1. Option=" + option);
            }
            return;
        }
        GameObject gameObject = Tile.getObject(objectId, objectX, objectY, player.getPosition().getZ());
        if (gameObject == null) {
            if (player.debug) {
                player.sendFilteredMessage("[ObjectAction] Object with ID: " + objectId + " is null. Option=" + option);
            }
            return;
        }
        if (player.debug) {
            DebugMessage debug = new DebugMessage()
                    .add("option", option)
                    .add("id", gameObject.id)
                    .add("name", gameObject.getDef().name)
                    .add("x", gameObject.x)
                    .add("y", gameObject.y)
                    .add("z", gameObject.z)
                    .add("type", gameObject.type)
                    .add("direction", gameObject.direction)
                    .add("options", Arrays.toString(gameObject.getDef().options))
                    .add("varpbitId", gameObject.getDef().varpBitId)
                    .add("varpId", gameObject.getDef().varpId);
            player.sendFilteredMessage("[ObjectAction] " + debug.toString());
        }
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeObject(gameObject, () -> {
            player.getPacketSender().resetMapFlag();
            int i = option - 1;
            if (i < 0 || i >= 5)
                return;
            ObjectAction action = null;
            ObjectAction[] actions = gameObject.actions;
            if (actions != null)
                action = actions[i];
            if (action == null && (actions = gameObject.getDef().defaultActions) != null)
                action = actions[i];
            if (action != null) {
                action.handle(player, gameObject);
                return;
            }
            player.sendMessage("Nothing interesting happens.");
        });
    }

}
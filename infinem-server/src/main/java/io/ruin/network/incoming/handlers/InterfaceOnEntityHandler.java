package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.World;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.DebugMessage;

public class InterfaceOnEntityHandler {

    @ClientPacketHolder(packets = {ClientPacket.OPPLAYERT})
    public static final class InterfaceOnPlayer implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readLEInt();
            int slot = in.readUnsignedLEShortAdd();
            int targetIndex = in.readUnsignedLEShortAdd();
            int itemId = in.readUnsignedShortAdd();
            int ctrlRun = in.readByteAdd();
            handleAction(player, World.getPlayer(targetIndex), interfaceHash, slot, itemId, ctrlRun);
        }

    }

    @ClientPacketHolder(packets = {ClientPacket.OPNPCT})
    public static final class InterfaceOnNpc implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int targetIndex = in.readUnsignedLEShort();
            int item = in.readUnsignedShortAdd();
            int interfaceHash = in.readLEInt();
            int slot = in.readUnsignedLEShort();
            int ctrlRun = in.readByteNeg();
            handleAction(player, World.getNpc(targetIndex), interfaceHash, slot, item, ctrlRun);
        }

    }

    protected static void handleAction(Player player, Entity target, int interfaceHash, int slot, int itemId, int ctrlRun) {
        if (target == null || player.isLocked())
            return;
        player.resetActions(true, true, true);
        player.face(target);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        if (player.debug) {
            DebugMessage debug = new DebugMessage()
                    .add("target", target.isPlayer() ? target.player.getName() : target.npc.getId())
                    .add("interface", interfaceHash)
                    .add("slot", slot)
                    .add("itemId", itemId);
            player.sendFilteredMessage("[ItemOnEntityAction] " + debug.toString());
        }
        if (itemId == -1
                || itemId == 65535
                || (target.isNpc() && (int) target.npc.getDef().custom_values.getOrDefault("ITEM_ON_NPC_SKIP_MOVE_CHECK", -1) == itemId))
            action(player, target, interfaceHash, slot, itemId);
        else
            TargetRoute.set(player, target, () -> action(player, target, interfaceHash, slot, itemId));
    }

    private static void action(Player player, Entity target, int interfaceHash, int slot, int itemId) {
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceHash);
        if (action == null)
            return;
        if (slot == 65535)
            slot = -1;
        if (itemId == 65535)
            itemId = -1;
        action.handleOnEntity(player, target, slot, itemId);
    }

}
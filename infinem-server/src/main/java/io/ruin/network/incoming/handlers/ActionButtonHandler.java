package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.event.GameEventProcessor;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.item.Item;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.DebugMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActionButtonHandler {

    @ClientPacketHolder(packets = {
            ClientPacket.IF_BUTTON1, ClientPacket.IF_BUTTON2, ClientPacket.IF_BUTTON3,
            ClientPacket.IF_BUTTON4, ClientPacket.IF_BUTTON5, ClientPacket.IF_BUTTON6,
            ClientPacket.IF_BUTTON7, ClientPacket.IF_BUTTON8, ClientPacket.IF_BUTTON9,
            ClientPacket.IF_BUTTON10})
    public static final class DefaultHandler implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int option = OPTIONS[opcode];
            int interfaceHash = in.readInt();
            int slot = in.readUnsignedShort();
            int itemId = in.readUnsignedShort();
            handleAction(player, option, interfaceHash, slot, itemId, false);
        }

    }

    @ClientPacketHolder(packets = {ClientPacket.RESUME_PAUSEBUTTON})
    public static final class DialogueHandler implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readMInt();
            int slot = in.readUnsignedShort();
            if (GameEventProcessor.resumeWith(player, slot)) {
                return;
            }
            handleAction(player, 1, interfaceHash, slot, -1, true);
        }

    }

    @ClientPacketHolder(packets = {ClientPacket.IF_BUTTON})
    public static final class OtherHandler implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readInt();
            handleAction(player, 1, interfaceHash, -1, -1, false);
        }

    }

    public static void handleAction(Player player, int option, int interfaceHash, int slot, int itemId, boolean dialogue) {
        int interfaceId = interfaceHash >> 16;
        int childId = interfaceHash & 0xffff;
        if (childId == 65535)
            childId = -1;
        if (slot == 65535)
            slot = -1;
        if (itemId == 65535)
            itemId = -1;
        if (player.debug) {
            DebugMessage debug = new DebugMessage()
                    .add("option", option)
                    .add("inter", interfaceId)
                    .add("child", childId)
                    .add("slot", slot)
                    .add("item", itemId);
            player.sendFilteredMessage("[ActionButton] " + debug.toString());
        }
        if (player.hasTemporaryAttribute("TUTORIAL") && interfaceId != Interface.LOGOUT && !dialogue && interfaceId != 890 && interfaceId != Interface.APPEARANCE_CUSTOMIZATION)
            return;
        // Inventory
        if (option == 10 && interfaceId == 149 && itemId != -1) {
            player.getInventory().get(slot).examine(player);
            return;
        }
        // Ground item examine
        if (option == 10 && interfaceId == 0 && itemId != -1) {
            Item.examine(player, itemId);
            return;
        }
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceId, childId);
        if (action != null)
            action.handleClick(player, option, slot, itemId);
    }

}
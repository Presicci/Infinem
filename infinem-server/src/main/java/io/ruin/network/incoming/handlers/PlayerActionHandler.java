package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;

@ClientPacketHolder(packets = {
        ClientPacket.OPPLAYER1, ClientPacket.OPPLAYER2, ClientPacket.OPPLAYER3,
        ClientPacket.OPPLAYER4, ClientPacket.OPPLAYER5, ClientPacket.OPPLAYER6,
        ClientPacket.OPPLAYER7, ClientPacket.OPPLAYER8})
public class PlayerActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (player.isLocked())
            return;
        player.resetActions(true, true, true);

        int option = OPTIONS[opcode];
        if (option == 1) {
            int targetIndex = in.readUnsignedLEShort();
            int ctrlRun = in.readByte();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 2) {
            int targetIndex = in.readUnsignedShortAdd();
            int ctrlRun = in.readByteAdd();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 3) {
            int ctrlRun = in.readByteAdd();
            int targetIndex = in.readUnsignedLEShortAdd();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 4) {
            int targetIndex = in.readUnsignedShort();
            int ctrlRun = in.readByteSub();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 5) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readUnsignedLEShort();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 6) {
            int targetIndex = in.readUnsignedShortAdd();
            int ctrlRun = in.readByteAdd();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 7) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readUnsignedShortAdd();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 8) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readUnsignedShort();
            handle(player, option, targetIndex, ctrlRun);
            return;
        }
        player.sendFilteredMessage("Unhandled player action: option=" + option + " opcode=" + opcode);
    }

    private static void handle(Player player, int option, int targetIndex, int ctrlRun) {
        Player target = World.getPlayer(targetIndex);
        if (target == null)
            return;
        if (targetIndex == player.getIndex())
            return;
        PlayerAction action = player.getAction(option);
        if (action == null)
            return;
        player.getMovement().setCtrlRun(ctrlRun == 1);
        action.consumer.accept(player, target);
    }

}
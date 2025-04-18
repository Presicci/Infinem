package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;

import java.util.function.Consumer;

@ClientPacketHolder(packets = {
        ClientPacket.RESUME_P_NAMEDIALOG, ClientPacket.RESUME_P_COUNTDIALOG,
        ClientPacket.RESUME_P_OBJDIALOG, ClientPacket.RESUME_P_STRINGDIALOG})
public class InputHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (opcode == ClientPacket.RESUME_P_COUNTDIALOG.packetId) {
            int returnVal = in.readInt();
            Consumer<Integer> consumer = player.consumerInt;
            if (consumer != null) {
                player.consumerInt = null;
                consumer.accept(returnVal);
                if (player.retryIntConsumer) {
                    player.consumerInt = consumer;
                    player.retryIntConsumer = false;
                }
            }
        } else if (opcode == ClientPacket.RESUME_P_OBJDIALOG.packetId) {
            int returnVal = in.readUnsignedShort();
            Consumer<Integer> consumer = player.consumerInt;
            if (consumer != null) {
                player.consumerInt = null;
                consumer.accept(returnVal);
                if (player.retryIntConsumer) {
                    player.consumerInt = consumer;
                    player.retryIntConsumer = false;
                }
            }
        } else if (opcode == ClientPacket.RESUME_P_NAMEDIALOG.packetId || opcode == ClientPacket.RESUME_P_STRINGDIALOG.packetId) {
            String returnVal = in.readString();
            Consumer<String> consumer = player.consumerString;
            if (consumer != null) {
                player.consumerString = null;
                consumer.accept(returnVal);
                if (player.retryStringConsumer) {
                    player.consumerString = consumer;
                    player.retryStringConsumer = false;
                }
            }
        } else {
            throw new UnsupportedOperationException("unknown input opcode decode: " + opcode);
        }
    }

}
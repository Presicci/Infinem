package io.ruin.update;

import io.netty.channel.Channel;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.FileStore;
import io.ruin.api.netty.MessageDecoder;
import io.ruin.api.protocol.Response;

import java.util.Arrays;

public class HandshakeDecoder extends MessageDecoder<Channel> {

    private static final int SUB_BUILD = 1;
    public static int REVISION = 226;
    public static final int OPCODE = 15;
    public static final int SIZE = 4;

    private final FileStore fileStore;

    public HandshakeDecoder(FileStore fileStore) {
        super(null, false);
        this.fileStore = fileStore;
    }

    @Override
    protected void handle(Channel channel, InBuffer in, int opcode) {
        handle(fileStore, channel, in, opcode);
    }

    public static void handle(FileStore fileStore, Channel channel, InBuffer in, int opcode) {
        switch(opcode) {
            case OPCODE:
                int clientBuild = in.readInt();
                in.readInt();
                in.readInt();
                in.readInt();
                in.readInt();
                if (clientBuild == REVISION) {
                    channel.writeAndFlush(new OutBuffer(SUB_BUILD).addByte(0).toBuffer());
                    channel.pipeline().replace("decoder", "decoder", new Js5Decoder(fileStore));
                    return;
                }
                Response.GAME_UPDATED.send(channel);
                break;
            default:
                Response.BAD_SESSION_ID.send(channel);
                throw new IllegalArgumentException("Client " + channel.remoteAddress() + " sent invalid handshake opcode " + opcode);
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch (opcode) {
            case OPCODE: {
                return 20;
            }
        }
        return -3;
    }

}

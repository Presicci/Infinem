package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;

import java.util.Arrays;

public class ChatUpdate extends UpdateMask {

    private boolean shadow;

    private int color;

    private int type;

    private int effects;

    private int rankId;

    private String message;

    public void set(int color, int effects, int rankId, int type, String message) {
        this.color = color;
        this.effects = effects;
        this.rankId = rankId;
        this.type = type;
        this.message = message;
    }

    @Override
    public void reset() {
        message = null;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return message != null;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        out.addByte(color);
        out.addByte(effects);
        out.addByteNeg(0);
        out.addByteAdd(type);
        byte[] stringArray = Huffman.compressString(message);
        out.addByteSub(stringArray.length);
        System.out.println(Arrays.toString(stringArray));
        out.addBytesReverse(stringArray, 0, stringArray.length);
        //pattern
    }

    @Override
    public int get(boolean playerUpdate) {
        return 8192;
    }

}
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

    private byte[] pattern;

    private String message;

    public void set(int color, int effects, int rankId, int type, String message, byte[] pattern) {
        this.color = color;
        this.effects = effects;
        this.rankId = rankId;
        this.type = type;
        this.message = message;
        this.pattern = pattern;
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
        if (pattern != null) out.addBytes(pattern);
        //pattern
    }

    @Override
    public int get(boolean playerUpdate) {
        return 8192;
    }

    private int[] getPatternColors() {
        if (pattern.length < 1 || pattern.length > 8) return null;
        int[] colors = new int[pattern.length];
        for (int index = 0; index < pattern.length; index++) {
            if (pattern[index] < 0 || pattern[index] > COLOR_CODES.length) return null;
            colors[index] = COLOR_CODES[pattern[index]];
        }
        return colors;
    }

    private static final int[] COLOR_CODES = new int[]{
            0xffffff,
            0xe40303,
            0xff8c00,
            0xffed00,
            0x8026,
            0x24408e,
            0x732982,
            0xff218c,
            0xb55690,
            0x5049cc,
            0xa3a3a3,
            0xd52d00,
            0xef7627,
            0xfcf434,
            0x78d70,
            0x21b1ff,
            0x9b4f96,
            0xffafc7,
            0xd162a4,
            0x7bade3,
            0xff9a56,
            0x26ceaa,
            0x73d7ee,
            0x9c59d1,
            0x98e8c1,
            0xb57edc,
            0x2c2c2c,
            0x940202,
            0x613915,
            0xd0c100,
            0x4a8123,
            0x38a8,
            0x800080,
            0xd60270,
            0xa30262,
            0x3d1a78
    };
}
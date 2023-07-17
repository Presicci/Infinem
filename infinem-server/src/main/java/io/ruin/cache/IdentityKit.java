package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class IdentityKit {

    /**
     * Storage
     */

    public static IdentityKit[] LOADED;

    private static final List<Integer> HAIRSTYLES = new ArrayList<>(25);
    private static final List<Integer> BEARDSTYLES = new ArrayList<>(16);
    private static final List<Integer> BODYSTYLES = new ArrayList<>(15);
    private static final List<Integer> ARMSTYLES = new ArrayList<>(13);
    private static final List<Integer> LEGSSTYLES = new ArrayList<>(12);

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new IdentityKit[index.getLastFileId(3) + 1];
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(3, id);
            IdentityKit def = new IdentityKit();
            def.id = id;
            def.decode(new InBuffer(data));
            LOADED[id] = def;
            if (def.selectable) {
                int part = def.bodyPartId;
                if (part == 0) {
                    HAIRSTYLES.add(id);
                } else if (part == 1) {
                    BEARDSTYLES.add(id);
                } else if (part == 2) {
                    BODYSTYLES.add(id);
                } else if (part == 3) {
                    ARMSTYLES.add(id);
                } else if (part == 5) {
                    LEGSSTYLES.add(id);
                }
            }
        }
    }

    public static IdentityKit get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Default
     */

    public int id;
    public int bodyPartId = -1;
    public boolean selectable = false;
    int[] models;
    short[] recolorToFind;
    short[] recolorToReplace;
    short[] textureToFind;
    short[] textureToReplace;
    int[] chatheadModels = {-1, -1, -1, -1, -1};

    void decode(InBuffer stream) {
        for(; ; ) {
            int i = stream.readUnsignedByte();
            if(i == 0)
                break;
            method707(stream, i);
        }
    }

    void method707(InBuffer stream, int i) {
        if(i == 1)
            bodyPartId = stream.readUnsignedByte();
        else if(i == 2) {
            int i_7_ = stream.readUnsignedByte();
            models = new int[i_7_];
            for(int i_8_ = 0; i_8_ < i_7_; i_8_++)
                models[i_8_] = stream.readUnsignedShort();
        } else if(i == 3)
            selectable = false;
        else if(i == 40) {
            int i_9_ = stream.readUnsignedByte();
            recolorToFind = new short[i_9_];
            recolorToReplace = new short[i_9_];
            for(int i_10_ = 0; i_10_ < i_9_; i_10_++) {
                recolorToFind[i_10_] = (short) stream.readUnsignedShort();
                recolorToReplace[i_10_] = (short) stream.readUnsignedShort();
            }
        } else if(i == 41) {
            int i_11_ = stream.readUnsignedByte();
            textureToFind = new short[i_11_];
            textureToReplace = new short[i_11_];
            for(int i_12_ = 0; i_12_ < i_11_; i_12_++) {
                textureToFind[i_12_] = (short) stream.readUnsignedShort();
                textureToReplace[i_12_] = (short) stream.readUnsignedShort();
            }
        } else if(i >= 60 && i < 70)
            chatheadModels[i - 60] = stream.readUnsignedShort();
    }

    public static int getHairstyle(final int index) {
        if (index < 0 || index >= HAIRSTYLES.size()) {
            return 0;
        }
        return HAIRSTYLES.get(index);
    }

    public static int getBeardstyle(final int index) {
        if (index < 0 || index >= BEARDSTYLES.size()) {
            return 0;
        }
        return BEARDSTYLES.get(index);
    }

    public static int getBodystyle(final int index) {
        System.out.println(BODYSTYLES.size() + " / " + index);
        if (index < 0 || index >= BODYSTYLES.size()) {
            return 0;
        }
        return BODYSTYLES.get(index);
    }

    public static int getLegsstyle(final int index) {
        System.out.println(LEGSSTYLES.size() + " / " + index);
        if (index < 0 || index >= LEGSSTYLES.size()) {
            return 0;
        }
        return LEGSSTYLES.get(index);
    }

    public static int getArmstyle(final int index) {
        System.out.println(ARMSTYLES.size() + " / " + index);
        if (index < 0 || index >= ARMSTYLES.size()) {
            return 0;
        }
        return ARMSTYLES.get(index);
    }
}

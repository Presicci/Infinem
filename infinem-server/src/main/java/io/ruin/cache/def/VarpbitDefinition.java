package io.ruin.cache.def;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

import java.util.ArrayList;
import java.util.HashMap;

public class VarpbitDefinition {

    public static VarpbitDefinition[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new VarpbitDefinition[index.getLastFileId(14) + 1];
        HashMap<Integer, ArrayList<VarpbitDefinition>> varpMap = new HashMap<>();
        int highestVarpId = -1;
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(14, id);
            VarpbitDefinition bit = new VarpbitDefinition(id);
            bit.decode(new InBuffer(data));

            ArrayList<VarpbitDefinition> bits = varpMap.get(bit.varpId);
            if(bits == null)
                bits = new ArrayList<>();
            bits.add(bit);
            varpMap.put(bit.varpId, bits);

            if(bit.varpId > highestVarpId)
                highestVarpId = bit.varpId;

            LOADED[id] = bit;
        }
        VarpDefinition.LOADED = new VarpDefinition[highestVarpId + 1];
        varpMap.forEach((varpId, bits) -> new VarpDefinition(varpId, bits.toArray(new VarpbitDefinition[bits.size()])));
    }

    public static VarpbitDefinition get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Separator
     */

    public int id;

    public int varpId;

    public int leastSigBit;

    public int mostSigBit;

    public VarpbitDefinition(int id) {
        this.id = id;
    }

    private void decode(InBuffer in) {
        int opcode;
        while((opcode = in.readUnsignedByte()) != 0)
            decode(in, opcode);
    }

    private void decode(InBuffer in, int i) {
        if(i == 1) {
            varpId = in.readUnsignedShort();
            leastSigBit = in.readUnsignedByte();
            mostSigBit = in.readUnsignedByte();
        }
    }

}

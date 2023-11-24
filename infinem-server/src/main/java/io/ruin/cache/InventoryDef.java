package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.cache.type.ArchiveType;
import io.ruin.cache.type.GroupType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/23/2023
 */
public class InventoryDef {

    public static InventoryDef[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(ArchiveType.CONFIGS.getId());
        LOADED = new InventoryDef[index.getLastFileId(GroupType.INV.getId()) + 1];
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(GroupType.INV.getId(), id);
            InventoryDef def = new InventoryDef();
            def.id = id;
            def.decode(new InBuffer(data));
            LOADED[id] = def;
        }
    }

    public int id, size;

    void decode(InBuffer buffer) {
        for(; ; ) {
            int opcode = buffer.readUnsignedByte();
            if(opcode == 0)
                break;
            decode(buffer, opcode);
        }
    }

    void decode(InBuffer buffer, int opcode) {
        if (opcode == 2) {
            size = buffer.readUnsignedShort();
        }
    }

    public static InventoryDef get(int id) {
        if (id < 0 || id >= LOADED.length) {
            return null;
        }
        return LOADED[id];
    }

    public static int getSize(int id) {
        InventoryDef def = get(id);
        return def == null ? 0 : def.size;
    }

}

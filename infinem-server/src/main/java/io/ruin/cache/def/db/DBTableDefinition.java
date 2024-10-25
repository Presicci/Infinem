package io.ruin.cache.def.db;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.cache.CacheDefinition;
import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.type.ArchiveType;
import io.ruin.cache.type.GroupType;
import io.ruin.cache.type.ScriptVarType;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/19/2024
 */
public class DBTableDefinition implements CacheDefinition {

    public static DBTableDefinition[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(ArchiveType.CONFIGS.getId());
        LOADED = new DBTableDefinition[index.getLastFileId(GroupType.DBTABLE.getId()) + 1];
        for (int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(GroupType.DBTABLE.getId(), id);
            DBTableDefinition def = new DBTableDefinition(id);
            if (data != null)
                def.decode(new InBuffer(data));
            LOADED[id] = def;
            //System.out.println("[DBTable]" + id + ": " + Arrays.deepToString(def.types) + " --- " + Arrays.deepToString(def.defaultColumnValues));
        }
    }

    public static DBTableDefinition get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    public int id;
    public ScriptVarType[][] types;
    public Object[] defaultColumnValues;

    public DBTableDefinition(int id) {
        this.id = id;
    }

    public void decode(InBuffer in) {
        for (; ; ) {
            int opcode = in.readUnsignedByte();
            if (opcode == 0)
                break;
            decode(in, opcode);
        }
    }

    private void decode(InBuffer in, int opcode) {
        if (opcode == 1) {
            int numColumns = in.readUnsignedByte();
            ScriptVarType[][] types = new ScriptVarType[numColumns][];
            Object[][] defaultValues = null;

            for (int setting = in.readUnsignedByte(); setting != 255; setting = in.readUnsignedByte()) {
                int columnId = setting & 0x7F;
                boolean hasDefault = (setting & 0x80) != 0;
                ScriptVarType[] columnTypes = new ScriptVarType[in.readUnsignedByte()];
                for (int i = 0; i < columnTypes.length; i++) {
                    columnTypes[i] = ScriptVarType.forId(in.readUnsignedShortSmart());
                }
                types[columnId] = columnTypes;

                if (hasDefault) {
                    if (defaultValues == null) {
                        defaultValues = new Object[types.length][];
                    }

                    defaultValues[columnId] = decodeColumnFields(in, columnTypes);
                }
            }

            this.types = types;
            this.defaultColumnValues = defaultValues;
        } else {
            System.err.println("Unrecognized dbtable opcode " + opcode);
        }
    }

    public static Object[] decodeColumnFields(InBuffer in, ScriptVarType[] types) {
        int fieldCount = in.readUnsignedShortSmart();
        Object[] values = new Object[fieldCount * types.length];

        for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++) {
            for (int typeIndex = 0; typeIndex < types.length; typeIndex++) {
                ScriptVarType type = types[typeIndex];
                int valuesIndex = fieldIndex * types.length + typeIndex;

                if (type == ScriptVarType.STRING) {
                    values[valuesIndex] = in.readString();
                } else {
                    values[valuesIndex] = in.readInt();
                }
            }
        }

        return values;
    }
}

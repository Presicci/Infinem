package io.ruin.cache.def.db;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.cache.type.ArchiveType;
import io.ruin.cache.type.GroupType;
import io.ruin.cache.type.ScriptVarType;
import io.ruin.model.map.object.GameObject;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/19/2024
 */
public class DBRowDefinition {

    public static DBRowDefinition[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(ArchiveType.CONFIGS.getId());
        LOADED = new DBRowDefinition[index.getLastFileId(GroupType.DBROW.getId()) + 1];
        for (int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(GroupType.DBROW.getId(), id);
            DBRowDefinition def = new DBRowDefinition(id);
            if (data != null)
                def.decode(new InBuffer(data));
            LOADED[id] = def;
            //System.out.println("[DBRow]" + def);
        }
    }

    public static DBRowDefinition get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    public int id;
    public int tableId;
    public ScriptVarType[][] columnTypes;
    public Object[][] columnValues;

    public DBRowDefinition(int id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "DBRow{" + "id=" + id + ", tableId=" + tableId + ", types=" + Arrays.deepToString(columnTypes) + ", values=" + Arrays.deepToString(columnValues) + "}";
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
        switch (opcode) {
            case 3:
                int numColumns = in.readUnsignedByte();
                ScriptVarType[][] types = new ScriptVarType[numColumns][];
                Object[][] columnValues = new Object[numColumns][];

                for (int columnId = in.readUnsignedByte(); columnId != 255; columnId = in.readUnsignedByte()) {
                    ScriptVarType[] columnTypes = new ScriptVarType[in.readUnsignedByte()];
                    for (int i = 0; i < columnTypes.length; i++) {
                        columnTypes[i] = ScriptVarType.forId(in.readUnsignedShortSmart());
                    }
                    types[columnId] = columnTypes;
                    columnValues[columnId] = DBTableDefinition.decodeColumnFields(in, columnTypes);
                }

                this.columnTypes = types;
                this.columnValues = columnValues;
                break;
            case 4:
                tableId = in.readVarInt2();
                break;
            default:
                System.err.println("Unrecognized dbrow opcode " + opcode);
                break;
        }
    }

    public Object getColumnValue(int column) {
        return columnValues[column][0];
    }

    public Object[] getColumnValues(int column) {
        return columnValues[column];
    }
}

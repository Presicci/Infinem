package io.ruin.cache.def.db;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.cache.type.ArchiveType;
import io.ruin.cache.type.BaseVarType;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/19/2024
 */
public class DBTableIndexDefinition {

    public static final Map<Integer, DBTableIndexDefinition> indexes = new HashMap<>();

    public static void load() {
        IndexFile index = Server.fileStore.get(ArchiveType.DBTABLEINDEX.getId());
        for (int archiveId : index.table.archiveIds) {
            int archiveSize = index.getLastFileId(archiveId) + 1;
            for (int fileId = 0; fileId < archiveSize; fileId++) {
                byte[] data = index.getFile(archiveId, fileId);
                DBTableIndexDefinition row = new DBTableIndexDefinition(archiveId, fileId - 1);
                if (data != null)
                    row.decode(new InBuffer(data));
                indexes.put(archiveId << 16 | fileId, row);
            }
        }
    }

    public int tableId;
    public int columnId;
    public BaseVarType[] tupleTypes;
    public List<Map<Object, List<Integer>>> tupleIndexes;

    public DBTableIndexDefinition(int tableId, int columnId) {
        this.tableId = tableId;
        this.columnId = columnId;
    }

    private void decode(InBuffer in) {
        int tupleSize = in.readVarInt2();
        BaseVarType[] tupleTypes = new BaseVarType[tupleSize];
        List<Map<Object, List<Integer>>> tupleIndexes = new ArrayList<>(tupleSize);

        for (int i = 0; i < tupleSize; i++) {
            tupleTypes[i] = BaseVarType.forId(in.readUnsignedByte());

            int valueCount = in.readVarInt2();
            Map<Object, List<Integer>> valueToRows = new HashMap<>(valueCount);

            while (valueCount-- > 0) {
                Object value = decodeValue(tupleTypes[i], in);

                int rowCount = in.readVarInt2();
                List<Integer> rowIds = new ArrayList<>(rowCount);

                while (rowCount-- > 0) {
                    rowIds.add(in.readVarInt2());
                }

                valueToRows.put(value, rowIds);
            }

            tupleIndexes.add(i, valueToRows);
        }

        this.tupleTypes = tupleTypes;
        this.tupleIndexes = tupleIndexes;
    }

    private static Object decodeValue(BaseVarType baseType, InBuffer in) {
        switch (baseType) {
            case INTEGER:
                return in.readInt();
            case LONG:
                return in.readLong();
            case STRING:
                return in.readString();
        }
        return null;
    }
}

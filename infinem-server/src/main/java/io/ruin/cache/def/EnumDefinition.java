package io.ruin.cache.def;

import com.google.common.collect.ImmutableMap;
import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.cache.CacheDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumDefinition implements CacheDefinition {

    public static final ImmutableMap<Character, String> TYPE_MAP = ImmutableMap.<Character, String>builder().put('A', "seq").put('i', "int").put('1', "boolean").put('s', "string").put('v', "inv").put('z', "char").put('O', "namedobj").put('M', "midi").put('K', "idkit").put('o', "obj").put('n', "npc").put('c', "coordgrid").put('S', "stat").put('m', "model").put('d', "graphic").put('J', "struct").put('f', "fontmetrics").put('I', "component").put('k', "chatchar").put('g', "enum").put('l', "location").build();
    public static final ImmutableMap<String, Character> REVERSE_TYPE_MAP = ImmutableMap.<String, Character>builder().putAll(TYPE_MAP.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))).build();

    public static EnumDefinition[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new EnumDefinition[index.getLastFileId(8) + 1];
        for(int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(8, id);
            EnumDefinition def = new EnumDefinition(id);
            if(data != null)
                def.decode(new InBuffer(data));
            LOADED[id] = def;
        }
    }

    public static EnumDefinition get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    public int id;
    public int size = 0;
    public char keyType;
    public char valType;
    public String defaultString = "null";
    public int defaultInt;
    @Getter @Setter
    private Map<Integer, Object> values = new HashMap<>();

    public EnumDefinition(int id) {
        this.id = id;
    }

    public void decode(InBuffer in) {
        for(; ; ) {
            int opcode = in.readUnsignedByte();
            if(opcode == 0)
                break;
            decode(in, opcode);
        }
    }

    void decode(InBuffer in, int opcode) {
        if(opcode == 1)
            keyType = (char) in.readUnsignedByte();
        else if(opcode == 2)
            valType = (char) in.readUnsignedByte();
        else if(opcode == 3)
            defaultString = in.readString();
        else if(opcode == 4)
            defaultInt = in.readInt();
        else if(opcode == 5) {
            size = in.readUnsignedShort();
            for (int index = 0; index < size; ++index) {
                int key = in.readInt();
                String value = in.readString();
                values.put(key, value);
            }
            if (size != values.size()) {
                size = values.size();
            }
        } else if(opcode == 6) {
            size = in.readUnsignedShort();
            for (int index = 0; index < size; ++index) {
                int key = in.readInt();
                int value = in.readInt();
                values.put(key, value);
            }
            if (size != values.size()) {
                size = values.size();
            }
        }
    }

    public void setKeyType(final String keyType) {
        final Character type = REVERSE_TYPE_MAP.get(keyType);
        if (type == null) {
            throw new RuntimeException("Unable to find a matching type for " + keyType + ".");
        }
        this.keyType = type;
    }

    public void setValueType(final String valueType) {
        final Character type = REVERSE_TYPE_MAP.get(valueType);
        if (type == null) {
            throw new RuntimeException("Unable to find a matching type for " + valueType + ".");
        }
        this.valType = type;
    }

    public int[] getIntValuesArray() {
        if (valType == 's') return null;
        int[] intArray = new int[size];
        Object[] objValues = values.values().toArray(new Object[0]);
        for (int index = 0; index < size; index++) {
            intArray[index] = (Integer) objValues[index];
        }
        return intArray;
    }

    public String[] getStringValuesArray() {
        if (valType != 's') return null;
        String[] strArray = new String[size];
        Object[] objValues = values.values().toArray(new Object[0]);
        for (int index = 0; index < size; index++) {
            strArray[index] = (String) objValues[index];
        }
        return strArray;
    }

    public Map<Integer, String> getValuesAsStrings() {
        Map<Integer, String> map = new HashMap<>(size);
        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                throw new RuntimeException("Enum values not of string type");
            }
            map.put(entry.getKey(), (String) entry.getValue());
        }
        return map;
    }

    public Map<Integer, Integer> getValuesAsInts() {
        Map<Integer, Integer> map = new HashMap<>(size);
        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            if (!(entry.getValue() instanceof Integer)) {
                throw new RuntimeException("Enum values not of int type");
            }
            map.put(entry.getKey(), (Integer) entry.getValue());
        }
        return map;
    }

    public Map<Integer, Integer> getValuesAsKeysInts() {
        Map<Integer, Integer> map = new HashMap<>(size);
        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            if (!(entry.getValue() instanceof Integer)) {
                throw new RuntimeException("Enum values not of int type");
            }
            map.put((Integer) entry.getValue(), entry.getKey());
        }
        return map;
    }

    public Map<String, Integer> getValuesAsKeysStrings() {
        Map<String, Integer> map = new HashMap<>(size);
        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                throw new RuntimeException("Enum values not of string type");
            }
            map.put((String) entry.getValue(), entry.getKey());
        }
        return map;
    }
}
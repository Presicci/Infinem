package io.ruin.cache.type;

import lombok.AllArgsConstructor;

/**
 * From RuneLite
 */
@AllArgsConstructor
public enum BaseVarType {
    INTEGER(0, Integer.class),
    LONG(1, Long.class),
    STRING(2, String.class);

    private static final BaseVarType[] VALUES = values();

    public static BaseVarType forId(int id) {
        for (BaseVarType type : VALUES) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

    /**
     * The id of the type when being encoded or decoded.
     */
    private final int id;

    /**
     * The class the base type represents.
     */
    private final Class<?> clazz;
}

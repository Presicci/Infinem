package io.ruin.cache.def;

import io.ruin.cache.Varpbit;

public class VarpDefinition {

    public static VarpDefinition[] LOADED;

    public static VarpDefinition get(int id) {
        if(id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Separator
     */

    public final int id;

    public final Varpbit[] bits;

    public VarpDefinition(int id, Varpbit[] bits) {
        this.id = id;
        this.bits = bits;
        LOADED[id] = this;
    }

}

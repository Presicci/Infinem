package io.ruin.cache.def;

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

    public final VarpbitDefinition[] bits;

    public VarpDefinition(int id, VarpbitDefinition[] bits) {
        this.id = id;
        this.bits = bits;
        LOADED[id] = this;
    }

}

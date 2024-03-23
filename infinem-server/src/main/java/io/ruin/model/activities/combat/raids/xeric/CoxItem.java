package io.ruin.model.activities.combat.raids.xeric;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.ItemDef;

public class CoxItem {

    static {
        EnumDefinition.get(1666).ints().forEach((k, v) -> {
            mark(v);
        });
    }

    private static void mark(int id) {
        ItemDef def = ItemDef.get(id);
        if (def != null)
            mark(def);
    }

    private static void mark(ItemDef def) {
        def.coxItem = true;
        def.tradeable = true;
    }

}

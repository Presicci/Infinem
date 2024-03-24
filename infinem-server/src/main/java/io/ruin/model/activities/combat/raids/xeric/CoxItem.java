package io.ruin.model.activities.combat.raids.xeric;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.ItemDefinition;

public class CoxItem {

    static {
        EnumDefinition.get(1666).getValuesAsInts().forEach((k, v) -> {
            mark(v);
        });
    }

    private static void mark(int id) {
        ItemDefinition def = ItemDefinition.get(id);
        if (def != null)
            mark(def);
    }

    private static void mark(ItemDefinition def) {
        def.coxItem = true;
        def.tradeable = true;
    }

}

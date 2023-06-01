package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewerResultCommon extends DropViewerResult {
    private final LootTable.CommonTables commonTable;

    public DropViewerResultCommon(LootTable.CommonTables commonTable, int chance) {
        this.commonTable = commonTable;
        this.chance = chance;
    }

    public String get() {
        String name = commonTable.name;
        String amount = "<col=F5DEB3>Shared table";
        String c = chance == 1 ? "Always" : ("1 / " + (chance == 0 ? "?" : chance));
        return name + "|" + amount + "|" + c;
    }

    public Item getItem() {
        return new Item(commonTable.itemId, 1);
    }
}

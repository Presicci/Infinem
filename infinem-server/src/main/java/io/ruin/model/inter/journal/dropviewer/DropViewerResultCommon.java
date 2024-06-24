package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootTable;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewerResultCommon extends DropViewerResult {
    @Getter private final LootTable.CommonTables commonTable;

    public DropViewerResultCommon(LootTable.CommonTables commonTable, int chance) {
        this.commonTable = commonTable;
        this.chance = chance;
    }

    public String get() {
        String name = commonTable.name;
        String amount = "<col=F5DEB3>This table is shared by many monsters.";
        String c = chance == 1 ? "Always" : ("1 / " + (chance == 0 ? "?" : chance));
        return name + "||" + c + "|" + amount;
    }

    public Item getItem() {
        return new Item(commonTable.itemId, 1);
    }
}

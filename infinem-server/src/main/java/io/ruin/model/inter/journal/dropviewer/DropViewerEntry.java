package io.ruin.model.inter.journal.dropviewer;

import io.ruin.cache.NPCDef;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewerEntry {
    public int id;
    public String name;
    public LootTable table;

    public DropViewerEntry(int npcId) {
        this.id = npcId;
        this.name = NPCDef.get(npcId).name.replaceAll("<col=\\w{6}>|</col>", "");
    }

    public DropViewerEntry(String name, LootTable table) {
        this.name = name;
        this.table = table;
    }

    public DropViewerEntry(String name, boolean preserveWeight, LootTable... tables) {
        LootTable finalTable = new LootTable();
        for (LootTable table : tables) {
            for (LootTable.ItemsTable iTable : table.tables) {
                finalTable.addTable(preserveWeight ? iTable.weight : 0, iTable.items);
            }
        }
        this.name = name;
        this.table = finalTable;
    }
}

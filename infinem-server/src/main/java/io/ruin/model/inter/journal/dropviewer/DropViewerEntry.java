package io.ruin.model.inter.journal.dropviewer;

import io.ruin.cache.NPCDef;
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
}

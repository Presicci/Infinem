package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Color;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/26/2024
 */
public class DropViewerEntryItem extends DropViewerEntry{

    public int chance;

    public DropViewerEntryItem(String name, LootTable table, int itemId) {
        super(name, table);
        this.chance = DropViewer.getDropChance(itemId, table);
    }

    public String getChanceString() {
        return Color.YELLOW.wrap(chance == 1 ? "Always" : ("1/" + chance + " - "));
    }
}

package io.ruin.model.inter.journal.dropviewer;

import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;

public class DropViewerResultDescription extends DropViewerResult {

    private final int item;
    private final String description;

    public DropViewerResultDescription(int item, String description, int chance) {
        this.item = item;
        this.description = description;
        this.chance = chance;
    }

    public String get() {
        String[] split = description.split("_");
        String name = split[0];
        String desc = split[1];
        String c = chance == 1 ? "Always" : ("1 / " + (chance == 0 ? "?" : chance));
        return name + "|" + desc + "|" + c;
    }

    public Item getItem() {
        return new Item(item, 1);
    }
}
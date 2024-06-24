package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

public class DropViewerResultDescription extends DropViewerResult {

    @Getter
    @AllArgsConstructor
    protected static class ItemDescription {
        private final int itemId;
        private final String description;
    }

    public static final HashMap<String, ItemDescription[]> descriptionDrops = new HashMap<String, ItemDescription[]>() {{
        put("alchemical hydra", new ItemDescription[] { new ItemDescription(22973, "Brimstone ring part_Drop order: <col=F5DEB3>Eye, Fang, Heart")});
        put("hydra", new ItemDescription[] { new ItemDescription(22973, "Brimstone ring part_Drop order: <col=F5DEB3>Eye, Fang, Heart")});
        put("abyssal sire (unsired)", new ItemDescription[] { new ItemDescription(Items.BLUDGEON_SPINE, "Bludgeon part_Drop order: <col=F5DEB3>Spine, Claw, Axon")});
    }};

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
        return name + "||" + c + "|" + desc;
    }

    public Item getItem() {
        return new Item(item, 1);
    }
}
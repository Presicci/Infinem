package io.ruin.model.skills.construction;

import io.ruin.model.item.Item;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/23/2024
 */
@AllArgsConstructor
public enum BuildableReturnItem {
    MYTHICAL_CAPE(Buildable.MOUNTED_MYTHICAL_CAPE, new Item(22114)),
    CAPE_OF_LEGENDS(Buildable.MOUNTED_LEGENDS_CAPE, new Item(1052)),
    AMULET_OF_GLORY(Buildable.MOUNTED_AMULET_OF_GLORY, new Item(1704)),
    ANTI_DRAGON_SHIELD(Buildable.MOUNTED_ANTI_DRAGON_SHIELD, new Item(1540));

    private final Buildable buildable;
    private final Item item;

    public static final Map<Buildable, Item> RETURNED_ITEMS = new HashMap<>();

    static {
        for (BuildableReturnItem buildable : values()) {
            RETURNED_ITEMS.put(buildable.buildable, buildable.item);
        }
    }
}

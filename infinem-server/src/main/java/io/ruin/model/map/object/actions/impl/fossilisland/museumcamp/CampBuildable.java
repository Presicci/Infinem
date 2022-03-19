package io.ruin.model.map.object.actions.impl.fossilisland.museumcamp;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
@Getter
public enum CampBuildable {
    BANK(31427, Config.CAMP_BANK_CHEST, 21, 250.0, 5, new Item(Items.OAK_PLANK, 2), new Item(Items.IRON_BAR, 1)),
    CLEANING_TABLE(31428, Config.CAMP_CLEANING_TABLE, 5, 100.0, 5, new Item(Items.PLANK, 5)),
    WELL(31429, Config.CAMP_WELL, 22, 500.0, 5, new Item(Items.OAK_PLANK, 2), new Item(Items.BUCKET, 1), new Item(Items.ROPE, 1)),
    COOKING_POT(31430, Config.CAMP_COOKING_POT, 24, 800.0, 0, new Item(Items.LOGS, 2), new Item(Items.TINDERBOX, 1), new Item(Items.IRON_BAR, 1), new Item(Items.SOFT_CLAY, 3)),
    SPINNING_WHEEL(31431, Config.CAMP_SPINNING_WHEEL, 28, 1000.0, 5, new Item(Items.OAK_PLANK, 4)),
    LOOM(31432, Config.CAMP_LOOM, 29, 1000.0, 5, new Item(Items.OAK_PLANK, 2), new Item(Items.ROPE, 1));

    private final int level, nails, objectId;
    private final double experience;
    private final Item[] items;
    private final Config config;

    CampBuildable(int objectId, Config config, int level, double experience, int nails, Item... items) {
        this.objectId = objectId;
        this.config = config;
        this.level = level;
        this.nails = nails;
        this.experience = experience;
        this.items = items;
    }
}

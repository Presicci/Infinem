package io.ruin.model.skills.smithing.blastfurnace;

import io.ruin.model.item.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/14/2024
 */
@Getter
@AllArgsConstructor
public enum BlastFurnaceOre {
    COAL(Items.COAL, 2932, Items.STEEL_BAR),
    TIN(Items.TIN_ORE, 2924, Items.BRONZE_BAR),
    COPPER(Items.COPPER_ORE, 2925, Items.BRONZE_BAR),
    IRON(Items.IRON_ORE, 2926, Items.IRON_BAR),
    SILVER(Items.SILVER_ORE, 2930, Items.SILVER_BAR),
    GOLD(Items.GOLD_ORE, 2931, Items.GOLD_BAR),
    MITHRIL(Items.MITHRIL_ORE, 2927, Items.MITHRIL_BAR),
    ADAMANTITE(Items.ADAMANTITE_ORE, 2928, Items.ADAMANTITE_BAR),
    RUNITE(Items.RUNITE_ORE, 2929, Items.RUNITE_BAR);

    private final int oreId, npcId, barId;

    protected boolean isPrimaryOre() {
        return this != TIN && this != COAL;
    }

    protected static BlastFurnaceOre getOreById(int oreId) {
        return BY_ID.getOrDefault(oreId, COAL);
    }

    private static final Map<Integer, BlastFurnaceOre> BY_ID = new HashMap<>();
    protected static final List<Integer> ORE_IDS = new ArrayList<>();
    protected static int[] ORE_IDS_ARRAY;

    static {
        for (BlastFurnaceOre ore : values()) {
            BY_ID.put(ore.oreId, ore);
            ORE_IDS.add(ore.oreId);
        }
        ORE_IDS_ARRAY = ORE_IDS.stream().mapToInt(i->i).toArray();
    }
}

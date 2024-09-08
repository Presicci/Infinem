package io.ruin.model.activities.combat.pestcontrol.rewards;

import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/5/2024
 */
@Getter
@AllArgsConstructor
public enum PCReward {
    HERB_PACK(37, "Herb Pack", 30, -1),
    MINERAL_PACK(41, "Mineral Pack", 15, -1),
    SEED_PACK(45, "Seed Pack", 15, -1),
    VOID_MACE(51, "Void Knight Mace", 250, 8841),
    VOID_TOP(55, "Void Knight Top", 250, 8839),
    VOID_ROBES(59, "Void Knight Robes", 250, 8840),
    VOID_GLOVES(63, "Void Knight Gloves", 150, 8842),
    MAGE_HELM(67, "Void Mage Helm", 200, 11663),
    RANGER_HELM(71, "Void Ranger Helm", 200, 11664),
    MELEE_HELM(75, "Void Melee Helm", 200, 11665),
    VOID_SEAL(79, "Void Knight Seal", 10, 11666);

    private final int child;
    private final String name;
    private final int cost;
    private final int itemId;

    public static final Map<Integer, PCReward> REWARDS = new HashMap<>();

    public static final LootTable HERB_PACK_LOOT = new LootTable().addTable(0,
            new LootItem(Items.GRIMY_HARRALANDER_NOTE, 1, 2, 0),
            new LootItem(Items.GRIMY_RANARR_WEED_NOTE, 1, 2, 0),
            new LootItem(Items.GRIMY_TOADFLAX_NOTE, 1, 2, 0),
            new LootItem(Items.GRIMY_IRIT_LEAF_NOTE, 1, 2, 0),
            new LootItem(Items.GRIMY_AVANTOE_NOTE, 1, 2, 0),
            new LootItem(Items.GRIMY_KWUARM_NOTE, 1, 2, 0)
    );

    public static final LootTable MINERAL_PACK_LOOT = new LootTable().addTable(0,
            new LootItem(Items.COAL_NOTE, 25, 0),
            new LootItem(Items.IRON_ORE_NOTE, 18, 0)
    );

    public static final LootTable SEED_PACK_LOOT = new LootTable().addTable(0,
            new LootItem(Items.POTATO_SEED, 14, 350),
            new LootItem(Items.SWEETCORN_SEED, 3, 200),
            new LootItem(Items.TOMATO_SEED, 6, 170),
            new LootItem(Items.LIMPWURT_SEED, 2, 160),
            new LootItem(Items.CABBAGE_SEED, 9, 160),
            new LootItem(Items.ONION_SEED, 11, 160),
            new LootItem(Items.STRAWBERRY_SEED, 2, 150),
            new LootItem(Items.WATERMELON_SEED, 2, 150),
            new LootItem(22879, 2, 100),
            new LootItem(Items.ACORN, 1, 50),
            new LootItem(Items.WILLOW_SEED, 1, 40),
            new LootItem(Items.RANARR_SEED, 1, 40),
            new LootItem(Items.MAPLE_SEED, 1, 20),
            new LootItem(Items.YEW_SEED, 1, 19),
            new LootItem(Items.SPIRIT_SEED, 1, 2),
            new LootItem(Items.MAGIC_SEED, 1, 2),
            new LootItem(22869, 1, 1),
            new LootItem(22871, 1, 1)
    );

    static {
        for (PCReward reward : values()) {
            REWARDS.put(reward.child, reward);
        }
    }
}

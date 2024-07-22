package io.ruin.model.item.loot;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.skills.slayer.Slayer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2024
 */
public enum ConditionalNPCLootTable {
    /**
     * Revenants
     */
    REVENANT_IMP_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7881
    ),
    REVENANT_IMP_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7881
    ),
    REVENANT_GOBLIN_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7931
    ),
    REVENANT_GOBLIN_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7931
    ),
    REVENANT_PYREFIEND_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7932
    ),
    REVENANT_PYREFIEND_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7932
    ),
    REVENANT_HOBGOBLIN_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7933
    ),
    REVENANT_HOBGOBLIN_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7933
    ),
    REVENANT_CYCLOPS_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7934
    ),
    REVENANT_CYCLOPS_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7934
    ),
    REVENANT_HELLHOUND_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7935
    ),
    REVENANT_HELLHOUND_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7935
    ),
    REVENANT_DEMON_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7936
    ),
    REVENANT_DEMON_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7936
    ),
    REVENANT_ORK_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7937
    ),
    REVENANT_ORK_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7937
    ),
    REVENANT_DARK_BEAST_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7938
    ),
    REVENANT_DARK_BEAST_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7938
    ),
    REVENANT_KNIGHT_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7939
    ),
    REVENANT_KNIGHT_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7939
    ),
    REVENANT_DRAGON_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            },
            7940
    ),
    REVENANT_DRAGON_SKULLED("Skulled",
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            },
            7940
    ),

    /**
     * Giants
     */
    HILL_GIANT_WILDERNESS("Wilderness",
            (player, npc) -> player.wildernessLevel > 0,
            table -> table.modifyItemWeight(Items.GIANT_KEY, 2D),
            2098, 2099, 2100, 2101, 2102, 2103
    ),
    MOSS_GIANTS_WILDERNESS("Wilderness",
            (player, npc) -> player.wildernessLevel > 0,
            table -> table.modifyItemWeight(22374, 2.5D),
            2090, 2091, 2092, 2093, 3851, 3852, 7262, 8736
    ),
    MOSS_GIANTS_TASK("On-task",
            Slayer::isTask,
            table -> table.modifyItemWeight(22374, 2.5D),
            2090, 2091, 2092, 2093, 3851, 3852, 7262, 8736
    ),
    /**
     * Elves
     */
    ELF_WARRIOR_TASK("On-task",
            Slayer::isTask,
            table -> table.modifyItemWeight(4207, 2.5D),
            3429, 8759, 8877, 8879, 8881, 8882, 8883, 8884,             // Iorwerth warriors
            3428, 8760, 8878, 8880, 8885, 8886, 8923, 8936, 8937, 8953, 8954,   // Iorwerth archers
            5293, 5294,                                                         // Elf warriors
            5295, 5296                                                          // Elf archers
    ),
    /**
     * Misc
     */
    WYRMS_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("Pre-roll", 5D);
            },
            8610, 8611
    ),
    HYDRA_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("Pre-roll", 5D);
            },
            8609
    ),
    DRAKE_TASK("On-task",
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("Pre-roll", 5D);
            },
            8612,8613
    ),
    BASILISK_KNIGHT("On-task",
            Slayer::isTask,
            table -> {
                table.modifyItemWeight(24268, 5D);
            },
            9293
    ),
    ;

    @Getter private final String dropConditionName;
    @Getter private final int[] npcIds;
    @Getter private final BiPredicate<Player, NPC> condition;
    private final Consumer<LootTable> modifiers;

    ConditionalNPCLootTable(String dropConditionName, BiPredicate<Player, NPC> condition, Consumer<LootTable> modifiers, int... npcIds) {
        this.dropConditionName = dropConditionName;
        this.npcIds = npcIds;
        this.condition = condition;
        this.modifiers = modifiers;
    }

    public void testAndApply(Player player, NPC npc, LootTable lootTable) {
        if (condition.test(player, npc)) {
            modifiers.accept(lootTable);
        }
    }

    public void modifyTable(LootTable lootTable) {
        modifiers.accept(lootTable);
    }

    private void addLoadedTable(int npcId) {
        if (!LOADED_TABLES.containsKey(npcId)) {
            List<ConditionalNPCLootTable> list = new ArrayList<>();
            list.add(this);
            LOADED_TABLES.put(npcId, list);
        } else {
            LOADED_TABLES.get(npcId).add(this);
        }
    }

    public static LootTable testAndApplyAllModifications(Player player, NPC npc) {
        LootTable baseTable = NPCDefinition.get(npc.getId()).lootTable;
        if (!LOADED_TABLES.containsKey(npc.getId())) return baseTable;
        LootTable newTable = baseTable.copy();
        for (ConditionalNPCLootTable table : LOADED_TABLES.get(npc.getId())) {
            if (table.condition.test(player, npc)) {
                table.modifiers.accept(newTable);
            }
        }
        return newTable;
    }

    public static final HashMap<Integer, List<ConditionalNPCLootTable>> LOADED_TABLES = new HashMap<>();

    static {
        for (ConditionalNPCLootTable table : values()) {
            for (int npcId : table.npcIds) {
                if (npcId > 0) {
                    table.addLoadedTable(npcId);
                }
            }
        }
    }
}

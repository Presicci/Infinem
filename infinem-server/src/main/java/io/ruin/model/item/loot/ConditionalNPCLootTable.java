package io.ruin.model.item.loot;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.dropviewer.DropViewerCustomEntries;
import io.ruin.model.inter.journal.dropviewer.DropViewerEntry;
import io.ruin.model.item.Items;
import io.ruin.model.skills.slayer.Slayer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2024
 */
public enum ConditionalNPCLootTable {
    /**
     * Revenants
     */
    REVENANT_IMP_SKULLED_TASK("Revenant imp (skulled task)", 7881,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_IMP_TASK("Revenant imp (on-task)", 7881,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_IMP_SKULLED("Revenant imp (skulled)", 7881,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_GOBLIN_SKULLED_TASK("Revenant goblin (skulled task)", 7931,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_GOBLIN_TASK("Revenant goblin (on-task)", 7931,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_GOBLIN_SKULLED("Revenant goblin (skulled)", 7931,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_PYREFIEND_SKULLED_TASK("Revenant pyrefiend (skulled task)", 7932,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_PYREFIEND_TASK("Revenant pyrefiend (on-task)", 7932,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_PYREFIEND_SKULLED("Revenant pyrefiend (skulled)", 7932,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_HOBGOBLIN_SKULLED_TASK("Revenant hobgoblin (skulled task)", 7933,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_HOBGOBLIN_TASK("Revenant hobgoblin (on-task)", 7933,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_HOBGOBLIN_SKULLED("Revenant hobgoblin (skulled)", 7933,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_CYCLOPS_SKULLED_TASK("Revenant cyclops (skulled task)", 7934,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_CYCLOPS_TASK("Revenant cyclops (on-task)", 7934,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_CYCLOPS_SKULLED("Revenant cyclops (skulled)", 7934,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_HELLHOUND_SKULLED_TASK("Revenant hellhound (skulled task)", 7935,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_HELLHOUND_TASK("Revenant hellhound (on-task)", 7935,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_HELLHOUND_SKULLED("Revenant hellhound (skulled)", 7935,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_DEMON_SKULLED_TASK("Revenant demon (skulled task)", 7936,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_DEMON_TASK("Revenant demon (on-task)", 7936,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_DEMON_SKULLED("Revenant demon (skulled)", 7936,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_ORK_SKULLED_TASK("Revenant ork (skulled task)", 7937,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_ORK_TASK("Revenant ork (on-task)", 7937,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_ORK_SKULLED("Revenant ork (skulled)", 7937,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_DARK_BEAST_SKULLED_TASK("Revenant dark beast (skulled task)", 7938,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_DARK_BEAST_TASK("Revenant dark beast (on-task)", 7938,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_DARK_BEAST_SKULLED("Revenant dark beast (skulled)", 7938,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_KNIGHT_SKULLED_TASK("Revenant knight (skulled task)", 7939,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_KNIGHT_TASK("Revenant knight (on-task)", 7939,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_KNIGHT_SKULLED("Revenant knight (skulled)", 7939,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_DRAGON_SKULLED_TASK("Revenant dragon (skulled task)", 7940,
            (player, npc) -> player.getCombat().isSkulled() && Slayer.isTask(player, npc),
            table -> {
                table.modifyTableWeight("unique", 9.4D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    REVENANT_DRAGON_TASK("Revenant dragon (on-task)", 7940,
            Slayer::isTask,
            table -> {
                table.modifyTableWeight("unique", 4.7D);
            }
    ),
    REVENANT_DRAGON_SKULLED("Revenant dragon (skulled)", 7940,
            (player, npc) -> player.getCombat().isSkulled(),
            table -> {
                table.modifyTableWeight("unique", 2D);
                table.modifyTableWeight("Ancient statuettes", 2D);
                table.modifyItemWeight(Items.YEW_SEED, 2D);
                table.modifyItemWeight(Items.MAGIC_SEED, 2D);
                table.removeItem(Items.DRAGON_MED_HELM);
            }
    ),
    ;

    private final String dropTableName;
    private final int npcId;
    @Getter private final BiPredicate<Player, NPC> condition;
    @Getter private final LootTable newTable;

    ConditionalNPCLootTable(String dropTableName, int npcId, BiPredicate<Player, NPC> condition, Consumer<LootTable> modifiers) {
        this.dropTableName = dropTableName;
        this.npcId = npcId;
        this.condition = condition;
        LootTable oldTable = NPCDefinition.get(npcId).lootTable;
        newTable = oldTable.copy();
        modifiers.accept(newTable);
    }

    public static final HashMap<Integer, List<ConditionalNPCLootTable>> LOADED_TABLES = new HashMap<>();

    static {
        for (ConditionalNPCLootTable table : values()) {
            DropViewerCustomEntries.ENTRIES.add(new DropViewerEntry(table.dropTableName, table.newTable));
            if (!LOADED_TABLES.containsKey(table.npcId)) {
                List<ConditionalNPCLootTable> list = new ArrayList<>();
                list.add(table);
                LOADED_TABLES.put(table.npcId, list);
            } else {
                LOADED_TABLES.get(table.npcId).add(table);
            }
        }
    }
}

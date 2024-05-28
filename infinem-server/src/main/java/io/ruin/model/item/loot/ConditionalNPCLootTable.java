package io.ruin.model.item.loot;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.dropviewer.DropViewerCustomEntries;
import io.ruin.model.inter.journal.dropviewer.DropViewerEntry;
import io.ruin.model.item.Items;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2024
 */
public enum ConditionalNPCLootTable {
    REVENANT_GOBLIN("Revenant goblin (skulled)", 7931,
            player -> player.getCombat().isSkulled(),
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
    @Getter private final Predicate<Player> condition;
    @Getter private final LootTable newTable;

    ConditionalNPCLootTable(String dropTableName, int npcId, Predicate<Player> condition, Consumer<LootTable> modifiers) {
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

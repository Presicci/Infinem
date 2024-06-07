package io.ruin.data.impl.npcs;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.data.DataFile;
import io.ruin.model.item.loot.LootTable;
import java.util.*;

public class npc_drops extends DataFile {

    @Override
    public String path() {
        return "npcs/drops/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<DropTable> tables = JsonUtils.fromJson(json, List.class, DropTable.class);
        Map<Integer, LootTable> loaded = new HashMap<>();
        for(DropTable t : tables) {
            t.calculateWeight();
            //t.checkForTertiaryWeight(Arrays.toString(t.ids));
            if(t.guaranteed == null && t.totalWeight == 0) {
                //System.err.println("Drop table " + fileName + " has no drops.");
                //ServerWrapper.logWarning("Drop table " + fileName + " has no drops.");
                continue;
            }
            for (int id : t.ids) {
                LootTable loadedTable = loaded.get(id);
                loaded.put(id, loadedTable == null ? t : loadedTable.combine(t));
            }
        }
        loaded.forEach((id, table) -> {
            if (NPCDefinition.get(id) == null) {
                System.err.println("[npc_drops] NPC with ID: " + id + " has invalid cache def.");
                return;
            }
            NPCDefinition.get(id).lootTable = table;
        });
        return tables;
    }

    private static class DropTable extends LootTable {
        @Expose public Integer[] ids;
    }
}
package io.ruin.model.item.containers.collectionlog;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.StructDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import lombok.Getter;

import java.util.*;


@Getter
public enum CollectionLogCategory {
    BOSS(471, 48, new int[]{10, 11, 12, 29}, 40697866, 40697867, 40697868, 40697869),
    RAIDS(472, 3, new int[]{14, 15, 16}, 40697870, 40697871, 40697872, 40697878),
    CLUES(473, 9, new int[]{22, 30, 31}, 40697879, 40697887, 40697888, 40697880),
    MINIGAMES(474, 19, new int[]{25, 26, 35}, 40697881, 40697882, 40697891, 40697883),
    OTHER(475, 25, new int[]{27, 32, 33}, 40697884, 40697889, 40697890, 40697885);

    @Getter private final int categoryStruct;
    private final int count;
    private final int[] childIds;
    @Getter private final int[] params;
    private final Map<Integer, int[]> items = new HashMap<>();
    private final List<Integer> subcategories = new ArrayList<>();
    private final List<Integer> enums = new ArrayList<>();
    private List<CollectionLogEntry> entries;

    CollectionLogCategory(int categoryStruct, int count, int[] childIds, int...params) {
        this.categoryStruct = categoryStruct;
        this.count = count;
        this.childIds = childIds;
        this.params = params;
    }

    public void sendAccessMasks(Player player) {
        for (int childId : childIds) {
            player.getPacketSender().sendAccessMask(Interface.COLLECTION_LOG, childId, 0, count, 2);
        }
        player.getPacketSender().sendAccessMask(Interface.COLLECTION_LOG, 77, 0, 90, 2);
    }

    public void sendItems(Player player, int slot) {
        EnumDefinition genderEnum = EnumDefinition.get(2108);
        EnumDefinition transformEnum = EnumDefinition.get(3721);
        assert genderEnum != null;
        assert transformEnum != null;
        Map<Integer, Integer> femaleIds = genderEnum.getValuesAsInts();
        Map<Integer, Integer> transformIds = transformEnum.getValuesAsInts();
        int enumId = enums.get(slot);
        int[] itemIds = getItems(enumId);
        Item[] container = new Item[itemIds.length];
        int collectedCount = 0;
        for (int index = 0; index < itemIds.length; index++) {
            int itemId = itemIds[index];
            int amount = player.getCollectionLog().getCollected(itemId);
            if (femaleIds.containsKey(itemId) && !player.getAppearance().isMale()) {
                itemId = femaleIds.get(itemId);
            }
            if (transformIds.containsKey(itemId)) {
                itemId = transformIds.get(itemId);
            }
            if (amount > 0) {
                container[index] = new Item(itemId, amount);
                ++collectedCount;
            }
        }
        if (collectedCount >= itemIds.length) {
            CollectionLogEntry entry = entries.get(slot);
            if (entry != null && entry.getGreenLogVarbit() != null) entry.getGreenLogVarbit().set(player, 1);
        }
        player.getPacketSender().sendItems(-1, -1, 620, container);
    }

    public int[] getItems(int enumId) {
        return items.get(enumId);
    }

    public void sendKillCount(Player player, int slot) {
        if (entries.size() <= slot) {
            return;
        }
        CollectionLogEntry entry = entries.get(slot);
        Config.COLLECTION_LOG_KC_1.setInstant(player, entry.getKillcounts().length > 0 ? entry.getKillcounts()[0].apply(player) : 0);
        Config.COLLECTION_LOG_KC_2.setInstant(player, entry.getKillcounts().length > 1 ? entry.getKillcounts()[1].apply(player) : 0);
        Config.COLLECTION_LOG_KC_3.setInstant(player, entry.getKillcounts().length > 2 ? entry.getKillcounts()[2].apply(player) : 0);
    }

    private static final int STRUCT_LOG_SUBCATEGORY = 683;
    private static final int STRUCT_LOG_GROUP = 690;

    public static int TOTAL_COLLECTABLES;

    static {
        for (CollectionLogCategory info : values()) {
            info.entries = new ArrayList<>();
            StructDefinition category = StructDefinition.get(info.getCategoryStruct());
            EnumDefinition subcategories = EnumDefinition.get(category.getInt(STRUCT_LOG_SUBCATEGORY));
            for (int slot = 0; slot < subcategories.size; slot++) {
                StructDefinition subcategory = StructDefinition.get(subcategories.getIntValuesArray()[slot]);
                EnumDefinition group = EnumDefinition.get(subcategory.getInt(STRUCT_LOG_GROUP));
                info.subcategories.add(subcategories.getIntValuesArray()[slot]);
                info.items.put(subcategory.getInt(STRUCT_LOG_GROUP), group.getIntValuesArray());
                info.enums.add(subcategory.getInt(STRUCT_LOG_GROUP));
                for (int index = 0; index < group.getIntValuesArray().length; index++) {
                    ItemDefinition.get(group.getIntValuesArray()[index]).collectable = true;
                    TOTAL_COLLECTABLES++;
                }
            }
        }
    }
}


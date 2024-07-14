package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import lombok.Getter;

import java.util.HashSet;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/10/2024
 */
@Getter
public enum DropAllTask {
    GODSWORD_HILTS(489, Items.BANDOS_HILT, Items.ZAMORAK_HILT, Items.SARADOMIN_HILT, Items.ARMADYL_HILT),
    ARMADYL_SET(490, Items.ARMADYL_CHESTPLATE, Items.ARMADYL_CHAINSKIRT, Items.ARMADYL_HELMET),
    BANDOS_SET(491, Items.BANDOS_CHESTPLATE, Items.BANDOS_TASSETS, Items.BANDOS_BOOTS),
    CERB_CRYSTALS(492, Items.PRIMORDIAL_CRYSTAL, Items.PEGASIAN_CRYSTAL, Items.ETERNAL_CRYSTAL),
    MALEDICTION_SHARDS(896, Items.MALEDICTION_SHARD_1, Items.MALEDICTION_SHARD_2, Items.MALEDICTION_SHARD_3),
    ODIUM_SHARDS(897, Items.ODIUM_SHARD_1, Items.ODIUM_SHARD_2, Items.ODIUM_SHARD_3),
    DAGONHAI_SET(899, 24288, 24291, 24294),
    WILDERNESS_WEAPON(902, 22542, 22547, 22552),
    ;

    private final int taskUuid;
    private final int[] itemIds;

    public boolean hasCompleted(Player player) {
        for (int itemId : itemIds) {
            if (!player.getCollectionLog().getCollected().containsKey(itemId)) return false;
        }
        return true;
    }

    public void cleanupCollectedItems(HashSet<Integer> collectedItems) {
        for (int itemId : itemIds) {
            collectedItems.remove(itemId);
        }
    }

    DropAllTask(int taskUuid, int... itemIds) {
        this.taskUuid = taskUuid;
        this.itemIds = itemIds;
        for (int itemId : itemIds) {
            ItemDefinition.get(itemId).custom_values.put("DROPALL_TASK", this);
        }
    }
}

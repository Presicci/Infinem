package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.Tool;

public class InfernalEel {

    private static final int INFERNAL_EEL = 21293;

    public static final LootTable LOOT_TABLE = new LootTable().addTable(1,
            new LootItem(6529, 10, 20, 55), // tokkul
            new LootItem(9194, 1, 4),       // onyx bolt tips
            new LootItem(11994, 1, 5, 5)    // lava scale shard
    );

    private static void open(Player player, Item eel) {
        Item loot = LOOT_TABLE.rollItem();
        player.startEvent(event -> {
            player.lock();
            player.animate(7553);
            player.getInventory().remove(eel.getId(), 1);
            player.getInventory().add(loot);
            player.sendFilteredMessage("You break open the infernal eel and extract some " + loot.getDef().name + ".");
            event.delay(1);
            player.unlock();
        });
    }

    static {
        ItemItemAction.register(INFERNAL_EEL, Items.HAMMER, (player, primary, secondary) -> open(player, primary));
        ItemItemAction.register(INFERNAL_EEL, 25644, (player, primary, secondary) -> open(player, primary));
    }
}

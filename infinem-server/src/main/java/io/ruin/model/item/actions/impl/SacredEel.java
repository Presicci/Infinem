package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class SacredEel {
    private static final int SACRED_EEL = 13339;

    public static final LootTable chanceTable = new LootTable().addTable(1,
            new LootItem(12934, 3, 9, 16) // Zulrah scales
    );

    static {
        ItemItemAction.register(SACRED_EEL, Tool.KNIFE, (player, primary, secondary) -> {
            if (player.getStats().check(StatType.Cooking, 72, "dissect the eel")) {
                int offset = (int) Math.floor((player.getStats().get(StatType.Cooking).currentLevel - 72) / 8.0);
                int amount = Random.get(3 + offset, 5 + offset);
                player.startEvent(event -> {
                    player.lock();
                    player.animate(7151);
                    player.getInventory().remove(primary.getId(), 1);
                    player.getInventory().add(12934, amount);
                    player.getStats().addXp(StatType.Cooking, 100 + amount * 3, true);
                    player.sendFilteredMessage("You dissect the eel into some Zulrah scales.");
                    event.delay(1);
                    player.unlock();
                });
            }
        });
    }
}

package io.ruin.model.item.actions.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/25/2022
 */
public class Casket {

    private static final LootTable lootTable = new LootTable()
            .addTable(8,
                    new LootItem(Items.COINS, 40, 150),
                    new LootItem(Items.COINS, 80, 150),
                    new LootItem(Items.COINS, 160, 150),
                    new LootItem(Items.COINS, 320, 150),
                    new LootItem(Items.COINS, 640, 150),
                    new LootItem(Items.COINS, 1280, 150),
                    new LootItem(Items.COINS, 2560, 150),
                    new LootItem(Items.COINS, 100000, 1)
            ).addTable(8,
                    new LootItem(Items.UNCUT_SAPPHIRE, 1, 100),
                    new LootItem(Items.UNCUT_EMERALD, 1, 50),
                    new LootItem(Items.UNCUT_RUBY, 1, 25),
                    new LootItem(Items.UNCUT_DIAMOND, 1, 6),
                    new LootItem(Items.UNCUT_DRAGONSTONE, 1, 1)
            ).addTable(1,
                    new LootItem(Items.COSMIC_TALISMAN, 1, 100),
                    new LootItem(Items.LOOP_HALF_OF_KEY, 1, 15),
                    new LootItem(Items.TOOTH_HALF_OF_KEY, 1, 15),
                    new LootItem(Items.DRAGON_MED_HELM, 1, 1)
            );

    static {
        ItemAction.registerInventory(405, "open", (player, item) -> {
            item.remove();
            player.getInventory().add(lootTable.rollItem());
        });
    }
}

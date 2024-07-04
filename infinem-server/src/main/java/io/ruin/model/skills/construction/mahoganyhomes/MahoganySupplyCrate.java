package io.ruin.model.skills.construction.mahoganyhomes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
public class MahoganySupplyCrate {

    public static final LootTable LOOT_TABLE = new LootTable().addTable(1,
            new LootItem(Items.STEEL_BAR_NOTE, 24, 2000),
            new LootItem(Items.TEAK_PLANK_NOTE, 15, 2000),
            new LootItem(Items.MAHOGANY_PLANK_NOTE, 6, 1500),
            new LootItem(Items.OAK_PLANK_NOTE, 29, 1500),
            new LootItem(Items.OAK_PLANK_NOTE, 28, 1000),
            new LootItem(Items.SOFT_CLAY_NOTE, 46, 1000),
            new LootItem(Items.SOFT_CLAY_NOTE, 47, 1000),
            new LootItem(Items.BOLT_OF_CLOTH_NOTE, 9, 500),
            new LootItem(Items.LIMESTONE_BRICK_NOTE, 9, 500),
            new LootItem(Items.OAK_PLANK_NOTE, 30, 500),
            new LootItem(Items.SOFT_CLAY_NOTE, 45, 500),
            new LootItem(Items.SOFT_CLAY_NOTE, 48, 500),
            new LootItem(Items.STEEL_BAR_NOTE, 23, 500),
            new LootItem(Items.STEEL_BAR_NOTE, 25, 500),
            new LootItem(Items.TEAK_PLANK_NOTE, 16, 400),
            new LootItem(Items.MAHOGANY_PLANK_NOTE, 7, 300),
            new LootItem(Items.BOLT_OF_CLOTH_NOTE, 10, 100),
            new LootItem(Items.LIMESTONE_BRICK_NOTE, 10, 100)
    );

    private static void openAll(Player player) {
        player.startEvent(e -> {
            Item crate = player.getInventory().findItem(24884);
            while (crate != null) {
                open(player, crate);
                e.delay(1);
                crate = player.getInventory().findItem(24884);
            }
        });
    }

    private static void open(Player player, Item crate) {
        Item item = LOOT_TABLE.rollItem();
        crate.remove();
        player.getInventory().add(item);
        player.dialogue(new ItemDialogue().one(item.getDef().notedId, "You open the crate and find " + item.getAmount() + " " + item.getDef().name + "!"));
    }

    static {
        ItemAction.registerInventory(24884, "open", MahoganySupplyCrate::open);
        ItemAction.registerInventory(24884, "open-all", (player, item) -> openAll(player));
    }
}

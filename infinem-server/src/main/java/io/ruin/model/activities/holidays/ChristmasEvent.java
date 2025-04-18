package io.ruin.model.activities.holidays;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.stat.StatType;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/9/2024
 */
public class ChristmasEvent {

    public static final LootTable GIFT_TABLE = new LootTable()
            .addTable(15, LootTable.CommonTables.ALLOTMENT_SEED.items)
            .addTable(9, LootTable.CommonTables.UNCOMMON_SEED.items)
            .addTable(5, LootTable.CommonTables.RARE_SEED.items)
            .addTable(5, LootTable.CommonTables.USEFUL_HERB.items)
            .addTable(1, LootTable.CommonTables.TREE_HERB_SEED.items)
            .addTable(20,
            // Common
            new LootItem(Items.PURPLE_SWEETS, 1, 5, 45),
            new LootItem(Items.PURPLE_SWEETS, 5, 15, 15),
            new LootItem(Items.COAL, 1, 45),
            // Uncommon
            new LootItem(Items.CRYSTAL_KEY, 1, 20),
            new LootItem(Items.MUDDY_KEY, 1, 20),
            new LootItem(23499, 1, 20),  // Grubby key
            new LootItem(Items.DRAGON_SPEAR, 1, 15),
            new LootItem(Items.DRAGON_SCIMITAR, 1, 10),
            new LootItem(32040, 1, 10), // Transmog imbuement scroll
            // Cosmetics
            new LootItem(24428, 1, 7),  // Green gingerbread shield
            new LootItem(27558, 1, 6),  // Sack of coal
            new LootItem(27572, 1, 10),  // Festive nutcracker top
            new LootItem(27574, 1, 10),  // Festive nutcracker trousers
            new LootItem(27576, 1, 10),  // Festive nutcracker hat
            new LootItem(27578, 1, 10),  // Festive nutcracker boots
            new LootItem(27580, 1, 10),  // Festive nutcracker staff
            new LootItem(Items.SANTA_HAT, 1, 1)
    );

    public static void rollExperienceGiftBag(Player player, StatType type, double experience) {
        if (player.isTickEventActive(TickEventType.GIFT_BAG)) return;
        if (type == StatType.Prayer || type == StatType.Attack || type == StatType.Strength || type == StatType.Defence
                || type == StatType.Ranged || type == StatType.Magic || type == StatType.Hitpoints || type == StatType.Slayer) return;
        int roll = (int) Math.max(30, 60 - (experience / 100));
        if (Random.rollDie(roll)) {
            player.addTickEvent(new TickEvent(TickEventType.GIFT_BAG, 300));
            player.getInventory().addOrDrop(32042, 1);
            player.sendFilteredMessage("You find a gift bag.");
        }
    }

    private static final int[] GINGERBREAD_SHIELDS = { 24428, 24430, 24431 };

    private static void transformShield(Player player, Item item) {
        switch (item.getId()) {
            case 24428: item.setId(24430); break;
            case 24430: item.setId(24431); break;
            case 24431: item.setId(24428); break;
        }
    }

    static {
        ItemAction.registerInventory(32042, "open", (player, item) -> {
            player.getInventory().remove(32042, 1);
            player.getInventory().addOrDrop(GIFT_TABLE.rollItem());
        });
        for (int id : GINGERBREAD_SHIELDS) {
            ItemAction.registerInventory(id, "transform", ChristmasEvent::transformShield);
            ItemAction.registerEquipment(id, "transform", ChristmasEvent::transformShield);
        }
    }
}

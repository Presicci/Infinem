package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.thieving.ThievableChests;
import io.ruin.model.stat.StatType;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public class DarkChest {

    private static final int DARK_KEY = 25244;

    private static final LootTable LOOT = new LootTable()
            .addTable(9021, // Equipment
                    new LootItem(Items.RUNE_PICKAXE, 1, 2590),
                    new LootItem(Items.RUNE_AXE, 1, 2200),
                    new LootItem(Items.RUNE_CHAINBODY, 1, 741),
                    new LootItem(Items.RUNE_KITESHIELD, 1, 1480),
                    new LootItem(Items.RUNE_LONGSWORD, 1, 1720),
                    new LootItem(Items.DRAGON_PLATELEGS, 1, 52),
                    new LootItem(Items.DRAGON_PLATESKIRT, 1, 237)
            ).addTable(36245,   // Runes and ammunition
                    new LootItem(Items.ADAMANT_ARROW, 200, 400, 6780),
                    new LootItem(Items.DEATH_RUNE, 201, 399, 4920),
                    new LootItem(Items.RUNE_ARROW, 102, 299, 4900),
                    new LootItem(Items.CHAOS_RUNE, 302, 500, 4450),
                    new LootItem(Items.LAW_RUNE, 100, 300, 4080),
                    new LootItem(Items.BLOOD_RUNE, 100, 199, 3859),
                    new LootItem(Items.MIND_RUNE, 403, 600, 3680),
                    new LootItem(Items.NATURE_RUNE, 153, 350, 3569)
            ).addTable(17133,   // Herbs
                    new LootItem(Items.GRIMY_TORSTOL_NOTE, 10, 15, 4450),
                    new LootItem(Items.GRIMY_SNAPDRAGON_NOTE, 10, 15, 4310),
                    new LootItem(Items.GRIMY_DWARF_WEED_NOTE, 10, 15, 4290),
                    new LootItem(Items.GRIMY_RANARR_WEED_NOTE, 10, 15, 4080)
            ).addTable(13450,   // Leather
                    new LootItem(Items.BLACK_DRAGON_LEATHER_NOTE, 5, 10, 3730),
                    new LootItem(Items.BLUE_DRAGON_LEATHER_NOTE, 5, 10, 3649),
                    new LootItem(Items.GREEN_DRAGON_LEATHER_NOTE, 5, 10, 3259),
                    new LootItem(Items.RED_DRAGON_LEATHER_NOTE, 5, 10, 2810)
            ).addTable(9641,    // Bones
                    new LootItem(Items.BABYDRAGON_BONES_NOTE, 10, 15, 6090),
                    new LootItem(Items.DRAGON_BONES_NOTE, 10, 15, 3549)
            ).addTable(14507,   // Other
                    new LootItem(Items.COINS, 9_664, 29_496, 11199),
                    new LootItem(Items.DRAGONSTONE, 1, 1770),
                    new LootItem(25244, 2, 1270),              // Dark keys
                    new LootItem(Items.SHIELD_LEFT_HALF, 1, 265)
            );

    static {
        ObjectAction.register(40742, "open", ((player, obj) -> {
            if (!player.getStats().check(StatType.Thieving, 28, "open this chest"))
                return;
            if (!player.getInventory().hasId(DARK_KEY)) {
                player.dialogue(new MessageDialogue("The chest is securely locked. You'll need a key to open it."));
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the chest with your key.");
                player.privateSound(51);
                player.animate(536);
                World.startEvent(e -> {
                    obj.setId(40743);
                    e.delay(2);
                    obj.setId(obj.originalId);
                });
                player.getInventory().remove(DARK_KEY, 1);
                List<Item> loot = LOOT.rollItems(true);
                for(Item item : loot) {
                    player.getInventory().addOrDrop(item.getId(), item.getAmount());
                }
                PlayerCounter.DARK_CHEST_OPENED.increment(player, 1);
                player.getTaskManager().doLookupByUUID(911, 1); // Open the Dark Chest
                player.getStats().addXp(StatType.Thieving, 1500, true);
                event.delay(1);
                player.unlock();
            });
        }));
        ObjectAction.register(40742, "check", ((player, obj) -> {
            player.sendMessage("You have opened " + Color.RED.wrap("" + PlayerCounter.DARK_CHEST_OPENED.get(player)) + " dark chests.");
        }));
    }
}

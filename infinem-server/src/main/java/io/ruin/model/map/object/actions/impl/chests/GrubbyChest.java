package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class GrubbyChest {

    private static final LootTable FOOD = new LootTable().addTable(0,
            new LootItem(Items.EGG_POTATO, 4, 11),
            new LootItem(Items.SHARK, 4, 7),
            new LootItem(Items.SARADOMIN_BREW_2, 3, 1),
            new LootItem(Items.SUPER_RESTORE_2, 1, 1)
    );

    private static final LootTable POTION = new LootTable().addTable(0,
            new LootItem(Items.SUPER_DEFENCE_2, 1, 8),
            new LootItem(Items.SUPER_ATTACK_2, 1, 3),
            new LootItem(Items.SUPER_STRENGTH_2, 1, 3),
            new LootItem(Items.RANGING_POTION_2, 1, 3),
            new LootItem(Items.PRAYER_POTION_3, 2, 2),
            new LootItem(Items.SUPER_RESTORE_3, 2, 1)
    );

    private static final LootTable MAIN = new LootTable().addTable(0,
            new LootItem(Items.LAW_RUNE, 200, 6),
            new LootItem(Items.DEATH_RUNE, 200, 6),
            new LootItem(Items.ASTRAL_RUNE, 200, 6),
            new LootItem(Items.BLOOD_RUNE, 200, 6),
            new LootItem(Items.GRIMY_TOADFLAX_NOTE, 10, 4),
            new LootItem(Items.GRIMY_RANARR_WEED_NOTE, 10, 4),
            new LootItem(Items.COINS, 10000, 4),
            new LootItem(Items.GRIMY_SNAPDRAGON_NOTE, 10, 3),
            new LootItem(Items.GRIMY_TORSTOL_NOTE, 5, 3),
            new LootItem(Items.CRYSTAL_KEY, 1, 2),
            new LootItem(Items.DRAGON_BONES_NOTE, 10, 2),
            new LootItem(Items.RED_DRAGONHIDE_NOTE, 10, 2),
            new LootItem(Items.DRAGON_DART_TIP, 50, 1),
            new LootItem(Items.DRAGON_ARROWTIPS, 50, 1)
    );

    private static void openChest(Player player, GameObject obj) {
        Item grubbyKey = player.getInventory().findItem(23499);
        if (grubbyKey == null) {
            player.sendFilteredMessage("You need a Grubby key to open this chest.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You unlock the chest with your key.");
            grubbyKey.remove();
            player.privateSound(51);
            player.animate(536);

            List<Item> loot = new ArrayList<>();
            // Two food rolls
            loot.add(FOOD.rollItem());
            loot.add(FOOD.rollItem());
            // One potion roll
            loot.add(POTION.rollItem());
            // One main roll
            loot.add(MAIN.rollItem());

            for(Item item : loot) {
                player.getInventory().addOrDrop(item.getId(), item.getAmount());
            }

            World.startEvent(e -> {
                obj.setId(34839);
                e.delay(2);
                obj.setId(obj.originalId);
            });

            event.delay(1);
            player.unlock();
        });
    }

    static {
        ObjectAction.register(34901, 1, GrubbyChest::openChest);
    }
}

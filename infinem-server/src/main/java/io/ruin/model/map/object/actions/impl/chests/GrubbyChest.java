package io.ruin.model.map.object.actions.impl.chests;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootItemPair;
import io.ruin.model.item.loot.LootItemSet;
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

    public static final LootTable FOOD = new LootTable().addTable(0,
            new LootItem(Items.EGG_POTATO, 4, 12),
            new LootItem(Items.SHARK, 4, 7),
            new LootItemPair(Items.SARADOMIN_BREW_2, 3, 3, Items.SUPER_RESTORE_2, 1, 1, 1)
    );

    public static final LootTable POTION = new LootTable().addTable(0,
            new LootItemSet(8,
                    new LootItem(Items.SUPER_ATTACK_2, 1, 1),
                    new LootItem(Items.SUPER_STRENGTH_2, 1, 1),
                    new LootItem(Items.SUPER_DEFENCE_2, 1, 1)),
            new LootItemSet(8,
                    new LootItem(Items.SUPER_RANGING_2, 1, 1),
                    new LootItem(Items.SUPER_DEFENCE_2, 1, 1)),
            new LootItem(Items.PRAYER_POTION_3, 2, 3),
            new LootItem(Items.SUPER_RESTORE_3, 2, 1)
    );

    public static final LootTable MAIN = new LootTable().addTable(0,
            new LootItem(Items.LAW_RUNE, 200, 50),
            new LootItem(Items.DEATH_RUNE, 200, 50),
            new LootItem(Items.ASTRAL_RUNE, 200, 50),
            new LootItem(Items.BLOOD_RUNE, 200, 50),
            new LootItem(Items.GRIMY_TOADFLAX_NOTE, 10, 40),
            new LootItem(Items.GRIMY_RANARR_WEED_NOTE, 10, 40),
            new LootItem(Items.COINS, 10000, 40),
            new LootItem(Items.GRIMY_SNAPDRAGON_NOTE, 10, 35),
            new LootItem(Items.GRIMY_TORSTOL_NOTE, 5, 35),
            new LootItem(Items.CRYSTAL_KEY, 1, 30),
            new LootItem(Items.DRAGON_BONES_NOTE, 10, 30),
            new LootItem(Items.RED_DRAGONHIDE_NOTE, 10, 30),
            new LootItem(Items.DRAGON_DART_TIP, 50, 10),
            new LootItem(Items.DRAGON_ARROWTIPS, 50, 10)
    );

    public static final LootTable TERTIARY = new LootTable().addTable(0,
            new LootItem(-1, 1, 24),
            new LootItem(25844, 1, 1),
            new LootItem(25846, 1, 1)
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
            loot.addAll(FOOD.rollItems(false));
            loot.addAll(FOOD.rollItems(false));
            // One potion roll
            loot.addAll(POTION.rollItems(false));
            // One main roll
            loot.add(MAIN.rollItem());
            // Roll for egg sacs
            Item egg = TERTIARY.rollItem();
            if (egg != null) loot.add(egg);


            for(Item item : loot) {
                player.getInventory().addOrDrop(item.getId(), item.getAmount());
                player.getCollectionLog().collect(item);
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

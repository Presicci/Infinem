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
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/18/2022
 */
public class MuddyChest {

    private static final LootTable LOOT = new LootTable().addTable(0,
            new LootItem(Items.UNCUT_RUBY, 1, 0),
            new LootItem(Items.MITHRIL_BAR, 1, 0),
            new LootItem(Items.MITHRIL_DAGGER, 1, 0),
            new LootItem(Items.ANCHOVY_PIZZA, 1, 0),
            new LootItem(Items.LAW_RUNE, 2, 0),
            new LootItem(Items.DEATH_RUNE, 2, 0),
            new LootItem(Items.CHAOS_RUNE, 10, 0)
    );

    private static void openChest(Player player, GameObject obj) {
        Item muddyKey = player.getInventory().findItem(991);
        if (muddyKey == null) {
            player.sendFilteredMessage("You need a Muddy key to open this chest.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You unlock the chest with your key.");
            muddyKey.remove();
            player.privateSound(51);
            player.animate(536);

            List<Item> loot = new ArrayList<>(LOOT.rollItems(true));
            for(Item item : loot) {
                player.getInventory().addOrDrop(item.getId(), item.getAmount());
            }

            World.startEvent(e -> {
                obj.setId(171);
                e.delay(2);
                obj.setId(obj.originalId);
            });

            event.delay(1);
            player.unlock();
        });
    }

    static {
        ObjectAction.register(170, 1, MuddyChest::openChest);
    }
}

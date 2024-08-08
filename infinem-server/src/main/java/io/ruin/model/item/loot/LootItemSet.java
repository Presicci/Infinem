package io.ruin.model.item.loot;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/8/2024
 */
public class LootItemSet extends LootItem {

    @Expose public final LootItem[] items;

    public LootItemSet(int weight, LootItem... items) {
        super(items[0].id, items[0].min, items[0].max, weight);
        this.items = items;
    }

    public void addToList(List<Item> itemList) {
        for (LootItem lootItem : items) {
            Item item = new Item(lootItem.id, lootItem.min == lootItem.max ? lootItem.min : Random.get(lootItem.min, lootItem.max));
            item.lootBroadcast = broadcast;
            itemList.add(item);
        }
    }

    public Item[] toItems() {
        List<Item> itemList = new ArrayList<>();
        for (LootItem lootItem : items) {
            Item item = new Item(lootItem.id, lootItem.min == lootItem.max ? lootItem.min : Random.get(lootItem.min, lootItem.max));
            item.lootBroadcast = broadcast;
            itemList.add(item);
        }
        return itemList.toArray(new Item[0]);
    }
}

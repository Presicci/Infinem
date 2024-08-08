package io.ruin.model.item.loot;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/7/2024
 */
public class LootItemPair extends LootItem {

    @Expose
    public final int secondId, secondMin, secondMax;

    public LootItemPair(int id, int minAmount, int maxAmount, int secondId, int secondMin, int secondMax, int weight) {
        super(id, minAmount, maxAmount, weight);
        this.secondId = secondId;
        this.secondMin = secondMin;
        this.secondMax = secondMax;
    }

    public void addToList(List<Item> items) {
        Item item = new Item(id, min == max ? min : Random.get(min, max));
        item.lootBroadcast = broadcast;
        items.add(item);
        Item secondItem = new Item(secondId, secondMin == secondMax ? secondMin : Random.get(secondMin, secondMax));
        secondItem.lootBroadcast = broadcast;
        items.add(secondItem);
    }

    public Item secondToItem() {
        Item item = new Item(secondId, secondMin == secondMax ? secondMin : Random.get(secondMin, secondMax));
        item.lootBroadcast = broadcast;
        return item;
    }
}

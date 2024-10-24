package io.ruin.model.item.loot;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.item.Item;
import io.ruin.utility.Broadcast;

import java.util.List;
import java.util.Map;

public class LootItem {

    @Expose public final int id;

    @Expose public final int min, max;

    @Expose public int weight;

    @Expose public Broadcast broadcast;

    @Expose public Map<String, String> attributes;

    public LootItem(int id, int amount, int weight) {
        this(id, amount, amount, weight);
    }

    public LootItem(int id, int minAmount, int maxAmount, int weight) {
        this(id, minAmount, maxAmount, weight, null);
    }

    public LootItem(int id, int amount, int weight, boolean noted) {
        this.id = noted ? ItemDefinition.get(id).notedId : id;
        this.min = amount;
        this.max = amount;
        this.weight = weight;
    }

    public LootItem(int id, int amount, int weight, Map<String, String> attributes) {
        this.id = id;
        this.min = amount;
        this.max = amount;
        this.weight = weight;
        if (attributes == null)
            this.attributes = Maps.newHashMap();
        else
            this.attributes = Maps.newHashMap(attributes);
    }

    public LootItem(int id, int minAmount, int maxAmount, int weight, Map<String, String> attributes) {
        this.id = id;
        this.min = minAmount;
        this.max = maxAmount;
        this.weight = weight;
        if (attributes == null)
            this.attributes = Maps.newHashMap();
        else
            this.attributes = Maps.newHashMap(attributes);
    }

    public LootItem copy() {
        return new LootItem(id, min, max, weight, attributes);
    }

    public LootItem broadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
        return this;
    }

    public Item toItem() {
        Item item = new Item(id, min == max ? min : Random.get(min, max));
        item.lootBroadcast = broadcast;
        return item;
    }

    public void addToList(List<Item> items) {
        items.add(toItem());
    }

    public String getName() {
        ItemDefinition def = ItemDefinition.get(id);
        if(def.isNote())
            return ItemDefinition.get(def.notedId).name + " (noted)";
        return def.name;
    }

}
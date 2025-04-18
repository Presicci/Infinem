package io.ruin.model.item;

import java.util.ArrayList;
import java.util.Map;

public class ItemContainer extends ItemContainerG<Item> {

    @Override
    protected Item newItem(int id, int amount, Map<String, String> attributes) {
        return new Item(id, amount, attributes);
    }

    @Override
    protected Item[] newArray(int size) {
        return new Item[size];
    }

    public ArrayList<Item> collectNumberOfItems(int amount, int... ids) {
        int count = 0;
        ArrayList<Item> list = new ArrayList<>(ids.length);
        for (Item item : items) {
            if (item == null)
                continue;
            for (int id : ids) {
                if (item.getId() == id) {
                    if (count >= amount) return list;
                    if (count + item.getAmount() >= amount) {
                        list.add(new Item(item.getId(), amount - count));
                        count = amount;
                    } else {
                        list.add(item);
                        count += item.getAmount();
                    }
                }
            }
        }
        return list.isEmpty() ? null : list;
    }

}

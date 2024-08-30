package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/30/2024
 */
public enum ItemSetPack {
    FOUNDERS_T1(26554, new Item(26427), new Item(26430), new Item(26433), new Item(26436), new Item(26424)),
    FOUNDERS_T2(26557, new Item(26439), new Item(26442), new Item(26445), new Item(26448), new Item(32030), new Item(26500)),
    FOUNDERS_T3(26560, new Item(26451), new Item(26454), new Item(26457), new Item(26460), new Item(32031));

    private final int packId;
    private final Item[] packContents;

    ItemSetPack(int packId, Item... packContents) {
        this.packId = packId;
        this.packContents = packContents;
    }

    private void open(Player player, Item itemPack) {
        if (!player.getInventory().hasFreeSlots(itemPack.count())) {
            player.sendMessage("You need " + itemPack.count() + " free slots to open this pack.");
            return;
        }
        itemPack.remove();
        for (Item item : packContents) {
            player.getInventory().add(item);
        }
    }

    static {
        for(ItemSetPack pack : values())
            ItemAction.registerInventory(pack.packId, 1, pack::open);
    }
}

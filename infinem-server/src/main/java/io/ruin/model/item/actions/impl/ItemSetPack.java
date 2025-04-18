package io.ruin.model.item.actions.impl;

import io.ruin.model.content.transmog.UnlockableTransmog;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/30/2024
 */
public enum ItemSetPack {
    FOUNDERS_T1(26554, player -> player.getTransmogCollection().addToCollection(UnlockableTransmog.FOUNDER_HOOD_T1, UnlockableTransmog.FOUNDER_TOP_T1, UnlockableTransmog.FOUNDER_BOTTOM_T1,
            UnlockableTransmog.FOUNDER_BOOTS_T1),
            new Item(26427), new Item(26430), new Item(26433), new Item(26436), new Item(26424)),
    FOUNDERS_T2(26557, player -> player.getTransmogCollection().addToCollection(UnlockableTransmog.FOUNDER_HOOD_T2, UnlockableTransmog.FOUNDER_TOP_T2, UnlockableTransmog.FOUNDER_BOTTOM_T2,
            UnlockableTransmog.FOUNDER_BOOTS_T2, UnlockableTransmog.FOUNDER_CROSSBOW),
            new Item(26439), new Item(26442), new Item(26445), new Item(26448), new Item(32030), new Item(26500)),
    FOUNDERS_T3(26560, player -> player.getTransmogCollection().addToCollection(UnlockableTransmog.FOUNDER_HOOD_T3, UnlockableTransmog.FOUNDER_TOP_T3, UnlockableTransmog.FOUNDER_BOTTOM_T3,
            UnlockableTransmog.FOUNDER_BOOTS_T3, UnlockableTransmog.FOUNDER_WHIP),
            new Item(26451), new Item(26454), new Item(26457), new Item(26460), new Item(32031), new Item(32032));

    private final int packId;
    private final Item[] packContents;
    private final Consumer<Player> consumer;

    ItemSetPack(int packId, Item... packContents) {
        this(packId, null, packContents);
    }

    ItemSetPack(int packId, Consumer<Player> consumer, Item... packContents) {
        this.packId = packId;
        this.consumer = consumer;
        this.packContents = packContents;
    }

    private void open(Player player, Item itemPack) {
        if (!player.getInventory().hasFreeSlots(itemPack.count())) {
            player.sendMessage("You need " + itemPack.count() + " free slots to open this pack.");
            return;
        }
        if (consumer != null) consumer.accept(player);
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

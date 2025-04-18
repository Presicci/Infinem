package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

import java.util.ArrayList;

public class ItemSet {

    private final int id;

    private final int[] itemIds;

    private final String contentsMessage;

    private ItemSet(EnumDefinition map) {
        this.id = map.getIntValuesArray()[0];
        this.itemIds = new int[map.size - 1];
        System.arraycopy(map.getIntValuesArray(), 1, this.itemIds, 0, map.size - 1);
        StringBuilder message = new StringBuilder();
        for(int i = 0; i < itemIds.length; i++) {
            message.append("<col=ef1020>").append(ItemDefinition.get(itemIds[i]).name);
            if(i == itemIds.length - 1)
                message.append("</col>.");
            else
                message.append("</col>, ");
        }
        this.contentsMessage = message.toString();
        ItemDefinition.get(id).itemSet = this;
    }

    private ItemSet(int id, int... itemIds) {
        this.id = id;
        this.itemIds = itemIds;
        this.contentsMessage = null;
    }

    private void select(Player player) {
        ArrayList<Item> items = player.getInventory().collectOneOfEach(itemIds);
        if(items == null) {
            player.sendMessage("This set consists of:");
            player.sendMessage(contentsMessage);
            return;
        }
        for(Item item : items)
            item.remove();
        player.getInventory().add(id, 1);
    }

    private void unpack(Player player, Item item) {
        int freeSlots = player.getInventory().getFreeSlots() + 1; //plus 1 because the actual set will be removed
        if(freeSlots < itemIds.length) {
            player.sendMessage("You need " + (itemIds.length - 1) + " free inventory spaces to unpack this set.");
            return;
        }
        item.remove();
        for(int id : itemIds)
            player.getInventory().add(id, 1);
    }

    static {
        /**
         * Sets on interface
         */
        EnumDefinition map = EnumDefinition.get(1033);
        ArrayList<ItemSet> setList = new ArrayList<>();
        for(int i = 0; i < map.size; i++) {
            EnumDefinition itemsMap = EnumDefinition.get(map.getValuesAsInts().get(i));
            setList.add(new ItemSet(itemsMap));
        }
        ItemSet[] sets = setList.toArray(new ItemSet[setList.size()]);
        InterfaceHandler.register(Interface.ITEM_SETS, h -> {
            h.actions[2] = (DefaultAction) (p, option, slot, itemId) -> {
                if(slot < 0 || slot >= sets.length)
                    return;
                // Founder packs
                if (slot >= 103 && slot <= 105) {
                    p.sendMessage("You can't pack that item set.");
                    return;
                }
                if(option == 1) {
                    sets[slot].select(p);
                    return;
                }
                Item.examine(p, itemId);
            };
        });
        InterfaceHandler.register(Interface.ITEM_SETS_INV, h -> {
            h.actions[0] = (DefaultAction) (p, option, slot, itemId) -> {
                Item item = p.getInventory().get(slot, itemId);
                if(item == null)
                    return;
                if (item.getId() >= 26554 && item.getId() <= 26562) {
                    p.sendMessage("To unpack that, left-click it from your inventory.");
                    return;
                }
                if(option == 1) {
                    ItemSet set = item.getDef().itemSet;
                    if(set != null)
                        set.unpack(p, item);
                    return;
                }
                Item.examine(p, itemId);
            };
        });
        setList.clear();
        /**
         * Sets not on interface
         */
        setList.add(new ItemSet(9668, 5574, 5575, 5576));   //initiate
        setList.add(new ItemSet(9670, 9672, 9674, 9678));   //proselyte m
        setList.add(new ItemSet(9666, 9672, 9674, 9676));   //proselyte f
        for(ItemSet set : setList)
            ItemAction.registerInventory(set.id, "Unpack", set::unpack);
    }

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 451);
        player.openInterface(InterfaceType.INVENTORY, 430);
        player.getPacketSender().sendAccessMask(Interface.ITEM_SETS, 2, 0, 109, 1026);
        player.getPacketSender().sendAccessMask(Interface.ITEM_SETS_INV, 0, 0, 27, 1026);
    }

}
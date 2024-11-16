package io.ruin.model.shop.omnishop;

import io.ruin.cache.def.db.DBRowDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Inventory;
import io.ruin.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/13/2024
 */
@Getter
public class OmniShopItem {
    private final int slot, stock, cost, rowId;
    private final String description;

    private final List<Item> requiredItems;

    public OmniShopItem(int slot, int stock, int cost, int rowId, String description) {
        this.slot = slot;
        this.stock = stock;
        this.cost = cost;
        this.rowId = rowId;
        this.description = description;
        requiredItems = new ArrayList<>();
        Object[] required = DBRowDefinition.get(rowId).getColumnValues(5);
        for (int index = 0; index < required.length; index += 2) {
            if (index + 1 >= required.length) {
                System.out.println("Improper requirement length for " + description);
                break;
            }
            int row = (int) required[index];
            int amount = (int) required[index + 1];
            int itemId = (int) DBRowDefinition.get(row).getColumnValue(0);
            requiredItems.add(new Item(itemId, amount));
        }
    }

    private void sendRequiredItemString(Player player) {
        player.sendMessage("You need " + Utils.grammarCorrectListForItems(requiredItems) + " to buy this.");
    }

    public void purchase(Player player, int amount) {
        Inventory inventory = player.getInventory();
        if (!inventory.hasItems(requiredItems)) {
            sendRequiredItemString(player);
            return;
        }
        int itemId = (int) DBRowDefinition.get(getRowId()).getColumnValue(0);
        if (!inventory.hasRoomFor(itemId) && !OmniShopFakeItem.FAKE_ITEMS.containsKey(itemId)) {
            player.sendMessage("You don't have enough inventory space to buy that" + (amount > 1 ? " many" : "") + ".");
            return;
        }
        if (amount > 1) {
            for (Item item : requiredItems) {
                amount = Math.min(amount, inventory.getAmount(item.getId()) / item.getAmount());
            }
        }
        if (amount <= 0) {
            sendRequiredItemString(player);
            return;
        }
        for (Item item : requiredItems) {
            inventory.remove(item.getId(), item.getAmount() * amount);
        }
        if (OmniShopFakeItem.FAKE_ITEMS.containsKey(itemId)) {
            for (int replacementId : OmniShopFakeItem.FAKE_ITEMS.get(itemId).getReplacementItems()) {
                inventory.add(replacementId, amount);
                player.getCollectionLog().collect(replacementId, amount);
            }
        } else {
            inventory.add(itemId, amount);
            player.getCollectionLog().collect(itemId, amount);
        }
    }
}

package io.ruin.model.activities.shadesofmortton;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/23/2024
 */
public class FlamtaerBag {

    private static final int BAG = 12854;
    private static final int[] ACCEPTABLE_ITEMS = {Items.TIMBER_BEAM, Items.LIMESTONE_BRICK, Items.SWAMP_PASTE};
    private static final int[] MAX_AMOUNTS = {60, 60, 500};
    private static final String[] STORED_KEYS = {"FT_TIMBER", "FT_LIMESTONE", "FT_PASTE"};

    protected static boolean removeFromBag(Player player, int itemId, int amount) {
        for (int index = 0; index < ACCEPTABLE_ITEMS.length; index++) {
            if (ACCEPTABLE_ITEMS[index] != itemId) continue;
            if (player.getAttributeIntOrZero(STORED_KEYS[index]) >= amount) {
                player.incrementNumericAttribute(STORED_KEYS[index], -amount);
                return true;
            }
        }
        return false;
    }

    protected static boolean hasInBag(Player player, int itemId, int amount) {
        for (int index = 0; index < ACCEPTABLE_ITEMS.length; index++) {
            if (ACCEPTABLE_ITEMS[index] != itemId) continue;
            return player.getAttributeIntOrZero(STORED_KEYS[index]) >= amount;
        }
        return false;
    }

    private static void add(Player player, Item itemToAdd) {
        for (int index = 0; index < ACCEPTABLE_ITEMS.length; index++) {
            if (ACCEPTABLE_ITEMS[index] != itemToAdd.getId()) continue;
            int storedAmt = player.getAttributeIntOrZero(STORED_KEYS[index]);
            if (storedAmt >= MAX_AMOUNTS[index]) {
                player.sendMessage("You already have the maximum amount of that item in the bag.");
                return;
            }
            int amtToDeposit = 1;
            if (itemToAdd.getAmount() > 1) {
                amtToDeposit = Math.min(MAX_AMOUNTS[index] - storedAmt, itemToAdd.getAmount());
            }
            itemToAdd.remove(amtToDeposit);
            player.incrementNumericAttribute(STORED_KEYS[index], amtToDeposit);
            return;
        }
        player.sendMessage("You can't add that to the bag.");
    }

    private static void fill(Player player) {
        for (Item item : player.getInventory().getItems()) {
            if (item == null) continue;
            for (int index = 0; index < ACCEPTABLE_ITEMS.length; index++) {
                if (item.getId() != ACCEPTABLE_ITEMS[index]) continue;
                int storedAmt = player.getAttributeIntOrZero(STORED_KEYS[index]);
                if (storedAmt >= MAX_AMOUNTS[index]) continue;
                int amtToDeposit = 1;
                if (item.getAmount() > 1) {
                    amtToDeposit = Math.min(MAX_AMOUNTS[index] - storedAmt, item.getAmount());
                }
                item.remove(amtToDeposit);
                player.incrementNumericAttribute(STORED_KEYS[index], amtToDeposit);
            }
        }
    }

    private static void check(Player player) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < ACCEPTABLE_ITEMS.length; index++) {
            int storedAmt = player.getAttributeIntOrZero(STORED_KEYS[index]);
            sb.append(storedAmt);
            sb.append("x ");
            sb.append(ItemDefinition.get(ACCEPTABLE_ITEMS[index]).name);
            sb.append("<br>");
        }
        player.dialogue(new MessageDialogue("Your flamtaer bag contains:<br>" + sb));
    }

    private static void empty(Player player) {
        for (int index = 0; index < ACCEPTABLE_ITEMS.length; index++) {
            int storedAmt = player.getAttributeIntOrZero(STORED_KEYS[index]);
            if (storedAmt <= 0) continue;
            int itemId = ACCEPTABLE_ITEMS[index];
            int amtToWithdraw = Math.min(storedAmt, ItemDefinition.get(itemId).stackable ? storedAmt : player.getInventory().getFreeSlots());
            if (amtToWithdraw == 0) continue;
            player.getInventory().add(itemId, amtToWithdraw);
            player.incrementNumericAttribute(STORED_KEYS[index], -amtToWithdraw);
        }
    }

    static {
        ItemAction.registerInventory(BAG, "fill", (player, item) -> fill(player));
        ItemAction.registerInventory(BAG, "check", (player, item) -> check(player));
        ItemAction.registerInventory(BAG, "empty", (player, item) -> empty(player));
        for (int itemId : ACCEPTABLE_ITEMS) {
            ItemItemAction.register(itemId, BAG, (player, item, bag) -> add(player, item));
        }
    }
}

package io.ruin.model.item.actions.impl.storage;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
public class PlankSack {

    private static final String AMOUNT_KEY = "PLANK_S_AMT";
    private static final String TYPE_KEY = "PLANK_S_TYP";
    private static final int MAX_PLANKS = 28;
    private static final int[] PLANKS = { Items.MAHOGANY_PLANK, Items.TEAK_PLANK, Items.OAK_PLANK, Items.PLANK };

    public static boolean hasMaterials(Player player, int requiredPlankId, int requiredAmount) {
        int storedAmount = getStoredAmount(player);
        int storedType = getStoredType(player);
        int requiredType = plankIdToIndex(requiredPlankId);
        if (storedType != requiredType) {
            storedAmount = 0;
        }
        int inventoryAmount = player.getInventory().getAmount(requiredPlankId);
        return inventoryAmount + storedAmount >= requiredAmount;
    }

    public static void removeMaterials(Player player, int requiredPlankId, int requiredAmount) {
        int storedAmount = getStoredAmount(player);
        int storedType = getStoredType(player);
        int requiredType = plankIdToIndex(requiredPlankId);
        if (storedType == requiredType) {
            int newStoredAmount = player.incrementNumericAttribute(AMOUNT_KEY, -requiredAmount);
            requiredAmount -= storedAmount;
            if (newStoredAmount <= 0) clearAttributes(player);
        }
        if (requiredAmount > 0) {
            player.getInventory().remove(requiredPlankId, requiredAmount);
        }
    }

    private static void fill(Player player) {
        if (getStoredAmount(player) >= MAX_PLANKS) {
            player.sendMessage("Your plank sack is full.");
            return;
        }
        int storedType = getStoredType(player);
        if (storedType > 0) {
            fill(player, getPlankId(storedType));
            return;
        }
        List<Option> optionList = new ArrayList<>();
        for (int plankId : PLANKS) {
            if (player.getInventory().hasId(plankId)) optionList.add(new Option(ItemDefinition.get(plankId).name, () -> fill(player, plankId)));
        }
        if (optionList.isEmpty()) {
            player.sendMessage("You don't have any planks to fill the sack with.");
            return;
        }
        if (optionList.size() == 1) {
            optionList.get(0).consumer.accept(player);
            return;
        }
        player.dialogue(new OptionsDialogue("Which type of plank would you like to deposit?", optionList));
    }

    private static void fill(Player player, int plankId) {
        int depositAmt = Math.min(MAX_PLANKS - getStoredAmount(player),
                player.getInventory().getAmount(plankId));
        if (depositAmt <= 0) {
            player.sendMessage("You don't have any " + ItemDefinition.get(plankId).name + "s to fill the sack with.");
            return;
        }
        int type = plankIdToIndex(plankId);
        player.getInventory().remove(plankId, depositAmt);
        int newAmt = player.incrementNumericAttribute(AMOUNT_KEY, depositAmt);
        player.putAttribute(TYPE_KEY, type);
        player.sendMessage("You add " + depositAmt + " " + ItemDefinition.get(plankId).name + (depositAmt > 1 ? "s" : "") + " to your plank sack. It now has " + newAmt + " planks stored.");
    }

    private static void fillOne(Player player, int plankId) {
        int plankType = plankIdToIndex(plankId);
        int currentType = getStoredType(player);
        if (currentType > 0 && currentType != plankType) {
            int storedPlankId = getPlankId(currentType);
            player.sendMessage("Your sack already has " + ItemDefinition.get(storedPlankId) + "s in it.");
            return;
        }
        int currentAmt = getStoredAmount(player);
        if (currentAmt >= MAX_PLANKS) {
            player.sendMessage("Your plank sack is full.");
            return;
        }
        player.getInventory().remove(plankId, 1);
        player.putAttribute(TYPE_KEY, plankType);
        int newAmt = player.incrementNumericAttribute(AMOUNT_KEY, 1);
        player.sendMessage("You add " + ItemDefinition.get(plankId).descriptiveName + " to your plank sack. It now has " + newAmt + " planks stored.");
    }

    private static void check(Player player) {
        int amt = getStoredAmount(player);
        int type = getStoredType(player);
        if (amt <= 0 || type <= 0) {
            player.sendMessage("Your plank sack is empty.");
        } else {
            ItemDefinition definition = ItemDefinition.get(getPlankId(type));
            player.sendMessage("Your plank sack has " + amt + " " + definition.name + (amt > 1 ? "s" : "") + " stored.");
        }
    }

    private static void empty(Player player) {
        int amtToWithdraw = Math.min(player.getInventory().getFreeSlots(),
                getStoredAmount(player));
        if (amtToWithdraw <= 0) {
            player.sendMessage("Your plank sack is out of planks.");
            return;
        }
        int type = getStoredType(player);
        int plankId = getPlankId(type);
        int newAmt = player.incrementNumericAttribute(AMOUNT_KEY, -amtToWithdraw);
        if (newAmt <= 0) clearAttributes(player);
        player.getInventory().add(plankId, amtToWithdraw);
    }

    private static void clearAttributes(Player player) {
        player.removeAttribute(AMOUNT_KEY);
        player.removeAttribute(TYPE_KEY);
    }

    private static int plankIdToIndex(int itemId) {
        switch (itemId) {
            case Items.PLANK:
                return 1;
            case Items.OAK_PLANK:
                return 2;
            case Items.TEAK_PLANK:
                return 3;
            case Items.MAHOGANY_PLANK:
                return 4;
            default:
                return -1;
        }
    }

    private static int getPlankId(int type) {
        switch (type) {
            case 1:
                return Items.PLANK;
            case 2:
                return Items.OAK_PLANK;
            case 3:
                return Items.TEAK_PLANK;
            case 4:
                return Items.MAHOGANY_PLANK;
            default:
                return -1;
        }
    }

    private static int getStoredAmount(Player player) {
        return player.getAttributeIntOrZero(AMOUNT_KEY);
    }

    private static int getStoredType(Player player) {
        return player.getAttributeIntOrZero(TYPE_KEY);
    }

    static {
        ItemAction.registerInventory(24882, "fill", (player, item) -> fill(player));
        ItemAction.registerInventory(24882, "check", (player, item) -> check(player));
        ItemAction.registerInventory(24882, "empty", (player, item) -> empty(player));
        for (int plankId : PLANKS) {
            ItemItemAction.register(plankId, 24882, (player, plank, sack) -> fillOne(player, plankId));
        }
    }
}

package io.ruin.model.item.actions.impl.storage;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.skillcapes.SmithingSkillCape;
import io.ruin.model.item.containers.Equipment;

public class CoalBag {

    public static final int COAL = 453;

    public static final int COAL_BAG = 12019;

    private static int getBagSize(Player player) {
        int maxSize = 27;
        if (SmithingSkillCape.wearingSmithingCape(player))
            maxSize += 9;
        return maxSize;
    }

    private static void fill(Player player) {
        int maxSize = getBagSize(player);
        if(player.getAttributeIntOrZero("BAGGED_COAL") >= maxSize) {
            player.dialogue(new ItemDialogue().one(COAL_BAG, "The coal bag is full."));
            return;
        }
        for(Item item : player.getInventory().getItems()) {
            if(item != null && item.getId() == COAL) {
                item.remove();
                if(player.incrementNumericAttribute("BAGGED_COAL", 1) >= maxSize)
                    break;
            }
        }
        check(player);
    }

    private static void check(Player player) {
        if(player.getAttributeIntOrZero("BAGGED_COAL") == 0)
            player.dialogue(new ItemDialogue().one(COAL_BAG, "The coal bag is empty."));
        else if(player.getAttributeIntOrZero("BAGGED_COAL") == 1)
            player.dialogue(new ItemDialogue().one(COAL_BAG, "The coal bag contains one piece of coal."));
        else
            player.dialogue(new ItemDialogue().one(COAL_BAG, "The coal bag contains " + player.getAttributeIntOrZero("BAGGED_COAL") + " pieces of coal."));
    }

    private static void empty(Player player) {
        if(player.getAttributeIntOrZero("BAGGED_COAL") == 0) {
            player.dialogue(new ItemDialogue().one(COAL_BAG, "The coal bag is empty."));
            return;
        }
        int freeSlots = player.getInventory().getFreeSlots();
        if(freeSlots == 0) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        for(int i = 0; i < player.getInventory().getItems().length; i++) {
            if(player.getInventory().get(i) == null) {
                player.getInventory().set(i, new Item(COAL, 1));
                player.incrementNumericAttribute("BAGGED_COAL", -1);
                if (player.getAttributeIntOrZero("BAGGED_COAL") == 0)
                    break;
            }
        }
        if(player.getAttributeIntOrZero("BAGGED_COAL") == 0)
            player.dialogue(new ItemDialogue().one(COAL_BAG, "The coal bag is now empty."));
        else if(player.getAttributeIntOrZero("BAGGED_COAL") == 1)
            player.dialogue(new ItemDialogue().one(COAL_BAG, "There is one piece of coal left in the bag."));
        else
            player.dialogue(new ItemDialogue().one(COAL_BAG, "There are " + player.getAttributeIntOrZero("BAGGED_COAL") + " pieces of coal left in the bag."));
    }

    private static int withdrawToBag(Player player, Item item, int amount) {
        if (!player.getInventory().contains(COAL_BAG, 1))
            return 0;
        int intercept = Math.min(Math.min(getBagSize(player) - player.getAttributeIntOrZero("BAGGED_COAL"), item.getAmount()), amount);
        player.incrementNumericAttribute("BAGGED_COAL", intercept);
        player.sendFilteredMessage("You withdraw " + intercept + " coal directly to your coal bag.");
        return intercept;
    }

    static {
        ItemAction.registerInventory(COAL_BAG, "fill", (player, item) -> fill(player));
        ItemAction.registerInventory(COAL_BAG, "check", (player, item) -> check(player));
        ItemAction.registerInventory(COAL_BAG, "empty", (player, item) -> empty(player));
        ItemDef.get(COAL).bankWithdrawListener = CoalBag::withdrawToBag;
    }

}

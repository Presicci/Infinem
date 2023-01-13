package io.ruin.model.item.actions.impl.storage;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

public class GemBag {

    private static final int[] GEMS = new int[] { Items.UNCUT_SAPPHIRE, Items.UNCUT_EMERALD, Items.UNCUT_RUBY, Items.UNCUT_DIAMOND, Items.UNCUT_DRAGONSTONE };

    private static int getGemIndex(int gemId) {
        switch(gemId) {
            case Items.UNCUT_SAPPHIRE:
                return 0;
            case Items.UNCUT_EMERALD:
                return 1;
            case Items.UNCUT_RUBY:
                return 2;
            case Items.UNCUT_DIAMOND:
                return 3;
            case Items.UNCUT_DRAGONSTONE:
                return 4;
        }
        return -1;
    }

    private static int amountStored(Player player, int gemId) {
        int gemIndex = getGemIndex(gemId);
        if (gemIndex == -1)
            return -1;
        return player.gemBagContents[gemIndex];
    }

    private static void depositGem(Player player, int gemId, int amount) {
        int gemIndex = getGemIndex(gemId);
        player.gemBagContents[gemIndex] += amount;
    }

    private static void withdrawGem(Player player, int gemId, int amount) {
        int gemIndex = getGemIndex(gemId);
        player.gemBagContents[gemIndex] -= amount;
    }

    private static void clearBag(Player player) {
        player.gemBagContents = new int[5];
    }

    private static void addToBag(Player player, int gemId, int amount) {
        int gemSize = amountStored(player, gemId);
        if (gemSize >= 60) {
            player.sendFilteredMessage("Your gam bag cannot carry any more " + ItemDef.get(gemId).name.toLowerCase() + "s.");
            return;
        }
        int minToAdd = Math.min(amount, 60 - gemSize);
        int amountToAdd = Math.min(minToAdd, player.getInventory().count(gemId));
        if (amountToAdd == 0)
            return;
        player.getInventory().remove(gemId, amountToAdd);
        depositGem(player, gemId, amountToAdd);
        player.sendFilteredMessage("You add " + amountToAdd + " gem" + (amount == 1 ? "" : "s") + " to your bag.");
    }

    private static void fillBag(Player player, Item item) {
        int added = 0;
        for (int gemId : GEMS) {
            Item gem = player.getInventory().findItem(gemId);
            if (gem == null)
                continue;
            int gemSize = amountStored(player, gemId);
            if (gemSize >= 60) {
                player.sendFilteredMessage("Your gam bag cannot carry any more " + gem.getDef().name.toLowerCase() + "s.");
                continue;
            }
            int amountToAdd = Math.min(gem.count(), 60 - gemSize);
            if (amountToAdd == 0)
                continue;
            player.getInventory().remove(gem.getId(), amountToAdd);
            depositGem(player, gemId, amountToAdd);
            added += amountToAdd;
        }
        if (added > 0)
            player.sendFilteredMessage("You add " + added + " gem" + (added == 1 ? "" : "s") + " to the bag.");
        else
            player.sendFilteredMessage("You have no gems to add to the bag.");
    }

    public static void emptyBag(Player player, boolean bank) {
        for (int gemId : GEMS) {
            int freeSlots = player.getInventory().getFreeSlots();
            if (freeSlots == 0 && !bank)
                return;
            int gemSize = amountStored(player, gemId);
            int amountToRemove = Math.min(gemSize, freeSlots);
            if (amountToRemove == 0)
                continue;
            withdrawGem(player, gemId, amountToRemove);
            if (bank)
                player.getBank().add(gemId, amountToRemove);
            else
                player.getInventory().add(gemId, amountToRemove);
        }
    }

    private static void checkBag(Player player, Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append("The gem bag contains: ");
        int amount = 0;
        for (int gemId : GEMS) {
            int amountStored = amountStored(player, gemId);
            sb.append(amountStored).append(" ").append(ItemDef.get(gemId).name.toLowerCase()).append("s").append(gemId == Items.UNCUT_DRAGONSTONE ? "." : ", ");
            if (amountStored > 0)
                amount++;
        }
        if (amount > 0)
            player.sendFilteredMessage(sb.toString());
        else
            player.sendFilteredMessage("The bag is currently empty.");
    }

    private static void destroyBag(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Are you sure you want to destroy the item?",
                        "The contents of the bag will be destroyed with it.", item, () -> {
                    item.remove();
                    clearBag(player);
                })
        );
    }

    private static void deposit(Player player, Item gem, Item bag) {
        if (gem.getAmount() == 1) {
            addToBag(player, gem.getId(), 1);
        } else {
            player.dialogue(new OptionsDialogue(
                    "How many would you like to deposit?",
                    new Option("One", () -> addToBag(player, gem.getId(), 1)),
                    new Option("Five", () -> addToBag(player, gem.getId(), 5)),
                    new Option("X", () -> player.integerInput("How many would you like to deposit?", amt -> addToBag(player, gem.getId(), amt))),
                    new Option("All", () -> addToBag(player, gem.getId(), Integer.MAX_VALUE))
            ));
        }
    }

    static {
        ItemAction.registerInventory(12020, "fill", GemBag::fillBag);
        ItemAction.registerInventory(12020, "check", GemBag::checkBag);
        ItemAction.registerInventory(12020, "empty", ((player, item) -> emptyBag(player, false)));
        ItemAction.registerInventory(12020, "destroy", GemBag::destroyBag);

        for (int gemId : GEMS) {
            ItemItemAction.register(gemId, 12020, GemBag::deposit);
        }
    }
}

package io.ruin.model.skills.farming;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/14/2021
 */
public class SackOfVegetables {

    private static final int SACK = 5418;

    @AllArgsConstructor
    public enum Sacks {
        POTATOES(1942, 5420),
        CABBAGE(1965, 5460),
        ONIONS(1957, 5440);

        public int veggieId, sackId;
    }

    private static void emptySack(Player player, Item item, Sacks sack) {
        int withdrawAmount = getContainedAmount(item, sack);
        if (withdrawAmount > player.getInventory().getFreeSlots()) {
            withdrawAmount = player.getInventory().getFreeSlots();
        }
        if (withdrawAmount <= 0) {
            return;
        }
        player.getInventory().add(sack.veggieId, withdrawAmount);
        if (item.getId() - (withdrawAmount * 2) < sack.sackId) {
            item.setId(SACK);
        } else {
            item.setId(item.getId() - (withdrawAmount * 2));
        }
        player.sendMessage("You empty " + withdrawAmount + " " + ItemDef.get(sack.veggieId).name.toLowerCase() + "" + (withdrawAmount > 1 ? "s" : "") + " from the sack.");
    }

    private static void fillBasket(Player player, Item item, Sacks sack) {
        if (isFull(item, sack)) {
            player.sendMessage("The sack is full.");
            return;
        }
        int fillAmount = player.getInventory().getAmount(sack.veggieId);
        int basketSpace = getSpace(item, sack);
        if (fillAmount > basketSpace) {
            fillAmount = basketSpace;
        }
        if (fillAmount <= 0) {
            return;
        }
        player.getInventory().remove(sack.veggieId, fillAmount);
        item.setId(item.getId() + (fillAmount * 2));
        player.sendMessage("You add " + fillAmount + " " + ItemDef.get(sack.veggieId).name.toLowerCase() + "" + (fillAmount > 1 ? "s" : "") + " to the sack.");
    }

    private static void fillFromEmpty(Player player, Item item) {
        Sacks sack = null;
        for (Sacks s : Sacks.values()) {
            if (player.getInventory().hasId(s.veggieId)) {
                sack = s;
                break;
            }
        }
        if (sack == null) {
            player.sendMessage("You don't have any vegetables to put in the sack.");
            return;
        }
        if (isFull(item, sack)) {
            return;
        }
        int fillAmount = player.getInventory().getAmount(sack.veggieId);
        int basketSpace = getSpace(item, sack);
        if (fillAmount > basketSpace) {
            fillAmount = basketSpace;
        }
        if (fillAmount <= 0) {
            return;
        }
        player.getInventory().remove(sack.veggieId, fillAmount);
        item.setId(item.getId() + (fillAmount * 2));
        player.sendMessage("You add " + fillAmount + " " + ItemDef.get(sack.veggieId).name.toLowerCase() + "" + (fillAmount > 1 ? "s" : "") + " to the sack.");
    }

    private static void addOne(Player player, Item item, Sacks sack) {
        if (isFull(item, sack)) {
            player.sendMessage("The sack is full.");
            return;
        }
        int fillAmount = 1;
        int basketSpace = getSpace(item, sack);
        if (fillAmount > basketSpace) {
            fillAmount = basketSpace;
        }
        if (fillAmount <= 0) {
            player.sendMessage("The sack is full.");
            return;
        }
        player.getInventory().remove(sack.veggieId, fillAmount);
        if (item.getId() == SACK) {
            item.setId(sack.sackId);
        } else {
            item.setId(item.getId() + (fillAmount * 2));
        }
        player.sendMessage("You add one " + ItemDef.get(sack.veggieId).name.toLowerCase() + " to the sack.");
    }

    private static void removeOne(Player player, Item item, Sacks sack) {
        int withdrawAmount = 1;
        if (withdrawAmount > player.getInventory().getFreeSlots()) {
            withdrawAmount = player.getInventory().getFreeSlots();
        }
        if (withdrawAmount <= 0) {
            return;
        }
        player.getInventory().add(sack.veggieId, withdrawAmount);
        if (item.getId() - (withdrawAmount * 2) < sack.sackId) {
            item.setId(SACK);
        } else {
            item.setId(item.getId() - (withdrawAmount * 2));
        }
        player.sendMessage("You take one " + ItemDef.get(sack.veggieId).name.toLowerCase() + " from the sack.");
    }

    private static boolean isFull(Item item, Sacks sack) {
        return item.getId() == sack.sackId + 8;
    }

    private static int getContainedAmount(Item item, Sacks sack) {
        return ((item.getId() - sack.sackId) / 2) + 1;
    }

    private static int getSpace(Item item, Sacks basket) {
        return 10 - getContainedAmount(item, basket);
    }

    static {
        for (SackOfVegetables.Sacks sack : SackOfVegetables.Sacks.values()) {
            for (int index = 0; index < 10; index++) {
                ItemAction.registerInventory(sack.sackId + (index * 2), "empty", (player, item) -> SackOfVegetables.emptySack(player, item, sack));
                ItemAction.registerInventory(sack.sackId + (index * 2), "fill", (player, item) -> SackOfVegetables.fillBasket(player, item, sack));
                ItemAction.registerInventory(sack.sackId + (index * 2), "remove-one", (player, item) -> SackOfVegetables.removeOne(player, item, sack));
                ItemItemAction.register(sack.sackId + (index * 2), sack.veggieId, (player, item, fruit) -> SackOfVegetables.addOne(player, item, sack));
            }
            ItemItemAction.register(SACK, sack.veggieId, (player, item, fruit) -> SackOfVegetables.addOne(player, item, sack));
        }

        ItemAction.registerInventory(SACK, "fill", SackOfVegetables::fillFromEmpty);
    }
}


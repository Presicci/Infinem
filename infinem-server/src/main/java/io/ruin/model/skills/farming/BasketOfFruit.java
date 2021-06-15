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
public class BasketOfFruit {

    private static final int BASKET = 5376;

    @AllArgsConstructor
    public enum Baskets {
        APPLES(1955, 5378),
        ORANGE(2108, 5388),
        STRAWBERRY(5504, 5398),
        BANANA(1963, 5408),
        TOMATO(1982, 5960);

        public int fruitId, basketId;
    }

    private static void emptyBasket(Player player, Item item, Baskets basket) {
        int withdrawAmount = getContainedAmount(item, basket);
        if (withdrawAmount > player.getInventory().getFreeSlots()) {
            withdrawAmount = player.getInventory().getFreeSlots();
        }
        if (withdrawAmount <= 0) {
            return;
        }
        player.getInventory().add(basket.fruitId, withdrawAmount);
        if (item.getId() - (withdrawAmount * 2) < basket.basketId) {
            item.setId(BASKET);
        } else {
            item.setId(item.getId() - (withdrawAmount * 2));
        }
        player.sendMessage("You empty " + withdrawAmount + " " + ItemDef.get(basket.fruitId).name.toLowerCase() + "" + (withdrawAmount > 1 ? "s" : "") + " from the basket.");
    }

    private static void fillBasket(Player player, Item item, Baskets basket) {
        if (isFull(item, basket)) {
            player.sendMessage("The basket is full.");
            return;
        }
        int fillAmount = player.getInventory().getAmount(basket.fruitId);
        int basketSpace = getSpace(item, basket);
        if (fillAmount > basketSpace) {
            fillAmount = basketSpace;
        }
        if (fillAmount <= 0) {
            return;
        }
        player.getInventory().remove(basket.fruitId, fillAmount);
        item.setId(item.getId() + (fillAmount * 2));
        player.sendMessage("You add " + fillAmount + " " + ItemDef.get(basket.fruitId).name.toLowerCase() + "" + (fillAmount > 1 ? "s" : "") + " to the basket.");
    }

    private static void fillFromEmpty(Player player, Item item) {
        Baskets basket = null;
        for (Baskets b : Baskets.values()) {
            if (player.getInventory().hasId(b.fruitId)) {
                basket = b;
                break;
            }
        }
        if (basket == null) {
            player.sendMessage("You don't have any fruit to put in the basket.");
            return;
        }
        if (isFull(item, basket)) {
            return;
        }
        int fillAmount = player.getInventory().getAmount(basket.fruitId);
        int basketSpace = getSpace(item, basket);
        if (fillAmount > basketSpace) {
            fillAmount = basketSpace;
        }
        if (fillAmount <= 0) {
            return;
        }
        player.getInventory().remove(basket.fruitId, fillAmount);
        item.setId(item.getId() + (fillAmount * 2));
        player.sendMessage("You add " + fillAmount + " " + ItemDef.get(basket.fruitId).name.toLowerCase() + "" + (fillAmount > 1 ? "s" : "") + " to the basket.");
    }

    private static void addOne(Player player, Item item, Baskets basket) {
        if (isFull(item, basket)) {
            player.sendMessage("The basket is full.");
            return;
        }
        int fillAmount = 1;
        int basketSpace = getSpace(item, basket);
        if (fillAmount > basketSpace) {
            fillAmount = basketSpace;
        }
        if (fillAmount <= 0) {
            player.sendMessage("The basket is full.");
            return;
        }
        player.getInventory().remove(basket.fruitId, fillAmount);
        if (item.getId() == BASKET) {
            item.setId(basket.basketId);
        } else {
            item.setId(item.getId() + (fillAmount * 2));
        }
        player.sendMessage("You add one " + ItemDef.get(basket.fruitId).name.toLowerCase() + " to the basket.");
    }

    private static void removeOne(Player player, Item item, Baskets basket) {
        int withdrawAmount = 1;
        if (withdrawAmount > player.getInventory().getFreeSlots()) {
            withdrawAmount = player.getInventory().getFreeSlots();
        }
        if (withdrawAmount <= 0) {
            return;
        }
        player.getInventory().add(basket.fruitId, withdrawAmount);
        if (item.getId() - (withdrawAmount * 2) < basket.basketId) {
            item.setId(BASKET);
        } else {
            item.setId(item.getId() - (withdrawAmount * 2));
        }
        player.sendMessage("You take one " + ItemDef.get(basket.fruitId).name.toLowerCase() + " from the basket.");
    }

    private static boolean isFull(Item item, Baskets basket) {
        return item.getId() == basket.basketId + 8;
    }

    private static int getContainedAmount(Item item, Baskets basket) {
        return ((item.getId() - basket.basketId) / 2) + 1;
    }

    private static int getSpace(Item item, Baskets basket) {
        return 5 - getContainedAmount(item, basket);
    }

    static {
        for (Baskets basket : Baskets.values()) {
            for (int index = 0; index < 5; index++) {
                ItemAction.registerInventory(basket.basketId + (index * 2), "empty", (player, item) -> BasketOfFruit.emptyBasket(player, item, basket));
                ItemAction.registerInventory(basket.basketId + (index * 2), "fill", (player, item) -> BasketOfFruit.fillBasket(player, item, basket));
                ItemAction.registerInventory(basket.basketId + (index * 2), "remove-one", (player, item) -> BasketOfFruit.removeOne(player, item, basket));
                ItemItemAction.register(basket.basketId + (index * 2), basket.fruitId, (player, item, fruit) -> BasketOfFruit.addOne(player, item, basket));
            }
            ItemItemAction.register(BASKET, basket.fruitId, (player, item, fruit) -> BasketOfFruit.addOne(player, item, basket));
        }

        ItemAction.registerInventory(BASKET, "fill", BasketOfFruit::fillFromEmpty);
    }
}

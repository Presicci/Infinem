package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.handlers.CollectionBox;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.shop.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/26/2023
 */
public class CulinaromancersChest {

    private static final List<ShopItem> gloves = Arrays.asList(
            new ShopItem(Items.HARDLEATHER_GLOVES, 60, 65, -1),
            new ShopItem(Items.BRONZE_GLOVES, 60, 130, -1),
            new ShopItem(Items.IRON_GLOVES, 60, 325, -1),
            new ShopItem(Items.STEEL_GLOVES, 60, 650, -1),
            new ShopItem(Items.BLACK_GLOVES, 60, 1300, -1),
            new ShopItem(Items.MITHRIL_GLOVES, 60, 1950, -1),
            new ShopItem(Items.ADAMANT_GLOVES, 60, 3250, -1),
            new ShopItem(Items.RUNE_GLOVES, 60, 6500, -1),
            new ShopItem(Items.DRAGON_GLOVES, 60, 130000, -1),
            new ShopItem(Items.BARROWS_GLOVES, 60, 130000, -1)
    );
    private static final List<ShopItem> other = Arrays.asList(
            new ShopItem(Items.WOODEN_SPOON, 10, 45, 14),
            new ShopItem(Items.EGG_WHISK, 10, 65, 20),
            new ShopItem(Items.SPORK, 10, 422, 140),
            new ShopItem(Items.SPATULA, 10, 2496, 768),
            new ShopItem(Items.FRYING_PAN, 10, 2158, 664),
            new ShopItem(Items.SKEWER, 10, 4160, 1280),
            new ShopItem(Items.ROLLING_PIN, 10, 18720, 5760),
            new ShopItem(Items.KITCHEN_KNIFE, 10, 10400, 3200),
            new ShopItem(Items.MEAT_TENDERISER, 10, 53950, 16600),
            new ShopItem(Items.CLEAVER, 10, 33280, 10240)
    );

    static {
        ObjectAction.register(12308, 1, (player, obj) -> player.getBank().open());
        ObjectAction.register(12308, 3, (player, obj) -> CollectionBox.open(player));
        ObjectAction.register(12308, 4, (player, obj) -> Shop.shops.get(0).open(player));
        ObjectAction.register(12308, 5, (player, obj) -> {
            List<ShopItem> items = new ArrayList<>();
            items.addAll(gloves);   // TODO Unlock levels of this through something, maybe misthalin task points
            items.addAll(other);
            Shop shop = new Shop("Item Store", Currency.COINS, false, RestockRules.generateDefault(), items, true);
            shop.init();
            shop.populate();
            shop.open(player);
        });
    }
}

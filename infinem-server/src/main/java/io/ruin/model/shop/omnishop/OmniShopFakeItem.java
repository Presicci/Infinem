package io.ruin.model.shop.omnishop;

import io.ruin.model.entity.player.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/16/2024
 */
@Getter
public enum OmniShopFakeItem {
    GRACEFUL_RECOLOUR(30044,
            player -> player.getTransmogCollection().addToCollection(30047, 30050, 30053, 30056, 30059, 30062),
            30047, 30050, 30053, 30056, 30059, 30062);

    private final int shopItemId;
    private final Consumer<Player> onPurchaseAction;
    private final int[] replacementItems;

    protected static final Map<Integer, OmniShopFakeItem> FAKE_ITEMS = new HashMap<>();

    OmniShopFakeItem(int shopItemId, int... replacementItems) {
        this(shopItemId, null, replacementItems);
    }

    OmniShopFakeItem(int shopItemId, Consumer<Player> onPurchaseAction, int... replacementItems) {
        this.shopItemId = shopItemId;
        this.onPurchaseAction = onPurchaseAction;
        this.replacementItems = replacementItems;
    }

    static {
        for (OmniShopFakeItem item : values()) {
            FAKE_ITEMS.put(item.shopItemId, item);
        }
    }
}

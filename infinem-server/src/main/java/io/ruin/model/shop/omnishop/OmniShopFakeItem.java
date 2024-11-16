package io.ruin.model.shop.omnishop;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/16/2024
 */
@Getter
public enum OmniShopFakeItem {
    GRACEFUL_RECOLOUR(30044, 30047, 30050, 30053, 30056, 30059, 30062);

    private final int shopItemId;
    private final int[] replacementItems;

    protected static final Map<Integer, OmniShopFakeItem> FAKE_ITEMS = new HashMap<>();

    OmniShopFakeItem(int shopItemId, int... replacementItems) {
        this.shopItemId = shopItemId;
        this.replacementItems = replacementItems;
    }

    static {
        for (OmniShopFakeItem item : values()) {
            FAKE_ITEMS.put(item.shopItemId, item);
        }
    }
}

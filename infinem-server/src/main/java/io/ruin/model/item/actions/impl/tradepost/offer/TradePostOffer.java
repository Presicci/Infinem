package io.ruin.model.item.actions.impl.tradepost.offer;

import com.google.gson.annotations.Expose;
import io.ruin.model.item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/kLeptO">Augustinas R.</a>
 */
public class TradePostOffer {
    @Getter @Expose private final String username;
    @Getter @Expose private final Item item;
    @Getter @Expose private final int pricePerItem;
    @Getter @Expose private final long timestamp;
    @Expose public int unclaimedSales;

    public TradePostOffer(String username, Item item, int pricePerItem, long timestamp) {
        this.username = username;
        this.item = item;
        this.pricePerItem = pricePerItem;
        this.timestamp = timestamp;
    }
}
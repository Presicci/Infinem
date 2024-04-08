package io.ruin.model.item.actions.impl.tradepost;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/8/2024
 */
@Getter
@AllArgsConstructor
public class ExchangeHistory {
    @Expose private final int itemId;
    @Expose private final int itemQuantity;
    @Expose private final int price;
    @Expose private final ExchangeType type;
}

package io.ruin.model.shop.omnishop;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/13/2024
 */
@Getter
@AllArgsConstructor
public class OmniShopItem {
    private final int slot, stock, cost, rowId;
    private final String description;
}

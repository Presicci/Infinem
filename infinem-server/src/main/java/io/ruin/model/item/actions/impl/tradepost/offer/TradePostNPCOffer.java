package io.ruin.model.item.actions.impl.tradepost.offer;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/1/2024
 */
public class TradePostNPCOffer extends TradePostOffer {

    public TradePostNPCOffer(int id, Item item, int pricePerItem) {
        super(NPCDefinition.get(id).name, item, pricePerItem, -1);
    }
}

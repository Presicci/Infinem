package io.ruin.model.map.object.actions.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/14/2024
 */
public class BeerBarrel {

    static {
        ItemObjectAction.register(Items.BEER_GLASS, 364, (player, item, obj) -> {
            player.animate(827);
            item.setId(Items.BEER);
            player.sendFilteredMessage("You fill the glass with ale.");
        });
    }
}

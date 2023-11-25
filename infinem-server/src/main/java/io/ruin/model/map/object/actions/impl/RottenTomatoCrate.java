package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.shop.Shop;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public class RottenTomatoCrate {

    static {
        ObjectAction.register(20885, "buy", (player, obj) -> Shop.shops.get(1).open(player));
        ObjectAction.register(3195, "buy", (player, obj) -> Shop.shops.get(1).open(player));
    }
}

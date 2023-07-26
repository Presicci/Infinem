package io.ruin.model.map.object.actions.impl;

import io.ruin.data.impl.npcs.npc_shops;
import io.ruin.model.inter.handlers.CollectionBox;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.shop.*;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/26/2023
 */
public class CulinaromancersChest {

    static {

        ObjectAction.register(12308, 1, (player, obj) -> player.getBank().open());
        ObjectAction.register(12308, 3, (player, obj) -> CollectionBox.open(player));
        ObjectAction.register(12308, 4, (player, obj) -> Shop.shops.get(0).open(player));
    }
}

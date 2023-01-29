package io.ruin.model.item.actions.impl;

import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2023
 */
public class NewcomerMap {

    static {
        ItemAction.registerInventory(550, "read", ((player, item) -> player.openInterface(InterfaceType.MAIN, 615)));
    }
}

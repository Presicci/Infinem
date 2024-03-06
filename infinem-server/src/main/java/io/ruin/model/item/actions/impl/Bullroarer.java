package io.ruin.model.item.actions.impl;

import io.ruin.model.inter.handlers.TabEmote;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/6/2024
 */
public class Bullroarer {

    static {
        ItemAction.registerInventory(Items.BULL_ROARER, "swing", (player, item) -> TabEmote.BULL_ROARER.perform(player));
    }
}

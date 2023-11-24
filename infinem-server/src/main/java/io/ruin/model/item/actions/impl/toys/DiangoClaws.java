package io.ruin.model.item.actions.impl.toys;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/23/2023
 */
public class DiangoClaws {

    static {
        ItemAction.registerInventory(Items.DIANGOS_CLAWS, "emote", (player, item) -> player.animate(5283));
    }
}

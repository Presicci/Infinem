package io.ruin.model.item.actions.impl;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/12/2024
 */
public class FungicideRefill {
    static {
        int[] SPRAYS = { 7421, 7422, 7423, 7424, 7425, 7426, 7427, 7428, 7429, 7430 };
        for (int spray : SPRAYS) {
            ItemItemAction.register(spray, Items.FUNGICIDE, (player, primary, secondary) -> player.sendMessage("You should use the rest of the fungicide before refilling it."));
        }
        ItemItemAction.register(Items.FUNGICIDE_SPRAY_0, Items.FUNGICIDE, (player, primary, secondary) -> {
            primary.setId(Items.FUNGICIDE_SPRAY_10);
            secondary.remove();
            player.sendMessage("You refill the fungicide spray.");
        });
    }
}

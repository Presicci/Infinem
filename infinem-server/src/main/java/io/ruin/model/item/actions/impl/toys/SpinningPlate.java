package io.ruin.model.item.actions.impl.toys;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/23/2023
 */
public class SpinningPlate {

    static {
        ItemAction.registerInventory(Items.SPINNING_PLATE, "spin", (player, item) -> {
            player.startEvent(e -> {
                while (!player.getMovement().hasMoved()) {
                    player.animate(1902);
                    e.delay(4);
                }
            });
        });
    }
}

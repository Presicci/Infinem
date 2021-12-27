package io.ruin.model.skills.farming;

import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 12/26/2021
 */
public class Compost {

    private static final int[] COMPOST_POTIONS = { 6476, 6474, 6472, 6470 };
    private static final int SUPER_COMPOST = 6034;
    private static final int COMPOST = 6032;

    static {
        // Supercompost creation
        for (int index = 0; index < COMPOST_POTIONS.length; index++) {
            int finalIndex = index;
            ItemItemAction.register(COMPOST_POTIONS[index], COMPOST, (p, pot, compost) -> {
                p.sendFilteredMessage("You turn the compost into supercompost.");
                compost.setId(SUPER_COMPOST);
                if (finalIndex == 0) {
                    pot.setId(229);
                } else {
                    pot.setId(COMPOST_POTIONS[finalIndex - 1]);
                }
            });
            ItemAction.registerInventory(COMPOST_POTIONS[index], "empty", (p, item) -> {
                p.sendFilteredMessage("You empty the vial.");
                item.setId(229);
            });
        }
    }
}

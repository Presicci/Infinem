package io.ruin.model.skills.farming.items;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/2/2024
 */
public class Coconut {

    private static void crackOpen(Player player, Item coconut) {
        coconut.setId(Items.HALF_COCONUT);
        player.sendFilteredMessage("You crack open the coconut.");
    }

    private static void fillVial(Player player, Item coconut, Item vial) {
        coconut.setId(Items.COCONUT_SHELL);
        vial.setId(Items.COCONUT_MILK);
        player.sendFilteredMessage("You fill the vial with coconut milk.");
    }

    static {
        ItemItemAction.register(Items.HAMMER, Items.COCONUT, (player, primary, secondary) -> crackOpen(player, secondary));
        ItemItemAction.register(25644, Items.COCONUT, (player, primary, secondary) -> crackOpen(player, secondary));    // Imcando hammer
        ItemItemAction.register(Items.HALF_COCONUT, Items.VIAL, Coconut::fillVial);
    }
}

package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public class BrokenSpinningWheel {

    static {
        // varpbit 11716
        ObjectAction.register(40767, "repair", ((player, obj) -> {
            if (!player.getInventory().hasId(Items.HAMMER)) {
                player.dialogue(new MessageDialogue("You need a hammer to repair the spinning wheel."));
                return;
            }
        }));
    }
}

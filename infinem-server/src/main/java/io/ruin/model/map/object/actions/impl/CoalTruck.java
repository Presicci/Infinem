package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/28/2023
 */
public class CoalTruck {

    static {
        ObjectAction.register(10836, "remove-coal", ((player, obj) ->
                player.dialogue(new MessageDialogue("The dwarf will take your coal to the bank, no need to worry about it."))));
        ObjectAction.register(10836, "investigate", (((player, obj) ->
                player.dialogue(new MessageDialogue("If you deposit coal into the truck, the dwarves will bring it to your bank for you, free of charge.")))));
        ItemObjectAction.register(Items.COAL, 10836, ((player, item, obj) -> {
            int bankAmt = player.getBank().getAmount(Items.COAL);
            int amt = Math.min(Integer.MAX_VALUE - bankAmt, player.getInventory().getAmount(Items.COAL));
            if (amt <= 0) {
                player.dialogue(new MessageDialogue("Your bank has too much coal!"));
                return;
            }
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getInventory().remove(Items.COAL, amt);
                player.getBank().add(Items.COAL, amt);
                player.dialogue(new ItemDialogue().one(Items.COAL, "You deposit " + amt + " coal.<br>Your bank now has " + (bankAmt + amt) + " coal stored."));
                player.unlock();
            });
        }));
    }
}

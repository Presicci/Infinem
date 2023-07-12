package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
public class WreckedBoat {

    private static void takeItem(Player player, int itemId) {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.dialogue(new MessageDialogue("You don't have enough inventory space to take anything."));
            return;
        }
        player.getInventory().add(itemId, 1);
        player.dialogue(new ItemDialogue().one(itemId, "You take a " + ItemDef.get(itemId).name + "."));
    }

    static {
        ObjectAction.register(33195, "search", ((player, obj) -> {
            player.dialogue(
                    new ItemDialogue().two(Items.ROPE, Items.HAMMER, "The wrecked boat has a few supplies in it."),
                    new OptionsDialogue(
                            new Option("Take rope", () -> takeItem(player, Items.ROPE)),
                            new Option("Take hammer", () -> takeItem(player, Items.HAMMER)),
                            new Option("Take fishing net", () -> takeItem(player, Items.SMALL_FISHING_NET)),
                            new Option("Take pickaxe", () -> takeItem(player, Items.BRONZE_PICKAXE)),
                            new Option("Take nothing")
                    )
            );
        }));
    }
}

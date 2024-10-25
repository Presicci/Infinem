package io.ruin.model.content.transmog;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/6/2024
 */
public class TransmogScroll {

    private static void read(Player player) {
        player.dialogue(new MessageDialogue(
                        "This scroll can be used on any equippable item to add it to your transmog collection.<br>" +
                        "The scroll is consumed in the process.")
        );
    }

    private static void imbue(Player player, Item item) {
        if (item.getDef().equipSlot == -1) {
            player.dialogue(new ItemDialogue().one(item.getId(), "That item is not equippable."));
            return;
        }
        if (item.getDef().equipSlot == 13 || item.getDef().equipSlot == 12) {
            player.dialogue(new ItemDialogue().one(item.getId(), "You can't collect transmogs for that slot."));
            return;
        }
        if (player.getTransmogCollection().hasTransmog(item.getId())) {
            player.dialogue(new ItemDialogue().one(item.getId(), "You already have this item in your transmog collection."));
            return;
        }
        if (UnlockableTransmog.UNLOCKABLE_TRANSMOG_IDS.contains(item.getId())) {
            player.getTransmogCollection().addToCollection(item.getId(), true);
            return;
        }
        player.dialogue(new OptionsDialogue("Would you like to add " + item.getDef().name + " to your transmog collection?",
                new Option("Yes", () -> {
                    if (player.getInventory().remove(32040, 1) > 0) {
                        player.getTransmogCollection().addToCollection(item.getId(), true);
                    }
                }),
                new Option("No")
        ));
    }

    static {
        ItemAction.registerInventory(32040, "read", (player, item) -> read(player));
        ItemItemAction.register(32040, (player, scroll, secondary) -> imbue(player, secondary));
    }
}

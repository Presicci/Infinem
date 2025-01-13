package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
public class RelicFragment {

    public static void inspect(Player player, Item item, FragmentType type) {
        if (FragmentItem.isFragment(item)) {
            player.dialogue(new MessageDialogue("Fragment mods:<br>" + FragmentItem.getModString(item, false)));
            return;
        }
        FragmentItem.makeFragment(item, type);
        FragmentItem.rollMod(item);
        FragmentItem.rollMod(item);
        FragmentItem.rollMod(item);
        player.dialogue(new MessageDialogue("Fragment mods:<br>" + FragmentItem.getModString(item, false)));
    }

    public static void registerFragmentItem(int itemId, FragmentType type) {
        ItemAction.registerInventory(itemId, "inspect", (player, item) -> inspect(player, item, type));
    }
}

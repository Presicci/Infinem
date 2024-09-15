package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/12/2024
 */
public class MasterWand {

    private static void combine(Player player) {
        if (!player.getInventory().hasId(Items.BEGINNER_WAND) || !player.getInventory().hasId(Items.APPRENTICE_WAND) || !player.getInventory().hasId(Items.TEACHER_WAND)) {
            player.dialogue(new MessageDialogue("You need a Beginner, Apprentice, and Teacher wand to make a Master wand."));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Combine all three wands into a Master wand?", new Item(Items.MASTER_WAND), () -> {
            if (!player.getInventory().hasId(Items.BEGINNER_WAND) || !player.getInventory().hasId(Items.APPRENTICE_WAND) || !player.getInventory().hasId(Items.TEACHER_WAND)) {
                player.dialogue(new MessageDialogue("You need a Beginner, Apprentice, and Teacher wand to make a Master wand."));
                return;
            }
            player.getInventory().remove(Items.BEGINNER_WAND, 1);
            player.getInventory().remove(Items.APPRENTICE_WAND, 1);
            player.getInventory().remove(Items.TEACHER_WAND, 1);
            player.getInventory().add(Items.MASTER_WAND, 1);
            player.getCollectionLog().collect(new Item(Items.MASTER_WAND, 1));
            player.dialogue(new ItemDialogue().one(Items.MASTER_WAND, "You combine the wands into a Master wand."));
            player.getTaskManager().doLookupByUUID(236);    // Create a Master Wand
        }));
    }

    static {
        ItemItemAction.register(Items.BEGINNER_WAND, Items.APPRENTICE_WAND, (player, primary, secondary) -> combine(player));
        ItemItemAction.register(Items.BEGINNER_WAND, Items.TEACHER_WAND, (player, primary, secondary) -> combine(player));
        ItemItemAction.register(Items.APPRENTICE_WAND, Items.TEACHER_WAND, (player, primary, secondary) -> combine(player));
    }
}

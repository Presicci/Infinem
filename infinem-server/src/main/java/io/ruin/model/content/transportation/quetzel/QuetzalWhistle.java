package io.ruin.model.content.transportation.quetzel;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/5/2024
 */
public class QuetzalWhistle {

    private static final int[] WHISTLES = {
            29271,  // Basic
            29273,  // Enhanced
            29275   // Perfected
    };
    private static final Bounds VARLAMORE = new Bounds(1250, 2800, 1890, 3390, -1);
    private static final Bounds OVERWORLD = new Bounds(1045, 2515, 3948, 4140, -1);

    private static void signal(Player player, Item item) {
        // Basic whistle can only be used withing Varlamore
        if (item.getId() == WHISTLES[0] && !VARLAMORE.inBounds(player)) {
            player.sendMessage("Your Quetzal can only hear this whistle from within Varlamore.");
            return;
        }
        // Enhanced whistle can only be used in the overworld
        if (item.getId() == WHISTLES[1] && !OVERWORLD.inBounds(player)) {
            player.sendMessage("Your Quetzal can only hear this whistle from above ground.");
            return;
        }
        if (!player.getMovement().checkTeleport(player, 20)) return;
        QuetzalTransportSystem.open(player);
    }

    private static void check(Player player, Item item) {
        player.dialogue(new MessageDialogue("This feature will be added with Hunter Rumours."));
    }

    private static void rumour(Player player, Item item) {
        player.dialogue(new MessageDialogue("This feature will be added with Hunter Rumours."));
    }

    static {
        for (int whistle : WHISTLES) {
            ItemAction.registerInventory(whistle, "signal", QuetzalWhistle::signal);
            ItemAction.registerInventory(whistle, "check", QuetzalWhistle::check);
            ItemAction.registerInventory(whistle, "rumour", QuetzalWhistle::rumour);
        }
    }
}

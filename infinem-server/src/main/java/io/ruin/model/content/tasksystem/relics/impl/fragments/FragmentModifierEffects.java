package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.model.entity.player.Player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/5/2025
 */
public class FragmentModifierEffects {

    public static void rewardExperienceLamp(Player player) {
        if (player.getBank().hasRoomFor(2528)) {
            player.getBank().add(2528);
            player.sendMessage("You find an experience lamp! It was sent to your bank. You now have " + player.getBank().getAmount(2528) + " experience lamps banked.");
        } else {
            player.getInventory().addOrDrop(2528, 1);
            player.sendMessage("You find an experience lamp!");
        }
    }
}

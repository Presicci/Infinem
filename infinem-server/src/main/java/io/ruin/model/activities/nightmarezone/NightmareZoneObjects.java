package io.ruin.model.activities.nightmarezone;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Greco
 *
 */
public class NightmareZoneObjects {

    /**
     * Resources tab parent child ID
     */
    public static int RESOURCES = 4;
    /**
     * Upgrades tab parent child ID
     */
    public static int UPGRADES = 5;
    /**
     * Benefits tab parent child ID
     */
    public static int BENEFITS = 6;

    /**
     * increments (increases) the amount of nmz points specified
     * @param player
     * @param amount
     */
    public static void incrementNMZPoints(Player player, int amount) {
        PlayerCounter.NMZ_REWARD_POINTS.increment(player, amount);
    }

    /**
     * decrements (decreases) the amount of nmz points specified
     * @param player
     * @param amount
     */
    public static void decrementNMZPoints(Player player, int amount) {
        PlayerCounter.NMZ_REWARD_POINTS.decrement(player, amount);
    }

    static {
        /**
         * NMZ Rewards chest
         *
         */
        ObjectAction.register(26273, "search", (player, obj) -> {
            NightmareZoneRewards.open(player);
        });

        //ObjectAction.register(26277, "check", (player, obj) -> {
        //    player.dialogue(new ItemDialogue().one(11722, "This barrel contains super ranging potion that adventurers have purchased from Dominic with their reward points.\nYou have " + " "));
        //});
    }
}
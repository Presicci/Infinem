package io.ruin.model.skills.cooking.gnome;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/7/2023
 */
public class GnomeRestaurant {

    private static final String GNOME_CUSTOMER = "GNOME_CUSTOMER", GNOME_ORDER = "GNOME_ORDER";
    private static int GIANNE = 2547, REWARD_TOKEN = Items.REWARD_TOKEN_4;

    private static void assignTask(Player player, int tier) {
        //if (!player.getStats().check(StatType.Cooking, 6, "make deliveries")) return;
        if (player.hasAttribute(GNOME_ORDER) || player.hasAttribute(GNOME_CUSTOMER)) {
            player.dialogue(
                    new NPCDialogue(GIANNE, "You've already been assigned a delivery! Complete that one first, or I could reset it for you in exchange for a reward token."),
                    new OptionsDialogue(
                            new Option("Reset task. (Costs 1 reward token)", () -> reroll(player, tier)),
                            new Option("I'll finish that delivery right away!", new PlayerDialogue("I'll finish that delivery right away!"), new NPCDialogue(GIANNE, "Hurry along."))
                    )
            );
            return;
        }
        if (!player.getInventory().hasId(Items.ALUFT_ALOFT_BOX)) {
            if (!player.getInventory().hasRoomFor(Items.ALUFT_ALOFT_BOX)) {
                player.dialogue(new NPCDialogue(GIANNE, "You don't seem to have a delivery box. Free a bag space so I can give you one."));
                return;
            }
            player.getInventory().add(Items.ALUFT_ALOFT_BOX);
        }
        generateTask(player, tier);
    }

    private static void reroll(Player player, int tier) {
        if (!player.getInventory().contains(REWARD_TOKEN)) {
            player.dialogue(
                    new NPCDialogue(GIANNE, "You need a reward token to reroll a delivery. Looks like you're stick with this one..."),
                    new PlayerDialogue("Well, it was worth a shot.")
            );
            return;
        }
        player.getInventory().remove(REWARD_TOKEN, 1);
        generateTask(player, tier);
    }

    private static void generateTask(Player player, int tier) {
        GnomeRecipe order = Random.get(GnomeRecipe.values());
        player.putAttribute(GNOME_ORDER, order.ordinal());
        RestaurantCustomer customer = Random.get(RestaurantCustomer.values());
        player.putAttribute(GNOME_CUSTOMER, customer.ordinal());
        String customerName = NPCDefinition.get(customer.getNpcId()).name;
        player.dialogue(
                new NPCDialogue(GIANNE, customerName + " has ordered " + (order.ordinal() >= 16 ? "some " : "a ") + StringUtils.capitalizeFirst(order.name().toLowerCase())
                        + ". They can be found " + customer.getLocationString() + " Any questions?"),
                new OptionsDialogue(
                        new Option("Sounds good, I'll get that delivered immediately."),
                        new Option("How do I go about making that?", () -> howToCook(player)),
                        new Option("Could I handle a different delivery? (Costs 1 reward token)", () -> reroll(player, tier))
                )
        );
    }

    private static final int COOKBOOK = 2167;

    private static void howToCook(Player player) {
        if (player.getInventory().hasId(COOKBOOK)) {
            player.dialogue(new NPCDialogue(GIANNE, "You can read the recipes in the cook book I gave you. You can buy most of the ingredients from Hudo just north or from Heckel Funch by the bar to the east."));
            return;
        }
        if (!player.getInventory().hasRoomFor(COOKBOOK)) {
            player.dialogue(new NPCDialogue(GIANNE, "If you free up some bag space I can give you a cook book to help with preparing the deliveries. You can buy most of the ingredients from Hudo just north or from Heckel Funch by the bar to the east."));
            return;
        }
        player.getInventory().add(COOKBOOK);
        player.dialogue(new NPCDialogue(GIANNE, "This cook book should help with preparing the deliveries. You can buy most of the ingredients from Hudo just north or from Heckel Funch by the bar to the east."));
    }

    /**
     * Reward point unlocks
     * 1 - Reroll task
     * 5 - Unlock hard tasks
     *  - Increased xp
     *  - Get ingredients on accepting order
     *  - Free rerolls
     */
    static {
        //ItemAction.registerInventory(Items.ALUFT_ALOFT_BOX, "check", (player, item) -> );
    }
}

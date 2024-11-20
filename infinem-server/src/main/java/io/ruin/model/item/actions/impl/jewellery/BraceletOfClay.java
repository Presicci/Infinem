package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.utility.Color;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/20/2024
 */
public class BraceletOfClay {

    private static final int BRACELET_OF_CLAY = Items.BRACELET_OF_CLAY;
    private static final int MAX_CHARGES = 28;
    private static final String CHARGES_KEY = "BOCLAY_CHARGES";

    private static void breakAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(CHARGES_KEY);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == MAX_CHARGES) {
            player.dialogue(new ItemDialogue().one(BRACELET_OF_CLAY, "The bracelet is fully charged.<br>There would be no point in breaking it."));
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        "Status: " + charges + " soft clay left.",
                        new Option("Break the bracelet.", () -> {
                            item.remove();
                            fullCharges(player);
                            player.sendMessage("Your Bracelet of Clay has crumbled to dust.");
                            player.dialogue(new ItemDialogue().one(BRACELET_OF_CLAY, "Your Bracelet of Clay has crumbled to dust."));
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void checkAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(CHARGES_KEY);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == 1)
            player.sendMessage("You can mine 1 more pieces of soft clay before your bracelet crumbles to dust.");
        else
            player.sendMessage("You can mine " + charges + " more pieces of soft clay before your bracelet crumbles to dust.");
    }

    public static void fullCharges(Player player) {
        player.putAttribute(CHARGES_KEY, MAX_CHARGES);
    }

    public static boolean test(Player player) {
        if (!player.getEquipment().hasId(BRACELET_OF_CLAY))
            return false;
        int charges = player.getAttributeIntOrZero(CHARGES_KEY);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges > 1) {
            player.putAttribute(AttributeKey.SLAUGHTER_CHARGES, --charges);
        } else {
            player.getEquipment().remove(BRACELET_OF_CLAY, 1);
            fullCharges(player);
        }
        return true;
    }

    static {
        ItemAction.registerInventory(BRACELET_OF_CLAY, "break", BraceletOfClay::breakAction);
        ItemAction.registerEquipment(BRACELET_OF_CLAY, "check", BraceletOfClay::checkAction);
    }
}

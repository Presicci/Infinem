package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.utility.Color;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/22/2024
 */
public class FlamtaerBracelet {

    private static final int FLAMTAER_BRACELET = 21180;
    private static final int MAX_CHARGES = 40;

    private static void breakAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.FLAMTAER_BRACELET);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == MAX_CHARGES) {
            player.dialogue(new ItemDialogue().one(FLAMTAER_BRACELET, "The bracelet is fully charged.<br>There would be no point in breaking it."));
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        "Status: " + charges + " temple builds left.",
                        new Option("Break the bracelet.", () -> {
                            item.remove();
                            fullCharges(player);
                            player.sendMessage("Your Flamtaer Bracelet has crumbled to dust.");
                            player.dialogue(new ItemDialogue().one(FLAMTAER_BRACELET, "Your Flamtaer Bracelet has crumbled to dust."));
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void checkAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.FLAMTAER_BRACELET);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == 1)
            player.sendMessage("Your flamtaer bracelet has 1 charge left.");
        else
            player.sendMessage("Your flamtaer bracelet has " + charges + " charges left.");
    }

    public static void fullCharges(Player player) {
        player.putAttribute(AttributeKey.FLAMTAER_BRACELET, MAX_CHARGES);
    }

    public static boolean test(Player player) {
        if (!player.getEquipment().hasId(FLAMTAER_BRACELET))
            return false;
        int charges = player.getAttributeIntOrZero(AttributeKey.FLAMTAER_BRACELET);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (Random.rollDie(2)) {
            if (charges > 1) {
                player.putAttribute(AttributeKey.FLAMTAER_BRACELET, --charges);
                player.sendFilteredMessage("Your flamtaer bracelet helps you build the temple faster. " + Color.RED.wrap("It has " + (charges == 1 ? "1 charge" : charges + " charges") + " left."));
            } else {
                player.getEquipment().remove(FLAMTAER_BRACELET, 1);
                fullCharges(player);
                player.sendFilteredMessage("Your flamtaer bracelet helps you build the temple faster. " + Color.RED.wrap("It then crumbles to dust."));
            }
            return true;
        }
        return false;
    }

    static {
        ItemAction.registerInventory(FLAMTAER_BRACELET, "break", FlamtaerBracelet::breakAction);
        ItemAction.registerInventory(FLAMTAER_BRACELET, "check", FlamtaerBracelet::checkAction);
        ItemAction.registerEquipment(FLAMTAER_BRACELET, "check", FlamtaerBracelet::checkAction);
    }
}
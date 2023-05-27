package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2023
 */
public class EfaritaysAid {

    private static final int EFARITAYS_AID = 21140;
    private static final int MAX_CHARGES = 200;

    private static void breakAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.EFARITAYS_CHARGES);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == MAX_CHARGES) {
            player.dialogue(new ItemDialogue().one(EFARITAYS_AID, "The ring is fully charged.<br>There would be no point in breaking it."));
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        "Status: " + charges + " vampyre hits remaining.",
                        new Option("Break the ring.", () -> {
                            item.remove();
                            fullCharges(player);
                            player.sendMessage("Your Efaritay's aid has crumbled to dust.");
                            player.dialogue(new ItemDialogue().one(EFARITAYS_AID, "Your Efaritay's aid has crumbled to dust."));
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void checkAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.EFARITAYS_CHARGES);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == 1)
            player.sendMessage("Your efaritay's aid has 1 charge left.");
        else
            player.sendMessage("Your efaritay's aid has " + charges + " charges left.");
    }

    public static void fullCharges(Player player) {
        player.putAttribute(AttributeKey.EFARITAYS_CHARGES, MAX_CHARGES);
    }

    public static boolean test(Player player) {
        if (!player.getEquipment().hasId(EFARITAYS_AID))
            return false;
        int charges = player.getAttributeIntOrZero(AttributeKey.EFARITAYS_CHARGES);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges > 1) {
            player.putAttribute(AttributeKey.EXPEDITIOUS_CHARGES, --charges);
        } else {
            player.getEquipment().remove(EFARITAYS_AID, 1);
            fullCharges(player);
            player.sendFilteredMessage("Your efaritay's aid crumbles to dust.");

        }
        return true;
    }

    static {
        ItemAction.registerInventory(EFARITAYS_AID, "break", EfaritaysAid::breakAction);
        ItemAction.registerInventory(EFARITAYS_AID, "check", EfaritaysAid::checkAction);
        ItemAction.registerEquipment(EFARITAYS_AID, "check", EfaritaysAid::checkAction);
    }
}

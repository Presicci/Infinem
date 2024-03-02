package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/2/2024
 */
public class AmuletOfChemistry {

    private static final int AMULET_OF_CHEMISTRY = 21163;
    private static final int MAX_CHARGES = 5;
    private static final String KEY = "CHEMISTRY_CHARGES";

    private static void breakAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(KEY);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == MAX_CHARGES) {
            player.dialogue(new ItemDialogue().one(AMULET_OF_CHEMISTRY, "The amulet is fully charged.<br>There would be no point in breaking it."));
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        "Status: " + charges + " potions left.",
                        new Option("Break the amulet.", () -> {
                            item.remove();
                            fullCharges(player);
                            player.sendMessage("Your amulet of chemistry has crumbled to dust.");
                            player.dialogue(new ItemDialogue().one(AMULET_OF_CHEMISTRY, "Your amulet of chemistry has crumbled to dust."));
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void checkAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(KEY);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (charges == 1)
            player.sendMessage("Your amulet of chemistry has 1 charge left.");
        else
            player.sendMessage("Your amulet of chemistry has " + charges + " charges left.");
    }

    public static void fullCharges(Player player) {
        player.putAttribute(KEY, MAX_CHARGES);
    }

    public static boolean test(Player player) {
        if (!player.getEquipment().hasId(AMULET_OF_CHEMISTRY))
            return false;
        int charges = player.getAttributeIntOrZero(KEY);
        if (charges == 0) {
            fullCharges(player);
            charges = MAX_CHARGES;
        }
        if (Random.rollDie(20)) {
            if (charges > 1) {
                player.putAttribute(KEY, --charges);
                player.sendFilteredMessage("Your amulet of chemistry helps you creat a 4-dose potion. " + Color.RED.wrap("It has " + (charges == 1 ? "1 charge" : charges + " charges") + " left."));
            } else {
                player.getEquipment().remove(AMULET_OF_CHEMISTRY, 1);
                fullCharges(player);
                player.sendFilteredMessage("Your amulet of chemistry helps you creat a 4 dose potion. " + Color.RED.wrap("It then crumbles to dust."));
            }
            player.getTaskManager().doLookupByUUID(133);    // Make a 4 dose potion
            return true;
        }
        return false;
    }

    static {
        ItemAction.registerInventory(AMULET_OF_CHEMISTRY, "check", AmuletOfChemistry::checkAction);
        ItemAction.registerInventory(AMULET_OF_CHEMISTRY, "break", AmuletOfChemistry::breakAction);
        ItemAction.registerEquipment(AMULET_OF_CHEMISTRY, "check", AmuletOfChemistry::checkAction);
    }
}

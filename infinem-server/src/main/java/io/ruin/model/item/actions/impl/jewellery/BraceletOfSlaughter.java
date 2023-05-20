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
 * Created on 5/8/2023
 */
public class BraceletOfSlaughter {

    public static final int BRACELET_OF_SLAUGHTER = 21183;

    private static void breakBracelet(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.SLAUGHTER_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = 30;
        }
        if (charges == 30) {
            player.dialogue(new ItemDialogue().one(BRACELET_OF_SLAUGHTER, "The bracelet is fully charged.<br>There would be no point in breaking it."));
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        "Status: " + charges + " slayer kill saves left.",
                        new Option("Break the bracelet.", () -> {
                            item.remove();
                            newBracelet(player);
                            player.sendMessage("Your Bracelet of Slaughter has crumbled to dust.");
                            player.dialogue(new ItemDialogue().one(BRACELET_OF_SLAUGHTER, "Your Bracelet of Slaughter has crumbled to dust."));
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void check(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.SLAUGHTER_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = 30;
        }
        if (charges == 1)
            player.sendMessage("Your bracelet of slaughter has 1 charge left.");
        else
            player.sendMessage("Your bracelet of slaughter has " + charges + " charges left.");
    }

    public static void newBracelet(Player player) {
        player.putAttribute(AttributeKey.SLAUGHTER_CHARGES, 30);
    }

    public static boolean checkSlaughter(Player player) {
        int charges = player.getAttributeIntOrZero(AttributeKey.SLAUGHTER_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = 30;
        }
        if (player.getEquipment().hasId(BRACELET_OF_SLAUGHTER) && Random.rollDie(4)) {
            if (charges > 1) {
                player.putAttribute(AttributeKey.SLAUGHTER_CHARGES, --charges);
                player.sendFilteredMessage("Your bracelet of slaughter prevents your slayer count from decreasing. It has " + (charges == 1 ? "1 charge" : charges + " charges") + " left.");
            } else {
                player.getEquipment().remove(BRACELET_OF_SLAUGHTER, 1);
                newBracelet(player);
                player.sendFilteredMessage("Your bracelet of slaughter prevents your slayer count from decreasing. It then crumbles to dust.");
            }
            return true;
        }
        return false;
    }

    static {
        ItemAction.registerInventory(BRACELET_OF_SLAUGHTER, "break", BraceletOfSlaughter::breakBracelet);
        ItemAction.registerInventory(BRACELET_OF_SLAUGHTER, "check", BraceletOfSlaughter::check);
        ItemAction.registerEquipment(BRACELET_OF_SLAUGHTER, "check", BraceletOfSlaughter::check);
    }
}

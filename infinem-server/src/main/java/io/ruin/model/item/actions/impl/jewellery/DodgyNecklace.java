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
 * Created on 5/24/2023
 */
public class DodgyNecklace {

    private static final int DODGY_NECKLACE = 21143;
    private static final int MAX_CHARGES = 10;

    private static void breakAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.DODGY_NECKLACE_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = MAX_CHARGES;
        }
        if (charges == MAX_CHARGES) {
            player.dialogue(new ItemDialogue().one(DODGY_NECKLACE, "The necklace is fully charged.<br>There would be no point in breaking it."));
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        "Status: " + charges + " charges left.",
                        new Option("Break the necklace.", () -> {
                            item.remove();
                            newBracelet(player);
                            player.sendMessage("Your dodgy necklace has crumbled to dust.");
                            player.dialogue(new ItemDialogue().one(DODGY_NECKLACE, "Your dodgy necklace has crumbled to dust."));
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void checkAction(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.DODGY_NECKLACE_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = MAX_CHARGES;
        }
        if (charges == 1)
            player.sendMessage("Your dodgy necklace has 1 charge left.");
        else
            player.sendMessage("Your dodgy necklace has " + charges + " charges left.");
    }

    public static void newBracelet(Player player) {
        player.putAttribute(AttributeKey.DODGY_NECKLACE_CHARGES, MAX_CHARGES);
    }

    public static boolean test(Player player) {
        if (!player.getEquipment().hasId(DODGY_NECKLACE))
            return false;
        if (!Random.rollDie(4))
            return false;
        int charges = player.getAttributeIntOrZero(AttributeKey.DODGY_NECKLACE_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = MAX_CHARGES;
        }
        if (charges > 1) {
            player.putAttribute(AttributeKey.DODGY_NECKLACE_CHARGES, --charges);
            player.sendFilteredMessage("Your dodgy necklace protects you. It has " + (charges == 1 ? "1 charge" : charges + " charges") + " left.");
        } else {
            player.getEquipment().remove(DODGY_NECKLACE, 1);
            newBracelet(player);
            player.sendFilteredMessage("Your dodgy necklace protects you. It then crumbles to dust.");
        }
        return true;
    }

    static {
        ItemAction.registerInventory(DODGY_NECKLACE, "break", DodgyNecklace::breakAction);
        ItemAction.registerInventory(DODGY_NECKLACE, "check", DodgyNecklace::checkAction);
        ItemAction.registerEquipment(DODGY_NECKLACE, "check", DodgyNecklace::checkAction);
    }
}
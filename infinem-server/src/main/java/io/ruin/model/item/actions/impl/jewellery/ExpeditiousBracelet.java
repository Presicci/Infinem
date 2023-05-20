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
 * Created on 5/19/2023
 */
public class ExpeditiousBracelet {

    public static final int EXPEDITIOUS_BRACELET = 21177;

    private static void breakBracelet(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.EXPEDITIOUS_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = 30;
        }
        if (charges == 30) {
            player.dialogue(new ItemDialogue().one(EXPEDITIOUS_BRACELET, "The bracelet is fully charged.<br>There would be no point in breaking it."));
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        "Status: " + charges + " slayer kill skips left.",
                        new Option("Break the bracelet.", () -> {
                            item.remove();
                            newBracelet(player);
                            player.sendMessage("Your Expeditious Bracelet has crumbled to dust.");
                            player.dialogue(new ItemDialogue().one(EXPEDITIOUS_BRACELET, "Your Expeditious Bracelet has crumbled to dust."));
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void check(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(AttributeKey.EXPEDITIOUS_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = 30;
        }
        if (charges == 1)
            player.sendMessage("Your expeditious bracelet has 1 charge left.");
        else
            player.sendMessage("Your expeditious bracelet has " + charges + " charges left.");
    }

    public static void newBracelet(Player player) {
        player.putAttribute(AttributeKey.EXPEDITIOUS_CHARGES, 30);
    }

    public static boolean checkExpeditious(Player player) {
        int charges = player.getAttributeIntOrZero(AttributeKey.EXPEDITIOUS_CHARGES);
        if (charges == 0) {
            newBracelet(player);
            charges = 30;
        }
        if (player.getEquipment().hasId(EXPEDITIOUS_BRACELET) && Random.rollDie(4)) {
            if (charges > 1) {
                player.putAttribute(AttributeKey.EXPEDITIOUS_CHARGES, --charges);
                player.sendFilteredMessage("Your expeditious bracelet helps you progress your slayer task faster. It has " + (charges == 1 ? "1 charge" : charges + " charges") + " left.");
            } else {
                player.getEquipment().remove(EXPEDITIOUS_BRACELET, 1);
                newBracelet(player);
                player.sendFilteredMessage("Your expeditious bracelet helps you progress your slayer task faster. It then crumbles to dust.");
            }
            return true;
        }
        return false;
    }

    static {
        ItemAction.registerInventory(EXPEDITIOUS_BRACELET, "break", ExpeditiousBracelet::breakBracelet);
        ItemAction.registerInventory(EXPEDITIOUS_BRACELET, "check", ExpeditiousBracelet::check);
        ItemAction.registerEquipment(EXPEDITIOUS_BRACELET, "check", ExpeditiousBracelet::check);
    }
}

package io.ruin.model.item.actions.impl.teleport;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/23/2023
 */
public class Chronicle {

    private static final int MAX_CHARGES = 1000;

    private static void teleport(Player player) {
        int charges = player.getAttributeIntOrZero("CHRONICLE_CHARGES");
        if (charges <= 0) {
            player.sendMessage("Your book does not have any charges. Purchase some Teleport Cards from Diango.");
            return;
        }
        ModernTeleport.teleport(player, new Bounds(3199, 3353, 3202, 3357, 0));
        player.incrementNumericAttribute("CHRONICLE_CHARGES", -1);
        player.getTaskManager().doLookupByUUID(929);    // Teleport Using the Chronicle
        player.sendMessage(Color.RED, chargeMessage(player));
    }

    private static void check(Player player) {
        player.sendMessage(chargeMessage(player));
    }

    private static String chargeMessage(Player player) {
        int charges = player.getAttributeIntOrZero("CHRONICLE_CHARGES");
        if (charges <= 0) {
            return "Your book has run out of charges.";
        } else if (charges == 1) {
            return "You have one charge left in your book.";
        } else {
            return "Your book has " + charges + " charges left.";
        }
    }

    private static void charge(Player player) {
        int charges = player.getAttributeIntOrZero("CHRONICLE_CHARGES");
        int cardAmt = player.getInventory().getAmount(Items.TELEPORT_CARD);
        int amtToAdd = Math.min(MAX_CHARGES - charges, cardAmt);
        player.getInventory().remove(Items.TELEPORT_CARD, amtToAdd);
        int newCharges = charges + amtToAdd;
        player.incrementNumericAttribute("CHRONICLE_CHARGES", amtToAdd);
        if (amtToAdd == 1)
            player.sendMessage("You add a single charge to your book. It now has " + newCharges + " charges.");
        else
            player.sendMessage("You add " + amtToAdd + " charges to your book. It now has " + newCharges + " charges.");
    }

    static {
        ItemAction.registerInventory(Items.CHRONICLE, "teleport", (player, item) -> teleport(player));
        ItemAction.registerInventory(Items.CHRONICLE, "check charges", (player, item) -> check(player));
        ItemAction.registerEquipment(Items.CHRONICLE, "teleport", (player, item) -> teleport(player));
        ItemAction.registerEquipment(Items.CHRONICLE, "check charges", (player, item) -> check(player));
        ItemItemAction.register(Items.CHRONICLE, Items.TELEPORT_CARD, (player, primary, secondary) -> charge(player));
    }
}

package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/2/2023
 */
public class Camulet {

    private static final int MAX_CHARGES = 100;

    private static void teleport(Player player) {
        int currentCharges = player.getAttributeIntOrZero(AttributeKey.CAMULET_CHARGES);
        if (currentCharges <= 0) {
            player.sendMessage("Your Camulet doesn't have any charges remaining.");
            return;
        }
        player.putAttribute(AttributeKey.CAMULET_CHARGES, currentCharges - 1);    // 5 per bucket
        player.sendMessage("Your Camulet now has " + (currentCharges - 1) + " charges remaining.");
        ModernTeleport.teleport(player, 3189, 2923, 0);
    }

    private static void charge(Player player, Item dung) {
        int currentCharges = player.getAttributeIntOrZero(AttributeKey.CAMULET_CHARGES);
        if (currentCharges >= MAX_CHARGES) {
            player.sendMessage("Your Camulet already has the maximum amount of charges.");
            return;
        }
        if (!player.getInventory().hasId(Items.UGTHANKI_DUNG)) {
            player.sendMessage("You can only charge the Camulet with Ugthanki dung.");
            return;
        }
        dung.setId(Items.BUCKET);
        player.putAttribute(AttributeKey.CAMULET_CHARGES, Math.min(MAX_CHARGES, currentCharges + 5));    // 5 per bucket
        player.sendMessage("You add 5 charges to your Camulet. It now has " + player.getAttributeIntOrZero(AttributeKey.CAMULET_CHARGES) + " charges.");
    }

    private static void inspect(Player player) {
        player.sendMessage("Your Camulet has " + player.getAttributeIntOrZero(AttributeKey.CAMULET_CHARGES) + " charges remaining.");
    }

    static {
        ItemAction.registerInventory(Items.CAMULET, "check-charge", (player, item) -> inspect(player));
        ItemAction.registerEquipment(Items.CAMULET, "check", (player, item) -> inspect(player));
        ItemItemAction.register(Items.CAMULET, Items.UGTHANKI_DUNG, ((player, primary, secondary) -> charge(player, secondary)));
        ItemAction.registerEquipment(Items.CAMULET, "teleport", (player, item) -> teleport(player));
        ItemAction.registerInventory(Items.CAMULET, "rub", ((player, item) -> teleport(player)));
    }
}

package io.ruin.model.item.actions.impl;

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
        int currentCharges = player.getCamuletCharges();
        if (currentCharges <= 0) {
            player.sendMessage("Your Camulet doesn't have any charges remaining.");
            return;
        }
        player.setCamuletCharges(currentCharges - 1);
        player.sendMessage("Your Camulet now has " + (currentCharges - 1) + " charges remaining.");
        ModernTeleport.teleport(player, 3189, 2923, 0);
    }

    private static void charge(Player player, Item dung) {
        int currentCharges = player.getCamuletCharges();
        if (currentCharges >= 100) {
            player.sendMessage("Your Camulet already has the maximum amount of charges.");
            return;
        }
        if (!player.getInventory().hasId(Items.UGTHANKI_DUNG)) {
            player.sendMessage("You can only charge the Camulet with Ugthanki dung.");
            return;
        }
        dung.setId(Items.BUCKET);
        player.setCamuletCharges(Math.min(100, currentCharges + 5));    // 5 per bucket
        player.sendMessage("You add 5 charges to your Camulet. It now has " + player.getCamuletCharges() + " charges.");
    }

    private static void inspect(Player player) {
        player.sendMessage("Your Camulet has " + player.getCamuletCharges() + " charges remaining.");
    }

    static {
        ItemAction.registerInventory(Items.CAMULET, "check-charge", (player, item) -> inspect(player));
        ItemAction.registerEquipment(Items.CAMULET, "check", (player, item) -> inspect(player));
        ItemItemAction.register(Items.CAMULET, Items.UGTHANKI_DUNG, ((player, primary, secondary) -> charge(player, secondary)));
        ItemAction.registerEquipment(Items.CAMULET, "teleport", (player, item) -> teleport(player));
        ItemAction.registerInventory(Items.CAMULET, "rub", ((player, item) -> teleport(player)));
    }
}

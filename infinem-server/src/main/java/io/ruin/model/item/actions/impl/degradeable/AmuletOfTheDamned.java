package io.ruin.model.item.actions.impl.degradeable;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/24/2024
 */
public class AmuletOfTheDamned {

    private static void check(Player player, Item item) {
        player.sendMessage("Your Amulet of the damned has " + item.getCharges() + " charges remaining");
    }

    private static void removeCharge(Item item, int amount) {
        int newCharges = item.getCharges() - amount;
        if (newCharges <= 0) {
            item.remove();
            return;
        }
        item.setCharges(newCharges);
    }

    static {
        ItemDefinition.get(Items.AMULET_OF_THE_DAMNED).addPreDefendListener((player, item, hit) -> removeCharge(item, 1));
        EquipAction.register(Items.AMULET_OF_THE_DAMNED_FULL, (player -> {
            Item item = player.getEquipment().findItemIgnoringAttributes(Items.AMULET_OF_THE_DAMNED_FULL, false);
            if (item == null) return;
            item.setId(Items.AMULET_OF_THE_DAMNED);
            item.setCharges(20_000);
        }));
        ItemAction.registerInventory(Items.AMULET_OF_THE_DAMNED, "check", AmuletOfTheDamned::check);
        ItemAction.registerEquipment(Items.AMULET_OF_THE_DAMNED, "check", AmuletOfTheDamned::check);
    }
}

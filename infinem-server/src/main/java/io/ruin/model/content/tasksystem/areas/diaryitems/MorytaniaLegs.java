package io.ruin.model.content.tasksystem.areas.diaryitems;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DailyResetListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.jewellery.JewelleryTeleportBounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/24/2024
 */
public class MorytaniaLegs {

    private static final String KEY = "MORY_LEGS";
    private static final int[] ITEM_IDS = { Items.MORYTANIA_LEGS_1, Items.MORYTANIA_LEGS_2, Items.MORYTANIA_LEGS_3, Items.MORYTANIA_LEGS_4 };

    private static void ectoTeleport(Player player, Item item) {
        if (item.getId() != Items.MORYTANIA_LEGS_4) {
            int maxTeleports = item.getId() == Items.MORYTANIA_LEGS_1 ? 2 : 5;
            if (player.getAttributeIntOrZero(KEY) >= maxTeleports) {
                player.timeTillDailyReset("You've already used your teleports for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(KEY, 1);
        }
        ModernTeleport.teleport(player, JewelleryTeleportBounds.ECTOFUNTUS.getBounds());
    }

    private static void burghTeleport(Player player) {
        ModernTeleport.teleport(player, JewelleryTeleportBounds.BURGH.getBounds());
    }

    static {
        for (int itemId : ITEM_IDS) {
            ItemAction.registerInventory(itemId, "ecto teleport", MorytaniaLegs::ectoTeleport);
            ItemAction.registerEquipment(itemId, "ectofuntus pit", MorytaniaLegs::ectoTeleport);
        }
        ItemAction.registerInventory(Items.MORYTANIA_LEGS_3, "burgh teleport", (player, item) -> burghTeleport(player));
        ItemAction.registerEquipment(Items.MORYTANIA_LEGS_3, "burgh de rott", (player, item) -> burghTeleport(player));
        ItemAction.registerInventory(Items.MORYTANIA_LEGS_4, "burgh teleport", (player, item) -> burghTeleport(player));
        ItemAction.registerEquipment(Items.MORYTANIA_LEGS_4, "burgh de rott", (player, item) -> burghTeleport(player));
        DailyResetListener.register(player -> player.removeAttribute(KEY));
    }
}

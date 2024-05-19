package io.ruin.model.content.tasksystem.areas.diaryitems;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.jewellery.JewelleryTeleportBounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/19/2024
 */
public class FremennikSeaBoots {

    private static final String KEY = "SEA_BOOTS";
    private static final int[] ITEM_IDS = { Items.FREMENNIK_SEA_BOOTS_1, Items.FREMENNIK_SEA_BOOTS_2, Items.FREMENNIK_SEA_BOOTS_3, Items.FREMENNIK_SEA_BOOTS_4 };

    private static void teleport(Player player, Item item) {
        if (item.getId() != Items.FREMENNIK_SEA_BOOTS_4) {
            if (player.hasAttribute(KEY)) {
                player.timeTillDailyReset("You've already used your teleport for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(KEY, 1);
        }
        ModernTeleport.teleport(player, JewelleryTeleportBounds.RELLEKKA.getBounds());
    }

    static {
        for (int itemId : ITEM_IDS) {
            ItemAction.registerInventory(itemId, "teleport", FremennikSeaBoots::teleport);
            ItemAction.registerEquipment(itemId, "teleport", FremennikSeaBoots::teleport);
        }
    }
}

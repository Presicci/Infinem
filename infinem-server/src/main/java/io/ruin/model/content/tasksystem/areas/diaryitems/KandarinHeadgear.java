package io.ruin.model.content.tasksystem.areas.diaryitems;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.jewellery.JewelleryTeleportBounds;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/19/2024
 */
public class KandarinHeadgear {

    private static final String KEY = "KAND_HEADGEAR";
    private static final int[] ITEM_IDS = { Items.KANDARIN_HEADGEAR_3, Items.KANDARIN_HEADGEAR_4 };

    private static void teleport(Player player, Item item) {
        if (item.getId() != Items.KANDARIN_HEADGEAR_4) {
            if (player.hasAttribute(KEY)) {
                player.timeTillDailyReset("You've already used your teleport for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(KEY, 1);
        }
        ModernTeleport.teleport(player, JewelleryTeleportBounds.SHERLOCK.getBounds());
    }

    static {
        for (int itemId : ITEM_IDS) {
            ItemAction.registerInventory(itemId, "teleport", KandarinHeadgear::teleport);
            ItemAction.registerEquipment(itemId, "teleport", KandarinHeadgear::teleport);
        }
    }
}

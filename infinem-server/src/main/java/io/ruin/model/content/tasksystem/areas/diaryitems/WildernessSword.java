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
 * Created on 5/25/2024
 */
public class WildernessSword {

    private static final String KEY = "WILDY_SWORD";
    private static final int[] ITEM_IDS = { Items.WILDERNESS_SWORD_3, Items.WILDERNESS_SWORD_4 };

    private static void teleport(Player player, Item item) {
        if (item.getId() != Items.WILDERNESS_SWORD_4) {
            if (player.hasAttribute(KEY)) {
                player.timeTillDailyReset("You've already used your teleport for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(KEY, 1);
        }
        ModernTeleport.wildernessTeleport(player, 47, JewelleryTeleportBounds.FOUNTAIN_OF_RUNE.getBounds());
    }

    static {
        for (int itemId : ITEM_IDS) {
            ItemAction.registerInventory(itemId, "teleport", WildernessSword::teleport);
            ItemAction.registerEquipment(itemId, "teleport", WildernessSword::teleport);
        }
        DailyResetListener.register(player -> player.removeAttribute(KEY));
    }
}

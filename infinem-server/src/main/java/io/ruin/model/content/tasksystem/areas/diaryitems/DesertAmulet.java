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
 * Created on 2/29/2024
 */
public class DesertAmulet {

    private static final String KEY = "DESERT_AMULET";
    private static final int[] ITEM_IDS = { Items.DESERT_AMULET_2, Items.DESERT_AMULET_3 };

    private static void teleport(Player player, Item item, JewelleryTeleportBounds teleportBounds) {
        if (item.getId() != Items.DESERT_AMULET_4) {
            if (player.hasAttribute(KEY)) {
                player.timeTillDailyReset("You've already used your teleport for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(KEY, 1);
        }
        ModernTeleport.teleport(player, teleportBounds.getBounds());
    }

    static {
        for (int id : ITEM_IDS) {
            ItemAction.registerInventory(id, "teleport", (player, item) -> teleport(player, item, JewelleryTeleportBounds.NARDAH));
            ItemAction.registerEquipment(id, "teleport", (player, item) -> teleport(player, item, JewelleryTeleportBounds.NARDAH));
        }
        ItemAction.registerInventory(Items.DESERT_AMULET_4, "nardah", (player, item) -> teleport(player, item, JewelleryTeleportBounds.NARDAH_SHRINE));
        ItemAction.registerInventory(Items.DESERT_AMULET_4, "kalphite cave", (player, item) -> teleport(player, item, JewelleryTeleportBounds.KALPHITE_CAVE));
        ItemAction.registerEquipment(Items.DESERT_AMULET_4, "nardah", (player, item) -> teleport(player, item, JewelleryTeleportBounds.NARDAH_SHRINE));
        ItemAction.registerEquipment(Items.DESERT_AMULET_4, "kalphite cave", (player, item) -> teleport(player, item, JewelleryTeleportBounds.KALPHITE_CAVE));
        DailyResetListener.register(player -> player.removeAttribute(KEY));
    }
}

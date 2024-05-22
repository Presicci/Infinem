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
 * Created on 5/20/2024
 */
public class ArdougneCloak {

    private static final String KEY = "ARDY_CLOAK";
    private static final int[] ITEM_IDS = { Items.ARDOUGNE_CLOAK_2, Items.ARDOUGNE_CLOAK_3, Items.ARDOUGNE_CLOAK_4 };

    private static void farmTeleport(Player player, Item item) {
        if (item.getId() != Items.ARDOUGNE_CLOAK_4) {
            int maxUses = item.getId() == Items.ARDOUGNE_CLOAK_2 ? 3 : 5;
            int uses = player.getAttributeIntOrZero(KEY);
            if (uses >= maxUses) {
                player.timeTillDailyReset("You've already used your teleports for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(KEY, 1);
        }
        ModernTeleport.teleport(player, JewelleryTeleportBounds.ARDOUGNE_FARM.getBounds());
    }

    static {
        ItemAction.registerInventory(Items.ARDOUGNE_CLOAK_1, "monastery teleport", (player, item) -> ModernTeleport.teleport(player, JewelleryTeleportBounds.ARDOUGNE_MONASTERY.getBounds()));
        ItemAction.registerEquipment(Items.ARDOUGNE_CLOAK_1, "monastery teleport", (player, item) -> ModernTeleport.teleport(player, JewelleryTeleportBounds.ARDOUGNE_MONASTERY.getBounds()));
        for (int itemId : ITEM_IDS) {
            ItemAction.registerInventory(itemId, "monastery teleport", (player, item) -> ModernTeleport.teleport(player, JewelleryTeleportBounds.ARDOUGNE_MONASTERY.getBounds()));
            ItemAction.registerEquipment(itemId, "monastery teleport", (player, item) -> ModernTeleport.teleport(player, JewelleryTeleportBounds.ARDOUGNE_MONASTERY.getBounds()));
            ItemAction.registerInventory(itemId, "farm teleport", ArdougneCloak::farmTeleport);
            ItemAction.registerEquipment(itemId, "farm teleport", ArdougneCloak::farmTeleport);
        }
        DailyResetListener.register(player -> player.removeAttribute(KEY));
    }
}

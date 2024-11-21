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
 * Created on 11/21/2024
 */
public class RadasBlessing {

    private static final String WOODLAND_KEY = "RADA_WOODLAND";
    private static final String KARUULM_KEY = "RADA_KARUULM";

    private static final int RADA_1 = 22941, RADA_2 = 22943, RADA_3 = 22945, RADA_4 = 22947;
    private static final int[] ITEM_IDS = { RADA_1, RADA_2, RADA_3, RADA_4 };

    private static void woodlandTeleport(Player player, Item item) {
        if (item.getId() != RADA_3 && item.getId() != RADA_4) {
            if (player.getAttributeIntOrZero(WOODLAND_KEY) >= (item.getId() == RADA_1 ? 3 : 5)) {
                player.timeTillDailyReset("You've already used your teleports for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(WOODLAND_KEY, 1);
        }
        ModernTeleport.teleport(player, JewelleryTeleportBounds.RADA_WOODLAND.getBounds());
    }

    private static void karuulmTeleport(Player player, Item item) {
        if (item.getId() != RADA_4) {
            if (player.getAttributeIntOrZero(KARUULM_KEY) >= 3) {
                player.timeTillDailyReset("You've already used your teleports for the day.<br><br>");
                return;
            }
            player.incrementNumericAttribute(KARUULM_KEY, 1);
        }
        ModernTeleport.teleport(player, JewelleryTeleportBounds.RADA_KARUULM.getBounds());
    }

    static {
        for (int itemId : ITEM_IDS) {
            ItemAction.registerInventory(itemId, "kourend woodland", RadasBlessing::woodlandTeleport);
            ItemAction.registerInventory(itemId, "mount karuulm", RadasBlessing::karuulmTeleport);
            ItemAction.registerEquipment(itemId, "kourend woodland", RadasBlessing::woodlandTeleport);
            ItemAction.registerEquipment(itemId, "mount karuulm", RadasBlessing::karuulmTeleport);
        }
        DailyResetListener.register(player -> {
            player.removeAttribute(WOODLAND_KEY);
            player.removeAttribute(KARUULM_KEY);
        });
    }
}

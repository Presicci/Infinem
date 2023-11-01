package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class FletchingSkillCape {
    private static final int MITHRIL_GRAPPLE = 9419;
    private static final int BRONZE_CROSSBOW = 9174;

    private static final int CAPE = StatType.Fletching.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Fletching.trimmedCapeId;

    public static void search(Player player) {
        if (player.getInventory().hasFreeSlots(1) && !player.getInventory().contains(MITHRIL_GRAPPLE)) {
            player.getInventory().add(MITHRIL_GRAPPLE, 1);
            player.sendMessage("You search your cape for a mithril grapple...");
            if (player.getInventory().hasFreeSlots(1) && !player.getInventory().contains(BRONZE_CROSSBOW)) {
                player.getInventory().add(BRONZE_CROSSBOW, 1);
                player.sendMessage("... and a bronze crossbow.");
            }
        } else {
            player.sendMessage("You already have a mithril grapple.");
        }
    }

    static {
        ItemAction.registerInventory(CAPE, "Search", (player, item) -> search(player));
        ItemAction.registerEquipment(CAPE, "Search", (player, item) -> search(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Search", (player, item) -> search(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Search", (player, item) -> search(player));
    }
}

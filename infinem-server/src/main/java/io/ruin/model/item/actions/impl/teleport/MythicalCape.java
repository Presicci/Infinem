package io.ruin.model.item.actions.impl.teleport;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/1/2023
 */
public class MythicalCape {
    private static final int MYTHICAL_CAPE = 22114;

    static {
        ItemAction.registerInventory(MYTHICAL_CAPE, "teleport", ((player, item) -> teleport(player)));
        ItemAction.registerEquipment(MYTHICAL_CAPE, "teleport", ((player, item) -> teleport(player)));
    }

    public static void teleport(Player player) {
        ModernTeleport.teleport(player, new Bounds(2456, 2851, 2458, 2852, 0));
    }
}

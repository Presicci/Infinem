package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;
import io.ruin.model.map.Bounds;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/21/2020
 */
public class CraftingSkillCape {
    private static final int CAPE = StatType.Crafting.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Crafting.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Teleport", (player, item) -> teleport(player));
        ItemAction.registerEquipment(CAPE, "Teleport", (player, item) -> teleport(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Teleport", (player, item) -> teleport(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Teleport", (player, item) -> teleport(player));
    }

    public static void teleport(Player player) {
        ModernTeleport.teleport(player, new Bounds(2935, 3282, 2936, 3283, 0));
    }
}

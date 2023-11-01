package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/23/2020
 */
public class StrengthSkillCape {
    private static final int CAPE = StatType.Strength.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Strength.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Teleport", (player, item) -> teleport(player));
        ItemAction.registerEquipment(CAPE, "Warriors' Guild", (player, item) -> teleport(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Teleport", (player, item) -> teleport(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Warriors' Guild", (player, item) -> teleport(player));
    }

    protected static void teleport(Player player) {
        ModernTeleport.teleport(player, new Bounds(2850, 3547, 2852, 3549, 0));
    }
}

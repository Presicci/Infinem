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
public class FishingSkillCape {
    private static final int CAPE = StatType.Fishing.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Fishing.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));
        ItemAction.registerEquipment(CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));

        ItemAction.registerInventory(TRIMMED_CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
    }

    private static void teleportToFishingGuild(Player player) {
        ModernTeleport.teleport(player, new Bounds(2493,3414,2595,3416,0));
    }

    private static void teleportToOttosGrotto(Player player) {
        ModernTeleport.teleport(player, new Bounds(2501,3493,2502,3495,0));
    }
}

package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
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
    private static final int MASTER_CAPE = StatType.Fishing.masterCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));
        ItemAction.registerEquipment(CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));
        ItemAction.registerInventory(CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
        ItemAction.registerEquipment(CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
        ItemAction.registerInventory(MASTER_CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));
        ItemAction.registerEquipment(MASTER_CAPE, "Fishing Guild", (player, item) -> teleportToFishingGuild(player));
        ItemAction.registerInventory(MASTER_CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
        ItemAction.registerEquipment(MASTER_CAPE, "Otto's Grotto", (player, item) -> teleportToOttosGrotto(player));
    }

    public static void teleport(Player player) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("Fishing Guild", () -> teleportToFishingGuild(player)),
                new Option("Otto's Grotto", () -> teleportToOttosGrotto(player)))
        );
    }

    private static void teleportToFishingGuild(Player player) {
        ModernTeleport.teleport(player, new Bounds(2604, 3400, 2607, 3402, 0));
    }

    private static void teleportToOttosGrotto(Player player) {
        ModernTeleport.teleport(player, new Bounds(2501,3493,2502,3495,0));
    }
}

package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class FarmingSkillCape {

    private static final int CAPE = StatType.Farming.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Farming.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Farming.masterCapeId;

    public static boolean wearingFarmingCape(Player player) {
        int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return cape == CAPE || cape == TRIMMED_CAPE || cape == MASTER_CAPE || cape == 13342;
    }

    protected static void teleportToFarmingGuild(Player player, Item item) {
        player.sendMessage("Please note that the farming guild is not operated at this time.");
        ModernTeleport.teleport(player, new Position(1248, 3721, 0));
    }

    static {
        ItemAction.registerInventory(9810, "Teleport", FarmingSkillCape::teleportToFarmingGuild);
        ItemAction.registerInventory(9811, "Teleport", FarmingSkillCape::teleportToFarmingGuild);
        ItemAction.registerEquipment(9810, "Teleport", FarmingSkillCape::teleportToFarmingGuild);
        ItemAction.registerEquipment(9811, "Teleport", FarmingSkillCape::teleportToFarmingGuild);
    }
}

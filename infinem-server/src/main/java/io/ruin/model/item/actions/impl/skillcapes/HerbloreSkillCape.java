package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/21/2020
 */
public class HerbloreSkillCape {
    private static final int CAPE = StatType.Herblore.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Herblore.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Herblore.masterCapeId;
    private static final int PESTLE = 233;

    static {
        ItemAction.registerInventory(CAPE, "Search", (player, item) -> search(player));
        ItemAction.registerEquipment(CAPE, "Search", (player, item) -> search(player));

        ItemAction.registerInventory(TRIMMED_CAPE, "Search", (player, item) -> search(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Search", (player, item) -> search(player));
    }

    protected static void search(Player player) {
        if (player.getInventory().hasFreeSlots(1) && !player.getInventory().contains(PESTLE)) {
            player.getInventory().add(PESTLE, 1);
            player.sendMessage("You search your cape for a Pestle and mortar.");
        } else {
            player.sendMessage("You already have a Pestle and mortar.");
        }
    }

    public static boolean wearingHerbloreCape(Player player) {
        int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return cape == CAPE || cape == TRIMMED_CAPE || cape == 13342 || cape == MASTER_CAPE;
    }
}

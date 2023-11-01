package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/23/2020
 */
public class DefenceSkillCape {
    private static final int CAPE = StatType.Defence.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Defence.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerInventory(CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));
        ItemAction.registerEquipment(CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerEquipment(CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));

        ItemAction.registerInventory(TRIMMED_CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));
    }

    public static void check(Player player) {
        if (player.getHp() <= player.getMaxHp() * 0.10 && !player.getCombat().isDead()) {
            Item cape = player.getEquipment().get(Equipment.SLOT_CAPE);
            if (cape == null || cape.getId() != CAPE || cape.getId() != TRIMMED_CAPE)
                return;
            if(ModernTeleport.teleport(player, 2026, 3576, 0)) {
                player.sendFilteredMessage("Your cape saves you.");
            }
        }
    }

    private static void defenceToggle(Player player) {
        player.sendMessage("The cape doesn't wish to see you harmed and will remain active always.");
    }
    private static void defenceRespawn(Player player) {
        player.sendMessage("The cape will only see you safely to home.");
    }
}

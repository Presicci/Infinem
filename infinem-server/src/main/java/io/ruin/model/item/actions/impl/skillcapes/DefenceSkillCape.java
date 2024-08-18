package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerBoolean;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.MagicTeleportBounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/23/2020
 */
public class DefenceSkillCape {
    private static final int CAPE = StatType.Defence.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Defence.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Defence.masterCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerInventory(CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));
        ItemAction.registerEquipment(CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerEquipment(CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));

        ItemAction.registerInventory(TRIMMED_CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));

        ItemAction.registerInventory(MASTER_CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerInventory(MASTER_CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));
        ItemAction.registerEquipment(MASTER_CAPE, "Toggle Effect", (player, item) -> defenceToggle(player));
        ItemAction.registerEquipment(MASTER_CAPE, "Toggle Respawn", (player, item) -> defenceRespawn(player));
    }

    public static void check(Player player) {
        if (player.getHp() <= player.getMaxHp() * 0.10 && !player.getCombat().isDead()) {
            if (!PlayerBoolean.DEFENCE_CAPE.has(player)) return;
            Item cape = player.getEquipment().get(Equipment.SLOT_CAPE);
            if (cape == null
                    || (cape.getId() != CAPE && cape.getId() != TRIMMED_CAPE && cape.getId() != MASTER_CAPE))
                return;
            Bounds teleportBounds = player.getRespawnPoint().getBounds();
            if (ModernTeleport.teleport(player, teleportBounds)) {
                player.sendFilteredMessage("Your cape saves you.");
            }
        }
    }

    private static void defenceToggle(Player player) {
        boolean status = PlayerBoolean.DEFENCE_CAPE.toggle(player);
        if (status) player.sendMessage("Your defence cape will now act as a ring of life.");
        else player.sendMessage("Your defence cape will " + Color.RED.wrap("no longer") + " act as a ring of life.");
    }
    private static void defenceRespawn(Player player) {
        player.sendMessage("The cape will only see you safely to home.");
    }
}

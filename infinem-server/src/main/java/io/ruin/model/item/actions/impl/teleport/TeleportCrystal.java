package io.ruin.model.item.actions.impl.teleport;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.impl.locations.prifddinas.PrifCityEntrance;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/7/2024
 */
public class TeleportCrystal {

    private static final Position LLETYA = new Position(2331, 3172, 0);
    private static final Position PRIF = new Position(3263, 6066, 0);

    private static void teleport(Player player, Item item, Position destination, int toId) {
        if (destination == PRIF && !PrifCityEntrance.prifSkillCheckNoMessage(player)) return;
        if (toId > 0) item.setId(toId);
        ModernTeleport.teleport(player, destination);
        player.getTaskManager().doLookupByUUID(787);    // Use an Elven Teleport Crystal
    }

    static {
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_5, "lletya", (player, item) -> teleport(player, item, LLETYA, Items.TELEPORT_CRYSTAL_4));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_5, "prifddinas", (player, item) -> teleport(player, item, PRIF, Items.TELEPORT_CRYSTAL_4));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_4, "lletya", (player, item) -> teleport(player, item, LLETYA, Items.TELEPORT_CRYSTAL_3));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_4, "prifddinas", (player, item) -> teleport(player, item, PRIF, Items.TELEPORT_CRYSTAL_3));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_3, "lletya", (player, item) -> teleport(player, item, LLETYA, Items.TELEPORT_CRYSTAL_2));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_3, "prifddinas", (player, item) -> teleport(player, item, PRIF, Items.TELEPORT_CRYSTAL_2));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_2, "lletya", (player, item) -> teleport(player, item, LLETYA, Items.TELEPORT_CRYSTAL_1));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_2, "prifddinas", (player, item) -> teleport(player, item, PRIF, Items.TELEPORT_CRYSTAL_1));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_1, "lletya", (player, item) -> teleport(player, item, LLETYA, 6103));
        ItemAction.registerInventory(Items.TELEPORT_CRYSTAL_1, "prifddinas", (player, item) -> teleport(player, item, PRIF, 6103));
        ItemAction.registerInventory(23946, "lletya", (player, item) -> teleport(player, item, LLETYA, -1));
        ItemAction.registerInventory(23946, "prifddinas", (player, item) -> teleport(player, item, PRIF, -1));
    }
}

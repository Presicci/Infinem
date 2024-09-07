package io.ruin.model.item.actions.impl.teleport;

import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/7/2024
 */
public class DrakansMedallion {

    static {
        ItemAction.registerInventory(22400, "ver sinhaza", (player, item) -> player.sendMessage("That area isn't accessible yet."));
        ItemAction.registerInventory(22400, "darkmeyer", (player, item) -> ModernTeleport.teleport(player, new Bounds(3590, 3334, 3594, 3339, 0)));
        ItemAction.registerInventory(22400, "slepe", (player, item) -> ModernTeleport.teleport(player, new Bounds(3807, 9699, 3809, 9701, 1)));
        ItemAction.registerEquipment(22400, "ver sinhaza", (player, item) -> player.sendMessage("That area isn't accessible yet."));
        ItemAction.registerEquipment(22400, "darkmeyer", (player, item) -> ModernTeleport.teleport(player, new Bounds(3590, 3334, 3594, 3339, 0)));
        ItemAction.registerEquipment(22400, "slepe", (player, item) -> ModernTeleport.teleport(player, new Bounds(3807, 9699, 3809, 9701, 1)));
    }
}

package io.ruin.model.content.tasksystem.areas.diaryitems;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.jewellery.JewelleryTeleportBounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/22/2024
 */
public class KaramjaGloves {

    private static void gemMineTeleport(Player player) {
        ModernTeleport.teleport(player, JewelleryTeleportBounds.SHILO_GEM_MINE.getBounds());
    }

    private static void duradelTeleport(Player player) {
        ModernTeleport.teleport(player, JewelleryTeleportBounds.DURADEL.getBounds());
    }

    static {
        ItemAction.registerInventory(Items.KARAMJA_GLOVES_3, "gem mine", (player, item) -> gemMineTeleport(player));
        ItemAction.registerEquipment(Items.KARAMJA_GLOVES_3, "gem mine", (player, item) -> gemMineTeleport(player));
        ItemAction.registerInventory(Items.KARAMJA_GLOVES_4, "gem mine", (player, item) -> gemMineTeleport(player));
        ItemAction.registerEquipment(Items.KARAMJA_GLOVES_4, "gem mine", (player, item) -> gemMineTeleport(player));
        ItemAction.registerInventory(Items.KARAMJA_GLOVES_4, "duradel", (player, item) -> duradelTeleport(player));
        ItemAction.registerEquipment(Items.KARAMJA_GLOVES_4, "duradel", (player, item) -> duradelTeleport(player));
    }
}

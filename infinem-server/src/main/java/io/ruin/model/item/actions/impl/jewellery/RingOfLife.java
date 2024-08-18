package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerBoolean;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.MagicTeleportBounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.utility.Color;

public class RingOfLife {

    public static void check(Player player) {
        if(player.getDuel().stage >= 4)
            return;
        if (player.getHp() <= player.getMaxHp() * 0.10 && !player.getCombat().isDead()) {
            Item ring = player.getEquipment().get(Equipment.SLOT_RING);
            if (ring == null || ring.getId() != 2570)
                return;
            Bounds teleportBounds = PlayerBoolean.ROL_ARDY.has(player) ? MagicTeleportBounds.ARDOUGNE.getBounds() : player.getRespawnPoint().getBounds();
            if (ModernTeleport.teleport(player, teleportBounds)) {
                ring.remove();
                player.sendFilteredMessage("Your ring of life crumbles to dust.");
            }
        }
    }

    private static void toggleLocation(Player player) {
        if (!AreaReward.ARDOUGNE_RING_OF_LIFE.checkReward(player, "toggle the teleport to Ardougne.")) return;
        boolean status = PlayerBoolean.ROL_ARDY.toggle(player);
        if (status) player.sendMessage("Your ring of life will now teleport you to Ardougne.");
        else player.sendMessage("Your ring of life will now teleport you to your respawn point.");
    }

    static {
        ItemAction.registerInventory(Items.RING_OF_LIFE, "toggle-respawn", (player, item) -> toggleLocation(player));
    }
}

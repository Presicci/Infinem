package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.slayer.SlayerMaster;

public enum SlayerRing {

    /**
     * Regular
     */
    EIGHT(11866, 8, 11867),
    SEVEN(11867, 7, 11868),
    SIX(11868, 6, 11869),
    FIVE(11869, 5, 11870),
    FOUR(11870, 4, 11871),
    THREE(11871, 3, 11872),
    TWO(11872, 2, 11873),
    ONE(11873, 1, -1),

    /**
     * Eternal
     */
    ETERNAL(21268, -1, -1);

    private final int id, charges, replacementId;

    SlayerRing(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("ring", false,
                new JeweleryTeleports.Teleport("Slayer Tower", JewelleryTeleportBounds.SLAYER_TOWER.getBounds()),
                new JeweleryTeleports.Teleport("Fremennik Slayer Dungeon", JewelleryTeleportBounds.FREMENNIK_SLAYER_DUNGEON.getBounds()),
                new JeweleryTeleports.Teleport("Stronghold Slayer Cave", JewelleryTeleportBounds.STRONGHOLD_SLAYER_CAVE.getBounds()),
                new JeweleryTeleports.Teleport("Dark Beasts", JewelleryTeleportBounds.DARK_BEASTS.getBounds())
        );
        for(SlayerRing ring : values()) {
            teleports.register(ring.id, ring.charges, ring.replacementId);
            ItemAction.registerEquipment(ring.id, "check", (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerInventory(ring.id, "log", (player, item) -> KillCounter.openOwnSlayer(player));

        }
    }

}

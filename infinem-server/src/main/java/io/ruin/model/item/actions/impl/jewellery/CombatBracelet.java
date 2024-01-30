package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;

public enum CombatBracelet {

    SIX(11972, 6, 11974),
    FIVE(11974, 5, 11118),
    FOUR(11118, 4, 11120),
    THREE(11120, 3, 11122),
    TWO(11122, 2, 11124),
    ONE(11124, 1, 11126),
    UNCHARGED(11126, 0, -1);

    private final int id, charges, replacementId;

    CombatBracelet(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("bracelet", false,
                new JeweleryTeleports.Teleport("Warrior's Guild", JewelleryTeleportBounds.WARRIORS_GUILD.getBounds()),
                new JeweleryTeleports.Teleport("Champion's Guild", JewelleryTeleportBounds.CHAMPIONS_GUILD.getBounds()),
                new JeweleryTeleports.Teleport("Edgeville Monastery", JewelleryTeleportBounds.EDGEVILLE_MONASTERY.getBounds()),
                new JeweleryTeleports.Teleport("Ranging Guild", JewelleryTeleportBounds.RANGING_GUILD.getBounds())
        );
        for(CombatBracelet bracelet : values())
            teleports.register(bracelet.id, bracelet.charges, bracelet.replacementId);
    }

}

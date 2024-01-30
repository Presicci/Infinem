package io.ruin.model.item.actions.impl.jewellery;

public enum DigsitePendant {

    FIVE(11194, 5, 11193),
    FOUR(11193, 4, 11192),
    THREE(11192, 3, 11191),
    TWO(11191, 2, 11190),
    ONE(11190, 1, -1);

    private final int id, charges, replacementId;

    DigsitePendant(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("pendant", false,
                new JeweleryTeleports.Teleport("Digsite", JewelleryTeleportBounds.DIGSITE.getBounds()),
                new JeweleryTeleports.Teleport("House on the Hill", JewelleryTeleportBounds.HOUSE_ON_THE_HILL.getBounds()),
                new JeweleryTeleports.Teleport("Lithkren", JewelleryTeleportBounds.LITHKREN.getBounds())
        );
        for(DigsitePendant pendant : values())
            teleports.register(pendant.id, pendant.charges, pendant.replacementId);
    }

}


package io.ruin.model.item.actions.impl.jewellery;

public enum GamesNecklace {

    EIGHT(3853, 8, 3855),
    SEVEN(3855, 7, 3857),
    SIX(3857, 6, 3859),
    FIVE(3859, 5, 3861),
    FOUR(3861, 4, 3863),
    THREE(3863, 3, 3865),
    TWO(3865, 2, 3867),
    ONE(3867, 1, -1);

    private final int id, charges, replacementId;

    GamesNecklace(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("necklace", false,
                new JeweleryTeleports.Teleport("Burthorpe", JewelleryTeleportBounds.BURTHORPE.getBounds()),
                new JeweleryTeleports.Teleport("Barbarian Outpost", JewelleryTeleportBounds.BARBARIAN_OUTPOST.getBounds()),
                new JeweleryTeleports.Teleport("Corporeal Beast", JewelleryTeleportBounds.CORPOREAL_BEAST.getBounds()),
                new JeweleryTeleports.Teleport("Tears of Guthix", JewelleryTeleportBounds.TEARS_OF_GUTHIX.getBounds()),
                new JeweleryTeleports.Teleport("Wintertodt Camp", JewelleryTeleportBounds.WINTERTODT_CAMP.getBounds())
        );
        for(GamesNecklace necklace : values())
            teleports.register(necklace.id, necklace.charges, necklace.replacementId);
    }

}

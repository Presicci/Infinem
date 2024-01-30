package io.ruin.model.item.actions.impl.jewellery;

public enum AmuletOfGlory {

    /**
     * Regular glories
     */
    SIX(11978, 6, 11976),
    FIVE(11976, 5, 1712),
    FOUR(1712, 4, 1710),
    THREE(1710, 3, 1708),
    TWO(1708, 2, 1706),
    ONE(1706, 1, 1704),
    UNCHARGED(1704, 0, -1),

    /**
     * Trimmed glories
     */
    SIX_T(11964, 6, 11966),
    FIVE_T(11966, 5, 10354),
    FOUR_T(10354, 4, 10356),
    THREE_T(10356, 3, 10358),
    TWO_T(10358, 2, 10360),
    ONE_T(10360, 1, 10362),
    UNCHARGED_T(10362, 0, -1),

    /**
     * Eternal
     */
    ETERNAL(19707, -1, -1);

    private final int id, charges, replacementId;

    AmuletOfGlory(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("amulet", true,
                new JeweleryTeleports.Teleport("Edgeville", JewelleryTeleportBounds.EDGEVILLE.getBounds()),
                new JeweleryTeleports.Teleport("Karamja", JewelleryTeleportBounds.KARAMJA.getBounds()),
                new JeweleryTeleports.Teleport("Draynor Village", JewelleryTeleportBounds.DRAYNOR_VILLAGE.getBounds()),
                new JeweleryTeleports.Teleport("Al Kharid", JewelleryTeleportBounds.AL_KHARID.getBounds())
        );
        for(AmuletOfGlory glory : values())
            teleports.register(glory.id, glory.charges, glory.replacementId);
    }

}

package io.ruin.model.item.actions.impl.jewellery;

public enum RingOfDueling {

    EIGHT(2552, 8, 2554),
    SEVEN(2554, 7, 2556),
    SIX(2556, 6, 2558),
    FIVE(2558, 5, 2560),
    FOUR(2560, 4, 2562),
    THREE(2562, 3, 2564),
    TWO(2564, 2, 2566),
    ONE(2566, 1, -1);

    public final int id, charges, replacementId;

    RingOfDueling(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("ring", false,
                new JeweleryTeleports.Teleport("Duel Arena", JewelleryTeleportBounds.DUEL_ARENA.getBounds()),
                new JeweleryTeleports.Teleport("Castle Wars", JewelleryTeleportBounds.CASTLE_WARS.getBounds()),
                new JeweleryTeleports.Teleport("Ferox Enclave", JewelleryTeleportBounds.FEROX_ENCLAVE.getBounds())
        );
        for(RingOfDueling ring : values())
            teleports.register(ring.id, ring.charges, ring.replacementId);
    }

}

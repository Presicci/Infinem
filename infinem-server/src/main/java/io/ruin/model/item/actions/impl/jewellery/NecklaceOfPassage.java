package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.item.actions.impl.jewellery.JeweleryTeleports.Teleport;

public enum NecklaceOfPassage {

    FIVE(21146, 5, 21149),
    FOUR(21149, 4, 21151),
    THREE(21151, 3, 21153),
    TWO(21153, 2, 21155),
    ONE(21155, 1, -1);

    private final int id, charges, replacementId;

    NecklaceOfPassage(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("necklace", false,
                new Teleport("Wizard's Tower", JewelleryTeleportBounds.WIZARDS_TOWER.getBounds()),
                new Teleport("The Outpost", JewelleryTeleportBounds.THE_OUTPOST.getBounds()),
                new Teleport("Eagle's Eyrie", JewelleryTeleportBounds.EAGLES_EYRIE.getBounds())
        );
        for(NecklaceOfPassage necklace : values())
            teleports.register(necklace.id, necklace.charges, necklace.replacementId);
    }

}
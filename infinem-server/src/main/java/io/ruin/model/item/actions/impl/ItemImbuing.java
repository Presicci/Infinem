package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.ItemDefinition;

public enum ItemImbuing {
    BLACK_MASK(8921, 11784, 1_250_000),
    SLAYER_HELMET(11864, 11865, 1_250_000),
    BLACK_SLAYER_HELMET(19639, 19641, 1_250_000),
    GREEN_SLAYER_HELMET(19643, 19645, 1_250_000),
    RED_SLAYER_HELMET(19647, 19649, 1_250_000),
    TURQUOISE_SLAYER_HELMET(21888, 21890, 1_250_000),
    SALVE_AMULET(4081, 12017, 800_000),
    RING_OF_SUFFERING(19550, 19710, 725_000),
    RING_OF_SUFFERING_R(20655, 20657, 725_000),
    RING_OF_THE_GODS(12601, 13202, 650_000),
    BERSERKER_RING(6737, 11773, 650_000),
    WARRIOR_RING(6735, 11772, 650_000),
    ARCHERS_RING(6733, 11771, 650_000),
    SEERS_RING(6731, 11770, 650_000),
    TYRANNICAL_RING(12603, 12691, 650_000),
    TREASONOUS_RING(12605, 12692, 650_000);

    public final int regularId, nmzImbue, nmzCost;

    ItemImbuing(int regularId, int nmzImbue, int nmzCost) {
        this.regularId = regularId;
        this.nmzImbue = nmzImbue;
        this.nmzCost = nmzCost;

        ItemDefinition regularDef = ItemDefinition.get(regularId);
        ItemDefinition upgradeDef = ItemDefinition.get(nmzImbue);
        if(upgradeDef.protectValue < regularDef.protectValue)
            upgradeDef.protectValue = regularDef.protectValue;
        upgradeDef.upgradedFrom = this;
    }

    public static ItemImbuing getImbueable(int id) {
        for (ItemImbuing imbueable : values())
            if (imbueable.regularId == id)
                return imbueable;
        return null;
    }
}


package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.ItemDefinition;

public enum ItemImbuing {
    BLACK_MASK(8921, 11784),
    SLAYER_HELMET(11864, 11865),
    BLACK_SLAYER_HELMET(19639, 19641),
    GREEN_SLAYER_HELMET(19643, 19645),
    RED_SLAYER_HELMET(19647, 19649),
    TURQUOISE_SLAYER_HELMET(21888, 21890),
    SALVE_AMULET(4081, 12017),
    RING_OF_SUFFERING(19550, 19710),
    RING_OF_SUFFERING_R(20655, 20657),
    RING_OF_THE_GODS(12601, 13202),
    BERSERKER_RING(6737, 11773),
    WARRIOR_RING(6735, 11772),
    ARCHERS_RING(6733, 11771),
    SEERS_RING(6731, 11770),
    TYRANNICAL_RING(12603, 12691),
    TREASONOUS_RING(12605, 12692),
    ;

    public final int regularId, upgradeId;

    ItemImbuing(int regularId, int upgradeId) {
        this.regularId = regularId;
        this.upgradeId = upgradeId;

        ItemDefinition regularDef = ItemDefinition.get(regularId);
        ItemDefinition upgradeDef = ItemDefinition.get(upgradeId);
        if(upgradeDef.protectValue < regularDef.protectValue)
            upgradeDef.protectValue = regularDef.protectValue;
        upgradeDef.upgradedFrom = this;
    }

}


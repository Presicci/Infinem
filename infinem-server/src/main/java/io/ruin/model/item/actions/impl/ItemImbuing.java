package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.ItemDefinition;

public enum ItemImbuing {

    VOID_TOP(8839, 13072, 7000000),
    VOID_BOTTOM(8840, 13073, 7000000),
    MAGIC_SHORT(861, 12788, 4000000),
    SEERS_RING(6731, 11770, 5000000),
    ARCHERS_RING(6733, 11771, 5000000),
    WARRIOR_RING(6735, 11772, 5000000),
    BERSERKER_RING(6737, 11773, 5000000),
    SLAYER_HELMET(11864, 11865, 10000000),
    TYRANNICAL_RING(12603, 12691, 5000000),
    TREASONOUS_RING(12605, 12692, 5000000),
    RING_OF_THE_GODS(12601, 13202, 5000000),
    BLACK_MASK(8921, 11784, 10000000),
    BLACK_SLAYER_HELMET(19639, 19641, 10000000),
    GREEN_SLAYER_HELMET(19643, 19645, 10000000),
    RED_SLAYER_HELMET(19647, 19649, 10000000),
    TURQUOISE_SLAYER_HELMET(21888, 21890, 10000000),
    RING_OF_SUFFERING(19550, 19710, 5000000),
    RING_OF_SUFFERING_R(20655, 20657, 5000000),
    GUTHIX_CAPE(2413, 21793, 3000000),
    ZAMORAK_CAPE(2414, 21795, 3000000),
    SARADOMIN_CAPE(2412, 21791, 3000000),
    SALVE_AMULET(4081, 12017, 3000000);
    public final int regularId, upgradeId;

    public final int coinUpgradeCost;

    ItemImbuing(int regularId, int upgradeId, int coinUpgradeCost) {
        this.regularId = regularId;
        this.upgradeId = upgradeId;
        this.coinUpgradeCost = coinUpgradeCost;

        ItemDefinition regularDef = ItemDefinition.get(regularId);
        ItemDefinition upgradeDef = ItemDefinition.get(upgradeId);
        if(upgradeDef.protectValue < regularDef.protectValue)
            upgradeDef.protectValue = regularDef.protectValue;
        upgradeDef.upgradedFrom = this;
    }

}


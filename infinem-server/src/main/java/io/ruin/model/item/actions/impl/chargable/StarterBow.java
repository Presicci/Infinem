package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.ItemDef;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/23/2024
 */
public class StarterBow extends ChargeableItem {
    static {
        ChargeableItem chargeableItem = new StarterBow();
        register(chargeableItem);
        ItemDef.get(28555).addPreTargetDefendListener((player, item, hit, target) -> removeCharge(chargeableItem, player, item, 1));
    }

    @Override
    protected int getChargedId() {
        return 28555;
    }

    @Override
    protected int getUnchargedId() {
        return -1;
    }

    @Override
    protected int getChargeItem() {
        return -1;
    }

    @Override
    protected int getMaxCharges() {
        return 2000;
    }

    @Override
    protected int getChargesPerItem() {
        return 0;
    }

    @Override
    protected boolean doesReturnItems() {
        return false;
    }
}

package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.UnlockPerk;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/11/2024
 */
public class GoldPickupPerk extends UnlockPerk {

    @Override
    protected int getRequiredKills() {
        return 1000;
    }

    @Override
    protected String getLabel(int killCount) {
        return "Automatically Pickup Gold";
    }
}

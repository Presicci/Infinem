package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.IntervalPerk;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class RespawnPerk extends IntervalPerk {

    @Override
    protected double getIncrement() {
        return 0.01;
    }

    @Override
    protected int getInterval() {
        return 25;
    }

    @Override
    protected int getBreakpointCap() {
        return 75;
    }

    @Override
    protected String getLabel(int killCount) {
        return inverseDoubleToPercentage(getMultiplier(killCount)) + "% Faster Respawn Time";
    }

    @Override
    public double getMultiplier(int killCount) {
        double multiplier = super.getMultiplier(killCount);
        return multiplier == 0 ? 1 : 1 - multiplier;
    }
}

package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.IntervalPerk;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class RespawnPerk extends IntervalPerk {

    public RespawnPerk(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    protected double getIncrement() {
        return 0.01;
    }

    @Override
    protected int getInterval() {
        return isBoss ? getBossInterval() : 25;
    }

    private int getBossInterval() {
        return 50;
    }

    @Override
    protected int getIntervalCap() {
        return 75;
    }

    @Override
    protected boolean getInvertedPercentage() {
        return true;
    }

    @Override
    protected String getLabel(int killCount) {
        return "*% Faster Respawn Time";
    }
}

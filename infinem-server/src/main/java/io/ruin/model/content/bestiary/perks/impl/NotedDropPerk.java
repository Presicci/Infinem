package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.IntervalPerk;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class NotedDropPerk extends IntervalPerk {

    public NotedDropPerk(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    protected double getIncrement() {
        return 0.2;
    }

    @Override
    protected int getInterval() {
        return isBoss ? getBossInterval() : 1500;
    }

    private int getBossInterval() {
        return 1000;
    }

    @Override
    protected int getIntervalCap() {
        return 5;
    }

    @Override
    protected String getLabel(int killCount) {
        return "% Noted Drop Chance";
    }
}

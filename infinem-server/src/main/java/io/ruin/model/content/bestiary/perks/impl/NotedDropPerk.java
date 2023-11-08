package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.IntervalPerk;

import java.text.DecimalFormat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class NotedDropPerk extends IntervalPerk {

    @Override
    protected double getIncrement() {
        return 0.2;
    }

    @Override
    protected int getInterval() {
        return 1500;
    }

    @Override
    protected int getBreakpointCap() {
        return -1;
    }

    @Override
    protected String getLabel(int killCount) {
        return new DecimalFormat("#").format(getMultiplier(killCount) * 100f) + "% Noted Drop Chance";
    }
}

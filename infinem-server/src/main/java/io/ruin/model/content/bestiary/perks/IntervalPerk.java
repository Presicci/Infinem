package io.ruin.model.content.bestiary.perks;

import java.text.DecimalFormat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public abstract class IntervalPerk extends BestiaryPerk {

    public IntervalPerk() {}

    protected abstract double getIncrement();
    protected abstract int getInterval();
    protected abstract int getIntervalCap();

    @Override
    protected String getBreakpointString(int killCount) {
        int nextBreakpoint = getNextBreakpoint(killCount);
        if (nextBreakpoint <= 0) return "";
        double increment = getIncrement();
        return ("+" + new DecimalFormat("#").format(increment * 100f)) + "% at " + super.getBreakpointString(killCount);
    }

    public double getMultiplier(int killCount) {
        if (killCount < getInterval()) return getInvertedPercentage() ? 1 : 0;
        double multi = getIncrement() * Math.floor((double) killCount / getInterval());
        if (getIntervalCap() > 0) {
            multi = Math.min(getIntervalCap() * getIncrement(), multi);
        }
        System.out.println(multi);
        return getInvertedPercentage() ? 1D - multi : multi;
    }

    protected int getNextBreakpoint(int killCount) {
        int currentInterval = killCount / getInterval();
        int intervalCap = getIntervalCap();
        if (intervalCap > 0 && currentInterval > intervalCap) return -1;
        return (currentInterval + 1) * getInterval();
    }

    public int getFill(int killCount) {
        int nextBreakpoint = getNextBreakpoint(killCount);
        if (nextBreakpoint == -1) return MAX_FILL;
        return (int) (MAX_FILL * ((double) (getInterval() - (nextBreakpoint - killCount)) / (double) getInterval()));
    }
}

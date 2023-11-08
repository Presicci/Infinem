package io.ruin.model.content.bestiary.perks;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public abstract class IntervalPerk extends BestiaryPerk {

    public IntervalPerk() {}

    protected abstract double getIncrement();
    protected abstract int getInterval();
    protected abstract int getBreakpointCap();

    public double getMultiplier(int killCount) {
        if (killCount < getInterval()) return 0;
        return getIncrement() * (int) (killCount / getInterval());
    }

    protected int getNextBreakpoint(int killCount) {
        int currentBreakpoint = killCount / getInterval();
        int breakpointCap = getBreakpointCap();
        if (breakpointCap> 0 && currentBreakpoint > breakpointCap) return -1;
        return (currentBreakpoint + 1) * getInterval();
    }

    public int getFill(int killCount) {
        int nextBreakpoint = getNextBreakpoint(killCount);
        if (nextBreakpoint == -1) return MAX_FILL;
        return (int) (MAX_FILL * ((double) (getInterval() - (nextBreakpoint - killCount)) / (double) getInterval()));
    }
}

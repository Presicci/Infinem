package io.ruin.model.content.bestiary.perks;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public abstract class MixedPerk extends BestiaryPerk {

    public MixedPerk() {}

    protected abstract Map<Integer, Double> getBreakpoints();
    protected abstract double getIncrement();
    protected abstract int getInterval();

    protected int getLastBreakpoint() {
        List<Integer> keys = new ArrayList<>(getBreakpoints().keySet());
        return keys.get(keys.size() - 1);
    }

    public double getMultiplier(int killCount) {
        Map<Integer, Double> breakpoints = getBreakpoints();
        Optional<Integer> breakpoint = breakpoints.keySet().stream().filter(b -> b <= killCount).max(Comparator.naturalOrder());
        if (!breakpoint.isPresent())
            return 0;
        double chance = breakpoints.get(breakpoint.get());
        int lastBreakpoint = getLastBreakpoint();
        int incrementalKillCount = killCount - lastBreakpoint;
        if (incrementalKillCount < getInterval()) return chance;
        return chance + (getIncrement() * (int) (incrementalKillCount / getInterval()));
    }

    protected int getNextBreakpoint(int killCount) {
        Map<Integer, Double> breakpoints = getBreakpoints();
        Optional<Integer> breakpoint = breakpoints.keySet().stream().filter(b -> b > killCount).min(Comparator.naturalOrder());
        int bp =  breakpoint.orElse(-1);
        if (bp != -1) return bp;
        int lastBreakpoint = getLastBreakpoint();
        int currentBreakpoint = (killCount - lastBreakpoint) / getInterval();
        return (currentBreakpoint + 1) * getInterval() + lastBreakpoint;
    }

    public int getFill(int killCount) {
        if (killCount >= getLastBreakpoint()) {
            return (int) (MAX_FILL * ((double) (getInterval() - (getNextBreakpoint(killCount) - killCount)) / getInterval()));
        }
        Map<Integer, Double> breakpoints = getBreakpoints();
        List<Integer> keys = new ArrayList<>(breakpoints.keySet());
        int nextBreakpoint = getNextBreakpoint(killCount);
        int indexOfNext = keys.indexOf(nextBreakpoint);
        int lastBreakpoint = 0;
        if (indexOfNext > 0)
            lastBreakpoint = keys.get(keys.indexOf(nextBreakpoint) - 1);
        return (int) (MAX_FILL * ((double) (killCount - lastBreakpoint) / (double) (nextBreakpoint - lastBreakpoint)));
    }
}

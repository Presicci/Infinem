package io.ruin.model.content.bestiary.perks;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public abstract class BreakpointPerk extends BestiaryPerk {

    public BreakpointPerk() {}

    protected abstract Map<Integer, Double> getBreakpoints();

    @Override
    protected String getBreakpointString(int killCount) {
        int nextBreakpoint = getNextBreakpoint(killCount);
        if (nextBreakpoint <= 0) return "";
        Double breakpointReward = getBreakpoints().get(nextBreakpoint);
        return "+" + (getInvertedPercentage() ? inverseDoubleToPercentage(breakpointReward) : new DecimalFormat("#").format(breakpointReward * 100f)) + "% at " + super.getBreakpointString(killCount);
    }

    public double getMultiplier(int killCount) {
        Map<Integer, Double> breakpoints = getBreakpoints();
        Optional<Integer> breakpoint = breakpoints.keySet().stream().filter(b -> b <= killCount).max(Comparator.naturalOrder());
        if (!breakpoint.isPresent())
            return getInvertedPercentage() ? 1 : 0;
        return breakpoints.get(breakpoint.get());
    }

    protected int getNextBreakpoint(int killCount) {
        Map<Integer, Double> breakpoints = getBreakpoints();
        Optional<Integer> breakpoint = breakpoints.keySet().stream().filter(b -> b > killCount).min(Comparator.naturalOrder());
        return breakpoint.orElse(-1);
    }

    public int getFill(int killCount) {
        Map<Integer, Double> breakpoints = getBreakpoints();
        List<Integer> keys = new ArrayList<>(breakpoints.keySet());
        int nextBreakpoint = getNextBreakpoint(killCount);
        if (nextBreakpoint <= 0) return MAX_FILL;
        int indexOfNext = keys.indexOf(nextBreakpoint);
        int lastBreakpoint = 0;
        if (indexOfNext > 0)
            lastBreakpoint = keys.get(indexOfNext - 1);
        return (int) (MAX_FILL * ((float) (killCount - lastBreakpoint) / (float) (nextBreakpoint - lastBreakpoint)));
    }
}

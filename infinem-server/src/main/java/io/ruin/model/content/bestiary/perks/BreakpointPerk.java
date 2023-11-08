package io.ruin.model.content.bestiary.perks;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public abstract class BreakpointPerk extends BestiaryPerk {

    public BreakpointPerk() {}

    protected abstract Map<Integer, Double> getBreakpoints();

    public double getMultiplier(int killCount) {
        Map<Integer, Double> breakpoints = getBreakpoints();
        Optional<Integer> breakpoint = breakpoints.keySet().stream().filter(b -> b <= killCount).max(Comparator.naturalOrder());
        if (!breakpoint.isPresent())
            return 1;
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
        int indexOfNext = keys.indexOf(nextBreakpoint);
        int lastBreakpoint = 0;
        if (indexOfNext > 0)
            lastBreakpoint = keys.get(keys.indexOf(nextBreakpoint) - 1);
        return (int) (MAX_FILL * ((float) (killCount - lastBreakpoint) / (float) (nextBreakpoint - lastBreakpoint)));
    }
}

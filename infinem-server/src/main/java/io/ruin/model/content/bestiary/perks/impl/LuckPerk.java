package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/11/2024
 */
public class LuckPerk extends BreakpointPerk {

    public LuckPerk(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return isBoss ? getBossBreakpoints() : new LinkedHashMap<Integer, Double>() {{
            put(500, 0.01);
            put(1500, 0.02);
            put(5000, 0.03);
            put(10000, 0.04);
        }};
    }

    private Map<Integer, Double> getBossBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(250, 0.01);
            put(500, 0.02);
            put(1500, 0.03);
            put(3000, 0.04);
        }};
    }

    @Override
    protected String getBreakpointString(int killCount) {
        int nextBreakpoint = getNextBreakpoint(killCount);
        if (nextBreakpoint <= 0) return "";
        Double breakpointReward = getBreakpoints().get(nextBreakpoint);
        List<Integer> keys = new ArrayList<>(getBreakpoints().keySet());
        String breakpointString = "";
        if (getNextBreakpoint(killCount) > 0) breakpointString = nextBreakpoint + "";
        if (keys.indexOf(nextBreakpoint) == 0) {
            return "+" + new DecimalFormat("#").format(breakpointReward * 100f) + " at " + breakpointString;
        } else {
            Double multiplier = getMultiplier(killCount);
            return "+" + new DecimalFormat("#").format((breakpointReward - multiplier) * 100f) + " at " + breakpointString;
        }
    }

    @Override
    protected String getLabel(int killCount) {
        return "Tier * Luck When Rolling RDT";
    }
}
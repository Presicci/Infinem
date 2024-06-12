package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class AccuracyPerk extends BreakpointPerk {

    public AccuracyPerk(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return isBoss ? getBossBreakpoints() : new LinkedHashMap<Integer, Double>() {{
            put(50, 0.01);
            put(250, 0.02);
            put(500, 0.03);
            put(750, 0.04);
            put(1500, 0.05);
        }};
    }

    private Map<Integer, Double> getBossBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(100, 0.01);
            put(500, 0.02);
            put(1000, 0.03);
            put(1500, 0.04);
            put(3000, 0.05);
        }};
    }

    @Override
    protected String getLabel(int killCount) {
        return "*% Increased Accuracy";
    }
}

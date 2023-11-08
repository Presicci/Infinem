package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class AccuracyPerk extends BreakpointPerk {
    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(50, 1.01);
            put(250, 1.02);
            put(500, 1.03);
            put(750, 1.04);
            put(1500, 1.05);
        }};
    }

    @Override
    protected String getLabel(int killCount) {
        return doubleToPercentage(getMultiplier(killCount)) + "% Increased Accuracy";
    }
}

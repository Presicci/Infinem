package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class ReducedEnemyAccuracyPerk extends BreakpointPerk {
    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(125, 0.99);
            put(300, 0.98);
            put(450, 0.97);
            put(600, 0.96);
            put(725, 0.95);
            put(875, 0.94);
            put(950, 0.93);
            put(1100, 0.92);
            put(1200, 0.91);
            put(1450, 0.90);
        }};
    }

    @Override
    protected String getLabel(int killCount) {
        return inverseDoubleToPercentage(getMultiplier(killCount)) + "% Reduced Enemy Accuracy";
    }
}

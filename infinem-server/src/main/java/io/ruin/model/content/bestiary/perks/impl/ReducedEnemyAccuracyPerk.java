package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class ReducedEnemyAccuracyPerk extends BreakpointPerk {

    public ReducedEnemyAccuracyPerk(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return isBoss ? getBossBreakpoints() : new LinkedHashMap<Integer, Double>() {{
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

    private Map<Integer, Double> getBossBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(250, 0.99);
            put(600, 0.98);
            put(900, 0.97);
            put(1200, 0.96);
            put(1500, 0.95);
            put(1750, 0.94);
            put(1900, 0.93);
            put(2200, 0.92);
            put(2400, 0.91);
            put(2900, 0.90);
        }};
    }

    @Override
    protected boolean getInvertedPercentage() {
        return true;
    }

    @Override
    protected String getLabel(int killCount) {
        return "% Reduced Enemy Accuracy";
    }
}

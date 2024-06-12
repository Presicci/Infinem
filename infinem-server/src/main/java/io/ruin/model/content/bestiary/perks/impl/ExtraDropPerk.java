package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.MixedPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class ExtraDropPerk extends MixedPerk {

    public ExtraDropPerk(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return isBoss ? getBossBreakpoints() : new LinkedHashMap<Integer, Double>() {{
            put(20, 0.01);
            put(40, 0.02);
            put(60, 0.03);
            put(80, 0.04);
            put(100, 0.05);
        }};
    }

    private Map<Integer, Double> getBossBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(40, 0.01);
            put(80, 0.02);
            put(120, 0.03);
            put(160, 0.04);
            put(200, 0.05);
        }};
    }

    @Override
    protected double getIncrement() {
        return 0.02;
    }

    @Override
    protected int getInterval() {
        return isBoss ? getBossInterval() : 100;
    }

    private int getBossInterval() {
        return 200;
    }

    @Override
    protected String getLabel(int killCount) {
        return "% Extra Loot Chance";
    }
}

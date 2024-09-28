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
            put(100, 0.1);
            put(250, 0.2);
            put(500, 0.3);
            put(750, 0.4);
            put(1000, 0.5);
        }};
    }

    private Map<Integer, Double> getBossBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(50, 0.2);
            put(100, 0.4);
            put(150, 0.6);
            put(200, 0.8);
            put(250, 1.0);
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
        return 50;
    }

    @Override
    protected String getLabel(int killCount) {
        return "*% Extra Loot Chance";
    }
}

package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.MixedPerk;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class ExtraDropPerk extends MixedPerk {
    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(20, 0.01);
            put(40, 0.02);
            put(60, 0.03);
            put(80, 0.04);
            put(100, 0.05);
        }};
    }

    @Override
    protected double getIncrement() {
        return 0.02;
    }

    @Override
    protected int getInterval() {
        return 100;
    }

    @Override
    protected String getLabel(int killCount) {
        return new DecimalFormat("#").format(getMultiplier(killCount) * 100f) + "% Extra Loot Chance";
    }
}

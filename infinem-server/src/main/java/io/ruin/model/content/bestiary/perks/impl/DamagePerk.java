package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class DamagePerk extends BreakpointPerk {
    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(100, 1.1);
            put(1000, 1.15);
            put(2000, 1.2);
        }};
    }

    @Override
    protected String getLabel(int killCount) {
        return doubleToPercentage(getMultiplier(killCount)) + "% Increased Damage";
    }
}

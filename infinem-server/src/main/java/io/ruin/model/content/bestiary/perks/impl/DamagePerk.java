package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class DamagePerk extends BreakpointPerk {

    public DamagePerk(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return isBoss ? getBossBreakpoints() : new LinkedHashMap<Integer, Double>() {{
            put(100, 0.1);
            put(1000, 0.15);
            put(2000, 0.2);
        }};
    }

    private Map<Integer, Double> getBossBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(200, 0.1);
            put(2000, 0.15);
            put(4000, 0.2);
        }};
    }

    @Override
    protected String getLabel(int killCount) {
        return "% Increased Damage";
    }
}

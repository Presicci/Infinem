package io.ruin.model.content.bestiary.perks.impl;

import io.ruin.model.content.bestiary.perks.BreakpointPerk;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/7/2023
 */
public class AccuracyPerk extends BreakpointPerk {
    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(50, 0.01);
            put(250, 0.02);
            put(500, 0.03);
            put(750, 0.04);
            put(1500, 0.05);
        }};
    }

    @Override
    protected String getLabel(int killCount) {
        return "% Increased Accuracy";
    }
}

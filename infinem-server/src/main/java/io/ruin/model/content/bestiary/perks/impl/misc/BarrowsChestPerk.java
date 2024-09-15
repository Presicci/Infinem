package io.ruin.model.content.bestiary.perks.impl.misc;

import io.ruin.model.content.bestiary.perks.MixedPerk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/14/2024
 */
public class BarrowsChestPerk extends MixedPerk {

    @Override
    protected Map<Integer, Double> getBreakpoints() {
        return new LinkedHashMap<Integer, Double>() {{
            put(36, 0.02);
            put(72, 0.04);
            put(108, 0.06);
            put(144, 0.08);
            put(180, 0.1);
        }};
    }

    @Override
    protected double getIncrement() {
        return 0.01;
    }

    @Override
    protected int getInterval() {
        return 30;
    }

    @Override
    protected String getLabel(int killCount) {
        return "*% Extra Chest Roll Chance";
    }
}
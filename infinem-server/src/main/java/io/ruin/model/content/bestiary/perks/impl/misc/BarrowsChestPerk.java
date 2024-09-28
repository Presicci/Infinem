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
            put(60, 0.2);
            put(120, 0.4);
            put(180, 0.6);
            put(240, 0.8);
            put(300, 1.0);
        }};
    }

    @Override
    protected double getIncrement() {
        return 0.25;
    }

    @Override
    protected int getInterval() {
        return 300;
    }

    @Override
    protected String getLabel(int killCount) {
        return "*% Extra Chest Roll Chance";
    }
}
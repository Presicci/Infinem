package io.ruin.model.content.bestiary;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class BestiaryEntry {

    private static final Map<Integer, Double> damageBreakpoints = new HashMap<Integer, Double>() {{
            put(100, 1.1);
            put(1000, 1.15);
            put(2000, 1.2);
    }};

    private static final Map<Integer, Double> accuracyBreakpoints = new HashMap<Integer, Double>() {{
            put(50, 1.01);
            put(250, 1.02);
            put(500, 1.03);
            put(750, 1.04);
            put(1500, 1.05);
    }};

    private static final Map<Integer, Double> reducedEnemyAccuracyBreakpoints = new HashMap<Integer, Double>() {{
            put(125, 1.01);
            put(300, 1.02);
            put(450, 1.03);
            put(600, 1.04);
            put(725, 1.05);
            put(875, 1.06);
            put(950, 1.07);
            put(1100, 1.08);
            put(1200, 1.09);
            put(1450, 1.1);
    }};

    private static final Map<Integer, Double> extraDropChanceBreakpoints = new HashMap<Integer, Double>() {{
            put(20, 0.01);
            put(40, 0.02);
            put(60, 0.03);
            put(80, 0.04);
            put(100, 0.05);
    }};

    private static final int extraDropChanceInterval = 100;
    private static final double extraDropChanceIncrement = 0.02;

    private static final int notedItemInterval = 1500;
    private static final double notedItemIncrement = 0.2;

    public static double getExtraDropChance(int killCount) {
        Optional<Integer> breakpoint = extraDropChanceBreakpoints.keySet().stream().filter(b -> b < killCount).max(Comparator.naturalOrder());
        if (!breakpoint.isPresent())
            return 1;
        double dropChance = extraDropChanceBreakpoints.get(breakpoint.get());
        int incrementalKillCount = killCount - 100;
        if (incrementalKillCount < 100) return dropChance;
        return dropChance + (extraDropChanceIncrement * (int) (incrementalKillCount / extraDropChanceInterval));
    }
}

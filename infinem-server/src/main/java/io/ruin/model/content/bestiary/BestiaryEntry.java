package io.ruin.model.content.bestiary;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class BestiaryEntry {

    private final int killCount;

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

    private static final Map<Integer, Double> extraDropChanceBreakpoints = new HashMap<Integer, Double>() {{
            put(20, 0.01);
            put(40, 0.02);
            put(60, 0.03);
            put(80, 0.04);
            put(100, 0.05);
    }};

    private static final int extraDropChanceInterval = 100;
    private static final double extraDropChanceIncrement = 0.02;

    private static final int respawnInterval = 25;
    private static final double respawnDecrement = 0.01;

    private static final int notedItemInterval = 1500;
    private static final double notedItemIncrement = 0.2;

    public BestiaryEntry(int killCount) {
        this.killCount = killCount;
    }

    public static double getBreakpointMultiplier(Map<Integer, Double> breakpoints, int killCount) {
        Optional<Integer> breakpoint = breakpoints.keySet().stream().filter(b -> b < killCount).max(Comparator.naturalOrder());
        if (!breakpoint.isPresent())
            return 1;
        return breakpoints.get(breakpoint.get());
    }

    public double getDamageMultiplier() {
        return getBreakpointMultiplier(damageBreakpoints, killCount);
    }

    public double getAccuracyMultiplier() {
        return getBreakpointMultiplier(accuracyBreakpoints, killCount);
    }

    public double getReducedEnemyAccuracyMultiplier() {
        return getBreakpointMultiplier(reducedEnemyAccuracyBreakpoints, killCount);
    }

    public double getExtraDropChance() {
        Optional<Integer> breakpoint = extraDropChanceBreakpoints.keySet().stream().filter(b -> b < killCount).max(Comparator.naturalOrder());
        if (!breakpoint.isPresent())
            return 0;
        double dropChance = extraDropChanceBreakpoints.get(breakpoint.get());
        int incrementalKillCount = killCount - 100;
        if (incrementalKillCount < 100) return dropChance;
        return dropChance + (extraDropChanceIncrement * (int) (incrementalKillCount / extraDropChanceInterval));
    }

    public double getRespawnMultiplier() {
        if (killCount < respawnInterval) return 1;
        return 1 - (respawnDecrement * (int) (killCount / respawnInterval));
    }

    public double getNotedItemChance() {
        if (killCount < notedItemInterval) return 0;
        return notedItemIncrement * (int) (killCount / notedItemInterval);
    }
}

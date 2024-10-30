package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.api.utils.Random;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
@Getter
public enum FragmentModifier {
    /**
     * Provides the player with a x% experience boost in the skill.<br><br>
     */
    EXPERIENCE(FragmentType.FISHING,
            new FragmentModRange(0.21, 0.4, 75), new FragmentModRange(0.41, 0.6, 60), new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20), new FragmentModRange(0.81, 0.9, 10), new FragmentModRange(0.91, 1.0, 5)),
    /**
     * Provides the player with a x% chance to cook fish when caught.<br><br>
     */
    COOK_FISH(FragmentType.FISHING,
            new FragmentModRange(0.21, 0.4, 75), new FragmentModRange(0.41, 0.6, 60), new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20), new FragmentModRange(0.81, 0.9, 10), new FragmentModRange(0.91, 1.0, 5)),
    /**
     * Provides the player with a x% chance to bank fish when caught. Doesn't work with Endless harvest.<br><br>
     */
    BANK_FISH(FragmentType.FISHING,
            new FragmentModRange(0.21, 0.4, 75), new FragmentModRange(0.41, 0.6, 60), new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20), new FragmentModRange(0.81, 0.9, 10), new FragmentModRange(0.91, 1.0, 5)),
    /**
     * Fishing actions are x ticks faster.<br><br>
     */
    FASTER_FISHING(FragmentType.FISHING, new FragmentModRange(1, 1, 10), new FragmentModRange(2, 2, 2)),
    /**
     * Binary mod;<br>
     * Provides a chance to roll the RDT when fishing.<br><br>
     */
    FISHING_RDT(FragmentType.FISHING, new FragmentModRange(1, 1, 15))
    ;

    private final FragmentType[] types;
    private final FragmentModRange[] ranges;
    private int totalWeight;

    FragmentModifier(FragmentType type, FragmentModRange... ranges) {
        this(new FragmentType[] { type }, ranges);
    }

    FragmentModifier(FragmentType[] types, FragmentModRange... ranges) {
        this.types = types;
        this.ranges = ranges;
        for (FragmentModRange range : ranges) {
            this.totalWeight += range.getWeight();
        }
    }

    public static FragmentModRoll rollFrom(FragmentModifier[] mods) {
        double totalWeight = Stream.of(values()).mapToDouble(m -> m.getTotalWeight()).sum();
        double random = Random.get() * totalWeight;
        FragmentModifier rolledMod = null;
        FragmentModRange rolledRange = null;
        outer : for (FragmentModifier mod : mods) {
            for (FragmentModRange range : mod.getRanges()) {
                random -= range.getWeight();
                if (random <= 0.0D) {
                    rolledMod = mod;
                    rolledRange = range;
                    break outer;
                }
            }
        }
        return rolledMod == null || rolledRange == null
                ? null
                : new FragmentModRoll(rolledMod, (double) Random.get((int) (rolledRange.getMinValue() * 100), (int) (rolledRange.getMaxValue() * 100)) / 100D);
    }
}
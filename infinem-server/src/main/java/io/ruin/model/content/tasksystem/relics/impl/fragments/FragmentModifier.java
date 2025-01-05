package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import lombok.Getter;

import java.text.DecimalFormat;
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
    EXPERIENCE(new FragmentType[] {
                FragmentType.Agility, FragmentType.Cooking, FragmentType.Crafting, FragmentType.Farming, FragmentType.Firemaking, FragmentType.Fishing,
                FragmentType.Fletching, FragmentType.Herblore, FragmentType.Hunter, FragmentType.Mining, FragmentType.Runecrafting, FragmentType.Smithing,
                FragmentType.Thieving, FragmentType.Woodcutting
            },
            "#% increased * experience",
            new FragmentModRange(0.21, 0.4, 75), new FragmentModRange(0.41, 0.6, 60), new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20), new FragmentModRange(0.81, 0.9, 10), new FragmentModRange(0.91, 1.0, 5)),
    /**
     * Provides the player with a x% chance to bank resources from gathering. Doesn't work with Endless harvest.<br><br>
     */
    BANK_RESOURCES(new FragmentType[]{
                FragmentType.Fishing, FragmentType.Woodcutting, FragmentType.Mining,
            }, "#% chance to bank gathered resources",
            new FragmentModRange(0.21, 0.4, 75), new FragmentModRange(0.41, 0.6, 60), new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20), new FragmentModRange(0.81, 0.9, 10), new FragmentModRange(0.91, 0.99, 7),
            new FragmentModRange(1, 1, 5)),
    /**
     * Gathering actions are x ticks faster.<br><br>
     */
    TICK_FASTER(new FragmentType[]{
            FragmentType.Fishing, FragmentType.Woodcutting, FragmentType.Mining,
            }, "# tick(s) faster *",
            new FragmentModRange(1, 1, 10), new FragmentModRange(2, 2, 2)),
    /**
     * Binary mod;<br>
     * Provides a chance to roll the RDT when gathering.<br><br>
     */
    RDT(new FragmentType[]{
                FragmentType.Fishing, FragmentType.Woodcutting, FragmentType.Mining,
            }, "Provides a chance to roll the Rare Drop Table while *",
            new FragmentModRange(1, 1, 15)),
    /**
     * Provides the player with a x% chance to find an experience lamp while gathering.
     */
    EXPERIENCE_LAMP(new FragmentType[]{
                FragmentType.Fishing, FragmentType.Woodcutting, FragmentType.Mining,
            }, "#% chance to find an experience lamp while *",
            new FragmentModRange(0.01, 0.01, 10),
            new FragmentModRange(0.02, 0.02, 5),
            new FragmentModRange(0.03, 0.03, 2)),
    /**
     * Provides the player with a x% chance to cook fish when caught.<br><br>
     */
    COOK_FISH(FragmentType.Fishing, "#% chance to cook caught fish, rewarding cooking experience",
            new FragmentModRange(0.21, 0.4, 75), new FragmentModRange(0.41, 0.6, 60), new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20), new FragmentModRange(0.81, 0.9, 10), new FragmentModRange(0.91, 1.0, 7),
            new FragmentModRange(1, 1, 5)),
    ;

    private final FragmentType[] types;
    private final FragmentModRange[] ranges;
    private final String description;
    private int totalWeight;

    FragmentModifier(FragmentType type, String description, FragmentModRange... ranges) {
        this(new FragmentType[] { type }, description, ranges);
    }

    FragmentModifier(FragmentType[] types, String description, FragmentModRange... ranges) {
        this.types = types;
        this.description = description;
        this.ranges = ranges;
        for (FragmentModRange range : ranges) {
            this.totalWeight += range.getWeight();
        }
    }

    public String getDescription(FragmentType type, FragmentModRoll roll) {
        double value = roll.getValue() < 1 ? roll.getValue() * 100 : roll.getValue();
        return description.replace("#", new DecimalFormat("#").format(value)).replace("*", StringUtils.capitalizeFirst(type.name().toLowerCase())).replace("(s)", (int) value > 1 ? "s" : "");
    }

    public static FragmentModRoll rollFrom(FragmentModifier[] mods) {
        double totalWeight = Stream.of(mods).mapToDouble(FragmentModifier::getTotalWeight).sum();
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
        return rolledMod == null
                ? null
                : new FragmentModRoll(rolledMod, (double) Random.get((int) (rolledRange.getMinValue() * 100), (int) (rolledRange.getMaxValue() * 100)) / 100D);
    }
}
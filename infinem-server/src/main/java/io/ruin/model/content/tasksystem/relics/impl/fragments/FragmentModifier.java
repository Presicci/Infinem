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
     * Provides the player with a x% experience boost in the skill.
     */
    EXPERIENCE(new FragmentType[] {
                FragmentType.Agility, FragmentType.Cooking, FragmentType.Crafting, FragmentType.Farming, FragmentType.Firemaking, FragmentType.Fishing,
                FragmentType.Fletching, FragmentType.Herblore, FragmentType.Hunter, FragmentType.Mining, FragmentType.Runecrafting, FragmentType.Smithing,
                FragmentType.Thieving, FragmentType.Woodcutting
            },
            "#% increased * experience",
            new FragmentModRange(0.21, 0.4, 75),
            new FragmentModRange(0.41, 0.6, 60),
            new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20),
            new FragmentModRange(0.81, 0.9, 10),
            new FragmentModRange(0.91, 1.0, 5)),
    /**
     * Provides the player with a x% chance to bank resources from gathering. Doesn't work with Endless harvest.
     */
    BANK_RESOURCES(new FragmentType[]{
                FragmentType.Fishing, FragmentType.Woodcutting, FragmentType.Mining,
            }, "#% chance to bank gathered resources",
            new FragmentModRange(0.21, 0.4, 75),
            new FragmentModRange(0.41, 0.6, 60),
            new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20),
            new FragmentModRange(0.81, 0.9, 10),
            new FragmentModRange(0.91, 0.99, 7),
            new FragmentModRange(1, 1, 5)),
    /**
     * Gathering actions are x ticks faster.
     */
    TICK_FASTER(new FragmentType[]{
            FragmentType.Fishing, FragmentType.Woodcutting, FragmentType.Mining,
            }, "## tick(s) faster *",
            new FragmentModRange(1, 1, 10),
            new FragmentModRange(2, 2, 2)),
    /**
     * Binary mod;
     * Provides a chance to roll the RDT when gathering.
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
     * Provies +# invisible boost to skill level.
     */
    INVISIBLE_LEVEL(new FragmentType[]{
                FragmentType.Fishing, FragmentType.Woodcutting, FragmentType.Mining,
            }, "+## to invisible * level",
            new FragmentModRange(2, 3, 40),
            new FragmentModRange(3, 4, 30),
            new FragmentModRange(4, 5, 20),
            new FragmentModRange(5, 6, 15),
            new FragmentModRange(7, 9, 10),
            new FragmentModRange(10, 10, 5)),
    /**
     * ~~~~~~~
     * Agility
     * ~~~~~~~
     */
    AGILITY_LAP_GOLD(FragmentType.Agility, "## gold rewarded on completing an agility course lap",
            new FragmentModRange(250, 1000, 30),
            new FragmentModRange(1250, 3000, 20),
            new FragmentModRange(5000, 10000, 10),
            new FragmentModRange(15000, 25000, 5)),
    MARKS_OF_GRACE(FragmentType.Agility, "+## to found Marks of grace",
            new FragmentModRange(1, 1, 15),
            new FragmentModRange(2, 3, 10)),
    AUTOMATIC_LAPS(FragmentType.Agility, "Navigate +## agility course obstacles automatically after passing an obstacle",
            new FragmentModRange(1, 1, 25),
            new FragmentModRange(2, 2, 15),
            new FragmentModRange(3, 3, 10),
            new FragmentModRange(4, 4, 5)),
    /**
     * ~~~~~~~
     * Cooking
     * ~~~~~~~
     */
    /**
     * ~~~~~~~~
     * Crafting
     * ~~~~~~~~
     */
    /**
     * ~~~~~~~
     * Farming
     * ~~~~~~~
     */
    /**
     * ~~~~~~~~~~
     * Firemaking
     * ~~~~~~~~~~
     */
    /**
     * ~~~~~~~
     * Fishing
     * ~~~~~~~
     * Provides the player with a x% chance to cook fish when caught.
     */
    COOK_FISH(FragmentType.Fishing, "#% chance to cook caught fish, rewarding cooking experience",
            new FragmentModRange(0.21, 0.4, 75),
            new FragmentModRange(0.41, 0.6, 60),
            new FragmentModRange(0.61, 0.7, 40),
            new FragmentModRange(0.71, 0.8, 20),
            new FragmentModRange(0.81, 0.9, 10),
            new FragmentModRange(0.91, 1.0, 7),
            new FragmentModRange(1, 1, 5)),
    /**
     * Increases chance to receive clue bottles from fishing.
     */
    CLUE_BOTTLE(FragmentType.Fishing, "#% increased chance to find clue bottles while fishing",
            new FragmentModRange(0.5, 0.5, 15),
            new FragmentModRange(1.0, 1.0, 10),
            new FragmentModRange(1.5, 1.5, 5)),
    /**
     * Binary mod;
     * Ignore bait requirement.
     */
    IGNORE_BAIT(FragmentType.Fishing, "Can fish without bait",
            new FragmentModRange(1, 1, 10)),
    /**
     * ~~~~~~~~~
     * Fletching
     * ~~~~~~~~~
     */
    /**
     * ~~~~~~~~
     * Herblore
     * ~~~~~~~~
     */
    /**
     * ~~~~~~
     * Hunter
     * ~~~~~~
     */
    /**
     * ~~~~~~
     * Mining
     * ~~~~~~
     */
    /**
     * ~~~~~~~~~~~~
     * Runecrafting
     * ~~~~~~~~~~~~
     */
    /**
     * ~~~~~~~~
     * Smithing
     * ~~~~~~~~
     */
    /**
     * ~~~~~~~~
     * Thieving
     * ~~~~~~~~
     */
    /**
     * ~~~~~~~~~~~
     * Woodcutting
     * ~~~~~~~~~~~
     */
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
        double value = roll.getValue() * 100;
        return description
                // ## denotes a non percentage
                .replace("##", new DecimalFormat("#").format(roll.getValue()))
                // # denotes a percentage
                .replace("#", new DecimalFormat("#").format(value))
                // * denotes the skill name
                .replace("*", StringUtils.capitalizeFirst(type.name().toLowerCase())).replace("(s)", (int) value > 1 ? "s" : "");
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
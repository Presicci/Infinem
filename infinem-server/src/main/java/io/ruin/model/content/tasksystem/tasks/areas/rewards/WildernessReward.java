package io.ruin.model.content.tasksystem.tasks.areas.rewards;

import io.ruin.cache.Color;
import io.ruin.model.content.scroll.DiaryScroll;
import io.ruin.model.content.tasksystem.tasks.areas.AreaTaskTier;
import io.ruin.model.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/31/2023
 */
public enum WildernessReward {
    WILDERNESS_SWORD_1(AreaTaskTier.EASY, "Unlocks the Wilderness Sword 1",
            "Always slashes webs successfully"),
    FREE_RUNES_40(AreaTaskTier.EASY, "40 random free runes from Lundail once per day"),
    WILDERNESS_LEVER_CHOICE(AreaTaskTier.EASY, "Wilderness lever can teleport you to either Edgeville or Ardougne"),
    WILDERNESS_SWORD_2(AreaTaskTier.MEDIUM, "Unlocks the Wilderness Sword 2"),
    FREE_RUNES_80(AreaTaskTier.MEDIUM, "80 random free runes from Lundail once per day"),
    RESOURCE_AREA_DISCOUNT_20(AreaTaskTier.MEDIUM, "20% off entry to Resource Area"),
    ENT_YIELD_BOOST(AreaTaskTier.MEDIUM, "Increases the chance of a successful yield from ents by 15%"),
    //Access to Spindel, Artio and Calvar'ion.
    //Access to the shortcut in the Deep Wilderness Dungeon (requires Agility 46 )
    //Can have 4 ecumenical keys at a time
    WILDERNESS_SWORD_3(AreaTaskTier.HARD, "Unlocks the Wilderness Sword 3",
            "One free teleport to the Fountain of Rune daily"),
    FREE_RUNES_120(AreaTaskTier.HARD, "120 random free runes from Lundail once per day"),
    RESOURCE_AREA_DISCOUNT_50(AreaTaskTier.HARD, "50% off entry to Resource Area"),
    OBELISK_CHOICE(AreaTaskTier.HARD, "Able to choose your destination when teleporting through the Ancient Obelisks"),
    NOTED_WINE_OF_ZAMMY(AreaTaskTier.HARD, "Wine of zamorak found in the Chaos Temple and Deep Wilderness Dungeon will be noted"),
    //Can have 5 ecumenical keys at a time
    //Access to a shortcut to the Lava Maze (requires Agility 82 )
    //Access to a shortcut to the Lava Dragon Isle (requires Agility 74 )
    //50% more lava shards per lava scale
    WILDERNESS_SWORD_4(AreaTaskTier.ELITE, "Unlocks the Wilderness Sword 4",
            "Unlimited free teleports to the Fountain of Rune"),
    FREE_RUNES_200(AreaTaskTier.ELITE, "200 random free runes from Lundail once per day"),
    RESOURCE_AREA_FREE(AreaTaskTier.ELITE, "Free entry to Resource Area"),
    NOTED_DRAGON_BONES(AreaTaskTier.ELITE, "All dragon bones drops from dragons in the Wilderness are noted"),
    DARK_CRAB_CATCH_RATE(AreaTaskTier.ELITE, "Increased dark crab catch rate");

    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    WildernessReward(AreaTaskTier tier, String description, String... additionalDescription) {
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }

    public static void openRewards(Player player) {
        List<String> lines = new ArrayList<>();
        AreaTaskTier tier = null;
        for (WildernessReward task : values()) {
            if (tier != task.tier) {
                tier = task.tier;
                lines.add(Color.YELLOW.wrap(tier.toString()));
            }
            lines.add(Color.DARK_RED.wrap(task.description));
            if (task.additionalDescription.length > 0) {
                lines.addAll(Arrays.asList(task.additionalDescription));
            }
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Area Task Unlocks - Wilderness"), lines);
        scroll.open(player);
    }
}

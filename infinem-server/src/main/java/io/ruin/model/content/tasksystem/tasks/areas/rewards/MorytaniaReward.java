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
public enum MorytaniaReward {
    MORYTANIA_LEGS_1(AreaTaskTier.EASY, "Unlocks the Morytania Legs 1",
            "2 daily teleports to the Slime Pit beneath the Ectofuntus"),
    SLAYER_EXPERIENCE_25(AreaTaskTier.EASY, "2.5% more Slayer experience in the Slayer Tower"),
    //50% chance of a ghast ignoring you rather than attacking
    MORYTANIA_LEGS_2(AreaTaskTier.MEDIUM, "Unlocks the Morytania Legs 2",
            "5 daily teleports to the Slime Pit beneath the Ectofuntus",
            "Acts as a ghostspeak amulet when worn"),
    ROBIN_13(AreaTaskTier.MEDIUM, "Robin will exchange 13 slime buckets and bonemeal for bones daily"),
    SLAYER_EXPERIENCE_50(AreaTaskTier.MEDIUM, "5% more Slayer experience in the Slayer Tower"),
    MORYTANIA_LEGS_3(AreaTaskTier.HARD, "Unlocks the Morytania Legs 3",
            "Unlimited teleports to Burgh de Rott"),
    ROBIN_26(AreaTaskTier.HARD, "Robin will exchange 26 slime buckets and bonemeal for bones daily"),
    SHADE_PRAYER_EXP(AreaTaskTier.HARD, "50% more Prayer experience from burning shade remains"),
    ESTUARY_SHORTCUT(AreaTaskTier.HARD, "Access to a shortcut across the estuary on Mos Le'Harmless"),
    BARROWS_RUNES(AreaTaskTier.HARD, "50% more runes from the Barrows chest"),
    SLAYER_EXPERIENCE_75(AreaTaskTier.HARD, "7.5% more Slayer experience in the Slayer Tower"),
    //Double Mort myre fungi when casting Bloom
    //BONECRUSHER(), shop reward
    //An additional item that, when carried, causes bones dropped from killed monsters to be automatically buried, granting half the Prayer experience that would have been granted for burying them normally; charged with a small amount of ecto-tokens
    //It can be claimed from a ghost disciple. Note: A Ghostspeak amulet or Morytania legs 3 must be worn when claiming the Bonecrusher.
    MORYTANIA_LEGS_4(AreaTaskTier.ELITE, "Unlocks the Morytania Legs 4",
            "Unlimited teleports to the slime pit beneath the Ectofuntus"),
    ROBIN_39(AreaTaskTier.ELITE, "Robin will exchange 39 slime buckets and bonemeal for bones daily"),
    SHADE_FIREMAKING_EXP(AreaTaskTier.ELITE, "50% more Firemaking experience from burning shade remains"),
    BONECRUSHER_FULL_EXP(AreaTaskTier.ELITE, "Bones buried via the Bonecrusher grant full Prayer experience"),
    HARMONY_ISLAND_HERB(AreaTaskTier.ELITE, "Access to the herb patch on Harmony Island"),
    SLAYER_EXPERIENCE_10(AreaTaskTier.ELITE, "10% more Slayer experience in the Slayer Tower");

    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    MorytaniaReward(AreaTaskTier tier, String description, String... additionalDescription) {
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }

    public static void openRewards(Player player) {
        List<String> lines = new ArrayList<>();
        AreaTaskTier tier = null;
        for (MorytaniaReward task : values()) {
            if (tier != task.tier) {
                tier = task.tier;
                lines.add(Color.YELLOW.wrap(tier.toString()));
            }
            lines.add(Color.DARK_RED.wrap(task.description));
            if (task.additionalDescription.length > 0) {
                lines.addAll(Arrays.asList(task.additionalDescription));
            }
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Area Task Unlocks - Morytania"), lines);
        scroll.open(player);
    }
}

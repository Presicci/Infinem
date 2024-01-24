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
 * Created on 10/7/2023
 */
public enum MisthalinReward {
    EXPLORER_RING_1(AreaTaskTier.EASY, "Unlocks the Explorer's Ring 1",
            "30 casts of Low Level Alchemy per day without runes",
            "50% run energy replenish twice a day"),
    VARROCK_ARMOR_1(AreaTaskTier.EASY, "Unlocks the Varrock Armour 1",
            "10% chance of mining 2 ores at once, up to gold",
            "10% chance to smelt 2 bars at once in Misthalin, up to steel"),
    ZAFF_BATTLESTAVES_15(AreaTaskTier.EASY, "Zaff will sell 15 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_14(AreaTaskTier.EASY, "Skull sceptre will now hold up to 14 charges",
            "Skull sceptre parts now give 1 extra bone fragment"),
    BONE_WEAPON_SHOP(AreaTaskTier.EASY, "Unlock the bone weapon shop",
            "Managed by Nardok outside Dorgesh-Kaan"),
    AVAS_ATTRACTOR(AreaTaskTier.EASY, "Can purchase Ava's attractor",
            "Can be purchased from Ava in draynor manor"),
    EXPLORER_RING_2(AreaTaskTier.MEDIUM, "Unlocks the Explorer's Ring 2",
            "50% run energy replenish 3 times a day",
            "Three daily teleports to cabbage patch near Falador farm"),
    VARROCK_ARMOR_2(AreaTaskTier.MEDIUM, "Unlocks the Varrock Armour 2",
            "10% chance of mining 2 ores at once, up to mithril",
            "10% chance to smelt 2 bars at once in Misthalin, up to mithril"),
    DRAYNOR_VILLAGE_WALL_SHORTCUT(AreaTaskTier.MEDIUM, "Access to Draynor Village wall shortcut"),
    ZAFF_BATTLESTAVES_30(AreaTaskTier.MEDIUM, "Zaff will sell 30 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_18(AreaTaskTier.MEDIUM, "Skull sceptre will now hold up to 18 charges",
            "Skull sceptre parts now give 2 extra bone fragment"),
    AVAS_ACCUMULATOR(AreaTaskTier.MEDIUM, "Can purchase Ava's accumulator",
            "Can be purchased from Ava in draynor manor"),
    EXPLORER_RING_3(AreaTaskTier.HARD, "Unlocks the Explorer's Ring 3",
            "50% run energy replenish 4 times a day",
            "Unlimited teleports to cabbage patch near Falador farm"),
    VARROCK_ARMOR_3(AreaTaskTier.HARD, "Unlocks the Varrock Armour 3",
            "10% chance of mining 2 ores at once, up to adamantite",
            "10% chance to smelt 2 bars at once in Misthalin, up to adamantite",
            "Can be worn in place of a chef's hat to access the Cooks' Guild"),
    LUMBRIDGE_SWAMP_SHORTCUT(AreaTaskTier.HARD, "Access to a shortcut from Lumbridge Swamp to the desert"),
    //TEARS_OF_GUTHIX(AreaTaskTier.HARD, "10% increased experience from Tears of Guthix"),
    ZAFF_BATTLESTAVES_60(AreaTaskTier.HARD, "Zaff will sell 60 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_22(AreaTaskTier.HARD, "Skull sceptre will now hold up to 22 charges",
            "Skull sceptre parts now give 3 extra bone fragment"),
    COOKS_GUILD_BANK(AreaTaskTier.HARD, "Access to the Cooks' Guild bank"),
    EXPLORER_RING_4(AreaTaskTier.ELITE, "Unlocks the Explorer's Ring 4",
            "100% run energy replenish 3 times a day",
            "30 casts of High Level Alchemy per day without runes"),
    VARROCK_ARMOR_4(AreaTaskTier.ELITE, "Unlocks the Varrock Armour 4",
            "10% chance of mining 2 ores at once, up to amethyst",
            "10% chance to smelt 2 bars at once in Misthalin",
            "Acts as a prospector jacket for the purposes of experience bonus and clues"),
    CULINAROMANCERS_CHEST_DISCOUNT(AreaTaskTier.ELITE, "20% discount on items in the Culinaromancer's Chest"),
    FAIRY_RING(AreaTaskTier.ELITE, "Ability to use fairy rings without the need of a dramen or lunar staff"),
    SLAYER_BLOCK(AreaTaskTier.ELITE, "Unlocked the 6th slot for blocking Slayer tasks"),
    ZAFF_BATTLESTAVES_120(AreaTaskTier.ELITE, "Zaff will sell 120 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_26(AreaTaskTier.ELITE, "Skull sceptre will now hold up to 26 charges",
            "Skull sceptre parts now give 4 extra bone fragment"),
    ;

    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    MisthalinReward(AreaTaskTier tier, String description, String... additionalDescription) {
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }

    public static void openRewards(Player player) {
        List<String> lines = new ArrayList<>();
        AreaTaskTier tier = null;
        for (MisthalinReward task : values()) {
            if (tier != task.tier) {
                tier = task.tier;
                lines.add(Color.YELLOW.wrap(tier.toString()));
            }
            lines.add(Color.DARK_RED.wrap(task.description));
            if (task.additionalDescription.length > 0) {
                lines.addAll(Arrays.asList(task.additionalDescription));
            }
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Area Task Unlocks - Misthalin"), lines);
        scroll.open(player);
    }
}

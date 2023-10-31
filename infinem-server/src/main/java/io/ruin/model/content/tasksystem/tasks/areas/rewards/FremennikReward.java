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
public enum FremennikReward {
    FREMENNIK_SEA_BOOTS_1(AreaTaskTier.EASY, "Unlocks the Fremennik Sea Boots 1",
            "One free teleport to Rellekka every day"),
    PEER_DEPOSIT_BOX(AreaTaskTier.EASY, "Peer the Seer will act as a bank deposit box"),
    LYRE_EXTRA_CHARGE(AreaTaskTier.EASY, "Enchanted lyre gets an extra charge when making a sacrifice"),
    FREMENNIK_SEA_BOOTS_2(AreaTaskTier.MEDIUM, "Unlocks the Fremennik Sea Boots 2"),
    MISCELLANIA_DOCK_SHORTCUT(AreaTaskTier.MEDIUM, "Shortcut jump between Miscellania dock and Etceteria"),
    //+10% chance of gaining approval in Managing Miscellania
    FREMENNIK_SEA_BOOTS_3(AreaTaskTier.HARD, "Unlocks the Fremennik Sea Boots 3"),
    LYRE_WATERBIRTH_TELEPORT(AreaTaskTier.HARD, "Ability to change enchanted lyre teleport destination to Waterbirth Island"),
    AVIANCIE_NOTED_ADDY_BARS(AreaTaskTier.HARD, "Aviansies in the God Wars Dungeon will drop noted adamantite bars"),
    TROLL_STRONGHOLD_SHORTCUT(AreaTaskTier.HARD, "Shortcut to roof of the Troll Stronghold"),
    //Stony basalt teleport destination changed to the roof of the Troll Stronghold if you have Agility 73
    //Access to 2 new Lunar spells:
    //Tan Leather — Requires Magic 78 , 5Fire 2Astral 1Nature ; tans up to 5 hides per spell.
    //Recharge Dragonstone — Requires Magic 89 , 4Water 1Astral 1Soul ; recharges all items in inventory per spell.
    FREMENNIK_SEA_BOOTS_4(AreaTaskTier.ELITE, "Unlocks the Fremennik Sea Boots 4",
            "Unlimited free teleports to Rellekka"),
    NOTED_DAG_BONES(AreaTaskTier.ELITE, "Dagannoth bones will be dropped in noted form"),
    LYRE_JATIZSO_NEITIZNOT(AreaTaskTier.ELITE, "The Enchanted lyre can now teleport to Jatizso and Neitiznot"),
    //Seal of passage is no longer needed to interact with anyone on Lunar Isle
    //+15% chance of gaining approval in Managing Miscellania[1]
    ;

    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    FremennikReward(AreaTaskTier tier, String description, String... additionalDescription) {
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }

    public static void openRewards(Player player) {
        List<String> lines = new ArrayList<>();
        AreaTaskTier tier = null;
        for (FremennikReward task : values()) {
            if (tier != task.tier) {
                tier = task.tier;
                lines.add(Color.YELLOW.wrap(tier.toString()));
            }
            lines.add(Color.DARK_RED.wrap(task.description));
            if (task.additionalDescription.length > 0) {
                lines.addAll(Arrays.asList(task.additionalDescription));
            }
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Area Task Unlocks - Fremennik"), lines);
        scroll.open(player);
    }
}


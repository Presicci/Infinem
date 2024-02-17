package io.ruin.model.content.tasksystem.tasks.areas.rewards;

import io.ruin.cache.Color;
import io.ruin.model.content.scroll.DiaryScroll;
import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.content.tasksystem.tasks.areas.AreaTaskTier;
import io.ruin.model.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/28/2024
 */
public enum KandarinReward {
    THIEVING_BOOST_1(AreaTaskTier.MEDIUM, "10% increased chance to pickpocket in Ardougne"),
    YANILLE_TELEPORT(AreaTaskTier.MEDIUM, "Can switch the destination of Watchtower Teleport to Yanille"),
    THIEVING_BOOST_2(AreaTaskTier.HARD, "10% increased chance to pickpocket around Gielinor"),
    SEERS_TELEPORT(AreaTaskTier.HARD, "Can switch the destination of Camelot Teleport to Seers' Village");

    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    KandarinReward(AreaTaskTier tier, String description, String... additionalDescription) {
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }

    public boolean checkReward(Player player, String message) {
        return TaskArea.KANDARIN.checkTierUnlock(player, tier, message);
    }

    public boolean hasReward(Player player) {
        return TaskArea.KANDARIN.hasTierUnlocked(player, tier);
    }

    public static void openRewards(Player player) {
        List<String> lines = new ArrayList<>();
        AreaTaskTier tier = null;
        for (KandarinReward task : values()) {
            if (tier != task.tier) {
                tier = task.tier;
                lines.add(Color.YELLOW.wrap(tier.toString()));
            }
            lines.add(Color.DARK_RED.wrap(task.description));
            if (task.additionalDescription.length > 0) {
                lines.addAll(Arrays.asList(task.additionalDescription));
            }
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Area Task Unlocks - Kandarin"), lines);
        scroll.open(player);
    }
}

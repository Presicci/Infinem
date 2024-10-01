package io.ruin.model.content.tasksystem.areas;

import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
@AllArgsConstructor
public enum AreaConfig {
    FREMMY_HARD_LUNAR_SPELLS(TaskArea.FREMENNIK, AreaTaskTier.HARD, Config.varpbit(4533, false), 1);

    private final TaskArea area;
    private final AreaTaskTier tier;
    private final Config config;
    private final int activeValue;

    private void activate(Player player) {
        config.set(player, activeValue);
    }

    public boolean checkUnlocked(Player player, String message) {
        return area.checkTierUnlock(player, tier, message);
    }

    public boolean hasUnlocked(Player player) {
        return config.get(player) == activeValue;
    }

    public static void checkAll(Player player) {
        for (AreaConfig c : values()) {
            if (c.area.hasTierUnlocked(player, c.tier)) c.activate(player);
        }
    }
}

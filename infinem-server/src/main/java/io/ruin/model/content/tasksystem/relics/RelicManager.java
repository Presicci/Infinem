package io.ruin.model.content.tasksystem.relics;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;

import java.util.Arrays;


/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/11/2022
 */
public class RelicManager {

    // 1 = twisted, 2 = trailblazer, 3 = infinem, 4 = trailblazer II
    private static final Config RELIC_SET = Config.varpbit(10032, false).defaultValue(3);

    public static final int[] TIER_REQUIREMENTS = {
            0,      // Tier 1
            500,    // Tier 2
            2000,   // Tier 3
            4000,   // Tier 4
            7500,   // Tier 5
            15000   // Tier 6
    };

    private static final Config[] RELICS = {
            Config.varpbit(10049, true),
            Config.varpbit(10050, true),
            Config.varpbit(10051, true),
            Config.varpbit(10052, true),
            Config.varpbit(10053, true),
            Config.varpbit(11696, true)
    };

    public RelicManager(Player player) {
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;

    public boolean hasRelicInTier(Relic relic) {
        return RELICS[relic.getTier() - 1].get(player) != 0;
    }

    public boolean hasPointsForRelic(Relic relic) {
        return Config.LEAGUE_POINTS.get(player) >= TIER_REQUIREMENTS[relic.getTier() - 1];
    }

    public boolean hasRelicBefore(Relic relic) {
        if (relic.getTier() == 1) return true;
        return RELICS[relic.getTier() - 2].get(player) != 0;
    }

    public boolean takeRelic(Relic relic) {
        int tier = relic.getTier();
        Config config = RELICS[tier-1];
        if (hasRelicInTier(relic)) {
            player.sendMessage("You already have a relic of this tier.");
            return false;
        }
        int pointRequirement = TIER_REQUIREMENTS[tier-1];
        if (!hasPointsForRelic(relic)) {
            player.sendMessage("You need " + pointRequirement + " task points to unlock a relic from tier " + tier + ".");
            return false;
        }
        if (!hasPointsForRelic(relic)) {
            player.sendMessage("You need a relic from tier " + (tier - 1) + " before you can take a relic from this tier.");
            return false;
        }
        // Check point requirement
        config.set(player, relic.getConfigValue());
        return true;
    }

    private boolean hasRelic(Relic relic) {
        return RELICS[relic.getTier()-1].get(player) == relic.getConfigValue();
    }

    public boolean hasRelicEnalbed(Relic relic) {
        return hasRelic(relic);
    }

    public void removeRelic(int tier) {
        if (tier < 0 || tier >= RELICS.length) return;
        RELICS[tier-1].set(player, 0);
    }
}

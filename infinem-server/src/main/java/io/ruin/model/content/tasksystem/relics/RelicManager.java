package io.ruin.model.content.tasksystem.relics;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;

import java.util.Arrays;


/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/11/2022
 */
public class RelicManager {

    public static final int[] tierRequirements = {
            0,      // Tier 1
            500,    // Tier 2
            2000,   // Tier 3
            4000,   // Tier 4
            7500,   // Tier 5
            15000   // Tier 6
    };

    public RelicManager(Player player) {
        this.player = player;
        this.relics = new Relic[Relic.tiers];
        this.relicsEnabled = new Boolean[Relic.tiers];
        Arrays.fill(relicsEnabled, Boolean.TRUE);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;
    @Expose public final Relic[] relics;
    @Expose private final Boolean[] relicsEnabled;

    public boolean takeRelic(Relic relic) {
        int tier = relic.getTier();
        if (relics[tier-1] != null) {
            player.sendMessage("You already have a relic of this tier.");
            return false;
        }
        // Check point requirement
        relics[tier-1] = relic;
        return true;
    }

    private boolean hasRelic(Relic relic) {
        return relics[relic.getTier()-1] == relic;
    }

    public boolean hasRelicEnalbed(Relic relic) {
        return hasRelic(relic) && relicsEnabled[relic.getTier()-1] == Boolean.TRUE;
    }
}

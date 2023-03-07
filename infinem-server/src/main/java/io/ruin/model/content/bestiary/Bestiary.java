package io.ruin.model.content.bestiary;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class Bestiary {

    private Player player;

    @Expose private final Map<String, Integer> killCounts;

    public Bestiary(Player player) {
        this.player = player;
        killCounts = new HashMap<>();
    }

    private static final String[] CATEGORIES = {
            "goblin",
    };

    private static final String[] TRIM = {
            "superior"
    };

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the bestiary entry name that matches the provided npcName.
     * @param npcName raw name of the NPC that was killed
     * @return bestiary entry compatible name
     */
    private String getBestiaryName(String npcName) {
        String lowercaseName = npcName.toLowerCase();
        for (String trim : TRIM) {
            lowercaseName = lowercaseName.replace(trim, "");
        }
        for (String category : CATEGORIES) {
            if (lowercaseName.contains(category))
                return category;
        }
        return lowercaseName;
    }

    /**
     * Called on NPC death, increments the player's kill count for bestiary entry
     * matching npcName.
     * @param npcName raw name of the NPC that was killed
     */
    public void incrementKillCount(String npcName) {
         String bestiaryName = getBestiaryName(npcName);
         int currentCount = killCounts.getOrDefault(bestiaryName, 0);
         killCounts.put(bestiaryName, currentCount + 1);
         player.sendMessage("You have now killed " + (currentCount + 1) + "<col=ff0000> " + npcName + "s</col>.");
    }

    public int getKillCount(String npcName) {
        if (killCounts == null)
            return 0;
        return killCounts.getOrDefault(getBestiaryName(npcName), 0);
    }

    public double getDropPerkChance(String npcName) {
        double chance = BestiaryEntry.getDropPerk(getKillCount(npcName)).getChance();
        player.sendMessage("Your extra drop chance from " + npcName + " is " + chance + ".");
        return chance;
    }
}

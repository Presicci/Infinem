package io.ruin.model.content.bestiary;

import com.google.gson.annotations.Expose;
import io.ruin.cache.NPCDef;
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Called on NPC death, increments the player's kill count for bestiary entry
     * matching npcName.
     * @param def NPCDef for npc
     */
    public void incrementKillCount(NPCDef def) {
        String bestiaryName = def.bestiaryEntry;
         int currentCount = killCounts.getOrDefault(bestiaryName, 0);
         killCounts.put(bestiaryName, currentCount + 1);
         player.sendMessage("You have now killed " + (currentCount + 1) + "<col=ff0000> " + def.name + "s</col>.");
    }

    public int getKillCount(NPCDef def) {
        if (killCounts == null)
            return 0;
        return killCounts.getOrDefault(def.bestiaryEntry, 0);
    }

    public double getDropPerkChance(NPCDef def) {
        double chance = BestiaryEntry.getDropPerk(getKillCount(def)).getChance();
        player.sendMessage("Your extra drop chance from " + def.name + " is " + chance + ".");
        return chance;
    }
}

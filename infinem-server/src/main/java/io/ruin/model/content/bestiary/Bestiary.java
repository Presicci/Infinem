package io.ruin.model.content.bestiary;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.NPCDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;

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
        return getKillCount(def.bestiaryEntry);
    }

    public int getKillCount(String entry) {
        if (killCounts == null || entry == null || entry.equals(""))
            return 0;
        return killCounts.getOrDefault(entry, 0);
    }

    public double getDropPerkChance(NPCDef def) {
        double chance = BestiaryEntry.getDropPerk(getKillCount(def)).getChance();
        player.sendMessage("Your extra drop chance from " + def.name + " is " + chance + ".");
        return chance;
    }

    public String generateBestiaryInterfaceString() {
        int sortType = Config.BESTIARY_SORT.get(player);
        int totalEntries = BestiaryDef.ENTRIES.size();
        StringBuilder sb = new StringBuilder();
        if (player.debug) {
            BestiaryDef.ENTRIES.stream().sorted().forEach(entry -> {
                sb.append(StringUtils.capitalizeFirst(entry));
                sb.append("|");
                sb.append(getKillCount(entry));
                sb.append("|");
            });
        } else {
            BestiaryDef.ENTRIES.stream().filter(killCounts::containsKey).sorted().forEach(entry -> {
                sb.append(StringUtils.capitalizeFirst(entry));
                sb.append("|");
                sb.append(getKillCount(entry));
                sb.append("|");
            });
            for (int index = 0; index < totalEntries - killCounts.size(); index++) {
                if (index > 0)
                    sb.append("|");
                sb.append("???|0");
            }
        }

        return sb.toString();
    }
}

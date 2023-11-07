package io.ruin.model.content.bestiary;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.NPCDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.TabBestiary;
import io.ruin.model.inter.utils.Config;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class Bestiary {

    private Player player;

    @Expose @Getter private final Map<String, Integer> killCounts;

    public Bestiary(Player player) {
        this.player = player;
        killCounts = new LinkedHashMap<>();
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
         killCounts.remove(bestiaryName);   // Reset order in map, for recent sort
         killCounts.put(bestiaryName, currentCount + 1);
         TabBestiary.attemptRefresh(player);
         player.sendMessage("You have now killed " + (currentCount + 1) + "<col=ff0000> " + bestiaryName + "s</col>.");
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

    /*
     * Interface
     */
    @Setter @Getter private Set<String> entries = new HashSet<>();

    private List<String> orderedEntries;

    private String getEntryString(Map.Entry<String, Integer> entry) {
        return getEntryString(entry.getKey(), entry.getValue());
    }

    private String getEntryString(String entry, int kc) {
        return StringUtils.capitalizeFirst(entry) + "|" + kc + "|";
    }

    public String generateInterfaceString() {
        int sortType = Config.BESTIARY_SORT.get(player);
        if (entries == null || entries.isEmpty())
            entries = BestiaryDef.ENTRIES;
        StringBuilder sb = new StringBuilder();
        orderedEntries = new ArrayList<>();
        if (sortType == 0) {    // Sort by kills (highest->lowest)
            killCounts.entrySet().stream().filter(e -> entries.contains(e.getKey())).sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(entry -> {
                sb.append(getEntryString(entry));
                orderedEntries.add(entry.getKey());
            });
        } else if (sortType == 1) { // Sort alphabetically
            killCounts.entrySet().stream().filter(e -> entries.contains(e.getKey())).sorted(Map.Entry.comparingByKey()).forEach(entry -> {
                sb.append(getEntryString(entry));
                orderedEntries.add(entry.getKey());
            });
        } else if (sortType == 2) { // Bosses first
            killCounts.entrySet().stream().filter(e -> entries.contains(e.getKey()) && BestiaryDef.isBoss(e.getKey())).sorted(Map.Entry.comparingByKey()).forEach(entry -> {
                sb.append(getEntryString(entry));
                orderedEntries.add(entry.getKey());
            });
            killCounts.entrySet().stream().filter(e -> entries.contains(e.getKey()) && !BestiaryDef.isBoss(e.getKey())).sorted(Map.Entry.comparingByKey()).forEach(entry -> {
                sb.append(getEntryString(entry));
                orderedEntries.add(entry.getKey());
            });
        } else if (sortType == 3) { // Recent first
            List<String> keys = new ArrayList<>(killCounts.keySet());
            Collections.reverse(keys);
            for (String key : keys) {
                if (entries.contains(key))
                    sb.append(getEntryString(key, killCounts.get(key)));
            }
        }
        sb.deleteCharAt(sb.length() - 1);   // Trim trailing |
        return sb.toString();
    }

    /*
     * Entry interface
     */
    public void displayEntry(int slot) {
        int index = slot / 4;
        if (index > orderedEntries.size() - 1) {
            // ??? handling
            return;
        }
        System.out.println(Arrays.toString(orderedEntries.toArray(new String[0])));
        System.out.println("Slot: " + slot + ", index:" + index);
        String entry = orderedEntries.toArray(new String[0])[index];
        player.openInterface(InterfaceType.MAIN, 1010);
        player.getPacketSender().sendString(1010, 7, StringUtils.capitalizeFirst(entry));
        player.getPacketSender().sendClientScript(10070, "is", getKillCount(entry), "Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000");

    }
}

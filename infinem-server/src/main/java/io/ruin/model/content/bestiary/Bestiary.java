package io.ruin.model.content.bestiary;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.content.bestiary.perks.BestiaryPerk;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
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

    @Setter private Player player;

    @Expose @Getter private final Map<String, Integer> killCounts;

    private BestiaryEntry currentEntry;
    private List<BestiaryPerk> displayedPerks;

    public Bestiary(Player player) {
        this.player = player;
        killCounts = new LinkedHashMap<>();
    }

    /**
     * Called on NPC death, increments the player's kill count for bestiary entry
     * matching npcName.
     * @param def NPCDef for npc
     * @param amt Amount to increment
     */
    public void incrementKillCount(NPCDefinition def, int amt) {
        String bestiaryName = def.bestiaryEntry;
        if (bestiaryName == null || !BestiaryDef.ENTRIES.contains(bestiaryName)) return;
        int currentCount = killCounts.getOrDefault(bestiaryName, 0);
        killCounts.remove(bestiaryName);   // Reset order in map, for recent sort
        killCounts.put(bestiaryName, currentCount + amt);
        TabBestiary.attemptRefresh(player);
        Config.BESTIARY_KILLS.increment(player, 1);
        player.sendMessage("You have now killed " + (currentCount + 1) + "<col=ff0000> " + bestiaryName + "s</col>.");
    }

    /**
     * Called on NPC death, increments the player's kill count for bestiary entry
     * matching npcName.
     * @param def NPCDef for npc
     */
    public void incrementKillCount(NPCDefinition def) {
        incrementKillCount(def, 1);
    }

    public int getKillCount(NPCDefinition def) {
        return getKillCount(def.bestiaryEntry);
    }

    public int getKillCount(String entry) {
        if (entry == null || entry.isEmpty())
            return 0;
        return killCounts.getOrDefault(entry, 0);
    }

    public void calculateTotalKillcount() {
        int total = 0;
        for (int kc : killCounts.values()) {
            total += kc;
        }
        Config.BESTIARY_KILLS.set(player, total);
    }

    public BestiaryEntry getBestiaryEntry(NPCDefinition def) {
        return new BestiaryEntry(player, def.bestiaryEntry, getKillCount(def));
    }

    public BestiaryEntry getBestiaryEntry(String entry) {
        return new BestiaryEntry(player, entry, getKillCount(entry));
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
                if (entries.contains(key)) {
                    sb.append(getEntryString(key, killCounts.get(key)));
                    orderedEntries.add(key);
                }
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
        String entryName = orderedEntries.toArray(new String[0])[index];
        currentEntry = getBestiaryEntry(entryName);
        displayedPerks = currentEntry.getSortedPerks();
        player.openInterface(InterfaceType.MAIN, 1010);
        player.getPacketSender().sendString(1010, 7, StringUtils.capitalizeFirst(entryName));
        player.getPacketSender().sendClientScript(10070, "is", getKillCount(entryName), currentEntry.generateRewardString());
        player.getPacketSender().sendAccessMask(1010, 3, 0, 100, AccessMasks.ClickOp1);
    }

    static {
        InterfaceHandler.register(1010, h -> {
            h.actions[3] = (SlotAction) (player, slot) -> {
                Bestiary bestiary = player.getBestiary();
                BestiaryEntry entry = bestiary.currentEntry;
                int index = slot / 6;
                BestiaryPerk perk = bestiary.displayedPerks.get(index);
                entry.togglePerk(perk.getClass());
                player.getPacketSender().sendClientScript(10070, "is", bestiary.getKillCount(entry.getName()), entry.generateRewardString());
            };
        });
    }
}

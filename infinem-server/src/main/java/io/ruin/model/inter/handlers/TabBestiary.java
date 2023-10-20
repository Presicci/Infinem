package io.ruin.model.inter.handlers;

import io.ruin.model.content.bestiary.Bestiary;
import io.ruin.model.content.bestiary.BestiaryDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.journal.dropviewer.DropViewer;
import io.ruin.model.inter.utils.Config;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TabBestiary {

    public static void sendTab(Player player) {
        populateList(player);
        player.getPacketSender().sendAccessMask(1009, 14, 0, 12, 2);
        player.getPacketSender().sendAccessMask(1009, 2, 0, BestiaryDef.ENTRIES.size() * 4, 2);
    }

    private static void populateList(Player player) {
        Bestiary bestiary = player.getBestiary();
        player.getPacketSender().sendClientScript(10067, "iis", bestiary.getKillCounts().size(), BestiaryDef.ENTRIES.size(), bestiary.generateBaseInterfaceList());
    }

    public static void attemptRefresh(Player player) {
        if (player.isVisibleInterface(1009)) {
            populateList(player);
        }
    }

    public static void processSearch(Player player, String searchRegex) {
        searchRegex = searchRegex.toLowerCase();
        Set<String> searchEntries = new HashSet<>();
        for (String entry : BestiaryDef.ENTRIES) {
            if (entry.toLowerCase().contains(searchRegex))
                searchEntries.add(entry);
        }
        Bestiary bestiary = player.getBestiary();
        bestiary.setEntries(searchEntries);
        String searchString = bestiary.generateInterfaceString();
        Map<String, Integer> killcounts = bestiary.getKillCounts();
        int size = (int) searchEntries.stream().filter(killcounts::containsKey).count();
        player.getPacketSender().sendClientScript(10067, "iis", size, size, searchString);
    }

    static {
        InterfaceHandler.register(1009, interfaceHandler -> {
            interfaceHandler.actions[6] = (SimpleAction) DropViewer::open;
            interfaceHandler.actions[8] = (SimpleAction) (player) -> player.stringInput("Search:", search -> processSearch(player, search));
            interfaceHandler.actions[14] = (SlotAction) (player, slot) -> {
                Config.BESTIARY_SORT.set(player, slot-1);
                sendTab(player);
            };
        });
    }
}

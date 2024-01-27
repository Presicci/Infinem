package io.ruin.model.inter.journal.dropviewer;

import io.ruin.Server;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.process.task.TaskWorker;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewerSearch {

    private static final int MAX_RESULTS = 100;

    protected static void search(Player player, String name, boolean monster) {
        String imgTag;
        if (monster)
            player.sendMessage((imgTag = "<img=108>") + Color.DARK_GREEN.tag() + " Drop Viewer: Searching for monster \"" + name + "\"...");
        else
            player.sendMessage((imgTag = "<img=33>") + Color.DARK_GREEN.tag() + " Drop Viewer: Searching for monsters that drop \"" + name + "\"...");
        TaskWorker.startTask(t -> {
            List<DropViewerEntry> results = new ArrayList<>();
            String search = formatForSearch(name);
            AtomicBoolean tooMany = new AtomicBoolean(false);
            if (!search.isEmpty()) {
                LinkedHashMap<String, TreeMap<Integer, List<NPCDef>>> map = new LinkedHashMap<>();
                if (monster) {
                    NPCDef.forEach(npcDef -> {
                        String searchName = formatForSearch(npcDef.name);
                        if (!searchName.contains(search))
                            return;
                        String extension = DropViewerNPCExtensions.NPCS.get(npcDef.id);
                        if (extension != null) {
                            map.computeIfAbsent(searchName + extension, k -> new TreeMap<>()).computeIfAbsent(npcDef.combatLevel, k -> new ArrayList<>()).add(npcDef);
                        } else {
                            map.computeIfAbsent(searchName, k -> new TreeMap<>()).computeIfAbsent(npcDef.combatLevel, k -> new ArrayList<>()).add(npcDef);
                        }
                    });
                    for (DropViewerEntry entry : DropViewerCustomEntries.ENTRIES) {
                        if (formatForSearch(entry.name).contains(search))
                            results.add(entry);
                    }
                } else {
                    ItemDef.forEach(itemDef -> {
                        if (!formatForSearch(itemDef.name).contains(search))
                            return;
                        HashSet<NPCDef> npcDefs = DropViewer.drops.get(itemDef.id);
                        if (npcDefs != null)
                            npcDefs.forEach(npcDef -> {
                                String npcName = formatForSearch(npcDef.name);
                                String extension = DropViewerNPCExtensions.NPCS.get(npcDef.id);
                                if (extension != null) {
                                    map.computeIfAbsent(npcName + extension, k -> new TreeMap<>()).computeIfAbsent(npcDef.combatLevel, k -> new ArrayList<>()).add(npcDef);
                                } else {
                                    map.computeIfAbsent(npcName, k -> new TreeMap<>()).computeIfAbsent(npcDef.combatLevel, k -> new ArrayList<>()).add(npcDef);
                                }
                            });
                        HashSet<DropViewerEntry> nonNPCEntries = DropViewer.NON_NPC_DROPS.get(itemDef.id);
                        if (nonNPCEntries != null) {
                            nonNPCEntries.stream().filter(e -> !results.contains(e)).forEach(results::add);
                        }
                    });
                }
                map.values().forEach(levelsMap -> {
                    if (results.size() >= MAX_RESULTS) {
                        tooMany.set(true);
                        return;
                    }
                    List<NPCDef> matched = new ArrayList<>();
                    levelsMap.values().forEach(defs -> {
                        defs.sort(new Comparator<NPCDef>() {
                            @Override
                            public int compare(NPCDef d1, NPCDef d2) {
                                return Integer.compare(priority(d2), priority(d1));
                            }
                            private int priority(NPCDef def) {
                                int priority = 0;
                                if (def.combatInfo != null)
                                    priority++;
                                if (def.lootTable != null)
                                    priority++;
                                if (def.combatLevel > 0)
                                    priority++;
                                if (def.hasOption("attack"))
                                    priority++;
                                return priority;
                            }
                        });
                        NPCDef def = defs.get(0);
                        if (def.combatInfo != null && def.lootTable != null && def.combatLevel > 0)
                            matched.add(def);
                        //^ only match the highest priority npc with this combat level.
                    });
                    for (NPCDef def : matched) {
                        if (results.size() >= MAX_RESULTS) {
                            tooMany.set(true);
                            break;
                        }
                        DropViewerEntry result = new DropViewerEntry(def.id);
                        String extension = DropViewerNPCExtensions.NPCS.get(def.id);
                        if (extension != null)
                            result.name += extension;
                        if (matched.size() > 1) //multiple same levels
                            result.name += " (" + def.combatLevel + ")";
                        result.name = result.name.replace("Greater Skeleton Hellhound", "Grtr. Skeleton Hellhound");
                        results.add(result);
                    }
                });
            }
            Server.worker.execute(() -> {
                int found = results.size(); //minus two because of the search entries
                if (found == 0) {
                    player.removeAttribute(AttributeKey.DROP_VIEWER_RESULTS);
                    player.sendMessage(imgTag + Color.DARK_GREEN.tag() + " Drop Viewer: No results found.");
                } else {
                    player.putTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS, results);
                    if (found == 1)
                        player.sendMessage(imgTag + Color.DARK_GREEN.tag() + " Drop Viewer: 1 result found.");
                    else {
                        if (tooMany.get()) {
                            player.sendMessage(imgTag + Color.DARK_GREEN.tag() + " Drop Viewer: Too many results found, showing first " + found + ".");
                        } else {
                            player.sendMessage(imgTag + Color.DARK_GREEN.tag() + " Drop Viewer: " + found + " results found.");
                        }
                    }
                    sendEntries(player);
                }
            });
        });
    }

    private static String formatForSearch(String string) {
        return string.replace("'", "")
                .toLowerCase()
                .trim();
    }

    protected static void sendEntries(Player player) {
        List<DropViewerEntry> results = player.getTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS);
        String entries = buildEntries(results);
        player.getPacketSender().sendAccessMask(1000, 16, 0, results.size(), 2);
        player.getPacketSender().sendClientScript(9001, "is", results.size(), entries);
    }

    private static String buildEntries(List<DropViewerEntry> entries) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            DropViewerEntry e = entries.get(i);
            sb.append(e.name);

            if (i != entries.size() - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }
}

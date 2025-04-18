package io.ruin.model.inter.journal.dropviewer;

import io.ruin.Server;
import io.ruin.utility.Color;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.NPCDefinition;
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

    protected static void itemSearch(Player player, int itemId) {
        ItemDefinition def = ItemDefinition.get(itemId);
        String name = def.name;
        player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: Searching for monsters that drop \"" + name + "\"...");
        Map<String, List<Integer>> addedNPCs = new HashMap<>();
        TaskWorker.startTask(t -> {
            List<DropViewerEntryItem> results = new ArrayList<>();
            AtomicBoolean tooMany = new AtomicBoolean(false);
            for (int id : Arrays.asList(itemId, def.notedId)) {
                if (id < 0) continue;
                if (results.size() >= MAX_RESULTS) {
                    tooMany.set(true);
                    break;
                }
                HashSet<NPCDefinition> npcDefinitions = DropViewer.drops.get(id);
                if (npcDefinitions != null) {
                    npcDefinitions.forEach(npcDef -> {
                        if (results.size() >= MAX_RESULTS) {
                            tooMany.set(true);
                            return;
                        }
                        String extension = DropViewerNPCExtensions.NPCS.get(npcDef.id);
                        DropViewerEntryItem entry;
                        if (extension != null) {
                            entry = new DropViewerEntryItem(npcDef.name + extension, npcDef.lootTable, id, npcDef.id);
                        } else {
                            entry = new DropViewerEntryItem(npcDef.name, npcDef.lootTable, id, npcDef.id);
                        }
                        List<Integer> chances = addedNPCs.get(npcDef.name);
                        if (chances != null && chances.contains(entry.chance)) {
                            return;
                        }
                        results.add(entry);
                        addedNPCs.computeIfAbsent(npcDef.name, e -> new ArrayList<>()).add(entry.chance);
                    });
                }
                if (results.size() >= MAX_RESULTS) {
                    tooMany.set(true);
                    break;
                }
                HashSet<DropViewerEntry> nonNPCEntries = DropViewer.NON_NPC_DROPS.get(id);
                if (nonNPCEntries != null) {
                    nonNPCEntries.stream().filter(e -> !results.contains(e)).forEach(e -> {
                        results.add(new DropViewerEntryItem(e.name, e.table, id));
                    });
                }
            }
            Server.worker.execute(() -> {
                int found = results.size(); //minus two because of the search entries
                if (found == 0) {
                    player.removeAttribute(AttributeKey.DROP_VIEWER_RESULTS);
                    player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: No results found.");
                } else {
                    results.sort(Comparator.comparingInt(o -> o.chance));
                    player.putTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS, results);
                    if (found == 1)
                        player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: 1 result found.");
                    else {
                        if (tooMany.get()) {
                            player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: Too many results found, showing first " + found + ".");
                        } else {
                            player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: " + found + " results found.");
                        }
                    }
                    sendEntries(player);
                }
            });
        });
    }

    protected static void search(Player player, String name) {
        player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: Searching for monster \"" + name + "\"...");
        TaskWorker.startTask(t -> {
            List<DropViewerEntry> results = new ArrayList<>();
            String search = formatForSearch(name);
            AtomicBoolean tooMany = new AtomicBoolean(false);
            if (!search.isEmpty()) {
                LinkedHashMap<String, TreeMap<Integer, List<NPCDefinition>>> map = new LinkedHashMap<>();
                NPCDefinition.forEach(npcDef -> {
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
                map.values().forEach(levelsMap -> {
                    if (results.size() >= MAX_RESULTS) {
                        tooMany.set(true);
                        return;
                    }
                    List<NPCDefinition> matched = new ArrayList<>();
                    levelsMap.values().forEach(defs -> {
                        defs.sort(new Comparator<NPCDefinition>() {
                            @Override
                            public int compare(NPCDefinition d1, NPCDefinition d2) {
                                return Integer.compare(priority(d2), priority(d1));
                            }
                            private int priority(NPCDefinition def) {
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
                        NPCDefinition def = defs.get(0);
                        if (def.combatInfo != null && def.lootTable != null && def.combatLevel > 0)
                            matched.add(def);
                        //^ only match the highest priority npc with this combat level.
                    });
                    for (NPCDefinition def : matched) {
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
                    player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: No results found.");
                } else {
                    results.sort(Comparator.comparing(o -> o.name));
                    player.putTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS, results);
                    if (found == 1)
                        player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: 1 result found.");
                    else {
                        if (tooMany.get()) {
                            player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: Too many results found, showing first " + found + ".");
                        } else {
                            player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: " + found + " results found.");
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
            if (e instanceof DropViewerEntryItem) {
                sb.append(((DropViewerEntryItem) e).getChanceString());
            }
            sb.append(e.name);

            if (i != entries.size() - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }
}

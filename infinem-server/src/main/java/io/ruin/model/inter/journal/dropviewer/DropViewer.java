package io.ruin.model.inter.journal.dropviewer;

import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.impl.chests.BrimstoneChest;
import io.ruin.model.map.object.actions.impl.chests.CrystalChest;
import io.ruin.model.map.object.actions.impl.chests.DarkChest;
import io.ruin.model.map.object.actions.impl.chests.ElvenCrystalChest;
import io.ruin.model.map.object.actions.impl.chests.GrubbyChest;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewer {

    protected static final HashMap<Integer, LinkedHashSet<NPCDef>> drops = new HashMap<>();
    protected static final HashMap<Integer, LinkedHashSet<DropViewerEntry>> NON_NPC_DROPS = new HashMap<>();
    protected static final DropViewerEntry[] CUSTOM_ENTRIES = new DropViewerEntry[] {
            new DropViewerEntry("Beginner Clue Casket", ClueType.BEGINNER.lootTable),
            new DropViewerEntry("Easy Clue Casket", ClueType.EASY.lootTable),
            new DropViewerEntry("Medium Clue Casket", ClueType.MEDIUM.lootTable),
            new DropViewerEntry("Hard Clue Casket", ClueType.HARD.lootTable),
            new DropViewerEntry("Elite Clue Casket", ClueType.ELITE.lootTable),
            new DropViewerEntry("Master Clue Casket", ClueType.MASTER.lootTable),
            new DropViewerEntry("Crystal Chest", CrystalChest.LOOT_TABLE),
            new DropViewerEntry("Elven Crystal Chest", ElvenCrystalChest.LOOT_TABLE),
            new DropViewerEntry("Brimstone Chest", BrimstoneChest.LOOT_TABLE),
            new DropViewerEntry("Dark Chest", DarkChest.LOOT_TABLE),
            new DropViewerEntry("Grubby Chest", GrubbyChest.MAIN, GrubbyChest.FOOD, GrubbyChest.POTION)
    };

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.DROP_VIEWER);
        if (player.getTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS) == null) {
            player.putTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS, new ArrayList<>(Arrays.asList(
                    new DropViewerEntry(5886), new DropViewerEntry(5862), new DropViewerEntry(319),
                    new DropViewerEntry(7144), new DropViewerEntry(2215), new DropViewerEntry(5779),
                    new DropViewerEntry(963), new DropViewerEntry(3162), new DropViewerEntry(3129),
                    new DropViewerEntry(6766), new DropViewerEntry(8061), new DropViewerEntry(2042)
            )));
        }
        DropViewerSearch.sendEntries(player);
    }

    private static void displayDrops(Player player, int id, String name) {
        NPCDef def = NPCDef.get(id);
        if (def.lootTable == null) {
            player.sendMessage("<img=108>" + Color.DARK_GREEN.tag() + " Drop Viewer: " + Color.OLIVE.tag() + name + " has no drops.");
            return;
        }
        DropViewerResult petDrop = null;
        if(def.combatInfo != null && def.combatInfo.pet != null) {
            petDrop = new DropViewerResultPet(def.combatInfo.pet, def.combatInfo.pet.dropAverage);
        }
        displayDrops(petDrop, player, name, def.lootTable);
    }

    private static void displayDrops(Player player, String name, LootTable lootTable) {
        displayDrops(null, player, name, lootTable);
    }

    private static void displayDrops(DropViewerResult petDrop, Player player, String name, LootTable lootTable) {
        List<DropViewerResult> drops = new ArrayList<>();
        double totalTablesWeight = lootTable.totalWeight;
        if(lootTable.tables != null) {
            for (LootTable.ItemsTable table : lootTable.tables) {
                if (table != null) {
                    for (LootTable.CommonTables cTable : LootTable.CommonTables.values()) {
                        if (cTable.title.equalsIgnoreCase(table.name)) {
                            drops.add(new DropViewerResultCommon(cTable, (int) Math.ceil(1D /(table.weight / totalTablesWeight))));
                        }
                    }
                }
            }
        }
        if (lootTable.guaranteed != null) {
            for (LootItem item : lootTable.guaranteed) {
                drops.add(new DropViewerResultItem(item.id, item.min, item.max, 1));
            }
        }
        if (lootTable.tables != null) {
            for (LootTable.ItemsTable table : lootTable.tables) {
                if (table != null) {
                    double tableChance;
                    if (totalTablesWeight == 0) {
                        tableChance = 1;
                    } else {
                        tableChance = table.weight / totalTablesWeight;
                    }
                    if (table.items.length == 0) {
                        //Nothing!
                        //nothingPercentage = tableChance * 100D;
                    } else {
                        for (LootItem item : table.items) {
                            if (item.id == 0 || item.id == -1) {
                                continue;
                            }
                            int chance;
                            if (item.weight == 0)
                                chance = (int) (1D / tableChance);
                            else
                                chance = (int) (1D / (tableChance * (item.weight / table.totalWeight)));
                            List<Item> groupDrops = getGroupDrop(item.id, name);
                            if (groupDrops != null && groupDrops.size() > 1) {
                                drops.add(new DropViewerResultPair(groupDrops.get(0), groupDrops.get(1), chance));
                            } else {
                                drops.add(new DropViewerResultItem(item.id, item.min, item.max, chance));
                            }
                        }
                    }
                }
            }
        }
        if (!drops.isEmpty()) {
            drops.sort((d1, d2) -> {
                int x = d1.chance;
                int y = d2.chance;
                return Integer.compare(x, y);
            });
            if (petDrop != null) {
                drops.add(0, petDrop);
            }
            player.getPacketSender().sendClientScript(227, "is", 1000 << 16 | 1, "Viewing drop table for: <col=ff0000>" + name + "</col>");
            player.getPacketSender().sendClientScript(9004, "is", drops.size(), buildDropString(drops));
            int slot = 1;
            for (DropViewerResult drop : drops) {
                Item item = drop.getItem();
                player.getPacketSender().sendClientScript(9006, "iii", slot, item.getId(), item.getAmount());
                slot += 5;
            }
        }
    }

    private static List<Item> getGroupDrop(int item, String name) {
        Item[][] groups = NPCCombat.groupedDrops.get(name.toLowerCase());
        if (groups == null) {
            return null;
        }
        for (Item[] group : groups) {
            if (item == group[0].getId()) {
                return (Arrays.asList(group));
            }
        }
        return null;
    }

    private static String buildDropString(List<DropViewerResult> drops) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < drops.size(); i++) {
            DropViewerResult r = drops.get(i);
            sb.append(r.get());

            if (i != drops.size() - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    private static void clickEntry(Player player, int slot) {
        List<DropViewerEntry> results = player.getTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS);
        DropViewerEntry result = results.get(slot);
        if (result.table != null) {
            displayDrops(player, result.name, result.table);
        } else {
            displayDrops(player, result.id, result.name);
        }
    }

    static {
        NPCDef.forEach(def -> {
            if (def != null && def.lootTable != null)
                def.lootTable.allItems().forEach(item -> drops.computeIfAbsent(item.getId(), k -> new LinkedHashSet<>()).add(def));
            if (def != null && def.combatInfo != null && def.combatInfo.pet != null)
                drops.computeIfAbsent(def.combatInfo.pet.itemId, k -> new LinkedHashSet<>()).add(def);
        });
        for (DropViewerEntry entry : CUSTOM_ENTRIES) {
            for (LootItem item : entry.table.getLootItems()) {
                NON_NPC_DROPS.computeIfAbsent(item.id, k -> new LinkedHashSet<>()).add(entry);
            }
        }
        InterfaceHandler.register(Interface.DROP_VIEWER, h -> {
            h.actions[16] = (SlotAction) DropViewer::clickEntry;
            h.actions[17] = (SimpleAction) player -> player.stringInput("<img=108> Enter monster name to search for:", name -> DropViewerSearch.search(player, name, true));
            h.actions[18] = (SimpleAction) player -> player.stringInput("<img=33> Enter drop name to search for:", name -> DropViewerSearch.search(player, name, false));
        });
    }
}

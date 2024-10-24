package io.ruin.model.inter.journal.dropviewer;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.loot.*;
import io.ruin.utility.Color;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPCDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewer {

    protected static final HashMap<Integer, LinkedHashSet<NPCDefinition>> drops = new HashMap<>();
    protected static final HashMap<Integer, LinkedHashSet<DropViewerEntry>> NON_NPC_DROPS = new HashMap<>();

    public static void open(Player player) {
        player.openResizeableInterface(InterfaceType.MAIN, Interface.DROP_VIEWER);
        if (player.getTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS) == null) {
            player.putTemporaryAttribute(AttributeKey.DROP_VIEWER_RESULTS, new ArrayList<>(Arrays.asList(
                    new DropViewerEntry(5886), new DropViewerEntry(5862), new DropViewerEntry(319),
                    new DropViewerEntry(7144), new DropViewerEntry(2215), new DropViewerEntry(5779),
                    new DropViewerEntry(963), new DropViewerEntry(3162), new DropViewerEntry(3129),
                    new DropViewerEntry(6766), new DropViewerEntry(8061), new DropViewerEntry(2042)
            )));
        }
        player.getPacketSender().sendAccessMask(Interface.DROP_VIEWER, 24, 0, 1000, AccessMasks.ClickOp1, AccessMasks.ClickOp10);
        DropViewerSearch.sendEntries(player);
        player.getTaskManager().doLookupByUUID(1007);   // Open the Drop Viewer
    }

    private static void displayDropsWithConditionals(Player player, int id, String name) {
        NPCDefinition def = NPCDefinition.get(id);
        int activeModifiers = player.getTemporaryAttributeIntOrZero("DROPVIEWER_MODI");
        LootTable lootTable = def.lootTable.copy();
        if (!ConditionalNPCLootTable.LOADED_TABLES.containsKey(id)) displayDrops(player, id, name);
        int index = 1;
        for (ConditionalNPCLootTable table : ConditionalNPCLootTable.LOADED_TABLES.get(id)) {
            if ((activeModifiers & index) == index) table.modifyTable(lootTable);
            index *= 2;
        }
        DropViewerResult petDrop = null;
        if(def.combatInfo != null && def.combatInfo.pet != null) {
            petDrop = new DropViewerResultPet(def.combatInfo.pet, def.combatInfo.pet.dropAverage);
        }
        List<DropViewerResult> drops = calculateDrops(petDrop, name, lootTable);
        sendResults(player, id, name, drops, false);
    }

    private static void displayDrops(Player player, int id, String name) {
        NPCDefinition def = NPCDefinition.get(id);
        if (def.lootTable == null) {
            player.sendMessage(Color.DARK_GREEN.tag() + " Drop Viewer: " + Color.OLIVE.tag() + name + " has no drops.");
            return;
        }
        DropViewerResult petDrop = null;
        if(def.combatInfo != null && def.combatInfo.pet != null) {
            petDrop = new DropViewerResultPet(def.combatInfo.pet, def.combatInfo.pet.dropAverage);
        }
        displayDrops(petDrop, player, id, name, def.lootTable);
    }

    private static void displayDrops(Player player, DropViewerResultPet pet, String name, LootTable lootTable) {
        displayDrops(pet, player, -1, name, lootTable);
    }

    private static void displayDrops(Player player, String name, LootTable lootTable) {
        displayDrops(null, player, -1, name, lootTable);
    }

    private static void displayDrops(DropViewerResult petDrop, Player player, int id, String name, LootTable lootTable) {
        player.removeTemporaryAttribute("DROPVIEWER_MODI");
        List<DropViewerResult> drops = calculateDrops(petDrop, name, lootTable);
        if (drops.isEmpty()) return;
        sendResults(player, id, name, drops, true);
    }

    protected static int getDropChance(int itemId, LootTable lootTable) {
        if (lootTable == null) return -1;
        if (lootTable.guaranteed != null) {
            Optional<LootItem> item = Arrays.stream(lootTable.guaranteed).filter(i -> i.id == itemId).findFirst();
            if (item.isPresent()) return 1;
        }
        if (lootTable.tables != null) {
            double totalTablesWeight = lootTable.totalWeight;
            for (LootTable.ItemsTable table : lootTable.tables) {
                if (table == null) continue;
                Optional<LootItem> optionalItem = Arrays.stream(table.items).filter(i -> i.id == itemId).findFirst();
                if (optionalItem.isPresent()) {
                    double tableChance = 1;
                    if (totalTablesWeight != 0) {
                        tableChance = table.weight / totalTablesWeight;
                    }
                    LootItem item = optionalItem.get();
                    if (item.weight == 0)
                        return (int) (1D / tableChance);
                    else
                        return (int) Math.max(2, 1D / (tableChance * (item.weight / table.totalWeight)));
                }
            }
        }
        return -1;
    }

    private static List<DropViewerResult> calculateDrops(DropViewerResult petDrop, String name, LootTable lootTable) {
        List<DropViewerResult> drops = new ArrayList<>();
        double totalTablesWeight = lootTable.totalWeight;
        if(lootTable.tables != null) {
            for (LootTable.ItemsTable table : lootTable.tables) {
                if (table != null) {
                    for (LootTable.CommonTables cTable : LootTable.CommonTables.values()) {
                        if (cTable.title.equalsIgnoreCase(table.name)) {
                            drops.add(new DropViewerResultCommon(cTable, (int) Math.max(2, 1D /(table.weight / totalTablesWeight))));
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
                                chance = (int) Math.max(2, 1D / (tableChance * (item.weight / table.totalWeight)));
                            List<Item> groupDrops = getGroupDrop(item.id, name);
                            String dropDescription = getDropDescription(item.id, name);
                            if (groupDrops != null && groupDrops.size() > 1) {
                                drops.add(new DropViewerResultPair(groupDrops.get(0), groupDrops.get(1), chance));
                                drops.add(new DropViewerResultPair(groupDrops.get(1), groupDrops.get(0), chance));
                            } else if (dropDescription != null) {
                                drops.add(new DropViewerResultDescription(item.id, dropDescription, chance));
                            } else {
                                if (item instanceof LootItemSet) {
                                    Item[] setItems = ((LootItemSet) item).toItems();
                                    for (Item i : setItems) {
                                        drops.add(new DropViewerResultSet(chance, i, setItems));
                                    }
                                } else if (item instanceof LootItemPair) {
                                    drops.add(new DropViewerResultPair(item.toItem(), ((LootItemPair) item).secondToItem(), chance));
                                    drops.add(new DropViewerResultPair(((LootItemPair) item).secondToItem(), item.toItem(), chance));
                                } else {
                                    drops.add(new DropViewerResultItem(item.id, item.min, item.max, chance));
                                }
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
        }
        return drops;
    }

    private static void sendResults(Player player, int id, String name, List<DropViewerResult> drops, boolean resetScroll) {
        player.getPacketSender().sendClientScript(227, "is", 1000 << 16 | 1, name);
        player.getPacketSender().sendClientScript(9004, "is", resetScroll ? 1 : 0, buildDropString(drops));
        int slot = 1;
        for (DropViewerResult drop : drops) {
            Item item = drop.getItem();
            player.getPacketSender().sendClientScript(9006, "iiii", 1000 << 16 | 24, slot, item.getId(), item.getAmount());
            slot += 7;
        }
        sendModifiers(player, id);
        player.putTemporaryAttribute("DROPVIEWER_DROPS", drops);
        player.putTemporaryAttribute("DROPVIEWER_NAME", name);
        player.putTemporaryAttribute("DROPVIEWER_ID", id);
    }

    private static List<Item> getGroupDrop(int item, String name) {
        Item[][] groups = NPCDrops.groupedDrops.get(name.toLowerCase());
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

    private static String getDropDescription(int item, String name) {
        DropViewerResultDescription.ItemDescription[] descriptons = DropViewerResultDescription.descriptionDrops.get(name.toLowerCase());
        if (descriptons == null) {
            return null;
        }
        for (DropViewerResultDescription.ItemDescription description : descriptons) {
            if (item == description.getItemId()) {
                return description.getDescription();
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
        player.getPacketSender().setHidden(Interface.DROP_VIEWER, 32, true);
        if (result.table != null) {
            if (result.pet != null) {
                displayDrops(player, result.pet, result.name, result.table);
            } else {
                displayDrops(player, result.name, result.table);
            }
        } else {
            displayDrops(player, result.id, result.name);
        }
    }

    private static void clickItem(Player player, int option, int slot, int itemId) {
        if (option == 1) {
            List<DropViewerResult> drops = player.getTemporaryAttributeOrDefault("DROPVIEWER_DROPS", new ArrayList<>());
            int index = ((slot - 2) / 7);
            if (drops == null || drops.isEmpty() || drops.size() < index) return;
            DropViewerResult result = drops.get(index);
            if (result instanceof DropViewerResultCommon) {
                player.putTemporaryAttribute("DROPVIEWER_LAST", drops);
                player.putTemporaryAttribute("DROPVIEWER_LAST_NAME", player.getTemporaryAttributeOrDefault("DROPVIEWER_NAME", ""));
                player.putTemporaryAttribute("DROPVIEWER_LAST_ID", player.getTemporaryAttributeOrDefault("DROPVIEWER_ID", -1));
                LootTable.CommonTables table = ((DropViewerResultCommon) result).getCommonTable();
                displayDrops(player, table.name, new LootTable().addTable(1, table.items));
                player.getPacketSender().setHidden(Interface.DROP_VIEWER, 32, false);
            } else {
                DropViewerSearch.itemSearch(player, result.getItem().getId());
            }
        } else if (option == 10 && itemId > -1) {
            new Item(itemId).examine(player);
        }
    }

    private static void clickModifier(Player player, int bit) {
        player.putTemporaryAttribute("DROPVIEWER_MODI", NumberUtils.toggleBit(player.getTemporaryAttributeOrDefault("DROPVIEWER_MODI", 0), bit));
        int id = player.getTemporaryAttributeOrDefault("DROPVIEWER_ID", -1);
        String name = player.getTemporaryAttributeOrDefault("DROPVIEWER_NAME", "");
        displayDropsWithConditionals(player, id, name);
    }

    private static void sendModifiers(Player player, int id) {
        if (id > -1 && ConditionalNPCLootTable.LOADED_TABLES.containsKey(id)) {
            int activeModifiers = player.getTemporaryAttributeIntOrZero("DROPVIEWER_MODI");
            StringBuilder sb = new StringBuilder();
            int index = 1;
            for (ConditionalNPCLootTable table : ConditionalNPCLootTable.LOADED_TABLES.get(id)) {
                if (index > 1) sb.append("|");
                sb.append(table.getDropConditionName());
                sb.append("|");
                sb.append((activeModifiers & index) == index ? "1" : "0");
                index *= 2;
            }
            player.getPacketSender().sendClientScript(9000, "is", 1, sb.toString());
        } else {
            player.getPacketSender().sendClientScript(9000, "is", 0, "");
        }
    }

    static {
        NPCDefinition.forEach(def -> {
            if (def != null && def.lootTable != null)
                def.lootTable.allItems().forEach(item -> drops.computeIfAbsent(item.getId(), k -> new LinkedHashSet<>()).add(def));
            if (def != null && def.combatInfo != null && def.combatInfo.pet != null)
                drops.computeIfAbsent(def.combatInfo.pet.itemId, k -> new LinkedHashSet<>()).add(def);
        });
        for (DropViewerEntry entry : DropViewerCustomEntries.ENTRIES) {
            for (LootItem item : entry.table.getLootItems()) {
                NON_NPC_DROPS.computeIfAbsent(item.id, k -> new LinkedHashSet<>()).add(entry);
            }
        }
        InterfaceHandler.register(Interface.DROP_VIEWER, h -> {
            h.actions[16] = (SlotAction) DropViewer::clickEntry;
            h.actions[17] = (SimpleAction) player -> player.stringInput("Enter monster name to search for:", name -> DropViewerSearch.search(player, name));
            h.actions[18] = (SimpleAction) player -> {
                player.itemSearch("Search for monsters that drop:", false, item -> {
                    DropViewerSearch.itemSearch(player, item);
                });
            };
            h.actions[24] = (DefaultAction) DropViewer::clickItem;
            h.actions[32] = (SimpleAction) player -> {
                List<DropViewerResult> drops = player.getTemporaryAttributeOrDefault("DROPVIEWER_LAST", new ArrayList<>());
                String name = player.getTemporaryAttributeOrDefault("DROPVIEWER_LAST_NAME", "");
                int id = player.getTemporaryAttributeOrDefault("DROPVIEWER_LAST_ID", -1);
                if (drops == null || drops.isEmpty()) return;
                sendResults(player, id, name, drops, true);
                player.getPacketSender().setHidden(Interface.DROP_VIEWER, 32, true);
            };
            h.actions[34] = (SimpleAction) player -> clickModifier(player, 1);
            h.actions[35] = (SimpleAction) player -> clickModifier(player, 2);
            h.actions[36] = (SimpleAction) player -> clickModifier(player, 4);
        });
    }
}

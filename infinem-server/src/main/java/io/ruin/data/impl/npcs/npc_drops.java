package io.ruin.data.impl.npcs;

import com.google.common.base.CharMatcher;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.FileUtils;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.herblore.Herb;
import io.ruin.utility.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

import static io.ruin.cache.ItemID.COINS_995;

public class npc_drops extends DataFile {

    /*
     * Strings that if found in the chance line for a table, will use that table.
     *
     * Example:
     *
     * Herbs <-- Header
     * There is a 31/128 chance of rolling the herb drop table. <--- Chanceline
     * [Table] <---- Table that we want to use
     */
    private static final String[] chanceLinesWithTable = {
            "herb subtable", "Due to a unique mechanic", "noted herbs", "unique table",
            "bolt tips", "uniques sub-table", "bush seed table", "fruit tree seed table",
            "Two random herbs are dropped when this herb drop table is rolled",
            "Skotizo's gemstone sub-table", "shard table", "unique drop table",
            "sigil drop table", "noted herb", "hops seed table",
            "Each tertiary item may be simultaneously dropped",
            "coin", "mutagen sub-table", "some cabbages"
    };

    /*
     * Strings that if found in the chance line for a table, will use a table that is
     * after the next element.
     *
     * Robes <-- Header
     * There is a 31/128 chance of rolling the robe subtable. <--- Chanceline
     * The robe sub table contains useless shit. <--- Another string line, skip this
     * [Table] <---- Table that we want to use
     */
    private static final String[] chanceLinesWithTableSkipElement = {
            "robe subtable"
    };

    /*
     * Strings that if found in the chance line for a table, will skip that table entirely.
     *
     * Example:
     *
     * Location <--- Header
     * Slayer rings offer immediate access to Fremennik Slayer Dungeon. <--- Chanceline
     * <--- No table, so we skip
     *
     */
    private static final String[] chanceLinesWithoutTable = {
            "gem drop table", "rare drop", "Catacombs", "Krystilia", "cavalier", "Deadman",
            "unarmed", "Twisted", "pickpocket", "superior", "Defenders",
            "are dropped at a time", "guaranteed drop of at least one ancient shard"
    };

    /*
     * Strings in item names that will be ignored when parsing drop tables.
     * Case sensitive!
     */
    private static final String[] itemsToSkip = {
            "(f)", "Brimstone key", "Ikkle", "Ecumenical", "Callisto cub", "Pet kraken",
            "Baby mole", "Kalphite princess", "Pet dagannoth prime", "Pet dagannoth supreme",
            "Pet general graardor", "Pet kree'arra", "Pet snakeling", "Prince black dragon",
            "Skotos", "Venenatis spiderling", "Vet'ion jr.", "Hellpuppy", "Pet chaos elemental",
            "Pet dagannoth rex", "Pet dark core", "Pet k'ril tsutsaroth", "Pet smoke devil",
            "Pet zilyana", "Scorpia's offspring", "Sraracha", "Vorki"
    };

    @Override
    public String path() {
        return "npcs/drops/" + (World.isEco() ? "eco" : "pvp") + "/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<DropTable> tables = JsonUtils.fromJson(json, List.class, DropTable.class);
        Map<Integer, LootTable> loaded = new HashMap<>();
        for(DropTable t : tables) {
            t.calculateWeight();
            if(t.guaranteed == null && t.totalWeight == 0) {
                //System.err.println("Drop table " + fileName + " has no drops.");
                //ServerWrapper.logWarning("Drop table " + fileName + " has no drops.");
                continue;
            }
            for (int id : t.ids) {
                LootTable loadedTable = loaded.get(id);
                loaded.put(id, loadedTable == null ? t : loadedTable.combine(t));
            }
        }
        loaded.forEach((id, table) -> NPCDef.get(id).lootTable = table);
        return tables;
    }

    private static class DropTable extends LootTable {
        @Expose public Integer[] ids;
    }

    /**
     * Dumping
     */

    public static void dump(String wikiName) {
        List<Integer> ids = new ArrayList<>();
        int combatLevel = -1;
        String[] s = wikiName.split(">");
        try {
            if (s.length > 1) {
                combatLevel = Integer.parseInt(s[1]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int finalCombatLevel = combatLevel;
        NPCDef.forEach(npcDef -> {
            if (finalCombatLevel >= 0 && npcDef.combatLevel != finalCombatLevel) {
                return;
            }
            if(!npcDef.name.equalsIgnoreCase(s[0].replace("_", " ")))
                return;
            ids.add(npcDef.id);
        });
        Integer[] finalIds = new Integer[ids.size()];
        dump(s[0], ids.toArray(finalIds));
    }

    public static void dump(String wikiName, Integer[] ids) {
        DumpTable table = new WikiDumper(wikiName).run();
        if(table == null)
            return;
        table.export(wikiName, ids);
    }

    private static List<String> groupedDropsNotes;

    private static final class WikiDumper {

        private String wikiName;

        public WikiDumper(String wikiName) {
            this.wikiName = wikiName;
        }

        public DumpTable run() {
            try {
                Document doc = Jsoup.connect("https://oldschool.runescape.wiki/w/" + URLEncoder.encode(wikiName, "UTF-8"))
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .get();
                if(doc == null)
                    throw new IOException("Failed to connect to wiki page!");
                String[] searchHeaders = {"h3", "h4"};
                Elements tableHeaders = null;
                groupedDropsNotes = new ArrayList<>();
                hLoop: for(String s : searchHeaders) {
                    Elements headers = doc.body().select(s);
                    for(Element header : headers) {
                        Element dl = header.nextElementSibling();
                        if(dl != null && dl.is("table")) {
                            if (tableHeaders == null) {
                                tableHeaders = headers;
                                break;
                            } else {
                                tableHeaders.addAll(headers);
                                break;
                            }
                            //break hLoop;
                        }
                    }
                }
                if(tableHeaders == null)
                    throw new IOException("Failed to find table headers!");
                DumpTable table = new DumpTable();
                for(Element header : tableHeaders) {
                    Element dl = header.nextElementSibling();
                    if (dl != null && dl.is("p")) {
                        String chanceLine = dl.select("i").text();
                        if (chanceLine.equals("")) {
                            chanceLine = dl.text();
                        }
                        if (Utils.doesStringContainAny(chanceLine, chanceLinesWithoutTable)) {
                            continue;
                        } else if (Utils.doesStringContainAny(chanceLine, chanceLinesWithTable)) {
                            dl = dl.nextElementSibling();
                        } else if (Utils.doesStringContainAny(chanceLine, chanceLinesWithTableSkipElement)) {
                            dl = dl.nextElementSibling().nextElementSibling();
                        } else {
                            if (chanceLine.length() < 3 || !dl.nextElementSibling().is("table")) {
                                continue;
                            }
                            if (chanceLine.contains("/")) {
                                String[] chanceWords = chanceLine.split(" ");
                                String chance = chanceWords[3];
                                String tableType = chanceWords[8] + " " + chanceWords[9];
                                String[] chanceSplit = chance.split("/");
                                int finalChance = (int) ((Double.parseDouble(chanceSplit[0]) / Double.parseDouble(chanceSplit[1])) * 100000);
                                if (tableType.equalsIgnoreCase("general seed")) {
                                    table.addTable(tableType, finalChance, new LootItem(0, 1, 1, 5));
                                } else {
                                    table.addTable(tableType, finalChance);
                                }
                            } else {
                                String[] chanceWords = chanceLine.split(" ");
                                String chance = chanceWords[3] + chanceWords[4] + chanceWords[5];
                                String tableType = chanceWords[10] + " " + chanceWords[11];
                                String[] chanceSplit = chance.split("in");
                                int finalChance = (int) ((Double.parseDouble(chanceSplit[0]) / Double.parseDouble(chanceSplit[1])) * 100000);
                                if (tableType.equalsIgnoreCase("general seed")) {
                                    table.addTable(tableType, finalChance, new LootItem(0, 1, 1, 5));
                                } else {
                                    table.addTable(tableType, finalChance);
                                }
                            }
                            continue;
                        }
                    }
                    if(dl == null || !dl.is("table"))
                        continue;
                    String tableName = header.text();
                    int i = tableName.indexOf("[edit");
                    if(i != -1)
                        tableName = tableName.substring(0, i);
                    if (tableName.toLowerCase().contains("pickpocket") || tableName.toLowerCase().contains("catacombs")
                            || tableName.toLowerCase().contains("rare drop table")) {
                        continue;
                    }
                    tableName = tableName
                            .replace("100% drop", "Always")
                            .replaceFirst("100%", "Always")
                            .replace("Alwayss", "Always");
                    Elements trs = dl.select("tr");
                    if(trs == null) {
                        continue;
                    }
                    List<DumpItem> tableItems = new ArrayList<>();
                    for(Element tr : trs) {
                        System.out.println(tableName);
                        Elements tds = tr.select("td");
                        if(tds.size() == 0)
                            continue;
                        List<DumpItem> items = parse(tableName, tds.get(1).text(), tds.get(2).text(), tds.get(3));
                        if(items != null)
                            tableItems.addAll(items);
                    }
                    if(tableName.equalsIgnoreCase("always"))
                        table.guaranteedItems(tableItems.toArray(new DumpItem[0]));
                    else
                        table.addTable(tableName, 1, tableItems.toArray(new DumpItem[0]));
                }
                return table;
            } catch(Exception e) {
                if(e.getMessage() == null) {
                    e.printStackTrace();
                    return null;
                }
                if(e.getMessage().contains("HTTP error fetching URL")) {
                    //uhh, page does not exist??
                    return null;
                }
                if(e.getMessage().contains("timed out")) {
                    //timed out, try again!
                    ThreadUtils.sleep(10L);
                    return run();
                }
                ServerWrapper.logError("Error while parsing: " + wikiName, e);
                return null;
            }
        }

        private List<DumpItem> parse(String tableName, String item, String quantity, Element rarity) {
            try {
                /**
                 * Parse item
                 */
                if(item.equalsIgnoreCase("rare drop table") || item.equalsIgnoreCase("nothing")
                        || item.contains("Looting bag")
                        || (rarity.getElementsByTag("span").text().equalsIgnoreCase("always") && !tableName.equalsIgnoreCase("always")))
                    return null;
                int itemId;
                boolean asNote;
                if(item.contains("(m)")) {
                    item = item.replace("(m)", "");
                }
                if(Utils.doesStringContainAny(item, itemsToSkip)) {
                    //item = item.replace("(f)", "");
                    return null;
                }
                if(item.contains("(noted)")) {
                    itemId = findItem(item.replace("(noted)", ""), false);
                    asNote = true;
                } else {
                    itemId = findItem(item, false);
                    asNote = false;
                }
                /**
                 * Parse notes
                 */
                ArrayList<String> itemNotes = new ArrayList<>();
                Elements sups = rarity.getElementsByTag("sup");
                for(Element sup : sups) {
                    String ref = sup.id();
                    int i = ref.lastIndexOf("-");
                    if(i == -1) {
                        System.err.println("Non-indexed note: " + ref);
                        continue;
                    }
                    int first;
                    String ending;
                    try {
                        first = Integer.valueOf(ref.substring(i - 1, i));
                        int second = Integer.valueOf(ref.substring(i + 1));
                        ending = "_" + first + "-" + second;
                    } catch(NumberFormatException e1) {
                        try {
                            first = Integer.valueOf(ref.substring(i + 1));
                            ending = "-" + first;
                        } catch(NumberFormatException e2) {
                            continue;
                        }
                    }
                    if(!ref.endsWith(ending)) {
                        System.err.println("Error parsing ref: " + ref);
                        continue;
                    }
                    ref = ref.substring(0, ref.lastIndexOf(ending)).replace("cite_ref", "cite_note") + "-" + first;
                    Elements notes = rarity.ownerDocument().body()
                            .select("li#" + ref);
                    if(notes.size() != 1) {
                        System.err.println("Invalid amount of notes found for ref: " + ref);
                        continue;
                    }
                    for(Element e : notes.get(0).children()) {
                        if(e.is("span")) {
                            if (e.text().toLowerCase().contains("rag and bone man")) {
                                return null;
                            }
                            if (e.text().toLowerCase().contains("players will receive")
                                    || e.text().toLowerCase().contains("dropped together")
                                    || e.text().toLowerCase().contains("dropped along")) {
                                if (groupedDropsNotes.contains(e.text())) {
                                    System.out.println("Group drop line, removing! '" + e.text() + "'");
                                    return null;
                                } else {
                                    groupedDropsNotes.add(e.text());
                                }
                            }
                            itemNotes.add("WikiNote: " + e.text());
                        }
                    }
                }
                /**
                 * Parse droprates
                 */
                int rarityWeight = 1;
                if (!tableName.equalsIgnoreCase("always")) {
                    Element rarityFractionElement = rarity.getElementsByTag("span").get(0);
                    String rarityPercent = rarityFractionElement.attributes().getIgnoreCase("data-drop-percent");
                    if (rarityPercent.contains("×")) {
                        rarityPercent = rarityPercent.split(" ")[2];
                    }
                    if (rarityPercent.isEmpty()) {
                        String rarityText = rarityFractionElement.text();
                        if (rarityText.toLowerCase().contains("uncommon")) {
                            rarityPercent = "2";    // Default to 1/50
                        } else if (rarityText.toLowerCase().contains("common")) {
                            rarityPercent = "5";    // Default to 1/20
                        }  else if (rarityText.toLowerCase().contains("very rare")) {
                            rarityPercent = "0.1";  // Default to 1/1000
                        } else if (rarityText.toLowerCase().contains("rare")) {
                            rarityPercent = "0.725";    // Default to 1/138
                        }
                    }
                    rarityPercent = rarityPercent.replaceAll("[^a-zA-Z0-9.]","");
                    double rarityDouble = Double.parseDouble(rarityPercent) / 100;
                    rarityWeight = (int ) (100000 * rarityDouble);
                }
                /**
                 * Parse quantities
                 */
                String[] split = quantity.replace(" ", "").replace(";", " ")
                        .toLowerCase().split(" ");
                ArrayList<DumpItem> items = new ArrayList<>();
                for(String s : split) {
                    int id = itemId;
                    if(id != -1 && (asNote || s.contains("noted"))) {
                        ItemDef def = ItemDef.get(id);
                        if((id = def.notedId) == -1)
                            throw new IOException("Item can't be noted!");
                        s = s.replace("(noted)", "");
                        s = s.trim();
                    }
                    s = s.replace(",", "").trim();
                    int min, max;
                    if(s.equalsIgnoreCase("unknown")) {
                        min = 1;
                        max = 1;
                        itemNotes = new ArrayList<>(itemNotes); //give this item it's own notes (just in case there are multiple)
                        itemNotes.add("Warning: Quantities are guessed (min=1, max=1)");
                    } else {
                        String[] amtSplit = null;
                        if(s.contains("-"))
                            amtSplit = s.split("-");
                        else if(s.contains("–"))
                            amtSplit = s.split("–");
                        else if(s.contains("�"))
                            amtSplit = s.split("�");
                        if(amtSplit != null) {
                            if(amtSplit.length != 2)
                                throw new IOException("Invalid min-max amount: " + s);
                            min = Integer.valueOf(amtSplit[0].trim());
                            max = Integer.valueOf(amtSplit[1].trim());
                        } else {
                            min = Integer.valueOf(s);
                            max = min;
                        }
                    }
                    items.add(new DumpItem(id, min, max, rarityWeight, id == -1 ? item : null, itemNotes));
                }
                return items;
            } catch(Exception e) {
                ServerWrapper.logError("Error parsing wiki (" + wikiName + ") table entry! | table=" + tableName + "   item=" + item + "   quantity=" + quantity + "   rarity=" + rarity, e);
                return null;
            }
        }

        private static final Pattern raidPotion = Pattern.compile("(elder|twisted|kodai|revitalisation|prayer enhance|xeric's aid|overload) *(\\(([+\\-])\\))*");

        private int findItem(String name, boolean b) {
            name = name.trim().toLowerCase();
            boolean raidItem = false;
            /**
             * Custom name checks (Remember: lowercase)
             */
            if(name.equals("coins"))
                return COINS_995;
            if(name.equals("abyssal demon head"))
                name = "abyssal head";
            if (raidPotion.matcher(name).matches()) {
                raidItem = true;
                name += "(4)";
            }
            /**
             * Search for item with name...
             */
            ItemDef found = null;
            for(ItemDef def : ItemDef.cached.values()) {
                if(def == null || def.name == null || !def.name.equalsIgnoreCase(name))
                    continue;
                if(found == null) {
                    found = def;
                    continue;
                }
                if(def.inventoryModel != found.inventoryModel) {
                    /* doesn't match first found item */
                    continue;
                }
                if (raidItem && !def.coxItem)
                    continue;
                if(found.equipOption == -1 && def.equipOption != -1) {
                    found = def;
                } else if(!found.stackable) {
                    if(found.notedId == -1 && def.notedId != -1) {
                        found = def;
                    }
                    else if(def.stackable) {
                        found = def;
                    }
                }
            }
            if(found == null && !b) {
                if(name.contains(" ("))
                    return findItem(name.replace(" (", "("), true);
                if(name.contains("("))
                    return findItem(name.replace("(", " ("), true);
            }
            if(found == null) {
                /* no item found */
                return -1;
            }
            /**
             * Custom id checks
             */
            switch(found.id) {
                case 9418: return 9419; //mithril grapple (wrong id)
            }
            /**
             * Other custom checks
             */
            for(Herb herb : Herb.values()) {
                if(found.id == herb.cleanId)
                    return herb.grimyId;
            }
            return found.id;
        }

    }

    private static final class DumpTable extends LootTable {

        public void export(String wikiName, Integer[] ids) {
            File file = FileUtils.get("E:/Projects/Runescape/Infinem/infinem-server/data/wikidumps/npc/drops/" + wikiName + ".json");
            int totalWeight = 0;
            int[] tableWeights;
            int[] finalTableWeights = null;
            int commonTables = 0;
            int[] commonTableWeights = null;
            List<LootTable.ItemsTable> commonTab = new ArrayList<>();
            if(tables != null) {
                tableWeights = new int[tables.size()];
                finalTableWeights= new int[tables.size()];
                for(int i = 0; i < tables.size(); i++) {
                    LootTable.ItemsTable table = tables.get(i);
                    if(table.items != null) {
                        if (table.name.equalsIgnoreCase("herb drop")
                                || table.name.equalsIgnoreCase("uncommon seed")
                                || table.name.equalsIgnoreCase("rare seed")
                                || table.name.equalsIgnoreCase("general seed")
                                || table.name.equalsIgnoreCase("tree-herb seed")
                                || table.name.equalsIgnoreCase("useful herb")
                                || table.name.equalsIgnoreCase("allotment seed")
                                || table.name.equalsIgnoreCase("talisman drop")) {
                            ++commonTables;
                            commonTab.add(table);
                        } else {
                            for(int x = 0; x < table.items.length; x++) {
                                DumpItem item = (DumpItem) table.items[x];
                                tableWeights[i] += item.weight;
                                if (!table.name.equalsIgnoreCase("tertiary")) {
                                    totalWeight += item.weight;
                                }
                            }
                        }
                    }
                }
                if (commonTables > 0) {
                    for (LootTable.ItemsTable table : commonTab) {
                        totalWeight += table.weight;
                    }
                }
                for(int i = 0; i < tables.size(); i++) {
                    finalTableWeights[i] = (int) (((double) tableWeights[i] / (double) totalWeight) * 100000);
                }
                if (commonTables > 0) {
                    commonTableWeights = new int[commonTables];
                    for (int i = 0; i < commonTab.size(); i++) {
                        commonTableWeights[i] = (int) (((double) commonTab.get(i).weight / (double) totalWeight) * 100000);
                    }
                }
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                int commonTableIndex = 0;
                bw.write("[");
                bw.newLine();
                bw.write("  {");
                bw.newLine();
                bw.write("    \"ids\": " + Arrays.toString(ids).replace("[", "[ ").replace("]", " ]") + ",");
                if(guaranteed != null) {
                    bw.newLine();
                    bw.write("    \"guaranteed\": [");
                    for(int i = 0; i < guaranteed.length; i++) {
                        DumpItem item = (DumpItem) guaranteed[i];
                        bw.newLine();
                        boolean comment = false;
                        if(!item.notes.isEmpty()) {
                            for(String note : item.notes) {
                                bw.write("      #" + note);
                                bw.newLine();
                                if(note.startsWith("WikiNote"))
                                    comment = true;
                            }
                        }
                        if(item.unknownItem != null) {
                            bw.write("      #Unknown Item: \"" + item.unknownItem + "\"");
                            bw.newLine();
                            comment = true;
                        }
                        bw.write("      " + (comment ? "//" : "") + "{ \"id\": " + item.id + ", \"min\": " + item.min + ", \"max\": " + item.max + " }");
                        if(i != (guaranteed.length - 1))
                            bw.write(",");
                        bw.write("   //" + item.getName());
                    }
                    bw.newLine();
                    bw.write("    ]");
                    if(tables != null)
                        bw.write(",");
                }
                if(tables != null) {
                    bw.newLine();
                    bw.write("    \"tables\": [");
                    for(int i = 0; i < tables.size(); i++) {
                        LootTable.ItemsTable table = tables.get(i);
                        if (table.name.equalsIgnoreCase("herb drop")
                                || table.name.equalsIgnoreCase("uncommon seed")
                                || table.name.equalsIgnoreCase("rare seed")
                                || table.name.equalsIgnoreCase("general seed")
                                || table.name.equalsIgnoreCase("tree-herb seed")
                                || table.name.equalsIgnoreCase("useful herb")
                                || table.name.equalsIgnoreCase("allotment seed")
                                || table.name.equalsIgnoreCase("talisman drop")) {
                            bw.newLine();
                            bw.write("      {");
                            bw.newLine();
                            bw.write("        \"name\": \"" + table.name + "\",");
                            bw.newLine();
                            bw.write("        \"weight\": " + (commonTableWeights != null ? commonTableWeights[commonTableIndex++] : table.weight));
                            bw.write(","); //weight,
                            bw.newLine();
                            bw.write("        \"items\": [");
                            bw.newLine();
                            int index = -1;
                            if (table.name.equalsIgnoreCase("general seed")) {
                                index = 0;
                            } else if (table.name.equalsIgnoreCase("herb drop")) {
                                index = 1;
                            } else if (table.name.equalsIgnoreCase("uncommon seed")) {
                                index = 2;
                            } else if (table.name.equalsIgnoreCase("rare seed")) {
                                index = 3;
                            } else if (table.name.equalsIgnoreCase("tree-herb seed")) {
                                index = 4;
                            } else if (table.name.equalsIgnoreCase("useful herb")) {
                                index = 5;
                            } else if (table.name.equalsIgnoreCase("allotment seed")) {
                                index = 6;
                            } else if (table.name.equalsIgnoreCase("talisman drop")) {
                                index = 7;
                            }
                            if (index >= 0) {
                                bw.write("          " + "" + "{ \"id\": " + "0" + ", \"min\": " + index + ", \"max\": " + index + ", \"weight\": " + "5" + " }");
                                bw.newLine();
                            }
                            bw.write("        ]");
                            bw.newLine();
                            bw.write("      }");
                            if(i != (tables.size() - 1))
                                bw.write(",");
                        } else {
                            bw.newLine();
                            bw.write("      {");
                            bw.newLine();
                            bw.write("        \"name\": \"" + table.name + "\",");
                            bw.newLine();
                            bw.write("        \"weight\": " + (finalTableWeights != null ? finalTableWeights[i] : table.weight));
                            if(table.items != null) {
                                bw.write(","); //weight,
                                bw.newLine();
                                bw.write("        \"items\": [");
                                for(int x = 0; x < table.items.length; x++) {
                                    DumpItem item = (DumpItem) table.items[x];
                                    bw.newLine();
                                    boolean comment = false;
                                    if(!item.notes.isEmpty()) {
                                        for(String note : item.notes) {
                                            bw.write("          #" + note);
                                            bw.newLine();
                                            if(note.startsWith("WikiNote"))
                                                comment = true;
                                        }
                                    }
                                    if(item.unknownItem != null) {
                                        bw.write("          #Unknown Item: \"" + item.unknownItem + "\"");
                                        bw.newLine();
                                        comment = true;
                                    }
                                    bw.write("          " + (comment ? "//" : "") + "{ \"id\": " + item.id + ", \"min\": " + item.min + ", \"max\": " + item.max + ", \"weight\": " + item.weight + " }");
                                    if(x != (table.items.length - 1))
                                        bw.write(",");
                                    bw.write("   //" + item.getName());
                                }
                                bw.newLine();
                                bw.write("        ]");
                            }
                            bw.newLine();
                            bw.write("      }");
                            if(i != (tables.size() - 1))
                                bw.write(",");
                        }
                    }
                    bw.newLine();
                    bw.write("    ]");
                }
                bw.newLine();
                bw.write("  }");
                bw.newLine();
                bw.write("]");
            } catch(Exception e) {
                ServerWrapper.logError("Error while exporting", e);
            }
        }

        public int tableCount() {
            int count = 0;
            if(guaranteed != null)
                count++;
            if(tables != null)
                count += tables.size();
            return count;
        }

    }

    private static final class DumpItem extends LootItem {

        public final String unknownItem;
        public final List<String> notes;

        public DumpItem(int id, int minAmount, int maxAmount, int weight, String unknownItem, List<String> notes) {
            super(id, minAmount, maxAmount, weight);
            this.unknownItem = unknownItem;
            this.notes = notes;
        }

        @Override
        public String getName() {
            if(unknownItem != null)
                return unknownItem;
            return super.getName();
        }

    }

}
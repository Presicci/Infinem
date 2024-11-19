package io.ruin.data.dumper;

import io.ruin.api.utils.FileUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.shop.Shop;
import io.ruin.model.shop.ShopItem;
import io.ruin.model.skills.herblore.Herb;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/15/2024
 */
public class ShopDumper {

    public static void dump(String wikiName) {
        DumpShop shop = new WikiDumper(wikiName).run();
        if (shop == null)
            return;
        shop.export(wikiName);
    }

    private static final class WikiDumper {

        private final String wikiName;

        public WikiDumper(String wikiName) {
            this.wikiName = wikiName;
        }

        public DumpShop run() {
            try {
                Document doc = Jsoup.connect("https://oldschool.runescape.wiki/w/" + URLEncoder.encode(wikiName, "UTF-8"))
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .get();
                if (doc == null) throw new IOException("Failed to connect to wiki page!");
                String[] searchHeaders = {"h2"};
                Elements tableHeaders = null;
                for (String s : searchHeaders) {
                    Elements headers = doc.body().select(s);
                    for (Element header : headers) {
                        Element dl = header.nextElementSibling();
                        if (dl != null && dl.is("table")) {
                            if (tableHeaders == null) {
                                tableHeaders = headers;
                                break;
                            } else {
                                tableHeaders.addAll(headers);
                                break;
                            }
                        }
                    }
                }
                if (tableHeaders == null) throw new IOException("Failed to find table headers!");
                DumpShop shop = new DumpShop();
                shop.defaultStock = new ArrayList<>();
                Element header = tableHeaders.first();
                Element dl = header.nextElementSibling();
                if (dl != null && dl.is("table")) {
                    Elements trs = dl.select("tr");
                    if (trs != null) {
                        for (Element tr : trs) {
                            Elements tds = tr.select("td");
                            if (tds.isEmpty()) continue;
                            ShopItem item = parse(tds.get(1).text(), tds.get(2).text(), tds.get(4).text(), tds.get(5).text());
                            if (item != null) shop.defaultStock.add(item);
                        }
                    }
                }
                return shop;
            } catch (Exception e) {
                if (e.getMessage() == null) {
                    e.printStackTrace();
                    return null;
                }
                if (e.getMessage().contains("HTTP error fetching URL")) {
                    //uhh, page does not exist??
                    return null;
                }
                if (e.getMessage().contains("timed out")) {
                    //timed out, try again!
                    ThreadUtils.sleep(10L);
                    return run();
                }
                ServerWrapper.logError("Error while parsing: " + wikiName, e);
                return null;
            }
        }

        private ShopItem parse(String itemName, String stockString, String sellPriceString, String buyPriceString) {
            try {
                /**
                 * Parse item
                 */
                int itemId;
                boolean asNote;
                if (itemName.contains("(m)")) {
                    itemName = itemName.replace("(m)", "");
                }
                if (itemName.contains("(noted)")) {
                    itemId = findItem(itemName.replace("(noted)", ""), false);
                } else {
                    itemId = findItem(itemName, false);
                }
                int stock = Integer.parseInt(stockString.replace(",", ""));
                int sellPrice = Integer.parseInt(sellPriceString.replace(",", ""));
                int buyPrice = Integer.parseInt(buyPriceString.replace(",", ""));
                return new ShopItem(itemId, stock, sellPrice, buyPrice);
            } catch (Exception e) {
                ServerWrapper.logError("Error parsing wiki (" + wikiName + ") shop entry! | item=" + itemName + "   stock=" + stockString + "   sellprice=" + sellPriceString + "   buyprice=" + buyPriceString, e);
                return null;
            }
        }

        private int findItem(String name, boolean b) {
            name = name.trim().toLowerCase();
            boolean raidItem = false;
            /**
             * Custom name checks (Remember: lowercase)
             */
            if (name.equals("coins"))
                return COINS_995;
            if (name.equals("abyssal demon head"))
                name = "abyssal head";
            /**
             * Search for item with name...
             */
            ItemDefinition found = null;
            for (ItemDefinition def : ItemDefinition.cached.values()) {
                if (def == null || def.name == null || !def.name.equalsIgnoreCase(name))
                    continue;
                if (found == null) {
                    found = def;
                    continue;
                }
                if (def.inventoryModel != found.inventoryModel) {
                    /* doesn't match first found item */
                    continue;
                }
                if (raidItem && !def.coxItem)
                    continue;
                if (found.equipOption == -1 && def.equipOption != -1) {
                    found = def;
                } else if (!found.stackable) {
                    if (found.notedId == -1 && def.notedId != -1) {
                        found = def;
                    } else if (def.stackable) {
                        found = def;
                    }
                }
            }
            if (found == null && !b) {
                if (name.contains(" ("))
                    return findItem(name.replace(" (", "("), true);
                if (name.contains("("))
                    return findItem(name.replace("(", " ("), true);
            }
            if (found == null) {
                /* no item found */
                return -1;
            }
            /**
             * Custom id checks
             */
            switch (found.id) {
                case 9418:
                    return 9419; //mithril grapple (wrong id)
            }
            /**
             * Other custom checks
             */
            for (Herb herb : Herb.values()) {
                if (found.id == herb.cleanId)
                    return herb.grimyId;
            }
            return found.id;
        }

    }

    private static final class DumpShop extends Shop {

        public void export(String wikiName) {
            File file = FileUtils.get("E:/Projects/Runescape/Infinem/infinem-server/data/wikidumps/npc/shops/" + wikiName + ".json");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("{");
                bw.newLine();
                bw.write("  \"name\": \"" + wikiName + "\",");
                bw.newLine();
                bw.write("  \"currency\": \"COINS\",");
                bw.newLine();
                bw.write("  \"npcOption\": \"Trade\",");
                bw.newLine();
                bw.write("  \"generalStore\": false,");
                bw.newLine();
                bw.write("  \"npcs\": [");
                bw.newLine();
                bw.write("    -1");
                bw.newLine();
                bw.write("  ],");
                bw.newLine();
                bw.write("  \"sellMultiplier\": 1000,");
                bw.newLine();
                bw.write("  \"buyMultiplier\": 600,");
                bw.newLine();
                bw.write("  \"hideBuy\": false,");
                bw.newLine();
                bw.write("  \"items\": [");
                for (ShopItem item : defaultStock) {
                    bw.newLine();
                    bw.write("    { // " + item.getDef().name);
                    bw.newLine();
                    bw.write("      \"itemId\": " + item.getId() + ",");
                    bw.newLine();
                    bw.write("      \"stock\": " + item.getAmount() + ",");
                    bw.newLine();
                    bw.write("      \"buy_price\": " + item.buyPrice + ",");
                    bw.newLine();
                    bw.write("      \"sell_price\": " + item.price);
                    bw.newLine();
                    bw.write("    },");
                }
                bw.newLine();
                bw.write("  ]");
                bw.newLine();
                bw.write("}");
            } catch (Exception e) {
                ServerWrapper.logError("Error while exporting", e);
            }
        }
    }
}


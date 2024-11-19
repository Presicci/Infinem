package io.ruin.data.dumper;

import io.ruin.api.utils.FileUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.model.skills.slayer.SlayerCreature;
import lombok.AllArgsConstructor;
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
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/19/2024
 */
public class SlayerTaskDumper {

    public static void dump(String wikiName) {
        DumpAssignments assignments = new WikiDumper(wikiName).run();
        if (assignments == null)
            return;
        assignments.export(wikiName);
    }

    private static final class WikiDumper {

        private final String wikiName;

        public WikiDumper(String wikiName) {
            this.wikiName = wikiName;
        }

        public DumpAssignments run() {
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
                DumpAssignments assignments = new DumpAssignments();
                for(Element header : tableHeaders) {
                    Elements search = header.getElementsContainingText("tasks");
                    if (search.isEmpty()) continue;
                    Element dl = header.nextElementSibling();
                    if (wikiName.equalsIgnoreCase("krystilia")) dl = dl.nextElementSibling();
                    if (wikiName.equalsIgnoreCase("konar_quo_maten")) dl = dl.nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling();
                    if (dl != null && dl.is("table")) {
                        Elements trs = dl.select("tr");
                        if (trs != null) {
                            for (Element tr : trs) {
                                Elements tds = tr.select("td");
                                if (tds.isEmpty()) continue;
                                DumpTask task = null;
                                if (wikiName.equalsIgnoreCase("spria")) {
                                    task = parse(tds.get(0).text(), tds.get(1).text(), "N/A", tds.get(4).text());
                                } else if (wikiName.equalsIgnoreCase("turael")) {
                                    task = parse(tds.get(0).text(), tds.get(1).text(), "N/A", tds.get(5).text());
                                } else if (wikiName.equalsIgnoreCase("konar_quo_maten")) {
                                    task = parse(tds.get(0).text(), tds.get(2).text(), tds.get(3).text(), tds.get(6).text());
                                } else if (wikiName.equalsIgnoreCase("krystilia")) {
                                    if (tds.size() < 8) continue;
                                    task = parse(tds.get(0).text(), tds.get(1).text(), tds.get(2).text(), tds.get(7).text());
                                } else {
                                    task = parse(tds.get(0).text(), tds.get(1).text(), tds.get(2).text(), tds.get(5).text());
                                }
                                if (task != null) assignments.tasks.add(task);
                            }
                        }
                    }
                }
                return assignments;
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

        private DumpTask parse(String taskName, String amountString, String extendedAmtString, String weightString) {
            try {
                int minAmount;
                int maxAmount;
                if (!amountString.contains("-")) {
                    String[] splitAmount = amountString.split("\\[")[0].split("-");
                    minAmount = Integer.parseInt(splitAmount[0].replace(",", ""));
                    maxAmount = Integer.parseInt(splitAmount[0].replace(",", ""));
                } else {
                    String[] splitAmount = amountString.split("\\[")[0].split("-");
                    minAmount = Integer.parseInt(splitAmount[0].replace(",", ""));
                    maxAmount = Integer.parseInt(splitAmount[1].replace(",", ""));
                }
                int minExtendedAmount = 0;
                int maxExtendedAmount = 0;
                if (!extendedAmtString.contains("/")) {
                    String[] splitExtendedAmount = extendedAmtString.split("\\[")[0].split("-");
                    minExtendedAmount = Integer.parseInt(splitExtendedAmount[0].replace(",", ""));
                    maxExtendedAmount = Integer.parseInt(splitExtendedAmount[1].replace(",", ""));
                }
                int weight = Integer.parseInt(weightString.replace(",", "").split("\\[")[0]);
                return new DumpTask(taskName, minAmount, maxAmount, minExtendedAmount, maxExtendedAmount, weight);
            } catch (Exception e) {
                ServerWrapper.logError("Error parsing wiki (" + wikiName + ") task entry! | task=" + taskName + "   amount=" + amountString + "   extendedamount=" + extendedAmtString + "   weight=" + weightString, e);
                return null;
            }
        }
    }

    private static final class DumpAssignments {
        private List<DumpTask> tasks = new ArrayList<>();

        public void export(String wikiName) {
            File file = FileUtils.get("E:/Projects/Runescape/Infinem/infinem-server/data/wikidumps/slayertasks/" + wikiName + ".json");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("{");
                bw.newLine();
                bw.write("  \"name\": \"" + wikiName + "\",");
                bw.newLine();
                bw.write("  \"npcId\": -1,");
                bw.newLine();
                bw.write("  \"defs\": [");
                for (DumpTask task : tasks) {
                    bw.newLine();
                    bw.write("    { // " + task.taskName);
                    bw.newLine();
                    bw.write("      \"weighing\": " + task.weight + ",");
                    bw.newLine();
                    bw.write("      \"min\": " + task.minAmount + ",");
                    bw.newLine();
                    bw.write("      \"max\": " + task.maxAmount + ",");
                    bw.newLine();
                    if (task.minExtendedAmount > 0) {
                        bw.write("      \"minExtended\": " + task.minExtendedAmount + ",");
                        bw.newLine();
                        bw.write("      \"maxExtended\": " + task.maxExtendedAmount + ",");
                        bw.newLine();
                    }
                    bw.write("      \"creatureUid\": " + getTaskUUID(task.taskName));
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

        private int getTaskUUID(String name) {
            for (SlayerCreature creature : SlayerCreature.values()) {
                if (creature.getCategory().equalsIgnoreCase(name)) {
                    return creature.getUid();
                }
            }
            return -1;
        }
    }

    @AllArgsConstructor
    private static final class DumpTask {
        private String taskName;
        private int minAmount;
        private int maxAmount;
        private int minExtendedAmount;
        private int maxExtendedAmount;
        private int weight;
    }
}

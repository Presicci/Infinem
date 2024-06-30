package io.ruin.data.dumper;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.data.impl.items.item_info;
import io.ruin.model.item.containers.Equipment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.sql.PreparedStatement;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/7/2024
 */
public class ItemInfoDumper {

    public static void dump(int startingId, int dumpAmount) {
        int dumped = 0;
        for (ItemDefinition itemDefinition : ItemDefinition.cached.values()) {
            if (dumped > dumpAmount && dumpAmount > 0) return;
            if (itemDefinition.id < startingId) continue;
            if (itemDefinition.isNote()) continue;
            new WikiDumper(itemDefinition.id).run();
            dumped++;
        }
    }

    public static void dump(int id) {
        new WikiDumper(id).run();
    }

    private static final class WikiDumper {

        private final int id;

        public WikiDumper(int id) {
            this.id = id;
        }

        public void run() {
            try {
                //("https://oldschool.runescape.wiki/w/Special:Lookup?type=item&id=" + id)
                Document doc = Jsoup.connect("https://oldschool.runescape.wiki/w/Special:Ask?q=%5B%5BItem+ID%3A%3A" + id + "%5D%5D&p=format%3Dtable%2Flink%3Dnone%2Fheaders%3Dshow%2Fsearchlabel%3D...-20further-20results%2Fclass%3Dsortable-20wikitable-20smwtable%2Fprefix%3Dnone&po=%3FCategory%3ATradeable+items%3DTradeable+items%0A%3FWeight%0A%3FExamine%0A%3FEquipment+slot%0A%3FStab+attack+bonus%0A%3FSlash+attack+bonus%0A%3FCrush+attack+bonus%0A%3FMagic+attack+bonus%0A%3FRange+attack+bonus%0A%3FStab+defence+bonus%0A%3FSlash+defence+bonus%0A%3FCrush+defence+bonus%0A%3FMagic+defence+bonus%0A%3FRange+defence+bonus%0A%3FStrength+bonus%0A%3FRanged+Strength+bonus%0A%3FMagic+Damage+bonus%0A%3FPrayer+bonus%0A&sort=Item+ID&order=asc&eq=no&offset=0&limit=20")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .get();
                if (doc == null)
                    throw new IOException("Failed to connect to wiki page!");
                item_info.Temp temp = new item_info.Temp();
                temp.bonuses = new Integer[14];
                StringBuilder tags = new StringBuilder();
                Element table = doc.selectFirst(".wikitable");
                if (table == null) {
                    System.err.println(id + " is null");
                    return;
                }
                Element tbody = table.selectFirst("tbody");
                if (tbody == null) {
                    System.err.println(id + " is null");
                    return;
                }
                Element tr = tbody.selectFirst("tr");
                if (tr == null) {
                    System.err.println(id + " is null");
                    return;
                }
                int column = 0;
                for (Element td : tr.children()) {
                    if (td.text().isEmpty()) {
                        column++;
                        continue;
                    }
                    switch (column) {
                        case 0:
                            if (td.text().contains("#")) {
                                tags.append("@");
                            }
                            break;
                        case 1:
                            if (td.text().toLowerCase().contains("x")) temp.tradeable = true;
                            break;
                        case 2:
                            try {
                                temp.weight = Double.parseDouble(td.text());
                            } catch (NumberFormatException e) {
                                System.err.println("Couldn't parse weight to double, " + td.text());
                            }
                            break;
                        case 3:
                            temp.examine = td.text();
                            break;
                        case 4:
                            String slot = td.text();
                            if (slot.toLowerCase().contains("2h")) {
                                tags.append("two_handed");
                            }
                            temp.equip_slot = slotStringToInt(slot);
                            break;
                        default:
                            if (column > 4 && column < 19) {
                                try {
                                    temp.bonuses[column - 5] = Integer.parseInt(td.text());
                                } catch (NumberFormatException e) {
                                    System.err.println("Couldn't parse bonus " + (column - 5) + " to integer, " + td.text());
                                }
                            }
                            break;
                    }
                    column++;
                }
                Server.gameDb.execute(connection -> {
                    PreparedStatement statement = null;
                    try {
                        if (temp.equip_slot == null) {
                            statement = connection.prepareStatement("INSERT INTO item_info (id, name, tradeable, weight, examine) VALUES (?, ?, ?, ?, ?)");
                            statement.setInt(1, id);
                            statement.setString(2, ItemDefinition.get(id).name);
                            statement.setInt(3, temp.tradeable ? 1 : 0);
                            statement.setDouble(4, temp.weight);
                            statement.setString(5, temp.examine);
                        } else {
                            statement = connection.prepareStatement("INSERT INTO item_info (id, name, tradeable, weight, examine, tags, equip_slot, " +
                                    "stab_attack, slash_attack, crush_attack, magic_attack, range_attack, " +
                                    "stab_defence, slash_defence, crush_defence, magic_defence, range_defence, " +
                                    "melee_strength, ranged_strength, magic_damage, " +
                                    "prayer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            statement.setInt(1, id);
                            statement.setString(2, ItemDefinition.get(id).name);
                            statement.setInt(3, temp.tradeable ? 1 : 0);
                            statement.setDouble(4, temp.weight);
                            statement.setString(5, temp.examine);
                            statement.setString(6, tags.toString());
                            statement.setInt(7, temp.equip_slot);
                            statement.setInt(8, temp.bonuses[0]);
                            statement.setInt(9, temp.bonuses[1]);
                            statement.setInt(10, temp.bonuses[2]);
                            statement.setInt(11, temp.bonuses[3]);
                            statement.setInt(12, temp.bonuses[4]);
                            statement.setInt(13, temp.bonuses[5]);
                            statement.setInt(14, temp.bonuses[6]);
                            statement.setInt(15, temp.bonuses[7]);
                            statement.setInt(16, temp.bonuses[8]);
                            statement.setInt(17, temp.bonuses[9]);
                            statement.setInt(18, temp.bonuses[10]);
                            statement.setInt(19, temp.bonuses[11]);
                            statement.setInt(20, temp.bonuses[12]);
                            statement.setInt(21, temp.bonuses[13]);
                        }
                        statement.execute();
                    } finally {
                        DatabaseUtils.close(statement);
                        System.out.println("Dumped item " + id);
                    }
                });
            } catch(Exception e) {
                System.out.println(e.getMessage());
                if(e.getMessage() == null) {
                    e.printStackTrace();
                    return;
                }
                if(e.getMessage().contains("HTTP error fetching URL")) {
                    //uhh, page does not exist??
                    return;
                }
                if(e.getMessage().contains("timed out")) {
                    //timed out, try again!
                    ThreadUtils.sleep(10L);
                    return;
                }
                ServerWrapper.logError("Error while parsing: " + id, e);
            }
        }
    }

    public static void applyCurrentModifiers() {
        ItemDefinition.forEach(def -> {
            if (def.equipReqs == null || def.equipReqs.length == 0) return;
            int id = def.id;
            StringBuilder sb = new StringBuilder();
            for (int req : def.equipReqs) {
                int statId = req >> 8;
                int lvl = req & 0xff;
                sb.append(statId);
                sb.append(":");
                sb.append(lvl);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            String tag = sb.toString();
            Server.gameDb.execute(connection -> {
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement("UPDATE item_info SET equip_requirements= IF(equip_requirements = '' OR equip_requirements IS NULL, ?, concat_ws(',', equip_requirements, ?)) where id=? and not (equip_requirements is not null and equip_requirements like concat('%', ?, '%'))");
                    statement.setString(1, tag);
                    statement.setString(2, tag);
                    statement.setInt(3, id);
                    statement.setString(4, tag);
                    statement.execute();
                } finally {
                    DatabaseUtils.close(statement);
                    System.out.println("Added " + tag + " tag to " + id);
                }
            });
        });
    }

    private static int slotStringToInt(String slotString) {
        slotString = slotString.toLowerCase();
        if (slotString.contains("head")) {
            return Equipment.SLOT_HAT;
        } else if (slotString.contains("cape")) {
            return Equipment.SLOT_CAPE;
        } else if (slotString.contains("neck")) {
            return Equipment.SLOT_AMULET;
        } else if (slotString.contains("weapon")) {
            return Equipment.SLOT_WEAPON;
        }  else if (slotString.contains("2h")) {
            return Equipment.SLOT_WEAPON;
        } else if (slotString.contains("body")) {
            return Equipment.SLOT_CHEST;
        } else if (slotString.contains("shield")) {
            return Equipment.SLOT_SHIELD;
        } else if (slotString.contains("legs")) {
            return Equipment.SLOT_LEGS;
        } else if (slotString.contains("hands")) {
            return Equipment.SLOT_HANDS;
        } else if (slotString.contains("feet")) {
            return Equipment.SLOT_FEET;
        } else if (slotString.contains("ring")) {
            return Equipment.SLOT_RING;
        } else if (slotString.contains("ammo")) {
            return Equipment.SLOT_AMMO;
        }
        return -1;
    }
}

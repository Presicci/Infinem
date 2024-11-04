package io.ruin.data.dumper;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.combat.AttackStyle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/2/2024
 */
public class NPCInfoDumper {

    public static void dump(int startingId, int dumpAmount) {
        int dumped = 0;
        for (NPCDefinition def : NPCDefinition.cached.values()) {
            if (dumped > dumpAmount && dumpAmount > 0) return;
            if (def.id < startingId) continue;
            new WikiDumper(def.id).run();
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
                Document doc = Jsoup.connect("https://oldschool.runescape.wiki/w/Special:Ask?q=%5B%5BNPC+ID%3A%3A"
                                + id
                                + "%5D%5D&p=format%3Dtable%2Flink%3Dnone%2Fheaders%3Dshow%2Fsearchlabel%3D...-20further-20results%2Fclass%3Dsortable-20wikitable-20smwtable%2Fprefix%3Dnone&po=%3FCombat+level%0A%3FMonster+attribute%0A%3FExperience+bonus%0A%3FMax+hit%0A%3FAttack+style%0A%3FAttack+speed%0A%3FSlayer+level%0A%3FSlayer+experience%0A%3FSlayer+category%0A%3FHitpoints%0A%3FAttack+level%0A%3FStrength+level%0A%3FDefence+level%0A%3FMagic+level%0A%3FRanged+level%0A%3FAttack+bonus%0A%3FStrength+bonus%0A%3FMagic+attack+bonus%0A%3FMagic+Damage+bonus%0A%3FRange+attack+bonus%0A%3FRanged+Strength+bonus%0A%3FStab+defence+bonus%0A%3FSlash+defence+bonus%0A%3FCrush+defence+bonus%0A%3FMagic+defence+bonus%0A%3FElemental+weakness%0A%3FElemental+weakness+percent%0A%3FLight+range+defence+bonus%0A%3FStandard+range+defence+bonus%0A%3FHeavy+range+defence+bonus%0A%3FImmune+to+poison%0A%3FImmune+to+venom%0A%3FImmune+to+cannons%0A%3FImmune+to+thralls%0A&sort=NPC+ID&order=asc&eq=no&offset=0&limit=20")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .get();
                if (doc == null)
                    throw new IOException("Failed to connect to wiki page!");
                npc_combat.Info temp = new npc_combat.Info();
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
                        case 1:
                            break;
                        case 2:
                            List<TextNode> attributes = td.textNodes();
                            temp.attributes = new String[attributes.size()];
                            for (int index = 0; index < attributes.size(); index++) {
                                temp.attributes[index] = attributes.get(index).text();
                            }
                            break;
                        case 3:
                            try {
                                temp.combat_xp_modifier = Double.parseDouble(td.text());
                            } catch (NumberFormatException e) {
                                System.err.println("[NPCDUMP] Couldn't parse xp bonus to double, " + td.text());
                            }
                            break;
                        case 4:
                            String damageString = td.textNodes().get(0).text().split(" ")[0].split("/")[0].replace("+", "").replace("~", "");
                            if (damageString.contains("–") || damageString.contains("-")) {
                                damageString = damageString.split("–")[0].split("-")[0];
                                System.err.println("[NPCDUMP] Id: " + id + " has hyphen in damage string");
                            }
                            if (damageString.equalsIgnoreCase("n")) {
                                temp.max_damage = 0;
                                break;
                            }
                            if (damageString.equalsIgnoreCase("max")) {
                                temp.max_damage = 0;
                                break;
                            }
                            if (damageString.equalsIgnoreCase("varies")) {
                                temp.max_damage = 0;
                                break;
                            }
                            if (damageString.contains("x")) {
                                String[] strings = damageString.split("x");
                                temp.max_damage = Integer.parseInt(strings[0]) * Integer.parseInt(strings[1]);
                                break;
                            }
                            try {
                                if (damageString.contains("%")) temp.max_damage = 0;
                                else temp.max_damage = Integer.parseInt(damageString);
                            } catch (NumberFormatException e) {
                                System.err.println("[NPCDUMP] Couldn't parse damage for " + id + "; " + td.text());
                            }
                            if (damageString.isEmpty()) return;
                            break;
                        case 5:
                            if (td.text().toLowerCase().contains("typeless")) {
                                tags.append("typeless");
                            }
                            String style = td.textNodes().get(0).text().toLowerCase().split("\\(")[0].replace("typeless", "").trim();
                            for (AttackStyle s : AttackStyle.values()) {
                                if (s.name().replace("_", " ").equalsIgnoreCase(style)) {
                                    temp.attack_style = s;
                                    break;
                                }
                            }
                            if (style.equalsIgnoreCase("magic melee")) {
                                temp.attack_style = AttackStyle.MAGICAL_MELEE;
                                break;
                            }
                            if (style.equalsIgnoreCase("melee")) {
                                temp.attack_style = AttackStyle.SLASH;
                                break;
                            }
                            if (style.equalsIgnoreCase("all")) {
                                temp.attack_style = AttackStyle.NONE;
                                break;
                            }
                            if (style.toLowerCase().contains("magic")) {
                                temp.attack_style = AttackStyle.MAGIC;
                                break;
                            }
                            if (style.toLowerCase().contains("slash")) {
                                temp.attack_style = AttackStyle.SLASH;
                                break;
                            }
                            if (style.equalsIgnoreCase("n")) {
                                temp.attack_style = AttackStyle.NONE;
                                break;
                            }
                            if (style.isEmpty()) {
                                temp.attack_style = AttackStyle.NONE;
                                System.err.println("[NPCDUMP] Assuming NONE style for npc: " + id);
                                break;
                            }
                            if (temp.attack_style == null) {
                                System.err.println("[NPCDUMP] Unrecognized attack style: " + style);
                            }
                            break;
                        case 6:
                            temp.attack_ticks = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 7:
                            temp.slayer_level = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 8:
                            temp.slayer_xp = Double.parseDouble(td.text().replace(",", ""));
                            break;
                        case 9:
                            List<TextNode> categories = td.textNodes();
                            temp.slayer_tasks = new String[categories.size()];
                            for (int index = 0; index < categories.size(); index++) {
                                temp.slayer_tasks[index] = categories.get(index).text();
                            }
                            break;
                        case 10:
                            temp.hitpoints = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 11:
                            temp.attack = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 12:
                            temp.strength = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 13:
                            temp.defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 14:
                            temp.magic = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 15:
                            temp.ranged = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 16:
                            temp.attack_bonus = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 17:
                            temp.strength_bonus = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 18:
                            temp.magic_attack = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 19:
                            temp.magic_damage_bonus = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 20:
                            temp.ranged_attack = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 21:
                            temp.ranged_strength_bonus = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 22:
                            temp.stab_defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 23:
                            temp.slash_defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 24:
                            temp.crush_defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 25:
                            temp.magic_defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 26:
                            temp.elemental_weakness = td.text();
                            break;
                        case 27:
                            temp.elemental_weakness_percent = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 28:
                            temp.light_range_defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 29:
                            temp.ranged_defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 30:
                            temp.heavy_range_defence = Integer.parseInt(td.text().replace(",", ""));
                            break;
                        case 31:
                            temp.poison_immunity = td.text().equals("immune");
                            break;
                        case 32:
                            temp.venom_immunity = td.text().equals("immune");
                            break;
                    }
                    column++;
                }
                Server.gameDb.execute(connection -> {
                    PreparedStatement statement = null;
                    try {
                            statement = connection.prepareStatement("INSERT INTO npc_info (id, name, tags, attributes, xpbonus, maxdamage, attackstyle, attackticks, " +
                                    "slayerlevel, slayerxp, slayertasks, hitpoints, attack, strength, defence, magic, ranged, attackbonus, " +
                                    "strengthbonus, magicattack, magicdamagebonus, rangedattack, rangedatrengthbonus, " +
                                    "stabdefence, slashdefence, crushdefence, magicdefence, elementalweakness, elementalweaknesspercent, " +
                                    "lightrangedefence, rangedefence, heavyrangedefence, poisonimmunity, venomimmunity) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            statement.setInt(1, id);
                            statement.setString(2, NPCDefinition.get(id).name);
                            statement.setString(3, tags.toString());
                            statement.setString(4, Arrays.toString(temp.attributes).replace("[", "").replace("]", ""));
                            statement.setDouble(5, temp.combat_xp_modifier);
                            statement.setInt(6, temp.max_damage);
                            statement.setString(7, temp.attack_style == null ? "null" : temp.attack_style.name());
                            statement.setInt(8, temp.attack_ticks);
                            statement.setInt(9, temp.slayer_level);
                            statement.setDouble(10, temp.slayer_xp);
                            statement.setString(11, Arrays.toString(temp.slayer_tasks).replace("[", "").replace("]", ""));
                            statement.setInt(12, temp.hitpoints);
                            statement.setInt(13, temp.attack);
                            statement.setInt(14, temp.strength);
                            statement.setInt(15, temp.defence);
                            statement.setInt(16, temp.magic);
                            statement.setInt(17, temp.ranged);
                            statement.setInt(18, temp.attack_bonus);
                            statement.setInt(19, temp.strength_bonus);
                            statement.setInt(20, temp.magic_attack);
                            statement.setInt(21, temp.magic_damage_bonus);
                            statement.setInt(22, temp.ranged_attack);
                            statement.setInt(23, temp.ranged_strength_bonus);
                            statement.setInt(24, temp.stab_defence);
                            statement.setInt(25, temp.slash_defence);
                            statement.setInt(26, temp.crush_defence);
                            statement.setInt(27, temp.magic_defence);
                            statement.setString(28, temp.elemental_weakness);
                            statement.setInt(29, temp.elemental_weakness_percent);
                            statement.setInt(30, temp.light_range_defence);
                            statement.setInt(31, temp.ranged_defence);
                            statement.setInt(32, temp.heavy_range_defence);
                            statement.setInt(33, temp.poison_immunity ? 1 : 0);
                            statement.setInt(34, temp.venom_immunity ? 1 : 0);
                        statement.execute();
                    } finally {
                        DatabaseUtils.close(statement);
                        System.out.println("Dumped npc " + id);
                    }
                });
            } catch(Exception e) {
                System.out.println(e.getMessage());
                if(e.getMessage() == null) {
                    System.err.println("[NPCDUMP] " + id);
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
}

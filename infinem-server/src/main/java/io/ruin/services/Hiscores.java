package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.XenPost;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;
import io.ruin.utility.OfflineMode;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Hiscores {

    public static void save(Player player) {
        //if (player.isAdmin())
        //    return;
        saveHiscores(player);
    }

    private static void saveHiscores(Player player) {
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("username", player.getName());
        map.put("gamemode", getGameMode(player));
        map.put("secondary_gamemode", "Normal");
        // Skills
        map.put("overall", player.getStats().totalXp);
        map.put("attack", (int) player.getStats().get(StatType.Attack).experience);
        map.put("defence", (int) player.getStats().get(StatType.Defence).experience);
        map.put("strength", (int) player.getStats().get(StatType.Strength).experience);
        map.put("hitpoints", (int) player.getStats().get(StatType.Hitpoints).experience);
        map.put("ranged", (int) player.getStats().get(StatType.Ranged).experience);
        map.put("prayer", (int) player.getStats().get(StatType.Prayer).experience);
        map.put("magic", (int) player.getStats().get(StatType.Magic).experience);
        map.put("cooking", (int) player.getStats().get(StatType.Cooking).experience);
        map.put("woodcutting", (int) player.getStats().get(StatType.Woodcutting).experience);
        map.put("fletching", (int) player.getStats().get(StatType.Fletching).experience);
        map.put("fishing", (int) player.getStats().get(StatType.Fishing).experience);
        map.put("firemaking", (int) player.getStats().get(StatType.Firemaking).experience);
        map.put("crafting", (int) player.getStats().get(StatType.Crafting).experience);
        map.put("smithing", (int) player.getStats().get(StatType.Smithing).experience);
        map.put("mining", (int) player.getStats().get(StatType.Mining).experience);
        map.put("herblore", (int) player.getStats().get(StatType.Herblore).experience);
        map.put("agility", (int) player.getStats().get(StatType.Agility).experience);
        map.put("thieving", (int) player.getStats().get(StatType.Thieving).experience);
        map.put("slayer", (int) player.getStats().get(StatType.Slayer).experience);
        map.put("farming", (int) player.getStats().get(StatType.Farming).experience);
        map.put("runecrafting", (int) player.getStats().get(StatType.Runecrafting).experience);
        map.put("hunter", (int) player.getStats().get(StatType.Hunter).experience);
        map.put("construction", (int) player.getStats().get(StatType.Construction).experience);
        // Task points
        map.put("tasks_completed", Config.LEAGUE_TASKS_COMPLETED.get(player));
        map.put("task_points", Config.LEAGUE_POINTS.get(player));
        // Collection log
        map.put("collection_log_slots", player.getCollectionLog().getCollected().size());
        // Clues
        map.put("beginner", PlayerCounter.BEGINNER_CLUES_COMPLETED.get(player));
        map.put("easy", PlayerCounter.EASY_CLUES_COMPLETED.get(player));
        map.put("medium", PlayerCounter.MEDIUM_CLUES_COMPLETED.get(player));
        map.put("hard", PlayerCounter.HARD_CLUES_COMPLETED.get(player));
        map.put("elite", PlayerCounter.ELITE_CLUES_COMPLETED.get(player));
        map.put("master", PlayerCounter.MASTER_CLUES_COMPLETED.get(player));
        // Bosses
        map.put("abyssal_sire", KillCounter.getKillCount(player, BossKillCounter.ABYSSAL_SIRE));
        map.put("alchemical_hydra", KillCounter.getKillCount(player, BossKillCounter.ALCHEMICAL_HYDRA));
        map.put("barrows", KillCounter.getKillCount(player, BossKillCounter.BARROWS));
        map.put("bryophyta", KillCounter.getKillCount(player, BossKillCounter.BRYOPHYTA));
        map.put("callisto", KillCounter.getKillCount(player, BossKillCounter.CALLISTO));
        map.put("cerberus", KillCounter.getKillCount(player, BossKillCounter.CERBERUS));
        map.put("chambers_of_xeric", KillCounter.getKillCount(player, BossKillCounter.COX));
        map.put("chaos_elemental", KillCounter.getKillCount(player, BossKillCounter.CHAOS_ELE));
        map.put("chaos_fanatic", KillCounter.getKillCount(player, BossKillCounter.CHAOS_FANATIC));
        map.put("commander_zilyana", KillCounter.getKillCount(player, BossKillCounter.COMMANDER_ZILYANA));
        map.put("corporeal_beast", KillCounter.getKillCount(player, BossKillCounter.CORP_BEAST));
        map.put("dagannoth_prime", KillCounter.getKillCount(player, BossKillCounter.DAG_PRIME));
        map.put("dagannoth_rex", KillCounter.getKillCount(player, BossKillCounter.DAG_REX));
        map.put("dagannoth_supreme", KillCounter.getKillCount(player, BossKillCounter.DAG_SUPREME));
        map.put("crazy_archaeologist", KillCounter.getKillCount(player, BossKillCounter.CRAZY_ARCHAEOLOGIST));
        map.put("general_graardor", KillCounter.getKillCount(player, BossKillCounter.GENERAL_GRAARDOR));
        map.put("giant_mole", KillCounter.getKillCount(player, BossKillCounter.GIANT_MOLE));
        map.put("hespori", KillCounter.getKillCount(player, BossKillCounter.HESPORI));
        map.put("kalphite_queen", KillCounter.getKillCount(player, BossKillCounter.KALPHITE_QUEEN));
        map.put("king_black_dragon", KillCounter.getKillCount(player, BossKillCounter.KING_BLACK_DRAGON));
        map.put("kraken", KillCounter.getKillCount(player, BossKillCounter.KRAKEN));
        map.put("kreearra", KillCounter.getKillCount(player, BossKillCounter.KREEARRA));
        map.put("kril_tsutsaroth", KillCounter.getKillCount(player, BossKillCounter.KRIL_TSUTSAROTH));
        map.put("obor", KillCounter.getKillCount(player, BossKillCounter.OBOR));
        map.put("sarachnis", KillCounter.getKillCount(player, BossKillCounter.SARACHNIS));
        map.put("scorpia", KillCounter.getKillCount(player, BossKillCounter.SCORPIA));
        map.put("skotizo", KillCounter.getKillCount(player, BossKillCounter.SKOTIZO));
        map.put("thermonuclear_smoke_devil", KillCounter.getKillCount(player, BossKillCounter.THERMONUCLEAR_SMOKE_DEVIL));
        map.put("tzkal_zuk", KillCounter.getKillCount(player, BossKillCounter.ZUK));
        map.put("tztok_jad", KillCounter.getKillCount(player, BossKillCounter.JAD));
        map.put("venenatis", KillCounter.getKillCount(player, BossKillCounter.VENENATIS));
        map.put("vetion", KillCounter.getKillCount(player, BossKillCounter.VETION));
        map.put("vorkath", KillCounter.getKillCount(player, BossKillCounter.VORKATH));
        map.put("wintertodt", KillCounter.getKillCount(player, BossKillCounter.WINTERTODT));
        map.put("zulrah", KillCounter.getKillCount(player, BossKillCounter.ZULRAH));
        // Pking
        map.put("kills", Config.PVP_KILLS.get(player));
        map.put("deaths", Config.PVP_DEATHS.get(player));
        map.put("highest_shutdown", player.highestShutdown);
        map.put("highest_killspress", player.highestKillSpree);
        map.put("pk_rating", player.pkRating);
        String result = XenPost.post("update_highscores", map);
    }

    /**
     * ECO
     */
    public static void archive(Player player) {
        if (OfflineMode.enabled)
            return;
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("username", player.getName());
        String result = XenPost.post("archive_highscore", map);
    }

    private static String getGameMode(Player player) {
        if(player.getGameMode().isUltimateIronman()) {
            return "Ultimate Ironman";
        } else if (player.getGameMode().isHardcoreIronman()) {
            return "Hardcore Ironman";
        } else if (player.getGameMode().isIronMan()) {
            return "Ironman";
        } else {
            return "Regular";
        }
    }
}
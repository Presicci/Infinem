package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
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

public class Hiscores {

    public static void save(Player player) {
        //if (player.isAdmin())
        //    return;
        saveHiscores(player);
    }

    /**
     * ECO
     */
    public static void archive(Player player) {
        if (OfflineMode.enabled)
            return;
        Server.siteDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM highscores WHERE username = ? AND archive = 0 LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, player.getName());
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        resultSet.updateInt("archive", 1);
                        resultSet.updateRow();
                    }
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw, true);
                t.printStackTrace(pw);
                System.out.println("FAILED TO UPDATE HARDCORE DEATH FOR: "+player.getName());
                System.out.println(sw.getBuffer().toString());
                /* do nothing */
            }
        });
    }

    private static void saveHiscores(Player player) {
        if (OfflineMode.enabled)
            return;
        Server.siteDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM highscores WHERE username = ? AND archive = 0 LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, player.getName());
                    resultSet = statement.executeQuery();
                    if (!resultSet.next()) {
                        resultSet.moveToInsertRow();
                        updateECO(player, resultSet);
                        updatePVP(player, resultSet);
                        resultSet.insertRow();
                    } else {
                        updateECO(player, resultSet);
                        updatePVP(player, resultSet);
                        resultSet.updateRow();
                    }
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw, true);
                t.printStackTrace(pw);
                System.out.println("FAILED TO UPDATE HISCORES FOR: "+player.getName());
                System.out.println(sw.getBuffer().toString());
                /* do nothing */
            }
        });
    }

    private static void updateECO(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateString("username", player.getName());
        resultSet.updateString("gamemode", getGameMode(player));
        resultSet.updateString("secondary_gamemode", "Normal");
        // Skills
        resultSet.updateLong("overall", player.getStats().totalXp);
        resultSet.updateInt("attack", (int) player.getStats().get(StatType.Attack).experience);
        resultSet.updateInt("defence", (int) player.getStats().get(StatType.Defence).experience);
        resultSet.updateInt("strength", (int) player.getStats().get(StatType.Strength).experience);
        resultSet.updateInt("hitpoints", (int) player.getStats().get(StatType.Hitpoints).experience);
        resultSet.updateInt("ranged", (int) player.getStats().get(StatType.Ranged).experience);
        resultSet.updateInt("prayer", (int) player.getStats().get(StatType.Prayer).experience);
        resultSet.updateInt("magic", (int) player.getStats().get(StatType.Magic).experience);
        resultSet.updateInt("cooking", (int) player.getStats().get(StatType.Cooking).experience);
        resultSet.updateInt("woodcutting", (int) player.getStats().get(StatType.Woodcutting).experience);
        resultSet.updateInt("fletching", (int) player.getStats().get(StatType.Fletching).experience);
        resultSet.updateInt("fishing", (int) player.getStats().get(StatType.Fishing).experience);
        resultSet.updateInt("firemaking", (int) player.getStats().get(StatType.Firemaking).experience);
        resultSet.updateInt("crafting", (int) player.getStats().get(StatType.Crafting).experience);
        resultSet.updateInt("smithing", (int) player.getStats().get(StatType.Smithing).experience);
        resultSet.updateInt("mining", (int) player.getStats().get(StatType.Mining).experience);
        resultSet.updateInt("herblore", (int) player.getStats().get(StatType.Herblore).experience);
        resultSet.updateInt("agility", (int) player.getStats().get(StatType.Agility).experience);
        resultSet.updateInt("thieving", (int) player.getStats().get(StatType.Thieving).experience);
        resultSet.updateInt("slayer", (int) player.getStats().get(StatType.Slayer).experience);
        resultSet.updateInt("farming", (int) player.getStats().get(StatType.Farming).experience);
        resultSet.updateInt("runecrafting", (int) player.getStats().get(StatType.Runecrafting).experience);
        resultSet.updateInt("hunter", (int) player.getStats().get(StatType.Hunter).experience);
        resultSet.updateInt("construction", (int) player.getStats().get(StatType.Construction).experience);
        // Task points
        resultSet.updateInt("tasks_completed", Config.LEAGUE_TASKS_COMPLETED.get(player));
        resultSet.updateInt("task_points", Config.LEAGUE_POINTS.get(player));
        // Collection log
        resultSet.updateInt("collection_log_slots", player.getCollectionLog().getCollected().size());
        // Clues
        resultSet.updateInt("beginner", PlayerCounter.BEGINNER_CLUES_COMPLETED.get(player));
        resultSet.updateInt("easy", PlayerCounter.EASY_CLUES_COMPLETED.get(player));
        resultSet.updateInt("medium", PlayerCounter.MEDIUM_CLUES_COMPLETED.get(player));
        resultSet.updateInt("hard", PlayerCounter.HARD_CLUES_COMPLETED.get(player));
        resultSet.updateInt("elite", PlayerCounter.ELITE_CLUES_COMPLETED.get(player));
        resultSet.updateInt("master", PlayerCounter.MASTER_CLUES_COMPLETED.get(player));
        // Bosses
        resultSet.updateInt("abyssal_sire", KillCounter.getKillCount(player, BossKillCounter.ABYSSAL_SIRE));
        resultSet.updateInt("alchemical_hydra", KillCounter.getKillCount(player, BossKillCounter.ALCHEMICAL_HYDRA));
        resultSet.updateInt("barrows", KillCounter.getKillCount(player, BossKillCounter.BARROWS));
        resultSet.updateInt("bryophyta", KillCounter.getKillCount(player, BossKillCounter.BRYOPHYTA));
        resultSet.updateInt("callisto", KillCounter.getKillCount(player, BossKillCounter.CALLISTO));
        resultSet.updateInt("cerberus", KillCounter.getKillCount(player, BossKillCounter.CERBERUS));
        resultSet.updateInt("chambers_of_xeric", KillCounter.getKillCount(player, BossKillCounter.COX));
        resultSet.updateInt("chaos_elemental", KillCounter.getKillCount(player, BossKillCounter.CHAOS_ELE));
        resultSet.updateInt("chaos_fanatic", KillCounter.getKillCount(player, BossKillCounter.CHAOS_FANATIC));
        resultSet.updateInt("commander_zilyana", KillCounter.getKillCount(player, BossKillCounter.COMMANDER_ZILYANA));
        resultSet.updateInt("corporeal_beast", KillCounter.getKillCount(player, BossKillCounter.CORP_BEAST));
        resultSet.updateInt("dagannoth_prime", KillCounter.getKillCount(player, BossKillCounter.DAG_PRIME));
        resultSet.updateInt("dagannoth_rex", KillCounter.getKillCount(player, BossKillCounter.DAG_REX));
        resultSet.updateInt("dagannoth_supreme", KillCounter.getKillCount(player, BossKillCounter.DAG_SUPREME));
        resultSet.updateInt("crazy_archaeologist", KillCounter.getKillCount(player, BossKillCounter.CRAZY_ARCHAEOLOGIST));
        resultSet.updateInt("general_graardor", KillCounter.getKillCount(player, BossKillCounter.GENERAL_GRAARDOR));
        resultSet.updateInt("giant_mole", KillCounter.getKillCount(player, BossKillCounter.GIANT_MOLE));
        resultSet.updateInt("hespori", KillCounter.getKillCount(player, BossKillCounter.HESPORI));
        resultSet.updateInt("kalphite_queen", KillCounter.getKillCount(player, BossKillCounter.KALPHITE_QUEEN));
        resultSet.updateInt("king_black_dragon", KillCounter.getKillCount(player, BossKillCounter.KING_BLACK_DRAGON));
        resultSet.updateInt("kraken", KillCounter.getKillCount(player, BossKillCounter.KRAKEN));
        resultSet.updateInt("kreearra", KillCounter.getKillCount(player, BossKillCounter.KREEARRA));
        resultSet.updateInt("kril_tsutsaroth", KillCounter.getKillCount(player, BossKillCounter.KRIL_TSUTSAROTH));
        resultSet.updateInt("obor", KillCounter.getKillCount(player, BossKillCounter.OBOR));
        resultSet.updateInt("sarachnis", KillCounter.getKillCount(player, BossKillCounter.SARACHNIS));
        resultSet.updateInt("scorpia", KillCounter.getKillCount(player, BossKillCounter.SCORPIA));
        resultSet.updateInt("skotizo", KillCounter.getKillCount(player, BossKillCounter.SKOTIZO));
        resultSet.updateInt("thermonuclear_smoke_devil", KillCounter.getKillCount(player, BossKillCounter.THERMONUCLEAR_SMOKE_DEVIL));
        resultSet.updateInt("tzkal_zuk", KillCounter.getKillCount(player, BossKillCounter.ZUK));
        resultSet.updateInt("tztok_jad", KillCounter.getKillCount(player, BossKillCounter.JAD));
        resultSet.updateInt("venenatis", KillCounter.getKillCount(player, BossKillCounter.VENENATIS));
        resultSet.updateInt("vetion", KillCounter.getKillCount(player, BossKillCounter.VETION));
        resultSet.updateInt("vorkath", KillCounter.getKillCount(player, BossKillCounter.VORKATH));
        resultSet.updateInt("wintertodt", KillCounter.getKillCount(player, BossKillCounter.WINTERTODT));
        resultSet.updateInt("zulrah", KillCounter.getKillCount(player, BossKillCounter.ZULRAH));
        // Pking
        resultSet.updateInt("kills", Config.PVP_KILLS.get(player));
        resultSet.updateInt("deaths", Config.PVP_DEATHS.get(player));
        resultSet.updateInt("highest_shutdown", player.highestShutdown);
        resultSet.updateInt("highest_killspress", player.highestKillSpree);
        resultSet.updateInt("pk_rating", player.pkRating);
    }

    private static void updatePVP(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateInt("kills", Config.PVP_KILLS.get(player));
        resultSet.updateInt("deaths", Config.PVP_DEATHS.get(player));
        resultSet.updateInt("highest_shutdown", player.highestShutdown);
        resultSet.updateInt("highest_killspress", player.highestKillSpree);
        resultSet.updateInt("pk_rating", player.pkRating);
    }


    private static int getTotalSkillLevel(Player player) {
        int totalLevel = player.getStats().totalLevel;
        for (int i = 0; i < 7; i++)
            totalLevel -= player.getStats().get(i).fixedLevel;
        return totalLevel;
    }

    private static long getTotalSkillXp(Player player) {
        long totalExp = player.getStats().totalXp;
        for (int i = 0; i < 7; i++)
            totalExp -= player.getStats().get(i).experience;
        return totalExp;
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
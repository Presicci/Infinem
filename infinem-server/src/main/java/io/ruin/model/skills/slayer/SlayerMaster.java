package io.ruin.model.skills.slayer;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.slayer.master.Krystilia;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public class SlayerMaster {
    private final int npcId;
    private final List<SlayerTaskDef> defs;
    public static SlayerMaster[] masters = null;

    /*
     * Config indexes for each master
     */
    public static final int TURAEL_ID = 1;
    public static final int MAZCHNA_ID = 2;
    public static final int VANNAKA_ID = 3;
    public static final int CHAELDAR_ID = 4;
    public static final int NIEVE_ID = 5;
    public static final int DURADEL_ID = 6;
    public static final int KRYSTILIA_ID = 7;
    public static final int KONAR_ID = 8;
    public static final int STEVE_ID = 9;

    public SlayerMaster(int npcId, List<SlayerTaskDef> defs) {
        this.npcId = npcId;
        this.defs = defs;
    }

    /**
     * Generates a random task for a player.
     *
     * @param player The player getting a task.
     * @return The task being assigned.
     */
    public SlayerTaskDef randomTask(Player player) {
        int last = Config.SLAYER_TASK_1.get(player);
        // Using ImmutableRangeMaps for now, change if issues are identified
        ImmutableRangeMap.Builder<Integer, SlayerTaskDef> builder = ImmutableRangeMap.builder();

        int tmp = 0;

        for (SlayerTaskDef task : defs) {
            if (task != null && task.getCreatureUid() != last &&
                    player.getStats().get(StatType.Slayer).fixedLevel >= SlayerCreature.lookup(task.getCreatureUid()).getReq() &&
                    !SlayerUnlock.isBlocked(player, task.getCreatureUid())) {

                if (!player.slayerCombatCheck &&
                        player.getCombat().getLevel() < SlayerCreature.lookup(task.getCreatureUid()).getCbreq()) {
                    continue;
                }

                if (SlayerCreature.lookup(task.getCreatureUid()).canAssign != null && !SlayerCreature.lookup(task.getCreatureUid()).canAssign.apply(player, master(npcId))) {
                    continue;
                }

                builder.put(Range.closedOpen(tmp, tmp + task.getWeighing()), task); // Range from where we left off up to the task weight
                tmp += task.getWeighing();
            }
        }

        RangeMap<Integer, SlayerTaskDef> rangeMap = builder.build();
        Range<Integer> range = rangeMap.span();
        int rnd = Random.get(range.upperEndpoint() - 1);
        SlayerTaskDef def = rangeMap.get(rnd);

        // index 98 is a boss task
        if (def.getCreatureUid() == 98) {
            // Krystilia, duradel, nieve and steve can assign boss tasks
            // Krystilia can only assign wilderness bosses
            List<SlayerBoss> validTasks = npcId == Krystilia.KRYSTILIA
                    ? Arrays.asList(SlayerBoss.SCORPIA, SlayerBoss.CALLISTO, SlayerBoss.VET_ION, SlayerBoss.CRAZY_ARCHAEOLOGIST, SlayerBoss.THE_CHAOS_ELEMENTAL,
                    SlayerBoss.THE_CHAOS_FANATIC, SlayerBoss.THE_KING_BLACK_DRAGON, SlayerBoss.VENENATIS)
                    : Arrays.stream(SlayerBoss.values()).filter((v) -> v.canAssign == null || v.canAssign.apply(player)).collect(Collectors.toList());
            SlayerBoss task = validTasks.get(Random.get(validTasks.size() - 1));
            Config.BOSS_TASK.set(player, task.ordinal() + 1);
        } else {
            Config.BOSS_TASK.set(player, 0);
        }
        player.getTaskManager().doLookupByCategory(TaskCategory.SLAYERTASK, NPCDef.get(npcId).name);
        return def;
    }

    /**
     * Returns a slayer master matching provided npcID.
     *
     * @param npcId NpcID of the master.
     * @return SlayerMaster if found, null if no match.
     */
    public static SlayerMaster master(int npcId) {
        for (SlayerMaster master : masters) {
            if (master.npcId == npcId)
                return master;
        }

        return null;
    }

    /**
     * Checks the best master for a player based on their
     * combat level and returns a string containing that
     * master with their location.
     *
     * @param player The player being checked.
     * @return A string containing the best master for the player with their location.
     */
    public static String bestMaster(Player player) {
        final int cmb = player.getCombat().getLevel();
        if (cmb > 100)
            return "Duradel in Shilo Village";
        else if (cmb > 85)
            return "Steve in the Gnome<br>Stronghold";
        else if (cmb > 75)
            return "Konar on Mount<br>Karuulm";
        else if (cmb > 70)
            return "Chaeldar in Zanaris.";
        else if (cmb > 40)
            return "Vannaka in Edgeville<br>Dungeon";
        else if (cmb > 20)
            return "Mazchna in Canifis";
        return "Turael in Burthorpe";
    }

    /**
     * @param master The slayer master for the current task.
     * @param spree  The task spree.
     * @return Amount of slayer points to be rewarded.
     * TODO: Hook diary rewards in the form of task points in an area up to slayer points for Konar and Nieve/Steve
     */
    public static int getTaskPoints(int master, int spree) {
        if (spree % 1000 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 100;
                case VANNAKA_ID:
                    return 200;
                case CHAELDAR_ID:
                    return 500;
                case STEVE_ID:  // Steve/nieve 600 w/o diaries
                case NIEVE_ID:
                case DURADEL_ID:
                    return 750;
                case KRYSTILIA_ID:
                    return 1250;
                case KONAR_ID:
                    return 1000;    // 900 w/o diaries
                default:
                    return 0;
            }
        }

        if (spree % 250 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 70;
                case VANNAKA_ID:
                    return 140;
                case CHAELDAR_ID:
                    return 350;
                case STEVE_ID:  // Steve/nieve 420 w/o diaries
                case NIEVE_ID:
                case DURADEL_ID:
                    return 525;
                case KRYSTILIA_ID:
                    return 875;
                case KONAR_ID:
                    return 700; // 630 w/o diaries
                default:
                    return 0;
            }
        }

        if (spree % 100 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 50;
                case VANNAKA_ID:
                    return 100;
                case CHAELDAR_ID:
                    return 250;
                case STEVE_ID:  // Steve/nieve 300 w/o diaries
                case NIEVE_ID:
                case DURADEL_ID:
                    return 375;
                case KRYSTILIA_ID:
                    return 625;
                case KONAR_ID:
                    return 500; // 450 w/o diaries
                default:
                    return 0;
            }
        }

        if (spree % 50 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 15;
                case VANNAKA_ID:
                    return 60;
                case CHAELDAR_ID:
                    return 150;
                case STEVE_ID:  // Steve/nieve 180 w/o diaries
                case NIEVE_ID:
                case DURADEL_ID:
                    return 225;
                case KRYSTILIA_ID:
                    return 375;
                case KONAR_ID:
                    return 300; // 270 w/o diaries
                default:
                    return 0;
            }
        }

        if (spree % 10 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 5;
                case VANNAKA_ID:
                    return 20;
                case CHAELDAR_ID:
                    return 50;
                case STEVE_ID:  // Steve/nieve 60 w/o diaries
                case NIEVE_ID:
                case DURADEL_ID:
                    return 75;
                case KRYSTILIA_ID:
                    return 125;
                case KONAR_ID:
                    return 100; // 90 w/o diaries
                default:
                    return 0;
            }
        }

        switch (master) {
            case MAZCHNA_ID:
                return 2;
            case VANNAKA_ID:
                return 4;
            case CHAELDAR_ID:
                return 10;
            case STEVE_ID:  // Steve/nieve 12 w/o diaries
            case NIEVE_ID:
            case DURADEL_ID:
                return 15;
            case KRYSTILIA_ID:
                return 25;
            case KONAR_ID:
                return 20; // 18 w/o diaries
            default:
                return 0;
        }
    }

    public int getNpcId() {
        return npcId;
    }

    public List<SlayerTaskDef> getDefs() {
        return defs;
    }

    /**
     * @param player The player
     * @param left   Monsters left in current task
     * @return Message telling the player how much their current task is left
     */
    public static String getTaskText(Player player, int left) {
        String text = "You're still hunting " + SlayerCreature.taskName(player, Config.SLAYER_TASK_1.get(player)) + ", with " + left + " to go.<br>Come back when you're finished.";

        int master = Config.SLAYER_MASTER.get(player);
        if (master == KRYSTILIA_ID && Config.BOSS_TASK.get(player) == 0) {
            text = "You're still meant to be slaying " + SlayerCreature.taskName(player, Config.SLAYER_TASK_1.get(player)) + " in<br>the Wilderness, you have " + left + " to go. Come back when<br>you've finished your task.";
        } else if (master == KONAR_ID && Config.BOSS_TASK.get(player) == 0) {
            text = "You're still bringing balance to " + SlayerCreature.taskName(player, Config.SLAYER_TASK_1.get(player)) + " at the " + KonarData.TaskLocation.values()[player.slayerLocation].getName() + ", with " + left + " to go.<br>Come back when you're finished.";
        }

        return text;
    }

    /**
     * Checks the player's current task.
     * Sends a message with either the monsters remaining,
     * or that they need a new task.
     *
     * @param player The player being checked.
     */
    public static void checkTask(Player player) {
        int amount = Config.SLAYER_TASK_AMOUNT.get(player);
        if (amount > 0 && !SlayerCreature.taskName(player, Config.SLAYER_TASK_1.get(player)).equalsIgnoreCase("null")) {
            String name = SlayerCreature.taskName(player, Config.SLAYER_TASK_1.get(player));
            String location = "";
            int master = Config.SLAYER_MASTER.get(player);

            if (master == KRYSTILIA_ID && Config.BOSS_TASK.get(player) == 0) {
                location = " in the wilderness";
            } else if (master == KONAR_ID && Config.BOSS_TASK.get(player) == 0) {
                location = " at the " + KonarData.TaskLocation.values()[player.slayerLocation].getName();
            }

            player.sendMessage("You're assigned to kill " + name + "" + location + ", only " + amount + " to go.");
        } else {
            player.sendMessage("You need something new to hunt.");
        }
        player.getTaskManager().doLookupByUUID(31, 1);  // Check Your Slayer Task
    }
}

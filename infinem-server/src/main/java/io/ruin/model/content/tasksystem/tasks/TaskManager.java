package io.ruin.model.content.tasksystem.tasks;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.content.tasksystem.areas.AreaConfig;
import io.ruin.model.content.tasksystem.areas.AreaTaskTier;
import io.ruin.model.content.tasksystem.tasks.impl.DropAllTask;
import io.ruin.model.content.transmog.UnlockableTransmog;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.utility.Color;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.content.tasksystem.tasks.inter.TabTask;
import io.ruin.model.content.tasksystem.tasks.inter.TaskSQLBuilder;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.MapArea;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
public class TaskManager {

    private static final Config[] AREA_POINTS = {
            Config.varpbit(16000, true),
            Config.varpbit(16001, true),
            Config.varpbit(16002, true),
            Config.varpbit(16003, true),
            Config.varpbit(16004, true),
            Config.varpbit(16005, true),
            Config.varpbit(16006, true),
            Config.varpbit(16007, true),
            Config.varpbit(16008, true),
            Config.varpbit(16009, true),
            Config.varpbit(16019, true)
    };

    private static final Config[] COMPLETED_TASKS = {
            Config.varp(2616, false),
            Config.varp(2617, false),
            Config.varp(2618, false),
            Config.varp(2619, false),
            Config.varp(2620, false),
            Config.varp(2621, false),
            Config.varp(2622, false),
            Config.varp(2623, false),
            Config.varp(2624, false),
            Config.varp(2625, false),
            Config.varp(2626, false),
            Config.varp(2627, false),
            Config.varp(2628, false),
            Config.varp(2629, false),
            Config.varp(2630, false),
            Config.varp(2631, false),
            Config.varp(2808, false),
            Config.varp(2809, false),
            Config.varp(2810, false),
            Config.varp(2811, false),
            Config.varp(2812, false),
            Config.varp(2813, false),
            Config.varp(2814, false),
            Config.varp(2815, false),
            Config.varp(2816, false),
            Config.varp(2817, false),
            Config.varp(2818, false),
            Config.varp(2819, false),
            Config.varp(2820, false),
            Config.varp(2821, false),
            Config.varp(2822, false),
            Config.varp(2823, false),
            Config.varp(2824, false),
            Config.varp(2825, false),
            Config.varp(2826, false),
            Config.varp(2827, false),
            Config.varp(2828, false),
            Config.varp(2829, false),
            Config.varp(2830, false),
            Config.varp(2831, false),
            Config.varp(2832, false),
            Config.varp(2833, false),
            Config.varp(2834, false),
            Config.varp(2835, false)
    };

    public TaskManager(Player player) {
        this.player = player;
        this.inProgressTasks = new HashMap<>();
        this.completeTasks = new HashSet<>();
        this.completedCategories = new HashSet<>();
    }

    @Setter private Player player;
    @Expose private HashMap<Integer, Integer> inProgressTasks;
    @Expose private HashSet<Integer> completeTasks;
    @Expose private HashSet<Integer> completedCategories;

    public String searchString = "";

    private void completeTask(String taskName, int uuid, TaskArea taskArea, TaskDifficulty taskDifficulty) {
        if (completeTasks.contains(uuid))
            return;
        int pointGain = taskDifficulty.getPoints();
        AreaTaskTier prevGeneralTier = TaskArea.GENERAL.getHighestTier(player);
        AreaTaskTier prevTier = taskArea.getHighestTier(player);
        if (taskArea != TaskArea.GENERAL) {
            AREA_POINTS[taskArea.ordinal() - 1].increment(player, pointGain / 10);
        }
        int newPoints = Config.LEAGUE_POINTS.increment(player, pointGain);
        player.sendMessage("<col=990000>You've completed a task: " + taskName + "!");
        player.sendMessage("You now have " + newPoints + " task points.");
        completeTasks.add(uuid);
        Config.LEAGUE_TASKS_COMPLETED.set(player, completeTasks.size());
        player.getPacketSender().sendPopupNotification(0xff981f, "Task Complete!", "Task Completed: " + Color.WHITE.wrap(taskName)
                + "<br><br>Points Earned: " + Color.WHITE.wrap(pointGain + ""));
        TabTask.refresh(player);
        completeTaskBit(player, uuid);
        // Check for unlocks
        AreaTaskTier newTier = taskArea.getHighestTier(player);
        if (newTier != null && newTier != prevTier && taskArea != TaskArea.GENERAL) {
            player.sendMessage("<col=990000><shad=000000>You've reached the " + StringUtils.capitalizeFirst(newTier.name().toLowerCase()) + " tier of unlocks in " + taskArea + "!");
            AreaConfig.checkAll(player);
            UnlockableTransmog.unlockAreaTransmogs(player, taskArea);
        }
        AreaTaskTier newGeneralTier = TaskArea.GENERAL.getHighestTier(player);
        if (newGeneralTier != null && newGeneralTier != prevGeneralTier) {
            player.sendMessage("<col=990000><shad=000000>You've reached the " + StringUtils.capitalizeFirst(newGeneralTier.name().toLowerCase()) + " tier of general unlocks!");
        }
    }

    public void removeTask(String taskName, int uuid, TaskArea taskArea, TaskDifficulty difficulty) {
        if (!completeTasks.contains(uuid))
            return;
        int pointsLost = difficulty.getPoints();
        if (taskArea != TaskArea.GENERAL) {
            AREA_POINTS[taskArea.ordinal() - 1].increment(player, -(pointsLost / 10));
        }
        Config.LEAGUE_POINTS.increment(player, -pointsLost);
        completeTasks.remove(uuid);
        Config.LEAGUE_TASKS_COMPLETED.set(player, completeTasks.size());
        player.sendMessage("<col=990000>You have lost completion for task: " + taskName);
        removeTaskBit(player, uuid);
    }

    private static void completeTaskBit(Player player, int taskId) {
        int bit = taskId % 32;
        int varpIndex = taskId / 32;
        COMPLETED_TASKS[varpIndex].setBit(player, bit);
    }

    private static void removeTaskBit(Player player, int taskId) {
        int bit = taskId % 32;
        int varpIndex = taskId / 32;
        COMPLETED_TASKS[varpIndex].removeBit(player, bit);
    }

    static {
        // Set completed task varps on login
        LoginListener.register(player -> player.getTaskManager().completeTasks.forEach(taskId -> completeTaskBit(player, taskId)));
        // Check for configs that need to be set
        LoginListener.register(AreaConfig::checkAll);
    }

    public void resetTasks() {
        Config.LEAGUE_POINTS.set(player, 0);
        for (Config config : AREA_POINTS) {
            config.set(player, 0);
        }
        for (Config config : COMPLETED_TASKS) {
            config.set(player, 0);
        }
        this.inProgressTasks = new HashMap<>();
        this.completeTasks = new HashSet<>();
        this.completedCategories = new HashSet<>();
    }

    public int getAreaTaskPoints(int areaIndex) {
        if (areaIndex == 0) return Config.LEAGUE_POINTS.get(player);
        return AREA_POINTS[areaIndex - 1].get(player) * 10;
    }

    /**
     * Defaults:
     *  - amount: 1
     *  - mapArea: null
     *  - incremental: true
     * @param category Task category.
     * @param trigger Trigger keywords.
     */
    public void doLookupByCategory(TaskCategory category, String trigger) {
        doLookupByCategory(category, trigger, 1, true);
    }

    public void doLookupByCategory(TaskCategory category, String trigger, int amount) {
        doLookupByCategory(category, trigger, amount, true);
    }

    public void doLookupByCategory(TaskCategory category, int amount, boolean incremental) {
        doLookupByCategory(category, "", amount, incremental);
    }

    public void doLookupByCategory(TaskCategory category, String trigger, int amount, boolean incremental) {
        //  No tasks left for this category, abort
        if (completedCategories.contains(category.ordinal())) {
            return;
        }
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ?");
                statement.setString(1, StringUtils.capitalizeFirst(category.toString().toLowerCase()));
                rs = statement.executeQuery();
                checkResults(rs, TaskLookupType.CATEGORY, category, trigger, amount, incremental);
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }

    public void doLookupByCategoryAndTrigger(TaskCategory category, String trigger, int amount) {
        doLookupByCategoryAndTrigger(category, trigger, amount, true);
    }

    public void doLookupByCategoryAndTrigger(TaskCategory category, String trigger) {
        doLookupByCategoryAndTrigger(category, trigger, 1, true);
    }

    public void doLookupByCategoryAndTrigger(TaskCategory category, String trigger, int amount, boolean incremental) {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ? AND required_object REGEXP ?");
                statement.setString(1, StringUtils.capitalizeFirst(category.toString().toLowerCase()));
                String replace = trigger.trim().toLowerCase().replace("_", " ").replace("+", "\\+").replace("(", "\\(").replace(")", "\\)");
                statement.setString(2, "^" + replace + "$|," + replace + "$|," + replace + ",|^" + replace + ",|^any$");
                rs = statement.executeQuery();
                checkResults(rs, TaskLookupType.CAT_AND_TRIGGER, category, null, amount, incremental);
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }

    public void doDropGroupLookup(String trigger) {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = 'UNLOCKITEMSET' AND required_object REGEXP ?");
                String replace = trigger.trim().toLowerCase().replace("_", " ").replace("+", "\\+").replace("(", "\\(").replace(")", "\\)");
                statement.setString(1, "^" + replace + "$|," + replace + "$|," + replace + ",|^" + replace + ",");
                rs = statement.executeQuery();
                checkResults(rs, TaskLookupType.DROP_SET, null, trigger, 1, false);
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }

    public void doLookupByUUID(int uuid) {
        doLookupByUUID(uuid, 1);
    }

    public void doIncrementalLookupByUUID(int uuid, int amount) {
        doLookupByUUID(uuid, amount, true);
    }

    public void doLookupByUUID(int uuid, int amount) {
        doLookupByUUID(uuid, amount, false);
    }

    public void doLookupByUUID(int uuid, int amount, boolean incremental) {
        //  Task already completed, abort
        if (completeTasks.contains(uuid)) {
            return;
        }
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE uuid = ?");
                statement.setInt(1, uuid);
                rs = statement.executeQuery();
                checkResults(rs, TaskLookupType.UUID, null, null, amount, incremental);
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }

    public void doLevelUpLookup(int level, boolean hitpoints) {
        if (!hitpoints) {
            doLookupByCategory(TaskCategory.FIRSTLEVEL, level, false);
        }
        doLookupByCategory(TaskCategory.BASELEVEL, player.getStats().getBaseLevel(), false);
        doLookupByCategory(TaskCategory.TOTALLEVEL, player.getStats().totalLevel + 1, false);
        doLookupByCategory(TaskCategory.COMBATLEVEL, player.getCombat().getLevel(), false);
    }

    public void doKillLookup(int npcId) {
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.NPCKILL, NPCDefinition.get(npcId).name.toLowerCase(), 1, true);
    }

    public void doDropLookup(Item item) {
        int uuid = item.getDef().getCustomValueOrDefault("DROP_TASK", 0);
        if (uuid > 0) {
            doLookupByUUID(uuid, item.getAmount());
        }
        DropAllTask dropAllTask = item.getDef().getCustomValueOrDefault("DROPALL_TASK", null);
        if (dropAllTask != null && !completeTasks.contains(dropAllTask.getTaskUuid())) {
            if (dropAllTask.hasCompleted(player)) {
                doLookupByUUID(dropAllTask.getTaskUuid(), item.getAmount());
            }
        }
    }

    public boolean hasCompletedTask(int uuid) {
        return completeTasks.contains(uuid);
    }

    /**
     * Parses the result set for task(s) that match the provided criteria.
     * @param rs The ResultSet being parsed.
     * @param lookupType The type of lookup being performed, influences some logic.
     * @param category The category of the task.
     * @param trigger Trigger being tested against the required_objeects of the task.
     * @param amount The amount being tested.
     * @param incremental Whether the task is incremental or not.
     */
    private void checkResults(ResultSet rs, TaskLookupType lookupType, TaskCategory category, String trigger, int amount, boolean incremental) throws SQLException {
        MapArea mapArea = MapArea.getMapArea(player);
        boolean foundNotCompletedTask = false;
        while (rs.next()) {
            /*
             * Skip tasks that are already completed.
             */
            int uuid = rs.getInt("uuid");
            if (completeTasks.contains(uuid)) {
                continue;
            }

            // Track if we find a task that isn't completed, if we don't we can clear the category from searches
            foundNotCompletedTask = true;

            /*
             * If we send a trigger, make sure it matches any of the required_objects of the task.
             */
            String[] triggers = new String[0];
            if (trigger != null) {
                String triggerString = rs.getString("required_object");
                if (!triggerString.trim().isEmpty()) {
                    triggers = triggerString.trim().split(",");
                    if (!handleTrigger(lookupType, trigger, triggers)) {
                        continue;
                    }
                }
            }

            /*
             * If the task has an area restriction, check that the player satisfies it.
             */
            String mapareaString = rs.getString("maparea");
            if (!mapareaString.trim().isEmpty() && (mapArea == null || !mapareaString.equalsIgnoreCase(mapArea.toString().replace("_", " ")))) {
                continue;
            }

            /*
             * Checks if the player satisfies the required amount to complete the task
             * - If the task is incremental, increment the amount
             */
            int requiredAmount = rs.getInt("required_amount");
            int currentAmount = inProgressTasks.get(uuid) == null ? 0 : inProgressTasks.get(uuid);
            if (requiredAmount > 1) {
                if (incremental) {
                    currentAmount += amount;
                    if (currentAmount < requiredAmount) {
                        inProgressTasks.put(uuid, currentAmount);
                        continue;
                    } else {
                        inProgressTasks.remove(uuid);
                    }
                } else {
                    if (amount < requiredAmount) {
                        continue;
                    }
                }
            }

            /*
             * Get relevant info to complete the task.
             */
            String name = rs.getString("name");
            String taskArea = rs.getString("region").toLowerCase();
            TaskArea finalTaskarea = null;
            for (TaskArea ta : TaskArea.values()) {
                if (taskArea.trim().equalsIgnoreCase(ta.toString().toLowerCase())) {
                    finalTaskarea = ta;
                    break;
                }
            }
            String difficulty = rs.getString("difficulty").toLowerCase();
            TaskDifficulty finalDifficulty = null;
            for (TaskDifficulty diff : TaskDifficulty.values()) {
                if (difficulty.equalsIgnoreCase(diff.toString().toLowerCase())) {
                    finalDifficulty = diff;
                    break;
                }
            }
            if (finalDifficulty == null || finalTaskarea == null) {
                continue;
            }
            TaskArea finalTaskarea1 = finalTaskarea;
            TaskDifficulty finalDifficulty1 = finalDifficulty;
            player.addEvent(e -> {  // addEvent here to prevent sending packets in a thread
                completeTask(name, uuid, finalTaskarea1, finalDifficulty1);
            });
        }
        // Remove category from searches
        if (category != null && !foundNotCompletedTask && lookupType == TaskLookupType.CATEGORY) {
            completedCategories.add(category.ordinal());
        }
    }

    private boolean handleTrigger(TaskLookupType lookupType, String trigger, String[] triggers) {
        boolean found = false;
        for (String s : triggers) {
            if (s.equalsIgnoreCase(trigger) || s.equalsIgnoreCase("any")) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Name|Difficulty|Completed|Area
     * - Difficulty:
     *      0 - Easy
     *      1 - Medium
     *      2 - Hard
     *      3 - Elite
     *      4 - Master
     * - Completed:
     *      0 - No
     *      1 - Yes
     * - Area:
     *      0 - General/Multiple Regions
     *      1 - Asgarnia
     *      2 - Fremennik Provinces
     *      3 - Kandarin
     *      4 - Karamja
     *      5 - Kharidian Desert
     *      6 - Misthalin
     *      7 - Morytania
     *      8 - Tirannwn
     *      9 - Wilderness
     *      10 - Zeah
     */
    public void sendTasksToInterface() {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            StringBuilder sb = new StringBuilder();
            int count = 0;
            try {
                if (searchString == null || searchString.trim().isEmpty()) {
                    statement = connection.prepareStatement(TaskSQLBuilder.getSortQuery(player));
                } else {
                    statement = connection.prepareStatement(TaskSQLBuilder.getSearch(player));
                    statement.setString(1, "%" + searchString + "%");
                    statement.setString(2, "%" + searchString + "%");
                    statement.setString(3, "%" + searchString + "%");
                }
                rs = statement.executeQuery();
                while (rs.next()) {
                    int uuid = rs.getInt("uuid");
                    boolean completed = completeTasks.contains(uuid);
                    if (completed && Config.TASK_INTERFACE_COMPLETED.get(player) == 1)
                        continue;
                    if (!completed && Config.TASK_INTERFACE_COMPLETED.get(player) == 2)
                        continue;
                    String name = rs.getString("name");
                    String difficulty = rs.getString("difficulty");
                    String region = rs.getString("region");
                    if (count++ > 0) {
                        sb.append("|");
                    }
                    sb.append(name).append("|");
                    TaskDifficulty taskDifficulty = TaskDifficulty.getTaskDifficulty(difficulty);
                    sb.append(taskDifficulty == null ? 0 : taskDifficulty.ordinal()).append("|");
                    sb.append(completed ? "1" : "0").append("|");
                    TaskArea taskArea = TaskArea.getTaskArea(region);
                    sb.append(taskArea == null ? 0 : taskArea.ordinal());
                }
            } finally {
                DatabaseUtils.close(statement, rs);
                int finalCount = count;
                player.addEvent(e -> {
                    player.getPacketSender().sendClientScript(10060, "is", finalCount, sb.toString());
                    player.getPacketSender().sendAccessMask(1008, 24, 0, finalCount * 3, 2);
                });
            }
        });
    }

    public String generateInProgressString() {
        if (inProgressTasks.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int uuid : inProgressTasks.keySet()) {
            sb.append(uuid);
            sb.append("|");
            sb.append(NumberUtils.formatNumber(inProgressTasks.get(uuid)));
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);   // Trim trailing |
        return sb.toString();
    }
}

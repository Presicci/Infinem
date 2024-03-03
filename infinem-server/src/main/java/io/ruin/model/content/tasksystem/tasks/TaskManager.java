package io.ruin.model.content.tasksystem.tasks;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
public class TaskManager {

    public TaskManager(Player player) {
        this.player = player;
        this.taskPoints = new HashMap<>();
        for (TaskArea area : TaskArea.values()) {
            this.taskPoints.put(area.ordinal(), 0);
        }
        this.inProgressTasks = new HashMap<>();
        this.completeTasks = new HashSet<>();
        this.completedCategories = new HashSet<>();
        this.collectedItems = new HashSet<>();
    }

    @Setter private Player player;
    @Expose private HashMap<Integer, Integer> taskPoints;
    @Expose private HashMap<Integer, Integer> inProgressTasks;
    @Expose private HashSet<Integer> completeTasks;
    @Expose private HashSet<Integer> completedCategories;
    @Expose private HashSet<String> collectedItems;

    public String searchString = "";

    private void completeTask(String taskName, int uuid, TaskArea taskArea, TaskDifficulty taskDifficulty) {
        if (completeTasks.contains(uuid))
            return;
        int pointGain = taskDifficulty.getPoints();
        taskPoints.put(taskArea.ordinal(), taskPoints.get(taskArea.ordinal()) + pointGain);
        int newPoints = Config.LEAGUE_POINTS.increment(player, pointGain);
        player.sendMessage("<col=990000>You've completed a task: " + taskName + "!");
        player.sendMessage("You now have " + newPoints + " task points.");
        completeTasks.add(uuid);
        Config.LEAGUE_TASKS_COMPLETED.set(player, completeTasks.size());
        player.getPacketSender().sendPopupNotification(0xff981f, "Task Complete!", "Task Completed: " + Color.WHITE.wrap(taskName)
                + "<br><br>Points Earned: " + Color.WHITE.wrap(pointGain + ""));
        TabTask.refresh(player);
    }

    public void resetTasks() {
        Config.LEAGUE_POINTS.set(player, 0);
        this.taskPoints = new HashMap<>();
        for (TaskArea area : TaskArea.values()) {
            this.taskPoints.put(area.ordinal(), 0);
        }
        this.inProgressTasks = new HashMap<>();
        this.completeTasks = new HashSet<>();
        this.completedCategories = new HashSet<>();
        this.collectedItems = new HashSet<>();
    }

    public int getAreaTaskPoints(int areaIndex) {
        return taskPoints.get(areaIndex);
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
            System.out.println("category complete");
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
                String replace = trigger.trim().toLowerCase().replace("_", " ").replace("+", "/+");
                statement.setString(2, "^" + replace + "$|," + replace + "$|," + replace + ",|^" + replace + ",");
                rs = statement.executeQuery();
                checkResults(rs, TaskLookupType.CAT_AND_TRIGGER, null, null, amount, incremental);
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }

    public void doLookupByCategoryAndTriggerRegex(TaskCategory category, String trigger) {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ?");
                statement.setString(1, StringUtils.capitalizeFirst(category.toString().toLowerCase()));
                rs = statement.executeQuery();
                checkResults(rs, TaskLookupType.REGEX_COMPARE_TRIGGER, null, trigger, 1, false);
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
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ? AND required_object LIKE ?");
                statement.setString(1, StringUtils.capitalizeFirst("unlockitemset"));
                String replace = trigger.trim().toLowerCase().replace("_", " ");
                statement.setString(2, "^" + replace + "|," + replace);
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
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.NPCKILL, NPCDef.get(npcId).name.toLowerCase(), 1, true);
    }

    public void doSkillItemLookup(int itemId) {
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.SKILLITEM, ItemDef.get(itemId).name.toLowerCase(), 1, true);
    }

    public void doSkillItemLookup(int itemId, int amount) {
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.SKILLITEM, ItemDef.get(itemId).name.toLowerCase(), amount, true);
    }

    public void doSkillItemLookup(Item item) {
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.SKILLITEM, item.getDef().name.toLowerCase(), item.getAmount(), true);
    }

    public void doUnlockItemLookup(Item item) {
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.UNLOCKITEM, item.getDef().name.toLowerCase(), item.getAmount(), true);
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
                        System.out.println("[" + player.getName() + "] uuid=" + uuid + ", cat=" + category + ", trig=" + trigger + ", amt=" + amount + ", inc=" + incremental + ", exit on required_obj");
                        continue;
                    }
                }
            }

            /*
             * If the task has an area restriction, check that the player satisfies it.
             */
            String mapareaString = rs.getString("maparea");
            if (!mapareaString.trim().isEmpty() && (mapArea == null || !mapareaString.equalsIgnoreCase(mapArea.toString()))) {
                System.out.println("[" + player.getName() + "] uuid=" + uuid + ", cat=" + category + ", trig=" + trigger + ", amt=" + amount + ", inc=" + incremental + ", exit on maparea");
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
                        System.out.println("Task #" + uuid + " progress: " + currentAmount + "/" + requiredAmount);
                        continue;
                    } else {
                        inProgressTasks.remove(uuid);
                    }
                } else {
                    if (amount < requiredAmount) {
                        System.out.println("[" + player.getName() + "] uuid=" + uuid + ", cat=" + category + ", trig=" + trigger + ", amt=" + amount + ", exit on amt");
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
                System.out.println("TASK ERROR! uuid=" + uuid + " diff=" + difficulty.trim() + " / " + finalDifficulty + ", area=" + taskArea.trim() + " / " + finalTaskarea);
                continue;
            }
            if (lookupType == TaskLookupType.DROP_SET) {
                for (String s : triggers) {
                    collectedItems.remove(s);
                }
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
        if (lookupType == TaskLookupType.DROP_SET) {
            boolean complete = true;
            for (String s : triggers) {
                if (s.toLowerCase().contains(trigger.trim().toLowerCase())) {
                    collectedItems.add(s);
                }
                if (!collectedItems.contains(s))
                    complete = false;
            }
            return complete;
        } else if (lookupType == TaskLookupType.REGEX_COMPARE_TRIGGER) {
            boolean found = false;
            for (String s : triggers) {
                Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(trigger);
                if (matcher.find()) {
                    found = true;
                    break;
                }
            }
            return found;
        } else {
            boolean found = false;
            for (String s : triggers) {
                if (s.equalsIgnoreCase(trigger)) {
                    found = true;
                    break;
                }
            }
            return found;
        }
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
                statement = connection.prepareStatement(TaskSQLBuilder.getSelectQuery(player, searchString));
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
}

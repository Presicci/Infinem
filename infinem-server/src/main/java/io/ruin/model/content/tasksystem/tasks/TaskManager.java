package io.ruin.model.content.tasksystem.tasks;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.MapArea;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
public class TaskManager {

    public TaskManager(Player player) {
        this.player = player;
        this.globalTaskPoints = 0;
        this.taskPoints = new HashMap<>();
        for (TaskArea area : TaskArea.values()) {
            this.taskPoints.put(area.ordinal(), 0);
        }
        this.inProgressTasks = new HashMap<>();
        this.completeTasks = new HashSet<>();
        this.completedCategories = new HashSet<>();
        this.collectedItems = new HashSet<>();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;
    @Expose private int globalTaskPoints;
    @Expose private HashMap<Integer, Integer> taskPoints;
    @Expose private HashMap<Integer, Integer> inProgressTasks;
    @Expose private HashSet<Integer> completeTasks;
    @Expose private HashSet<Integer> completedCategories;
    @Expose private HashSet<String> collectedItems;

    private void completeTask(String taskName, int uuid, TaskArea taskArea, TaskDifficulty taskDifficulty) {
        if (completeTasks.contains(uuid))
            return;
        int pointGain = taskDifficulty.getPoints();
        taskPoints.put(taskArea.ordinal(), taskPoints.get(taskArea.ordinal()) + pointGain);
        globalTaskPoints += pointGain;
        player.sendMessage("<col=990000>You've completed a task: " + taskName + "!");
        player.sendMessage("You now have " + globalTaskPoints + " task points.");
        completeTasks.add(uuid);
    }

    public void resetTasks() {
        this.globalTaskPoints = 0;
        this.taskPoints = new HashMap<>();
        for (TaskArea area : TaskArea.values()) {
            this.taskPoints.put(area.ordinal(), 0);
        }
        this.inProgressTasks = new HashMap<>();
        this.completeTasks = new HashSet<>();
        this.completedCategories = new HashSet<>();
        this.collectedItems = new HashSet<>();
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

    public void doLookupByCategory(TaskCategory category, int amount, boolean incremental) {
        doLookupByCategory(category, "", amount, incremental);
    }

    public void doLookupByCategory(TaskCategory category, String trigger, int amount, boolean incremental) {
        //  No tasks left for this category, abort
        if (completedCategories.contains(category.ordinal())) {
            System.out.println("category complete");
            return;
        }
        long currentTime = System.currentTimeMillis();
        MapArea mapArea = MapArea.getMapArea(player);
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            boolean foundNotCompletedTask = false;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ?");
                statement.setString(1, StringUtils.capitalizeFirst(category.toString().toLowerCase()));
                rs = statement.executeQuery();
                while (rs.next()) {
                    int uuid = rs.getInt("uuid");
                    if (completeTasks.contains(uuid)) {
                        continue;
                    }
                    foundNotCompletedTask = true;
                    String trig = rs.getString("required_object");
                    if (trig.trim().length() > 0) {
                        String[] trigs = trig.trim().split(",");
                        boolean found = false;
                        for (String s : trigs) {
                            if (s.toLowerCase().equals(trigger.toLowerCase())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("[" + player.getName() + "]" + category + "/" + trigger + "/" + amount + "/" + incremental + ", exit on obj");
                            continue;
                        }
                    }
                    String area = rs.getString("maparea");
                    if (area.trim().length() > 0 && (mapArea == null || !area.toLowerCase().equalsIgnoreCase(mapArea.toString().toLowerCase()))) {
                        System.out.println("[" + player.getName() + "]" + category + "/" + trigger + "/" + amount + "/" + incremental + ", exit on map");
                        continue;
                    }
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
                                System.out.println("[" + player.getName() + "]" + category + "/" + trigger + "/" + amount + "/" + incremental + ", exit on amt");
                                continue;
                            }
                        }
                    }
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
                        System.out.println("cat TASK ERROR! diff=" + difficulty.trim() + " / " + finalDifficulty + ", area=" + taskArea.trim() + " / " + finalTaskarea);
                    } else {
                        TaskArea finalTaskarea1 = finalTaskarea;
                        TaskDifficulty finalDifficulty1 = finalDifficulty;
                        player.addEvent(e -> {  // addEvent here to prevent sending packets in a thread
                            completeTask(name, uuid, finalTaskarea1, finalDifficulty1);
                        });
                    }
                }
                if (!foundNotCompletedTask) {
                    completedCategories.add(category.ordinal());
                }
            } finally {
                DatabaseUtils.close(statement, rs);
                System.out.println(System.currentTimeMillis() - currentTime);
            }
        });
    }

    public void doLookupByCategoryAndTrigger(TaskCategory category, String trigger) {
        doLookupByCategoryAndTrigger(category, trigger, 1, true);
    }

    public void doLookupByCategoryAndTrigger(TaskCategory category, String trigger, int amount, boolean incremental) {
        long currentTime = System.currentTimeMillis();
        MapArea mapArea = MapArea.getMapArea(player);
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ? AND required_object REGEXP ?");
                statement.setString(1, StringUtils.capitalizeFirst(category.toString().toLowerCase()));
                String replace = trigger.trim().toLowerCase().replace("_", " ");
                statement.setString(2, "^" + replace + "|," + replace);
                rs = statement.executeQuery();
                while (rs.next()) {
                    int uuid = rs.getInt("uuid");
                    if (completeTasks.contains(uuid)) {
                        continue;
                    }
                    String area = rs.getString("maparea");
                    if (area.trim().length() > 0 && (mapArea == null || !area.toLowerCase().equalsIgnoreCase(mapArea.toString().toLowerCase().replace("_", " ")))) {
                        System.out.println("cat&trigger1 TASK ERROR! area=" + area.trim() + " / " + mapArea.toString().toLowerCase());
                        continue;
                    }
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
                                System.out.println("[" + player.getName() + "]" + category + "/" + trigger + "/" + amount + "/" + incremental + ", exit on amt");
                                continue;
                            }
                        }
                    }
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
                        System.out.println("cat&trigger2 TASK ERROR! diff=" + difficulty.trim() + " / " + finalDifficulty + ", area=" + taskArea.trim() + " / " + finalTaskarea);
                    } else {
                        TaskArea finalTaskarea1 = finalTaskarea;
                        TaskDifficulty finalDifficulty1 = finalDifficulty;
                        player.addEvent(e -> {  // addEvent here to prevent sending packets in a thread
                            completeTask(name, uuid, finalTaskarea1, finalDifficulty1);
                        });
                    }
                }
            } finally {
                DatabaseUtils.close(statement, rs);
                System.out.println(System.currentTimeMillis() - currentTime);
            }
        });
    }

    public void doDropGroupLookup(String trigger) {
        long currentTime = System.currentTimeMillis();
        MapArea mapArea = MapArea.getMapArea(player);
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ? AND required_object LIKE ?");
                statement.setString(1, StringUtils.capitalizeFirst("unlockitemset"));
                String replace = trigger.trim().toLowerCase().replace("_", " ");
                statement.setString(2, "^" + replace + "|," + replace);
                rs = statement.executeQuery();
                while (rs.next()) {
                    int uuid = rs.getInt("uuid");
                    if (completeTasks.contains(uuid)) {
                        continue;
                    }
                    String area = rs.getString("maparea");
                    if (area.trim().length() > 0 && (mapArea == null || !area.toLowerCase().equalsIgnoreCase(mapArea.toString().toLowerCase()))) {
                        System.out.println("[" + player.getName() + "]" + "/" + trigger + "/" + "/" + ", exit on map");
                        continue;
                    }
                    String trig = rs.getString("required_object");
                    String[] trigs;
                    if (trig.trim().length() > 0) {
                        trigs = trig.trim().split(",");
                        boolean complete = true;
                        for (String s : trigs) {
                            if (s.toLowerCase().contains(trigger.trim().toLowerCase())) {
                                collectedItems.add(s);
                            }
                            if (!collectedItems.contains(s))
                                complete = false;
                        }
                        if (!complete) {
                            System.out.println("[" + player.getName() + "]" + "/" + trigger + "/"  + "/"  + ", not complete set");
                            continue;
                        }
                    } else {
                        ServerWrapper.logError("Task#" + uuid + " does not have any valid item names listed for required_object");
                        continue;
                    }
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
                        System.out.println("TASK ERROR! diff=" + difficulty.trim() + " / " + finalDifficulty + ", area=" + taskArea.trim() + " / " + finalTaskarea);
                    } else {
                        for (String s : trigs) {
                            collectedItems.remove(s);
                        }
                        TaskArea finalTaskarea1 = finalTaskarea;
                        TaskDifficulty finalDifficulty1 = finalDifficulty;
                        player.addEvent(e -> {  // addEvent here to prevent sending packets in a thread
                            completeTask(name, uuid, finalTaskarea1, finalDifficulty1);
                        });
                    }
                }
            } finally {
                DatabaseUtils.close(statement, rs);
                System.out.println(System.currentTimeMillis() - currentTime);
            }
        });
    }

    public void doLookupByUUID(int uuid, int amount) {
        //  Task already completed, abort
        if (completeTasks.contains(uuid)) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        Server.gameDb.execute(connection -> {

            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE uuid = ?");
                statement.setInt(1, uuid);
                rs = statement.executeQuery();
                while (rs.next()) {
                    int requiredAmount = rs.getInt("required_amount");
                    int currentAmount = inProgressTasks.get(uuid) == null ? 0 : inProgressTasks.get(uuid);
                    if (requiredAmount > 1) {
                        currentAmount += amount;
                        if (currentAmount < requiredAmount) {
                            inProgressTasks.put(uuid, currentAmount);
                            System.out.println("Task #" + uuid + " progress: " + currentAmount + "/" + requiredAmount);
                            break;
                        } else {
                            inProgressTasks.remove(uuid);
                        }
                    }
                    String taskArea = rs.getString("region");
                    TaskArea finalTaskarea = null;
                    for (TaskArea ta : TaskArea.values()) {
                        if (taskArea.trim().equalsIgnoreCase(ta.toString())) {
                            finalTaskarea = ta;
                            break;
                        }
                    }
                    String difficulty = rs.getString("difficulty");
                    TaskDifficulty finalDifficulty = null;
                    for (TaskDifficulty diff : TaskDifficulty.values()) {
                        if (difficulty.equalsIgnoreCase(diff.toString())) {
                            finalDifficulty = diff;
                            break;
                        }
                    }
                    String name = rs.getString("name");
                    if (finalDifficulty == null || finalTaskarea == null) {
                        System.out.println("uuid TASK ERROR! diff=" + difficulty.trim() + " / " + finalDifficulty + ", area=" + taskArea.trim() + " / " + finalTaskarea);
                    } else {
                        TaskArea finalTaskarea1 = finalTaskarea;
                        TaskDifficulty finalDifficulty1 = finalDifficulty;
                        player.addEvent(e -> {  // addEvent here to prevent sending packets in a thread
                            completeTask(name, uuid, finalTaskarea1, finalDifficulty1);
                        });
                    }
                }
            } finally {
                DatabaseUtils.close(statement, rs);
                System.out.println(System.currentTimeMillis() - currentTime);
            }
        });
    }

    public void doLevelUpLookup(int level, boolean hitpoints) {
        if (!hitpoints) {
            doLookupByCategory(TaskCategory.FIRSTLEVEL, level, false);
        }
        doLookupByCategory(TaskCategory.BASELEVEL, player.getStats().getBaseLevel(), false);
        doLookupByCategory(TaskCategory.TOTALLEVEL, player.getStats().totalLevel, false);
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
}

package io.ruin.model.content.tasksystem.tasks;

import com.google.common.collect.Multimap;
import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.MapArea;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
public class TaskManager {

    TaskManager(Player player) {
        this.player = player;
        for (TaskArea area : TaskArea.values()) {
            this.taskPoints.put(area, 0);
        }
        this.globalTaskPoints = 0;
        this.taskPoints = new HashMap<>();
        this.inProgressTasks = new HashMap<>();
    }

    private final Player player;
    private int globalTaskPoints;
    private HashMap<TaskArea, Integer> taskPoints;
    private HashMap<Integer, Integer> inProgressTasks;
    private Multimap<TaskCategory, Integer> completeTasks;
    private ArrayList<TaskCategory> completedCategories;

    private void completeTask(String taskName, int uuid, TaskCategory taskCategory, TaskArea taskArea, TaskDifficulty taskDifficulty) {
        int pointGain = taskDifficulty.getPoints();
        taskPoints.put(taskArea, taskPoints.get(taskArea) + pointGain);
        globalTaskPoints += pointGain;
        player.sendMessage("<990000>You've completed a task: " + taskName + "!");
        player.sendMessage("You now have " + globalTaskPoints + " task points.");
        completeTasks.put(taskCategory, uuid);
    }

    public void doLookupByCategory(TaskCategory category, int trigger, int amount, MapArea mapArea) {
        //  No tasks left for this category, abort
        if (completedCategories.contains(category)) {
            return;
        }
        Server.gameDb.execute(connection -> {

            PreparedStatement statement = null;
            ResultSet rs = null;
            boolean foundNotCompletedTask = false;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category = ?");
                statement.setString(1, category.toString().toUpperCase());
                rs = statement.executeQuery();
                Collection<Integer> ignoredTasks = completeTasks.get(category);
                while (rs.next()) {
                    int uuid = rs.getInt("uuid");
                    if (ignoredTasks.contains(uuid)) {
                        continue;
                    }
                    foundNotCompletedTask = true;
                    int trig = rs.getInt("trigger");
                    if (trig > 0 && trig != trigger) {
                        continue;
                    }
                    String area = rs.getString("maparea");
                    if (area != null && !area.equalsIgnoreCase(mapArea.toString())) {
                        continue;
                    }
                    int requiredAmount = rs.getInt("amount");
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
                    String name = rs.getString("name");
                    String taskArea = rs.getString("area");
                    TaskArea finalTaskarea = null;
                    for (TaskArea ta : TaskArea.values()) {
                        if (taskArea.equalsIgnoreCase(ta.toString())) {
                            finalTaskarea = ta;
                            break;
                        }
                    }
                    String difficulty = rs.getString("area");
                    TaskDifficulty finalDifficulty = null;
                    for (TaskDifficulty diff : TaskDifficulty.values()) {
                        if (difficulty.equalsIgnoreCase(diff.toString())) {
                            finalDifficulty = diff;
                            break;
                        }
                    }
                    completeTask(name, uuid, category, finalTaskarea, finalDifficulty);
                }
                if (!foundNotCompletedTask) {
                    completedCategories.add(category);
                }
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }
}

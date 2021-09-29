package io.ruin.model.content.tasksystem.tasks;

import io.ruin.model.map.MapArea;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public class Task {

    /**
     * Unique identifier id for the task.
     */
    private int uuid;

    /**
     * The category of the task.
     */
    private TaskCategory taskCategory;

    /**
     * The area where the task is based in.
     * GENERAL if no area
     */
    private TaskArea taskArea;

    /**
     * The difficulty of the task.
     */
    private TaskDifficulty taskDifficulty;

    /**
     * Description of what is needed to complete the task
     */
    private String description;

    private String name;

    /**
     * How many times it needs to be done before completion.
     */
    private int goal;

    /**
     * Array of triggers for completing the task.
     */
    private int[] triggers;

    /**
     * Bounds for where the task needs to be completed.
     */
    private MapArea taskBounds;

    public Task(int uuid, String name, String description, TaskDifficulty taskDifficulty, TaskArea taskArea, TaskCategory taskCategory, int goal, MapArea taskBounds, int... triggers) {
        this.uuid = uuid;
        this.name = name;
        this.taskCategory = taskCategory;
        this.taskArea = taskArea;
        this.taskDifficulty = taskDifficulty;
        this.description = description;
        this.goal = goal;
        this.taskBounds = taskBounds;
        this.triggers = triggers;
    }

    public Task(int uuid, String name, String description, TaskDifficulty taskDifficulty, TaskArea taskArea, TaskCategory taskCategory, MapArea taskBounds, int... triggers) {
        this(uuid, name, description, taskDifficulty, taskArea, taskCategory, 1, taskBounds, triggers);
    }

    public Task(int uuid, String name, String description, TaskDifficulty taskDifficulty, TaskArea taskArea, TaskCategory taskCategory, int goal, int... triggers) {
        this(uuid, name, description, taskDifficulty, taskArea, taskCategory, goal, null, triggers);
    }

    public Task(int uuid, String name, TaskCategory taskCategory, TaskArea taskArea, TaskDifficulty taskDifficulty, String description, int... triggers) {
        this(uuid, name, description, taskDifficulty, taskArea, taskCategory, 1, null, triggers);
    }
}

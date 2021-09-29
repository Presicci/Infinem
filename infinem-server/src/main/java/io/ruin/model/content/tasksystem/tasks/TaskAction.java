package io.ruin.model.content.tasksystem.tasks;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/28/2021
 */
public interface TaskAction {

    void accept(int amount, int triggers, String bounds);
}

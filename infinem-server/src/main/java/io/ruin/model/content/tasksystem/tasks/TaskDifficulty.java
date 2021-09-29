package io.ruin.model.content.tasksystem.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
@AllArgsConstructor
@Getter
public enum TaskDifficulty {
    EASY(10), MEDIUM(50), HARD(100), ELITE(250), MASTER(500);

    private final int points;
}

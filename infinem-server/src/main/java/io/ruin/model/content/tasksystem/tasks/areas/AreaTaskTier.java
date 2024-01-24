package io.ruin.model.content.tasksystem.tasks.areas;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/7/2023
 */
@AllArgsConstructor
public enum AreaTaskTier {
    EASY(250),
    MEDIUM(600),
    HARD(1000),
    ELITE(1500);

    @Getter private final int pointThreshold;
}

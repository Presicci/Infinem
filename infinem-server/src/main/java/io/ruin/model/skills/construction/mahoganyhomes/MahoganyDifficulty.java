package io.ruin.model.skills.construction.mahoganyhomes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MahoganyDifficulty {
    BEGINNER(2, 500, 2, 3, "wooden"),
    NOVICE(3, 1250, 1, 2, "oak"),
    ADEPT(4, 2250, 0, 2, "teak"),
    EXPERT(5, 2750, 0, 1, "mahogany");

    private final int pointReward;
    private final double experienceReward;
    private final int minRemovedObjects, maxRemovedObjects;
    private final String plankName;
}

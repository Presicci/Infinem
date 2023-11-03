package io.ruin.model.content.tasksystem.tasks.areas.rewards;

import io.ruin.model.content.tasksystem.tasks.areas.AreaTaskTier;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/31/2023
 */
public enum KaramjaReward {
    KARAMJA_GLOVES_1(AreaTaskTier.EASY, "Unlocks the Karamja Gloves 1",
            "Better deals in shops on Karamja when worn"),
    KARAMJA_GLOVES_2(AreaTaskTier.MEDIUM, "Unlocks the Karamja Gloves 2",
            "")



    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    KaramjaReward(AreaTaskTier tier, String description, String... additionalDescription) {
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }
}

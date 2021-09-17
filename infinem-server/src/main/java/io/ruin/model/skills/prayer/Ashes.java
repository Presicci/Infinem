package io.ruin.model.skills.prayer;

import io.ruin.model.entity.player.PlayerCounter;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/17/2021
 */
@AllArgsConstructor
public enum Ashes {
    FIENDISH(-1, 10, PlayerCounter.FIENDISH_ASHES_BURIED),
    VILE(-1, 25, PlayerCounter.VILE_ASHES_BURIED),
    MALICIOUS(-1, 65, PlayerCounter.MALICIOUS_ASHES_BURIED),
    ABYSSAL(-1, 85, PlayerCounter.ABYSSAL_ASHES_BURIED),
    INFERNAL(-1, 110, PlayerCounter.INFERNAL_ASHES_BURIED);

    private final int itemId;
    private final double experience;
    private final PlayerCounter playercounter;
}

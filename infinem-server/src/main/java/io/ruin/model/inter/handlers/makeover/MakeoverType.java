package io.ruin.model.inter.handlers.makeover;

import io.ruin.model.entity.player.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/19/2024
 */
@Getter
@AllArgsConstructor
public enum MakeoverType {
    HAIR(0, Style.HAIR, 496),
    FACIAL_HAIR(0, Style.JAW, 2630),
    BLANK(-1, null, -1),
    TOP(1, Style.TORSO, 5495),
    ARMS(1, Style.ARMS, 5496),
    LEGS(2, Style.LEGS, 5497),
    FEET(3, Style.FEET, 5498),
    HANDS(-1, Style.HANDS, 5499);

    private final int colorIndex;
    private final Style style;
    private final int enumId;
}

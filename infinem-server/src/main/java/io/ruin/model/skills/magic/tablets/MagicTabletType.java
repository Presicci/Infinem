package io.ruin.model.skills.magic.tablets;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2024
 */
@Getter
@AllArgsConstructor
public enum MagicTabletType {
    MODERN(0, 5271),
    ANCIENT(1, 3256),
    LUNAR(2, 3253),
    ARCEUUS(3, 2628);

    private final int vbIndex;
    private final int tabletEnum;

    public static MagicTabletType getType(int index) {
        for (MagicTabletType type : values()) {
            if (type.vbIndex == index) return type;
        }
        return null;
    }
}

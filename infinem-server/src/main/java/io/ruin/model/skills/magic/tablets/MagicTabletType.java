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
    ANCIENT(1),
    LUNAR(2),
    ARCEUUS(3);

    private final int vbIndex;

    public static MagicTabletType getType(int index) {
        for (MagicTabletType type : values()) {
            if (type.vbIndex == index) return type;
        }
        return null;
    }
}

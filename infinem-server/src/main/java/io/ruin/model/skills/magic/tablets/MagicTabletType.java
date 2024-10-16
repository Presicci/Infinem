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
    OAK(0, 4108),
    OAK_EAGLE(0, 4178),
    OAK_DEMON(0, 4179),
    TEAK_EAGLE(0, 5267),
    TEAK_DEMON(0, 5268),
    MAHOGANY_EAGLE(0, 5269),
    MAHOGANY_DEMON(0, 5270),
    MARBLE_LECTERN(0, 5271),
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

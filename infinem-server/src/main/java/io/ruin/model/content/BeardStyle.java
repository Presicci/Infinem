package io.ruin.model.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/14/2023
 */
@Getter
@RequiredArgsConstructor
public enum BeardStyle {

    CLEAN_SHAVEN(0, 14),
    GOATEE(1, 10),
    LONG(2, 11),
    MEDIUM(3, 12),
    SMALL_MOUSTACHE(4, 13),
    SHORT(5, 15),
    POINTY(6, 16),
    SPLIT(7, 17),
    HANDLEBAR(8, 111),
    MUTTON(9, 112),
    FULL_MOTTON(10, 113),
    BIG_MOUSTACHE(11, 114),
    WAXED_MOUSTACHE(12, 115),
    DALI(13, 116),
    VIZIER(14, 117);

    private final int slotId, id;
    private static final BeardStyle[] VALUES = values();
    private static final Map<Integer, Integer> STYLES = new HashMap<>(VALUES.length);

    static {
        for (val style : VALUES) {
            STYLES.put(style.slotId, style.id);
        }
    }

    public static int getStyle(final int slotId) {
        return STYLES.get(slotId);
    }

    public static int getSlot(final int style) {
        for (BeardStyle s : values()) {
            if (s.id == style)
                return s.slotId;
        }
        return -1;
    }
}

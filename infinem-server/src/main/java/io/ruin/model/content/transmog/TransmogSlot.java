package io.ruin.model.content.transmog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/4/2024
 */
@Getter
@AllArgsConstructor
public enum TransmogSlot {
    HAT(0),
    CAPE(1),
    AMULET(2),
    WEAPON(3),
    CHEST(4),
    SHIELD(5),
    LEGS(7),
    HANDS(9),
    FEET(10),
    RING(12),
    AMMO(13);

    private final int equipmentSlot;

    public int getComponent() {
        return 10 + ordinal();
    }

    private static Map<Integer, TransmogSlot> SLOT_BY_ID = new HashMap<>();

    public static TransmogSlot getSlot(int slotId) {
        return SLOT_BY_ID.getOrDefault(slotId, null);
    }

    static {
        for (TransmogSlot slot : values()) {
            SLOT_BY_ID.put(slot.getEquipmentSlot(), slot);
        }
    }
}

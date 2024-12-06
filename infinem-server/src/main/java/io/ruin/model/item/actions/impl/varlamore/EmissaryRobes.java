package io.ruin.model.item.actions.impl.varlamore;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;

import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/5/2024
 */
public class EmissaryRobes {

    private static final int[] OUTFIT_PIECES = {
            29868,  // Hood
            29870,  // Top
            29872,  // Bottoms
            29874   // Sandals
    };
    private static final int[] EMISSARY_MEMBERS = {
            13744, 13745, 13746, 13747, 13748, 13749, 13750, 13751,  // Ascended
            13722, 13723, 13724, 13725, 13732, 13733, 13734, 13735, 13736, 13737, 13763, 13765, // Acolyte
            13738, 13739, 13740, 13741, 13742, 13743, 13764 // Chosen
    };

    private static Predicate<Entity> isProtected() {
        return entity -> {
            if (!entity.isPlayer()) return false;
            Equipment equipment = entity.player.getEquipment();
            for (int id : OUTFIT_PIECES) {
                if (!equipment.hasId(id)) return false;
            }
            return true;
        };
    }

    static {
        SpawnListener.register(EMISSARY_MEMBERS, npc -> {
            npc.aggressionImmunity = isProtected();
        });
    }
}

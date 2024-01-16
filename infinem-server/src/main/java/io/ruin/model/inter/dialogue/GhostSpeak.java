package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
public class GhostSpeak {

    private static final List<Integer> MORY_LEGS = Arrays.asList(Items.MORYTANIA_LEGS_2, Items.MORYTANIA_LEGS_3, Items.MORYTANIA_LEGS_4);

    public static boolean canSpeak(Player player) {
        Equipment equipment = player.getEquipment();
        return equipment.getId(Equipment.SLOT_AMULET) == Items.GHOSTSPEAK_AMULET || MORY_LEGS.contains(equipment.getId(Equipment.SLOT_LEGS));
    }
}

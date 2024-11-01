package io.ruin.model.activities.duelarena;

import io.ruin.api.utils.ListUtils;
import io.ruin.model.entity.player.Player;

import java.util.List;

public enum DuelRule {

    NO_RANGED("No Ranged", 4, 30),
    NO_MELEE("No Melee", 5, 31),
    NO_MAGIC("No Magic", 6, 32),
    NO_SPECIALS("No Special Attack", 13, 33),
    FUN_WEAPONS("Fun weapons", 12, 34),
    NO_FORFEIT("Forfeit", 0, 35),
    NO_PRAYER("No Prayer", 9, 36),
    NO_DRINKS("No Drinks", 7, 37),
    NO_FOOD("No Food", 8, 38),
    NO_MOVEMENT("No Movement", 1, 39),
    OBSTACLES("Obstacles", 10, 60),//TODO REMOVE
    NO_WEAPON_SWITCH("No Weapon Switching", 2, 40),
    SHOW_INVENTORIES("Show Inventories", 3, 41),

    NO_HELMS("Disable Head Slot", 14, 48),
    NO_CAPES("Disable Back Slot", 15, 49),
    NO_AMULETS("Disable Neck Slot", 16, 50),
    NO_AMMO("Disable Ammo Slot", 27, 58),
    NO_WEAPON("Disable Right Hand Slot", 17, 51),
    NO_BODY("Disable Torso Slot", 18, 52),
    NO_SHIELD("Disable Left Hand Slot", 19, 53),
    NO_LEGS("Disable Leg Slot", 21, 54),
    NO_RING("Disable Ring Slot", 26, 57),
    NO_BOOTS("Disable Feet Slot", 24, 56),
    NO_GLOVES("Disable Hand Slot", 23, 55);

    public final String message;

    public final int bitValue, bitPos;

    private final List<Integer> childIds;

    DuelRule(String message, int bitPos, Integer... childIds) {
        this.message = message;
        this.bitPos = bitPos;
        this.bitValue = 1 << bitPos;
        this.childIds = ListUtils.toList(childIds);
    }

    public boolean isToggled(Player player) {
        Duel duel = player.getDuel();
        return duel.stage >= 3 && duel.isToggled(this);
    }

    public static DuelRule get(int childId) {
        for(DuelRule rule : values()) {
            if(rule.childIds.contains(childId))
                return rule;
        }
        return null;
    }

}

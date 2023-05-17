package io.ruin.model.combat;

public enum HitType {

    /*
    case 12: return BLOCK_ME;
    case 13: return BLOCK_OTHER;
    case 16: return DAMAGE_ME;
    case 17: return DAMAGE_OTHER;
    case 2: return POISON;
    case 4: return DISEASE;
    case 5: return VENOM;
    case 6: return HEAL;
    case 18: return DAMAGE_ME_CYAN;
    case 19: return DAMAGE_OTHER_CYAN;
    case 20: return DAMAGE_ME_ORANGE;
    case 21: return DAMAGE_OTHER_ORANGE;
    case 22: return DAMAGE_ME_YELLOW;
    case 23: return DAMAGE_OTHER_YELLOW;
    case 24: return DAMAGE_ME_WHITE;
    case 25: return DAMAGE_OTHER_WHITE;
     */

    CORRUPTION(false, 0),
    EMPTY_1(false, 1),
    POISON(true, 2),
    EMPTY_3(false, 3),
    DISEASE(false, 4),
    VENOM(true, 5),
    HEAL(false, 6),
    EMPTY_7(false, 7),
    EMPTY_8(false, 8),
    EMPTY_9(false, 9),
    EMPTY_10(false, 10),
    EMPTY_11(false, 11),
    BLOCKED(true, 12),
    BLOCK_OTHER(false, 13),
    EMPTY_14(false, 14),
    EMPTY_15(false, 15),
    DAMAGE(true, 16),
    DAMAGE_OTHER(false, 17),
    DAMAGE_SHIELD_ME(true, 18),
    DAMAGE_SHIELD_OTHER(false, 19),
    DAMAGE_ZALCANO_ME(false, 20),
    DAMAGE_ZALCANO_OTHER(false, 21),
    HEAL_TOTEM_ME(true, 22),
    HEAL_TOTEM_OTHER(false, 23),
    DAMAGE_TOTEM_ME(true, 24),
    DAMAGE_TOTEM_OTHER(false, 25);

    public final boolean resetActions;
    public final int activeId;

    HitType(boolean resetActions, int activeId) {
        this.resetActions = resetActions;
        this.activeId = activeId;
    }

}
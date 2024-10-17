package io.ruin.model.combat;

public enum HitType {
    CORRUPTION(false, 0),
    EMPTY_1(false, 1),
    POISON(true, 2),
    YELLOW(false, 3),
    DISEASE(false, 4),
    VENOM(true, 5),
    HEAL(false, 6),
    EMPTY_7(false, 7),
    EMPTY_8(false, 8),
    EMPTY_9(false, 9),
    EMPTY_10(false, 10),
    EMPTY_11(false, 11),
    BLOCKED(true, 12, 13),
    BLOCK_OTHER(false, 13),
    EMPTY_14(false, 14),
    EMPTY_15(false, 15),
    DAMAGE(true, 16, 17),
    DAMAGE_OTHER(false, 17),
    DAMAGE_SHIELD_ME(true, 18, 19),
    DAMAGE_SHIELD_OTHER(false, 19),
    DAMAGE_ZALCANO_ME(false, 20, 21),
    DAMAGE_ZALCANO_OTHER(false, 21),
    HEAL_TOTEM_ME(true, 22, 23),
    HEAL_TOTEM_OTHER(false, 23),
    DAMAGE_TOTEM_ME(true, 24, 25),
    DAMAGE_TOTEM_OTHER(false, 25);

    public final boolean resetActions;
    public final int activeId, tintedId;

    HitType(boolean resetActions, int activeId) {
        this.resetActions = resetActions;
        this.activeId = activeId;
        this.tintedId = activeId;
    }

    HitType(boolean resetActions, int activeId, int tintedId) {
        this.resetActions = resetActions;
        this.activeId = activeId;
        this.tintedId = tintedId;
    }
}
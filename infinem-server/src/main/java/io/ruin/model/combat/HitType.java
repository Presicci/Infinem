package io.ruin.model.combat;

public enum HitType {

    // 0 = blocked
    // 1 = damage
    // 2 = Poison
    // 3 = Disease
    // 4 = Nightmare totem being healed
    // 5 = Nightmare totem being damaged
    // 6 = Verzik and Nightmare's shields, tempeross
    // 7 = NOTHING
    // 8 = NOTHING
    // 9 = Tinted block
    // 10 = Tinted hit
    // 11 = Venom
    // 12 = Disease
    // 13 = Tinted Nightmare totem being healed
    // 14 = Zalcano stone armour
    // 15 = NPC healing

    BLOCKED(true),
    DAMAGE(true),
    POISON(false),
    DISEASE1(false),
    TOTEM_HEAL(false),
    TOTEM_DAMAGE(false),
    SHIELD(false),
    FILLER(false),
    FILLER1(false),
    BLOCKED_TINT(false),
    DAMAGE_TINT(false),
    VENOM(false),
    DISEASE2(false),
    TOTEM_HEAL_TINT(false),
    ZALCANO(false),
    HEAL(false);

    public final boolean resetActions;

    HitType(boolean resetActions) {
        this.resetActions = resetActions;
    }

}
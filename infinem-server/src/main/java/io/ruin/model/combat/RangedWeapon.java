package io.ruin.model.combat;

import io.ruin.model.map.Projectile;

import java.util.Arrays;

public enum RangedWeapon {
    /**
     * Knifes
     */
    BRONZE_KNIFE(DamageType.LIGHT, new RangedData(219, Projectile.thrown(212, 11))),
    IRON_KNIFE(DamageType.LIGHT, new RangedData(220, Projectile.thrown(213, 11))),
    STEEL_KNIFE(DamageType.LIGHT, new RangedData(221, Projectile.thrown(214, 11))),
    BLACK_KNIFE(DamageType.LIGHT, new RangedData(222, Projectile.thrown(215, 11))),
    MITHRIL_KNIFE(DamageType.LIGHT, new RangedData(223, Projectile.thrown(216, 11))),
    ADAMANT_KNIFE(DamageType.LIGHT, new RangedData(224, Projectile.thrown(217, 11))),
    RUNE_KNIFE(DamageType.LIGHT, new RangedData(225, Projectile.thrown(218, 11))),
    DRAGON_KNIFE(DamageType.LIGHT, new RangedData(-1, Projectile.thrown(28, 11))),
    DRAGON_KNIFE_P(DamageType.LIGHT, new RangedData(-1, Projectile.thrown(697, 11))),
    /**
     * Darts
     */
    BRONZE_DART(DamageType.LIGHT, new RangedData(232, Projectile.thrown(226, 11))),
    IRON_DART(DamageType.LIGHT, new RangedData(233, Projectile.thrown(227, 11))),
    STEEL_DART(DamageType.LIGHT, new RangedData(234, Projectile.thrown(228, 11))),
    BLACK_DART(DamageType.LIGHT, new RangedData(273, Projectile.thrown(34, 11))),
    MITHRIL_DART(DamageType.LIGHT, new RangedData(235, Projectile.thrown(229, 11))),
    ADAMANT_DART(DamageType.LIGHT, new RangedData(236, Projectile.thrown(230, 11))),
    RUNE_DART(DamageType.LIGHT, new RangedData(237, Projectile.thrown(231, 11))),
    AMETHYST_DART(DamageType.LIGHT, new RangedData(1937, Projectile.thrown(1936, 105))),
    DRAGON_DART(DamageType.LIGHT, new RangedData(1123, Projectile.thrown(1122, 105))),
    /**
     * Throwing axes
     */
    BRONZE_THROWING_AXE(DamageType.LIGHT, new RangedData(43, Projectile.thrown(36, 11))),
    IRON_THROWING_AXE(DamageType.LIGHT, new RangedData(42, Projectile.thrown(35, 11))),
    STEEL_THROWING_AXE(DamageType.LIGHT, new RangedData(44, Projectile.thrown(37, 11))),
    BLACK_THROWING_AXE(DamageType.LIGHT, new RangedData(47, Projectile.thrown(40, 11))),
    MITHRIL_THROWING_AXE(DamageType.LIGHT, new RangedData(45, Projectile.thrown(38, 11))),
    ADAMANT_THROWING_AXE(DamageType.LIGHT, new RangedData(46, Projectile.thrown(39, 11))),
    RUNE_THROWING_AXE(DamageType.LIGHT, new RangedData(48, Projectile.thrown(41, 11))),
    DRAGON_THROWING_AXE(DamageType.LIGHT, new RangedData(1320, Projectile.thrown(1319, 11))),
    MORRIGANS_THROWING_AXE(DamageType.LIGHT, new RangedData(1624, Projectile.thrown(1623, 11))),
    /**
     * Thrown (misc)
     */
    OBBY_RING(DamageType.LIGHT, new RangedData(Projectile.thrown(442, 11))),
    CHINCHOMPA(DamageType.HEAVY, new RangedData(Projectile.thrown(908, 11))),
    RED_CHINCHOMPA(DamageType.HEAVY, new RangedData(Projectile.thrown(909, 11))),
    BLACK_CHINCHOMPA(DamageType.HEAVY, new RangedData(Projectile.thrown(1272, 11))),
    MORRIGANS_JAVELIN(DamageType.LIGHT, new RangedData(1619, Projectile.thrown(1620, 11))),
    /**
     * Generated
     */
    STARTER_BOW(DamageType.STANDARD, new RangedData(19, 1104, Projectile.arrow(10))),
    CRYSTAL_BOW(DamageType.STANDARD, new RangedData(250, Projectile.arrow(249))),
    CRAWS_BOW(DamageType.STANDARD, new RangedData(1611, Projectile.arrow(1574))),
    WEBWEAVER_BOW(DamageType.STANDARD, new RangedData(2283, Projectile.arrow(2282))),
    /**
     * Fired (ammo)
     */
    NORMAL_BOW(
            DamageType.STANDARD,
            RangedAmmo.BRONZE_ARROW,
            RangedAmmo.IRON_ARROW,
            RangedAmmo.STEEL_ARROW,
            RangedAmmo.BROAD_ARROW,
            RangedAmmo.MITHRIL_ARROW,
            RangedAmmo.ADAMANT_ARROW,
            RangedAmmo.RUNE_ARROW,
            RangedAmmo.ICE_ARROW,
            RangedAmmo.AMETHYST_ARROW,
            RangedAmmo.DRAGON_ARROW
    ),
    DARK_BOW(
            DamageType.STANDARD,
            RangedAmmo.BRONZE_ARROW,
            RangedAmmo.IRON_ARROW,
            RangedAmmo.STEEL_ARROW,
            RangedAmmo.BROAD_ARROW,
            RangedAmmo.MITHRIL_ARROW,
            RangedAmmo.ADAMANT_ARROW,
            RangedAmmo.RUNE_ARROW,
            RangedAmmo.ICE_ARROW,
            RangedAmmo.AMETHYST_ARROW,
            RangedAmmo.DRAGON_ARROW
    ),
    BALLISTA(
            DamageType.HEAVY,
            RangedAmmo.BRONZE_JAVELIN,
            RangedAmmo.IRON_JAVELIN,
            RangedAmmo.STEEL_JAVELIN,
            RangedAmmo.MITHRIL_JAVELIN,
            RangedAmmo.ADAMANT_JAVELIN,
            RangedAmmo.RUNE_JAVELIN,
            RangedAmmo.AMETHYST_JAVELIN,
            RangedAmmo.DRAGON_JAVELIN
    ),
    CROSSBOW(
            DamageType.HEAVY,
            Arrays.stream(RangedAmmo.values()).filter(a -> a.name().toLowerCase().contains("bolt")).toArray(RangedAmmo[]::new)
    ),
    TWISTED_BOW(
            DamageType.STANDARD,
            RangedAmmo.BRONZE_ARROW,
            RangedAmmo.IRON_ARROW,
            RangedAmmo.STEEL_ARROW,
            RangedAmmo.BROAD_ARROW,
            RangedAmmo.MITHRIL_ARROW,
            RangedAmmo.ADAMANT_ARROW,
            RangedAmmo.RUNE_ARROW,
            RangedAmmo.ICE_ARROW,
            RangedAmmo.AMETHYST_ARROW,
            RangedAmmo.DRAGON_ARROW,
            RangedAmmo.CORRUPT_ARROW
    ),
    KARILS_CROSSBOW(DamageType.HEAVY, RangedAmmo.BOLT_RACK),
    HUNTERS_CROSSBOW(DamageType.HEAVY, RangedAmmo.KEBBIT_BOLTS, RangedAmmo.LONG_KEBBIT_BOLTS),
    TOXIC_BLOWPIPE(DamageType.LIGHT),
    CORRUPTED_JAVELIN(DamageType.LIGHT);

    public final RangedData data;
    public final boolean[] allowedAmmo;
    public final DamageType damageType;

    RangedWeapon(DamageType damageType) {
        this.data = null;
        this.allowedAmmo = null;
        this.damageType = damageType;
    }

    RangedWeapon(DamageType damageType, RangedData data) {
        this.data = data;
        this.allowedAmmo = null;
        this.damageType = damageType;
    }

    RangedWeapon(DamageType damageType, RangedAmmo... ammo) {
        this.data = null;
        this.allowedAmmo = new boolean[RangedAmmo.values().length];
        for(RangedAmmo a : ammo)
            this.allowedAmmo[a.ordinal()] = true;
        this.damageType = damageType;
    }

    public boolean allowAmmo(RangedAmmo ammo) {
        return allowedAmmo[ammo.ordinal()];
    }

    public enum DamageType {
        LIGHT, STANDARD, HEAVY
    }
}
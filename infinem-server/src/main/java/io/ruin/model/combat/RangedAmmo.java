package io.ruin.model.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.special.ranged.bolts.*;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public enum RangedAmmo {

    /**
     * Arrows
     */
    BRONZE_ARROW(new RangedData(19, 1104, Projectile.arrow(10))),
    IRON_ARROW(new RangedData(18, 1105, Projectile.arrow(9))),
    STEEL_ARROW(new RangedData(20, 1106, Projectile.arrow(11))),
    BROAD_ARROW(new RangedData(20, 1106, Projectile.arrow(11))),
    MITHRIL_ARROW(new RangedData(21, 1107, Projectile.arrow(12))),
    ADAMANT_ARROW(new RangedData(22, 1108, Projectile.arrow(13))),
    RUNE_ARROW(new RangedData(24, 1109, Projectile.arrow(15))),
    ICE_ARROW(new RangedData(25, 1110, Projectile.arrow(16))),
    AMETHYST_ARROW(new RangedData(1385, 1383, Projectile.arrow(1384))),
    DRAGON_ARROW(new RangedData(1116, 1111, Projectile.arrow(1120))),
    CORRUPT_ARROW(new RangedData(5020, 5021, Projectile.arrow(5022))),

    /**
     * Bolts
     */
    BRONZE_BOLTS(new RangedData(Projectile.BOLT)),
    IRON_BOLTS(new RangedData(Projectile.BOLT)),
    STEEL_BOLTS(new RangedData(Projectile.BOLT)),
    MITHRIL_BOLTS(new RangedData(Projectile.BOLT)),
    ADAMANT_BOLTS(new RangedData(Projectile.BOLT)),
    BROAD_BOLTS(new RangedData(Projectile.BOLT)),
    RUNITE_BOLTS(new RangedData(Projectile.BOLT)),
    AMETHYST_BROAD_BOLTS(new RangedData(Projectile.BOLT)),
    BLURITE_BOLTS(new RangedData(Projectile.BOLT)),
    BONE_BOLTS(new RangedData(Projectile.bolt(696))),
    SILVER_BOLTS(new RangedData(Projectile.BOLT)),
    OPAL_BOLTS(new RangedData(Projectile.BOLT), new OpalBoltEffect()),
    JADE_BOLTS(new RangedData(Projectile.BOLT), new JadeBoltEffect()),
    PEARL_BOLTS(new RangedData(Projectile.BOLT), new PearlBoltEffect()),
    TOPAZ_BOLTS(new RangedData(Projectile.BOLT), new TopazBoltEffect()),
    SAPPHIRE_BOLTS(new RangedData(Projectile.BOLT), new SapphireBoltEffect()),
    EMERALD_BOLTS(new RangedData(Projectile.BOLT), new EmeraldBoltEffect()),
    RUBY_BOLTS(new RangedData(Projectile.BOLT), new RubyBoltEffect()),
    DIAMOND_BOLTS(new RangedData(Projectile.BOLT), new DiamondBoltEffect()),
    DRAGONSTONE_BOLTS(new RangedData(Projectile.BOLT), new DragonBoltEffect()),
    ONYX_BOLTS(new RangedData(Projectile.BOLT), new OnyxBoltEffect()),
    BOLT_RACK(new RangedData(true, Projectile.BOLT)),
    KEBBIT_BOLTS(new RangedData(Projectile.BOLT)),
    LONG_KEBBIT_BOLTS(new RangedData(Projectile.BOLT)),

    /**
     * Dragon bolts
     */
    DRAGON_BOLTS(new RangedData(Projectile.DRAGON_BOLT)),
    DRAGON_OPAL_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new OpalBoltEffect()),
    DRAGON_JADE_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new JadeBoltEffect()),
    DRAGON_PEARL_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new PearlBoltEffect()),
    DRAGON_TOPAZ_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new TopazBoltEffect()),
    DRAGON_SAPPHIRE_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new SapphireBoltEffect()),
    DRAGON_EMERALD_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new EmeraldBoltEffect()),
    DRAGON_RUBY_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new RubyBoltEffect()),
    DRAGON_DIAMOND_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new DiamondBoltEffect()),
    DRAGON_DRAGONSTONE_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new DragonBoltEffect()),
    DRAGON_ONYX_BOLTS(new RangedData(Projectile.DRAGON_BOLT), new OnyxBoltEffect()),

    /**
     * Javelins
     */
    BRONZE_JAVELIN(new RangedData(true, Projectile.javelin(200))),
    IRON_JAVELIN(new RangedData(true, Projectile.javelin(201))),
    STEEL_JAVELIN(new RangedData(true, Projectile.javelin(202))),
    MITHRIL_JAVELIN(new RangedData(true, Projectile.javelin(203))),
    ADAMANT_JAVELIN(new RangedData(true, Projectile.javelin(204))),
    RUNE_JAVELIN(new RangedData(true, Projectile.javelin(205))),
    AMETHYST_JAVELIN(new RangedData(true, Projectile.javelin(1386))),
    DRAGON_JAVELIN(new RangedData(true, Projectile.javelin(1301)));

    public final RangedData data;

    public final BiFunction<Entity, Hit, Boolean> effect;

    RangedAmmo(RangedData data) {
        this(data, null);
    }

    RangedAmmo(RangedData data, BiFunction<Entity, Hit, Boolean> effect) {
        this.data = data;
        this.effect = effect;
    }

    private static final List<BoltEffect> VALID_BOLT_EFFECTS = Arrays.asList(
            new DiamondBoltEffect(true), new DragonBoltEffect(true), new EmeraldBoltEffect(true),
            new OnyxBoltEffect(true), new OpalBoltEffect(true), new PearlBoltEffect(true),
            new RubyBoltEffect(true)
    );

    public static boolean procDeadeyeBoltEffect(Entity target, Hit hit) {
        if (!Random.rollDie(10)) return false;
        return Random.get(VALID_BOLT_EFFECTS).apply(target, hit);
    }
}
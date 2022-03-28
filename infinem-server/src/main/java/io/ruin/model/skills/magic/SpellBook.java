package io.ruin.model.skills.magic;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.BountyTeleport;
import io.ruin.model.skills.magic.spells.HomeTeleport;
import io.ruin.model.skills.magic.spells.TodoSpell;
import io.ruin.model.skills.magic.spells.ancient.*;
import io.ruin.model.skills.magic.spells.arceuus.ArceuusTeleport;
import io.ruin.model.skills.magic.spells.arceuus.Reanimate;
import io.ruin.model.skills.magic.spells.lunar.*;
import io.ruin.model.skills.magic.spells.modern.*;

public enum SpellBook {

    MODERN(
            HomeTeleport.MODERN,
            new WindStrike(),
            new Confuse(),
            new EnchantCrossbowBolt(),
            new WaterStrike(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.ONE),
            new EarthStrike(),
            new Weaken(),
            new FireStrike(),
            new BonesBananas(),
            new WindBolt(),
            new Curse(),
            new Bind(),
            new Alchemy(true),
            new WaterBolt(),
            ModernTeleport.VARROCK_TELEPORT,
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.TWO),
            new EarthBolt(),
            ModernTeleport.LUMBRIDGE_TELEPORT,
            new TodoSpell("Telekinetic Grab"),
            new FireBolt(),
            ModernTeleport.FALADOR_TELEPORT,
            CrumbleUndead.INSTANCE,
            new TeleportToHouse(),
            new WindBlast(),
            new SuperheatItem(),
            ModernTeleport.CAMELOT_TELEPORT,
            new WaterBlast(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.THREE),
            new IbanBlast(),
            new Snare(),
            new MagicDart(),
            ModernTeleport.ARDOUGNE_TELEPORT,
            new EarthBlast(),
            new Alchemy(false),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_WATER),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.FOUR),
            ModernTeleport.WATCHTOWER_TELEPORT,
            new FireBlast(),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_EARTH),
            new BonesPeaches(),
            new SaradominStrike(),
            new ClawsOfGuthix(),
            new FlamesOfZamorak(),
            ModernTeleport.TROLLHEIM_TELEPORT,
            new WindWave(),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_FIRE),
            ModernTeleport.APE_ATOLL_TELEPORT,
            new WaterWave(),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_AIR),
            new Vulnerability(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.FIVE),
            ModernTeleport.KOUREND_TELEPORT,
            new EarthWave(),
            new Enfeeble(),
            Teleother.LUMBRIDGE.toSpell(),
            new FireWave(),
            new Entangle(),
            new Stun(),
            new Charge(),
            new WindSurge(),
            Teleother.FALADOR.toSpell(),
            new WaterSurge(),
            new Teleblock(),
            new BountyTeleport(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.SIX),
            Teleother.CAMELOT.toSpell(),
            new EarthSurge(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.SEVEN),
            new FireSurge()
    ),
    ANCIENT(
            new IceRush(),
            new IceBlitz(),
            new IceBurst(),
            new IceBarrage(),
            new BloodRush(),
            new BloodBlitz(),
            new BloodBurst(),
            new BloodBarrage(),
            new SmokeRush(),
            new SmokeBlitz(),
            new SmokeBurst(),
            new SmokeBarrage(),
            ShadowRush.INSTANCE,
            ShadowBlitz.INSTANCE,
            ShadowBurst.INSTANCE,
            ShadowBarrage.INSTANCE,

            new AncientTeleport(54, 64.0, new Bounds(3098, 9881, 3095, 9884, 0), Rune.LAW.toItem(2), Rune.FIRE.toItem(1), Rune.AIR.toItem(1)),
            new AncientTeleport(60, 70.0, new Bounds(3349, 3345, 3346, 3348, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(1)),
            new AncientTeleport(66, 76.0, new Bounds(3490, 3471, 3493, 3472, 0), Rune.LAW.toItem(2), Rune.BLOOD.toItem(1)),
            new AncientTeleport(72, 82.0, new Bounds(3013, 3500, 3015, 3501, 0), Rune.LAW.toItem(2), Rune.WATER.toItem(4)),
            new AncientTeleport(78, 88.0, new Bounds(2965, 3964, 2969, 3697, 0), Rune.LAW.toItem(2), Rune.FIRE.toItem(3), Rune.AIR.toItem(2)),
            new AncientTeleport(84, 94.0, new Bounds(3162, 3663, 3164, 3665, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2)),
            new AncientTeleport(90, 100.0, new Bounds(3287, 3886, 3288, 3887, 0), Rune.LAW.toItem(2), Rune.BLOOD.toItem(2)),
            new AncientTeleport(96, 106.0, new Bounds(2970, 3871, 2975, 3875, 0), Rune.LAW.toItem(2), Rune.WATER.toItem(8)),
            HomeTeleport.ANCIENT
    ),
    LUNAR(
            HomeTeleport.LUNAR,
            new BakePie(),
            new CurePlant(),
            new MonsterExamine(),
            new TodoSpell("NPC Contact"),
            new CureOther(),
            new Humidify(),
            new LunarTeleport(69, 66, new Bounds(new Position(2113, 3915), 1)),
            TeleGroup.MOONCLAN.toSpell(),
            new CureMe(),
            new HunterKit(),
            new LunarTeleport(75, 76, new Bounds(new Position(2546, 3755), 1)),
            TeleGroup.WATERBIRTH.toSpell(),
            new TodoSpell("Cure Group"),
            new StatSpy(),
            new LunarTeleport(75, 76, new Bounds(new Position(2543, 3568), 1)),
            TeleGroup.BARBARIAN.toSpell(),
            new SuperglassMake(),
            new TanLeather(),
            new LunarTeleport(78, 80, new Bounds(new Position(2636, 3167), 1)),
            TeleGroup.KHAZARD.toSpell(),
            new TodoSpell("Dream"),
            new StringJewellery(),
            new TodoSpell("Stat Restore Pot Share"),
            new MagicImbue(),
            new FertileSoil(),
            new TodoSpell("Boost Potion Share"),
            new LunarTeleport(87, 92, new Bounds(new Position(2612, 3391), 1)),
            TeleGroup.FISHING_GUILD.toSpell(),
            new TodoSpell("Plank Make"),
            new LunarTeleport(87, 92, new Bounds(new Position(2802, 3449), 1)),
            TeleGroup.CATHERBY.toSpell(),
            new TodoSpell("Recharge Dragonstone"),
            new LunarTeleport(89, 96, new Bounds(new Position(2973, 3939), 1)),
            TeleGroup.ICE_PLATEAU.toSpell(),
            new TodoSpell("Energy Transfer"),
            new TodoSpell("Heal Other"),
            new TodoSpell("Vengeance Other"),
            new Vengeance(),
            new TodoSpell("Heal Group"),
            new TodoSpell("Spellbook Swap"),
            new TodoSpell("Geomancy"),
            new SpinFlaxSpell(),
            new LunarTeleport(71, 69, new Bounds(new Position(2468, 3247), 1))  // TODO Add warning message
    ),
    ARCEUUS(
            HomeTeleport.ARCEUUS,
            new Reanimate(Reanimate.Monster.GOBLIN),
            new ArceuusTeleport(6, 10.0, new Bounds(3241, 3195, 3242, 3196, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(2)),
            new Reanimate(Reanimate.Monster.MONKEY),
            new Reanimate(Reanimate.Monster.IMP),
            new Reanimate(Reanimate.Monster.MINOTAUR),
            new ArceuusTeleport(17, 16.0, new Bounds(3108, 3350, 3109, 3351, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.WATER.toItem(1)),
            new Reanimate(Reanimate.Monster.SCORPION),
            new Reanimate(Reanimate.Monster.BEAR),
            new Reanimate(Reanimate.Monster.UNICORN),
            new Reanimate(Reanimate.Monster.DOG),
            new ArceuusTeleport(28, 22.0, new Bounds(2979, 3509, 2980, 3510, 0), Rune.LAW.toItem(1), Rune.MIND.toItem(2)),
            new Reanimate(Reanimate.Monster.CHAOS_DRUID),
            new ArceuusTeleport(34, 27.0, new Bounds(3084, 3489, 3088, 3492, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1)),
            new Reanimate(Reanimate.Monster.GIANT),
            new ArceuusTeleport(40, 30.0, new Bounds(3433, 3460, 3435, 3462, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(2)),
            new Reanimate(Reanimate.Monster.OGRE),
            new Reanimate(Reanimate.Monster.ELF),
            new Reanimate(Reanimate.Monster.TROLL),
            new ArceuusTeleport(48, 50.0, new Bounds(3547, 3528, 3549, 3529, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.SOUL.toItem(1)),
            new Reanimate(Reanimate.Monster.HORROR),
            new Reanimate(Reanimate.Monster.KALPHITE),
            new ArceuusTeleport(61, 68.0, new Bounds(2499, 3291, 2501, 3292, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2)),
            new Reanimate(Reanimate.Monster.DAGANNOTH),
            new Reanimate(Reanimate.Monster.BLOODVELD),
            new ArceuusTeleport(65, 74.0, new Bounds(3796, 2864, 3798, 2866, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1), Rune.NATURE.toItem(1)),
            new Reanimate(Reanimate.Monster.TZHAAR),
            new ArceuusTeleport(71, 82.0, new Bounds(2979, 3763, 2981, 3763, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1), Rune.BLOOD.toItem(1)),
            new Reanimate(Reanimate.Monster.DEMON),
            new Reanimate(Reanimate.Monster.AVIANSIE),
            new TodoSpell("Resurrect Crops"),
            new ArceuusTeleport(83, 90.0, new Bounds(3564, 3313, 3566, 3315, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2), Rune.BLOOD.toItem(1)),
            new Reanimate(Reanimate.Monster.ABYSSAL_CREATURE),
            new ArceuusTeleport(90, 100.0, new Bounds(2800, 9210, 2801, 9211, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2), Rune.BLOOD.toItem(2)),
            new Reanimate(Reanimate.Monster.DRAGON)
    );

    public final Spell[] spells;

    public final String name;

    public int spellIdOffset;

    SpellBook(Spell... spells) {
        this.spells = spells;
        this.name = name().toLowerCase();
    }

    public void setActive(Player player) {
        TabCombat.updateAutocast(player, false);
        Config.MAGIC_BOOK.set(player, ordinal());
    }

    public boolean isActive(Player player) {
        return Config.MAGIC_BOOK.get(player) == ordinal();
    }

    static {
        SpellBook.MODERN.spellIdOffset = 4;
        SpellBook.ANCIENT.spellIdOffset = 74;
        SpellBook.LUNAR.spellIdOffset = 99;
        SpellBook.ARCEUUS.spellIdOffset = 143;
    }
}
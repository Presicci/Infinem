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
import io.ruin.model.skills.magic.spells.arceuus.*;
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
            new TodoSpell("Telekinetic Grab"),  // Handled in InterfaceOnGroundItemHandler.java
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
            new NPCContact(),
            new CureOther(),
            new Humidify(),
            new LunarTeleport(69, 66, new Bounds(new Position(2113, 3915), 1)),
            TeleGroup.MOONCLAN.toSpell(),
            new CureMe(),
            new HunterKit(),
            new LunarTeleport(75, 76, new Bounds(new Position(2546, 3755), 1)),
            TeleGroup.WATERBIRTH.toSpell(),
            new CureGroup(),
            new StatSpy(),
            new LunarTeleport(75, 76, new Bounds(new Position(2543, 3568), 1)),
            TeleGroup.BARBARIAN.toSpell(),
            new SuperglassMake(),
            new TanLeather(),
            new LunarTeleport(78, 80, new Bounds(new Position(2636, 3167), 1)),
            TeleGroup.KHAZARD.toSpell(),
            new Dream(),
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
            new VengeanceOther(),
            new Vengeance(),
            new HealGroup(),
            new TodoSpell("Spellbook Swap"),
            new Geomancy(),
            new SpinFlaxSpell(),
            new LunarTeleport(71, 69, new Bounds(new Position(2468, 3247), 1))  // TODO Add warning message
    ),
    ARCEUUS(
            HomeTeleport.ARCEUUS,
            new TodoSpell("Basic Reanimation"),
            new ArceuusTeleport(6, 10.0, new Bounds(1631, 3835, 1634, 3838, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(2)),  // Arceuus library
            new TodoSpell("Adept Reanimation"),
            new TodoSpell("Expert Reanimation"),
            new TodoSpell("Master Reanimation"),
            new ArceuusTeleport(17, 16.0, new Bounds(3108, 3350, 3109, 3351, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.WATER.toItem(1)),   // Draynor manor
            new TodoSpell("Empty Slot"),
            new ArceuusTeleport(28, 22.0, new Bounds(2979, 3509, 2980, 3510, 0), Rune.LAW.toItem(1), Rune.MIND.toItem(2)),  // Mind altar
            new ArceuusTeleport(34, 27.0, new Bounds(3084, 3489, 3088, 3492, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1)),  // TODO respawn
            new ArceuusTeleport(40, 30.0, new Bounds(3433, 3460, 3435, 3462, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(2)),  // Salve graveyard
            new ArceuusTeleport(48, 50.0, new Bounds(3547, 3528, 3549, 3529, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.SOUL.toItem(1)),    // Frankenstrains castle
            new ArceuusTeleport(61, 68.0, new Bounds(2499, 3291, 2501, 3292, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2)),  // West ardy
            new ArceuusTeleport(65, 74.0, new Bounds(3796, 2864, 3798, 2866, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1), Rune.NATURE.toItem(1)),   // Harmony island
            new ArceuusTeleport(71, 82.0, new Bounds(2979, 3763, 2981, 3763, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1), Rune.BLOOD.toItem(1)),    // Cemetary TODO WARNING
            new TodoSpell("Resurrect Crops"),
            new ArceuusTeleport(83, 90.0, new Bounds(3564, 3313, 3566, 3315, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2), Rune.BLOOD.toItem(1)),    // Barrows
            new ArceuusTeleport(90, 100.0, new Bounds(2768, 2702, 2769, 2704, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2), Rune.BLOOD.toItem(2)),   // Ape atoll
            new ArceuusTeleport(23, 19.0, new Bounds(1345, 3740, 1347, 3741, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.FIRE.toItem(1)),    // Battlefront
            new InferiorDemonbane(),
            new SuperiorDemonbane(),
            new DarkDemonbane(),
            new TodoSpell("Mark of Darkness"),
            new TodoSpell("Ghostly Grasp"),
            new TodoSpell("Skeletal Grasp"),
            new TodoSpell("Undead Grasp"),
            new TodoSpell("Ward of Arceuus"),
            new TodoSpell("Lesser Corruption"),
            new TodoSpell("Greater Corruption"),
            new TodoSpell("Demonic Offering"),
            new TodoSpell("Sinister Offering"),
            new TodoSpell("Degrime"),
            new TodoSpell("Shadow Veil"),
            new TodoSpell("Vile Vigour"),
            new TodoSpell("Dark Lure"),
            new TodoSpell("Death Charge"),
            new TodoSpell("Resurrect Lesser Ghost"),
            new TodoSpell("Resurrect Lesser Skeleton"),
            new TodoSpell("Resurrect Lesser Zombie"),
            new TodoSpell("Resurrect Superior Ghost"),
            new TodoSpell("Resurrect Superior Skeleton"),
            new TodoSpell("Resurrect Superior Zombie"),
            new TodoSpell("Resurrect Greater Ghost"),
            new TodoSpell("Resurrect Greater Skeleton"),
            new TodoSpell("Resurrect Greater Zombie")
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
        SpellBook.MODERN.spellIdOffset = 5;
        SpellBook.ANCIENT.spellIdOffset = 75;
        SpellBook.LUNAR.spellIdOffset = 100;
        SpellBook.ARCEUUS.spellIdOffset = 144;
    }
}
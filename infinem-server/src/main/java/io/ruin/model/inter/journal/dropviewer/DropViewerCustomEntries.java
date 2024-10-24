package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.combat.barrows.BarrowsRewards;
import io.ruin.model.activities.combat.bosses.hespori.HesporiLoot;
import io.ruin.model.activities.combat.bosses.slayer.sire.FontOfConsumption;
import io.ruin.model.activities.combat.pestcontrol.rewards.PCReward;
import io.ruin.model.activities.combat.raids.xeric.XericRewards;
import io.ruin.model.activities.shadesofmortton.FuneralPyre;
import io.ruin.model.activities.shadesofmortton.ShadeChest;
import io.ruin.model.activities.wilderness.bosses.callisto.CallistoDropTable;
import io.ruin.model.activities.wilderness.bosses.vetion.VetionDropTable;
import io.ruin.model.entity.npc.actions.misc.Wyson;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.BirdNest;
import io.ruin.model.item.actions.impl.ImplingJar;
import io.ruin.model.item.actions.impl.InfernalEel;
import io.ruin.model.item.actions.impl.WintertodtCrate;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.loot.RareDropTable;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.object.actions.impl.PyreSite;
import io.ruin.model.map.object.actions.impl.chests.*;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.construction.mahoganyhomes.MahoganySupplyCrate;
import io.ruin.model.skills.mining.Mining;
import io.ruin.model.skills.thieving.PickPocket;
import io.ruin.model.skills.thieving.Stall;
import io.ruin.model.skills.thieving.ThievableChests;
import io.ruin.model.skills.thieving.WallSafe;
import io.ruin.model.skills.woodcutting.Woodcutting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DropViewerCustomEntries {

    public static final List<DropViewerEntry> ENTRIES = Stream.of(
            new DropViewerEntry("Beginner Clue Casket", ClueType.BEGINNER.lootTable),
            new DropViewerEntry("Easy Clue Casket", ClueType.EASY.lootTable),
            new DropViewerEntry("Medium Clue Casket", ClueType.MEDIUM.lootTable),
            new DropViewerEntry("Hard Clue Casket", ClueType.HARD.lootTable),
            new DropViewerEntry("Elite Clue Casket", ClueType.ELITE.lootTable),
            new DropViewerEntry("Master Clue Casket", ClueType.MASTER.lootTable),
            new DropViewerEntry("Crystal Chest", CrystalChest.LOOT_TABLE),
            new DropViewerEntry("Elven Crystal Chest", ElvenCrystalChest.LOOT_TABLE),
            new DropViewerEntry("Brimstone Chest", BrimstoneChest.LOOT_TABLE),
            new DropViewerEntry("Dark Chest", DarkChest.LOOT_TABLE),
            new DropViewerEntry("Grubby Chest", false, GrubbyChest.MAIN, GrubbyChest.FOOD, GrubbyChest.POTION, GrubbyChest.TERTIARY),
            new DropViewerEntry("Larrans Chest (Big)", LarransChest.bigTable),
            new DropViewerEntry("Larrans Chest (Small)", LarransChest.smallTable),
            new DropViewerEntry("Baby Impling", ImplingJar.BABY_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Young Impling", ImplingJar.YOUNG_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Gourmet Impling", ImplingJar.GOURMET_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Earth Impling", ImplingJar.EARTH_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Essence Impling", ImplingJar.ESSENCE_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Eclectic Impling", ImplingJar.ECLECTIC_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Nature Impling", ImplingJar.NATURE_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Magpie Impling", ImplingJar.MAGPIE_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Ninja Impling", ImplingJar.NINJA_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Crystal Impling", ImplingJar.CRYSTAL_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Dragon Impling", ImplingJar.DRAGON_IMPLING_JAR.getLootTable()),
            new DropViewerEntry("Man (Pickpocket)", PickPocket.MAN.lootTable),
            new DropViewerEntry("Woman (Pickpocket)", PickPocket.WOMAN.lootTable),
            new DropViewerEntry("Farmer (Pickpocket)", PickPocket.FARMER.lootTable),
            new DropViewerEntry("H.A.M Member (Pickpocket)", PickPocket.HAM.lootTable),
            new DropViewerEntry("Warrior (Pickpocket)", PickPocket.WARRIOR.lootTable),
            new DropViewerEntry("Rogue (Pickpocket)", PickPocket.ROGUE.lootTable),
            new DropViewerEntry("Cave Goblin (Pickpocket)", PickPocket.CAVE_GOBLIN.lootTable),
            new DropViewerEntry("Master Farmer (Pickpocket)", PickPocket.MASTER_FARMER.lootTable),
            new DropViewerEntry("Guard (Pickpocket)", PickPocket.GUARD.lootTable),
            new DropViewerEntry("Bandit (Pickpocket)", PickPocket.BANDIT.lootTable),
            new DropViewerEntry("Knight (Pickpocket)", PickPocket.KNIGHT.lootTable),
            new DropViewerEntry("Watchman (Pickpocket)", PickPocket.WATCHMAN.lootTable),
            new DropViewerEntry("Paladin (Pickpocket)", PickPocket.PALADIN.lootTable),
            new DropViewerEntry("Gnome (Pickpocket)", PickPocket.GNOME.lootTable),
            new DropViewerEntry("Hero (Pickpocket)", PickPocket.HERO.lootTable),
            new DropViewerEntry("Vyre (Pickpocket)", PickPocket.VYRE.lootTable),
            new DropViewerEntry("Elf (Pickpocket)", PickPocket.ELF.lootTable),
            new DropViewerEntry("Elf (Pickpocket, Prif)", PickPocket.ELF_PRIF.lootTable),
            new DropViewerEntry("TzHaar-Hur (Pickpocket)", PickPocket.TZHAAR_HUR.lootTable),
            new DropViewerEntry("Vegetable Stall", Stall.VEGETABLE_STALL.lootTable),
            new DropViewerEntry("Baker's Stall", Stall.BAKERS_STALL.lootTable),
            new DropViewerEntry("Crafting Stall", Stall.CRAFTING_STALL.lootTable),
            new DropViewerEntry("Monkey Food Stall", Stall.MONKEY_FOOD_STALL.lootTable),
            new DropViewerEntry("Monkey General Stall", Stall.MONKEY_GENERAL_STALL.lootTable),
            new DropViewerEntry("Tea Stall", Stall.TEA_STALL.lootTable),
            new DropViewerEntry("Silk Stall", Stall.SILK_STALL.lootTable),
            new DropViewerEntry("Wine Stall", Stall.WINE_STALL.lootTable),
            new DropViewerEntry("Fruit Stall", Stall.FRUIT_STALL.lootTable),
            new DropViewerEntry("Seed Stall", Stall.SEED_STALL.lootTable),
            new DropViewerEntry("Fur Stall", Stall.FUR_STALL.lootTable),
            new DropViewerEntry("Fish Stall", Stall.FISH_STALL.lootTable),
            new DropViewerEntry("Crossbow Stall", Stall.CROSSBOW_STALL.lootTable),
            new DropViewerEntry("Silver Stall", Stall.SILVER_STALL.lootTable),
            new DropViewerEntry("Spice Stall", Stall.SPICE_STALL.lootTable),
            new DropViewerEntry("Magic Stall", Stall.MAGIC_STALL.lootTable),
            new DropViewerEntry("Scimitar Stall", Stall.SCIMITAR_STALL.lootTable),
            new DropViewerEntry("Gem Stall", Stall.GEM_STALL.lootTable),
            new DropViewerEntry("Ore Stall", Stall.ORE_STALL.lootTable),
            new DropViewerEntry("Isle of Souls Chest", ThievableChests.Chest.ISLE_OF_SOULS.lootTable),
            new DropViewerEntry("Ardougne Castle Chest", ThievableChests.Chest.ARDOUGNE_CASTLE.lootTable),
            new DropViewerEntry("Stone Chest", ThievableChests.Chest.STONE_CHEST.lootTable),
            new DropViewerEntry("Dorgesh-Kaan Avg Chest", ThievableChests.Chest.DORGESH_KAAN.lootTable),
            new DropViewerEntry("Dorgesh-Kaan Rich Chest", ThievableChests.Chest.DORG_RICH.lootTable),
            new DropViewerEntry("Rogue's Castle Chest", ThievableChests.Chest.ROGUES_CASTLE.lootTable),
            new DropViewerEntry("East Ardougne Chest", ThievableChests.Chest.NATURE.lootTable),
            new DropViewerEntry("Chaos Druid Tower Chest", ThievableChests.Chest.BLOOD.lootTable),
            new DropViewerEntry("Steel Arrowtip Chest", ThievableChests.Chest.ARROWTIP.lootTable),
            new DropViewerEntry("Wall Safe", WallSafe.LOOT_TABLE),
            new DropViewerEntry("Barrows (Per brother)", false, BarrowsRewards.MAIN_TABLE, BarrowsRewards.TERTIARY_TABLE,
                    new LootTable().addTable(1,
                            new LootItem(-1, 1, 2420 / 2),
                            new LootItem(Items.AHRIMS_HOOD, 1, 1),
                            new LootItem(Items.AHRIMS_ROBETOP, 1, 1),
                            new LootItem(Items.AHRIMS_ROBESKIRT, 1, 1),
                            new LootItem(Items.AHRIMS_STAFF, 1, 1),
                            new LootItem(Items.DHAROKS_HELM, 1, 1),
                            new LootItem(Items.DHAROKS_PLATEBODY, 1, 1),
                            new LootItem(Items.DHAROKS_PLATELEGS, 1, 1),
                            new LootItem(Items.DHAROKS_GREATAXE, 1, 1),
                            new LootItem(Items.GUTHANS_HELM, 1, 1),
                            new LootItem(Items.GUTHANS_PLATEBODY, 1, 1),
                            new LootItem(Items.GUTHANS_CHAINSKIRT, 1, 1),
                            new LootItem(Items.GUTHANS_WARSPEAR, 1, 1),
                            new LootItem(Items.KARILS_COIF, 1, 1),
                            new LootItem(Items.KARILS_LEATHERTOP, 1, 1),
                            new LootItem(Items.KARILS_LEATHERSKIRT, 1, 1),
                            new LootItem(Items.KARILS_CROSSBOW, 1, 1),
                            new LootItem(Items.TORAGS_HELM, 1, 1),
                            new LootItem(Items.TORAGS_PLATEBODY, 1, 1),
                            new LootItem(Items.TORAGS_PLATELEGS, 1, 1),
                            new LootItem(Items.TORAGS_HAMMERS, 1, 1),
                            new LootItem(Items.VERACS_HELM, 1, 1),
                            new LootItem(Items.VERACS_BRASSARD, 1, 1),
                            new LootItem(Items.VERACS_PLATESKIRT, 1, 1),
                            new LootItem(Items.VERACS_FLAIL, 1, 1)
                    )
            ),
            new DropViewerEntry("Sinister Chest", SinisterChest.LOOT_TABLE),
            new DropViewerEntry("Digsite Workman (Pickpocket)", PickPocket.DIGSITE_WORKMAN.lootTable),
            new DropViewerEntry("Pest Control Herbs", PCReward.HERB_PACK_LOOT),
            new DropViewerEntry("Pest Control Minerals", PCReward.MINERAL_PACK_LOOT),
            new DropViewerEntry("Pest Control Seeds", PCReward.SEED_PACK_LOOT),
            new DropViewerEntry("Infernal Eel", InfernalEel.LOOT_TABLE),
            new DropViewerEntry("Sulliuscep", Woodcutting.SULLIUSCEP_LOOT),
            new DropViewerEntry("Rare Drop Table", RareDropTable.RARE_DROP_TABLE),
            new DropViewerEntry("Chambers of Xeric (Unique)", XericRewards.UNIQUE_TABLE),
            new DropViewerEntry("Abyssal Sire (Unsired)", FontOfConsumption.LOOT),
            new DropViewerEntry("Hespori", HesporiLoot.NORMAL_SEEDS),
            new DropViewerEntry("Hespori (Anima)", HesporiLoot.ANIMA_SEEDS),
            new DropViewerEntry("Bird Nest (Ring)", BirdNest.RING_LOOT),
            new DropViewerEntry("Bird Nest (Seed)", BirdNest.SEED_LOOT),
            new DropViewerEntry("Bird Nest (Wyson Seed)", BirdNest.WYSON_SEED_LOOT),
            new DropViewerEntry("Trade Mole Parts - Wyson", Wyson.NESTS),
            new DropViewerEntry("Supply Crate (Mahogany Homes)", MahoganySupplyCrate.LOOT_TABLE),
            new DropViewerEntry("Gem Rock", Mining.GEM_ROCK_TABLE),
            new DropViewerEntry("Muddy Chest", MuddyChest.LOOT),
            new DropViewerEntry("Chewed Bones", PyreSite.CHEWED_BONES_LOOT),
            new DropViewerEntry("Mystery Box", RandomEvent.MYSTERY_BOX),
            new DropViewerEntry("Loar Remains", FuneralPyre.LOAR.getLootTable()),
            new DropViewerEntry("Phrin Remains", FuneralPyre.PHRIN.getLootTable()),
            new DropViewerEntry("Riyl Remains", FuneralPyre.RIYL.getLootTable()),
            new DropViewerEntry("Asyn Remains", FuneralPyre.ASYN.getLootTable()),
            new DropViewerEntry("Fiyr Remains", FuneralPyre.FIYR.getLootTable()),
            new DropViewerEntry("Urium Remains", FuneralPyre.URIUM.getLootTable()),
            new DropViewerEntry("Bronze Red Chest (SoM)", ShadeChest.BRONZE_RED.getLootTable()),
            new DropViewerEntry("Bronze Brown Chest (SoM)", ShadeChest.BRONZE_BROWN.getLootTable()),
            new DropViewerEntry("Bronze Crimson Chest (SoM)", ShadeChest.BRONZE_CRIMSON.getLootTable()),
            new DropViewerEntry("Bronze Black Chest (SoM)", ShadeChest.BRONZE_BLACK.getLootTable()),
            new DropViewerEntry("Bronze Purple Chest (SoM)", ShadeChest.BRONZE_PURPLE.getLootTable()),
            new DropViewerEntry("Steel Red Chest (SoM)", ShadeChest.STEEL_RED.getLootTable()),
            new DropViewerEntry("Steel Brown Chest (SoM)", ShadeChest.STEEL_BROWN.getLootTable()),
            new DropViewerEntry("Steel Crimson Chest (SoM)", ShadeChest.STEEL_CRIMSON.getLootTable()),
            new DropViewerEntry("Steel Black Chest (SoM)", ShadeChest.STEEL_BLACK.getLootTable()),
            new DropViewerEntry("Steel Purple Chest (SoM)", ShadeChest.STEEL_PURPLE.getLootTable()),
            new DropViewerEntry("Black Red Chest (SoM)", ShadeChest.BLACK_RED.getLootTable()),
            new DropViewerEntry("Black Brown Chest (SoM)", ShadeChest.BLACK_BROWN.getLootTable()),
            new DropViewerEntry("Black Crimson Chest (SoM)", ShadeChest.BLACK_CRIMSON.getLootTable()),
            new DropViewerEntry("Black Black Chest (SoM)", ShadeChest.BLACK_BLACK.getLootTable()),
            new DropViewerEntry("Black Purple Chest (SoM)", ShadeChest.BLACK_PURPLE.getLootTable()),
            new DropViewerEntry("Silver Red Chest (SoM)", ShadeChest.SILVER_RED.getLootTable()),
            new DropViewerEntry("Silver Brown Chest (SoM)", ShadeChest.SILVER_BROWN.getLootTable()),
            new DropViewerEntry("Silver Crimson Chest (SoM)", ShadeChest.SILVER_CRIMSON.getLootTable()),
            new DropViewerEntry("Silver Black Chest (SoM)", ShadeChest.SILVER_BLACK.getLootTable()),
            new DropViewerEntry("Silver Purple Chest (SoM)", ShadeChest.SILVER_PURPLE.getLootTable()),
            new DropViewerEntry("Gold Red Chest (SoM)", ShadeChest.GOLD_RED.getLootTable()),
            new DropViewerEntry("Gold Brown Chest (SoM)", ShadeChest.GOLD_BROWN.getLootTable()),
            new DropViewerEntry("Gold Crimson Chest (SoM)", ShadeChest.GOLD_CRIMSON.getLootTable()),
            new DropViewerEntry("Gold Black Chest (SoM)", ShadeChest.GOLD_BLACK.getLootTable()),
            new DropViewerEntry("Gold Purple Chest (SoM)", ShadeChest.GOLD_PURPLE.getLootTable()),
            new DropViewerEntry("Wintertodt Crate", WintertodtCrate.TABLE),
            new DropViewerEntry("Callisto", new DropViewerResultPet(Pet.CALLISTO_CUB, 1500), true, CallistoDropTable.REGULAR, CallistoDropTable.UNIQUE)
    ).collect(Collectors.toCollection(ArrayList::new));
}

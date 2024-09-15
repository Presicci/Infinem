package io.ruin.model.content.tasksystem.areas;

import io.ruin.api.utils.StringUtils;
import io.ruin.utility.Color;
import io.ruin.model.content.scroll.DiaryScroll;
import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.player.Player;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/29/2024
 */
public enum AreaReward {
    /**
     * General rewards
     */
    CULINAROMANCERS_CHEST_EASY(TaskArea.GENERAL, AreaTaskTier.EASY, "Unlock up to Steel gloves in the Culinaromancer's Chest"),
    CULINAROMANCERS_CHEST_MEDIUM(TaskArea.GENERAL, AreaTaskTier.MEDIUM, "Unlock up to Adamant gloves in the Culinaromancer's Chest"),
    CULINAROMANCERS_CHEST_HARD(TaskArea.GENERAL, AreaTaskTier.HARD, "Unlock up to Dragon gloves in the Culinaromancer's Chest"),
    CULINAROMANCERS_CHEST_ELITE(TaskArea.GENERAL, AreaTaskTier.ELITE, "Unlock Barrows gloves in the Culinaromancer's Chest"),
    CULINAROMANCERS_CHEST_DISCOUNT(TaskArea.GENERAL, AreaTaskTier.ELITE, "20% discount on items in the Culinaromancer's Chest"),
    FAIRY_RING(TaskArea.GENERAL, AreaTaskTier.ELITE, "Ability to use fairy rings without the need of a dramen or lunar staff"),
    /**
     * Asgarnia rewards
     */
    FALADOR_SHIELD_1(TaskArea.ASGARNIA, AreaTaskTier.EASY, "Unlocks the Falador Shield 1",
            "25% Prayer restore once per day"),
    //Tight-gap shortcut to the Chaos Temple from Burthorpe
    FALADOR_SHIELD_2(TaskArea.ASGARNIA, AreaTaskTier.MEDIUM, "Unlocks the Falador Shield 2",
            "50% Prayer restore once per day"),
    FALADOR_FARMING_EXPERIENCE(TaskArea.ASGARNIA, AreaTaskTier.MEDIUM, "10% more experience from the south Falador farming patches"),
    //Access to a shortcut in the Motherlode Mine (requires 54 Agility)
    //Higher chance of receiving a clue scroll (medium) (1/106) from a guard in Falador (The shield does not need to be equipped)
    FALADOR_SHIELD_3(TaskArea.ASGARNIA, AreaTaskTier.HARD, "Unlocks the Falador Shield 3",
            "100% Prayer restore once per day",
            "Giant Mole indicator when equipped"),
    CRAFTING_GUILD_BANK(TaskArea.ASGARNIA, AreaTaskTier.HARD, "Access to the Crafting Guild bank"),
    NOTED_GIANT_MOLE_PARTS(TaskArea.ASGARNIA, AreaTaskTier.HARD, "Giant Mole parts will drop noted"),
    //Access to a shortcut to the Fountain of Heroes in the Heroes' Guild basement, requiring 67 Agility
    FALADOR_SHIELD_4(TaskArea.ASGARNIA, AreaTaskTier.ELITE, "Unlocks the Falador Shield 4",
            "100% Prayer restore twice per day"),
    FALADOR_TREE_PATCH_DISEASE_FREE(TaskArea.ASGARNIA, AreaTaskTier.ELITE, "Falador tree patch will never get diseased"),
    //Increased chance at receiving higher level ores when cleaning pay-dirt.[1]
    //The roll for each ore type is increased by ~1 percentage point.[2]
    ALTERNATE_AMETHYST_MINE(TaskArea.ASGARNIA, AreaTaskTier.ELITE, "Access to alternative Amethyst mining spot"),

    /**
     * Desert rewards
     */
    DESERT_AMULET_1(TaskArea.DESERT, AreaTaskTier.EASY, "Unlocks the Desert Amulet 1"),
    //PHAROH_SCEPTRE_CHARGES_10(TaskArea.DESERT, AreaTaskTier.EASY, "Pharaoh's sceptre can hold up to 10 charges"),
    NOTED_GOAT_HORN(TaskArea.DESERT, AreaTaskTier.EASY, "Goats will always drop noted desert goat horn"),
    //Simon Templeton will now buy your noted artefacts too
    DESERT_AMULET_2(TaskArea.DESERT, AreaTaskTier.MEDIUM, "Unlocks the Desert Amulet 2",
            "One teleport to Nardah per day"),
    ANCIENT_MAGIC(TaskArea.DESERT, AreaTaskTier.MEDIUM, "Access to ancient magics and the ancient pyramid"),
    //PHAROH_SCEPTRE_CHARGES_25(TaskArea.DESERT, AreaTaskTier.MEDIUM, "Pharaoh's sceptre can hold up to 25 charges"),
    DESERT_AMULET_3(TaskArea.DESERT, AreaTaskTier.HARD, "Unlocks the Desert Amulet 3"),
    //PHAROH_SCEPTRE_CHARGES_50(TaskArea.DESERT, AreaTaskTier.HARD, "Pharaoh's sceptre can hold up to 50 charges"),
    //AL_KHARID_PALACE_SHORTCUT(TaskArea.DESERT, AreaTaskTier.HARD, "Access to the big window shortcut in Al Kharid Palace"),
    ZAHUR_UNFINISHED_POTIONS(TaskArea.DESERT, AreaTaskTier.HARD, "Zahur will create unfinished potions for 200 coins per potion"),
    ZAHUR_CLEAN_HERB(TaskArea.DESERT, AreaTaskTier.HARD, "Zahur will now clean noted grimy herbs for 200 coins each"),
    //All carpet rides are free.
    //Ropes placed at both the Kalphite Lair entrance and the Kalphite Queen tunnel entrance become permanent.
    DESERT_AMULET_4(TaskArea.DESERT, AreaTaskTier.ELITE, "Unlocks the Desert Amulet 4",
            "Unlimited teleports to Nardah and the Kalphite Cave",
            "The Nardah teleport now takes players directly inside the Elidinis shrine",
            "100% protection against desert heat when worn"),
    //PHAROH_SCEPTRE_CHARGES_100(TaskArea.DESERT, AreaTaskTier.ELITE, "Pharaoh's sceptre can hold up to 100 charges"),
    //Free pass-through of the Shantay Pass and the Ruins of Unkah
    //Access to a crevice shortcut, requiring 86 Agility, in the Kalphite Lair from the entrance to the antechamber before the Kalphite Queen boss room.

    /**
     * Fremennik rewards
     */
    FREMENNIK_SEA_BOOTS_1(TaskArea.FREMENNIK, AreaTaskTier.EASY, "Unlocks the Fremennik Sea Boots 1",
            "One free teleport to Rellekka every day"),
    PEER_DEPOSIT_BOX(TaskArea.FREMENNIK, AreaTaskTier.EASY, "Peer the Seer will act as a bank deposit box"),
    LYRE_EXTRA_CHARGE(TaskArea.FREMENNIK, AreaTaskTier.EASY, "Enchanted lyre gets an extra charge when making a sacrifice"),
    SKULGRIMENS_BATTLE_GEAR(TaskArea.FREMENNIK, AreaTaskTier.EASY, "Unlock Skulgrimen's Battle Gear shop"),
    FREMENNIK_SEA_BOOTS_2(TaskArea.FREMENNIK, AreaTaskTier.MEDIUM, "Unlocks the Fremennik Sea Boots 2"),
    //+10% chance of gaining approval in Managing Miscellania
    FREMENNIK_SEA_BOOTS_3(TaskArea.FREMENNIK, AreaTaskTier.HARD, "Unlocks the Fremennik Sea Boots 3"),
    LYRE_WATERBIRTH_TELEPORT(TaskArea.FREMENNIK, AreaTaskTier.HARD, "Ability to change enchanted lyre teleport destination to Waterbirth Island"),
    AVIANSIE_NOTED_ADDY_BARS(TaskArea.FREMENNIK, AreaTaskTier.HARD, "Aviansies in the God Wars Dungeon will drop noted adamantite bars"),
    // Unlock miscellania

    //Stony basalt teleport destination changed to the roof of the Troll Stronghold if you have Agility 73
    //Access to 2 new Lunar spells:
    //Tan Leather — Requires Magic 78 , 5Fire 2Astral 1Nature ; tans up to 5 hides per spell.
    //Recharge Dragonstone — Requires Magic 89 , 4Water 1Astral 1Soul ; recharges all items in inventory per spell.
    FREMENNIK_SEA_BOOTS_4(TaskArea.FREMENNIK, AreaTaskTier.ELITE, "Unlocks the Fremennik Sea Boots 4",
            "Unlimited free teleports to Rellekka"),
    NOTED_DAG_BONES(TaskArea.FREMENNIK, AreaTaskTier.ELITE, "Dagannoth bones will be dropped in noted form"),
    LYRE_JATIZSO_NEITIZNOT(TaskArea.FREMENNIK, AreaTaskTier.ELITE, "The Enchanted lyre can now teleport to Jatizso and Neitiznot"),
    //Seal of passage is no longer needed to interact with anyone on Lunar Isle
    //+15% chance of gaining approval in Managing Miscellania[1]

    /**
     * Kandarin rewards
     */
    KANDARIN_HEADGEAR_1(TaskArea.KANDARIN, AreaTaskTier.EASY, "Unlocks the Kandarin Headgear 1",
            "Functions as a light source",
            "Normal logs reward double logs when chopped"),
    ARDOUGNE_CLOAK_1(TaskArea.KANDARIN, AreaTaskTier.EASY, "Unlocks the Ardougne Cloak 1",
            "Unlimited teleports to the Ardougne Monastery"),
    COAL_TRUCKS(TaskArea.KANDARIN, AreaTaskTier.EASY, "Access to the coal trucks west of McGrubor's Wood"),
    // Jubster and Frogeel drops will be noted at the Tower of Life
    // Double death runes wehn trading in cats
    // 5% more marks of grace on Seers' village rooftop course
    KANDARIN_HEADGEAR_2(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "Unlocks the Kandarin Headgear 2"),
    ARDOUGNE_CLOAK_2(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "Unlocks the Ardougne Cloak 2",
            "Three teleports to the Ardougne farm patch every day"),
    THIEVING_BOOST_1(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "10% increased chance to pickpocket in Ardougne"),
    YANILLE_TELEPORT(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "Can switch the destination of Watchtower Teleport to Yanille"),
    ARDOUGNE_RING_OF_LIFE(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "Ability to toggle the Ring of life teleport to Ardougne"),
    OURANIA_RUNES(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "Receive additional runes when crafting essence at the Ourania Altar"),
    COIN_POUCHES_1(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "Ability to hold a maximum of 56 coin pouches"),
    CATHERBY_HERB_LIFE(TaskArea.KANDARIN, AreaTaskTier.MEDIUM, "5% chance to save a harvest life from Catherby herb patch"),
    // 100 free noted pure essence every day from wizard cromperty
    // Unicow, Newtroost, and Spidine drops will be noted in the tower of life
    KANDARIN_HEADGEAR_3(TaskArea.KANDARIN, AreaTaskTier.HARD, "Unlocks the Kandarin Headgear 3",
            "One free teleport to Sherlock per day"),
    ARDOUGNE_CLOAK_3(TaskArea.KANDARIN, AreaTaskTier.HARD, "Unlocks the Ardougne Cloak 3",
            "Five teleports to the Ardougne farm patch every day"),
    THIEVING_BOOST_2(TaskArea.KANDARIN, AreaTaskTier.HARD, "10% increased chance to pickpocket around Gielinor"),
    SEERS_TELEPORT(TaskArea.KANDARIN, AreaTaskTier.HARD, "Can switch the destination of Camelot Teleport to Seers' Village"),
    COIN_POUCHES_2(TaskArea.KANDARIN, AreaTaskTier.HARD, "Ability to hold a maximum of 84 coin pouches"),
    // 150 free noted pure essence every day from Wizard Cromperty
    THORMAC_DISCOUNT_1(TaskArea.KANDARIN, AreaTaskTier.HARD, "Thormac will enchant battlestaves for 30,000 coins each (down from 40,000)"),
    // 15% more marks of grace from Seers' Village Rooftop Course
    // 10% increased chance to save a harvest life from the Catherby herb patch
    KANDARIN_HEADGEAR_4(TaskArea.KANDARIN, AreaTaskTier.ELITE, "Unlocks the Kandarin Headgear 4",
            "Unlimited free teleports to Sherlock"),
    ARDOUGNE_CLOAK_4(TaskArea.KANDARIN, AreaTaskTier.ELITE, "Unlocks the Ardougne Cloak 4",
            "Unlimited teleports to the Ardougne farm patch"),
    COIN_POUCHES_3(TaskArea.KANDARIN, AreaTaskTier.ELITE, "Ability to hold a maximum of 140 coin pouches"),
    // 250 free noted pure essence every day from Wizard Cromperty
    THORMAC_DISCOUNT_2(TaskArea.KANDARIN, AreaTaskTier.ELITE, "Thormac will enchant battlestaves for 20,000 coins each (down from 30,000)"),
    // 15% increased chance to save a harvest life from the Catherby herb patch
    // 25% more marks of grace from the Ardougne Rooftop Course
    // Bert will automatically deliver 84 buckets of sand to your bank each day you log in. (Ultimate ironmen are ineligible)
    // Ability to hold a maximum of 140 coin pouches

    /**
     * Karamja rewards
     */
    KARAMJA_GLOVES_1(TaskArea.KARAMJA, AreaTaskTier.EASY, "Unlocks the Karamja Gloves 1"),
    KARAMJA_GLOVES_2(TaskArea.KARAMJA, AreaTaskTier.MEDIUM, "Unlocks the Karamja Gloves 2",
            "Grant access to underground Shilo Village mine when worn"),
        // While worn, 10% additional Agility experience from redeeming Agility tickets
        // While worn, 10% additional Agility experience earned from all obstacles in the Brimhaven Agility Arena
    KARAMJA_GLOVES_3(TaskArea.KARAMJA, AreaTaskTier.HARD, "Unlocks the Karamja Gloves 3",
            "Unlimited teleports to the underground Shilo Village mine"),
    KARAMJA_GLOVES_4(TaskArea.KARAMJA, AreaTaskTier.ELITE, "Unlocks the Karamja Gloves 4",
            "Unlimited teleports to Duradel"),
    /*
     *  10% chance of receiving 2 Agility arena tickets and Brimhaven vouchers, instead of one, upon every successful dispenser tag at the Brimhaven Agility Arena
        Free usage of Shilo Village's furnace
        Free cart rides on Hajedy's cart system
        Free access to the Hardwood Grove
        Access to the stepping stones shortcut leading to the red dragons in Brimhaven dungeon, requiring 83 Agility
        Red dragons in Brimhaven Dungeon will always drop noted red dragonhide
        All Metal dragons (bronze, iron, and steel) in Brimhaven Dungeon will drop their respective bars in noted form if toggled-on through Pirate Jackie the Fruit - can toggle on/off freely
        1 free resurrection, per day, in the TzHaar Fight Cave (not the Inferno)
        Double Tokkul from the TzHaar Fight Cave, the Inferno, and TzHaar-Ket-Rak's Challenges
     */

    /**
     * Misthalin rewards
     */
    EXPLORER_RING_1(TaskArea.MISTHALIN, AreaTaskTier.EASY, "Unlocks the Explorer's Ring 1",
            "30 casts of Low Level Alchemy per day without runes",
            "50% run energy replenish twice a day"),
    VARROCK_ARMOR_1(TaskArea.MISTHALIN, AreaTaskTier.EASY, "Unlocks the Varrock Armour 1",
            "10% chance of mining 2 ores at once, up to gold",
            "10% chance to smelt 2 bars at once, up to steel"),
    ZAFF_BATTLESTAVES_15(TaskArea.MISTHALIN, AreaTaskTier.EASY, "Zaff will sell 15 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_14(TaskArea.MISTHALIN, AreaTaskTier.EASY, "Skull sceptre will now hold up to 14 charges",
            "Skull sceptre parts now give 1 extra bone fragment"),
    BONE_WEAPON_SHOP(TaskArea.MISTHALIN, AreaTaskTier.EASY, "Unlock the bone weapon shop",
            "Managed by Nardok outside Dorgesh-Kaan"),
    AVAS_ATTRACTOR(TaskArea.MISTHALIN, AreaTaskTier.EASY, "Can purchase Ava's attractor",
            "Can be purchased from Ava in draynor manor"),
    EXPLORER_RING_2(TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, "Unlocks the Explorer's Ring 2",
            "50% run energy replenish 3 times a day",
            "Three daily teleports to cabbage patch near Falador farm"),
    VARROCK_ARMOR_2(TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, "Unlocks the Varrock Armour 2",
            "10% chance of mining 2 ores at once, up to mithril",
            "10% chance to smelt 2 bars at once, up to mithril"),
    ZAFF_BATTLESTAVES_30(TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, "Zaff will sell 30 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_18(TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, "Skull sceptre will now hold up to 18 charges",
            "Skull sceptre parts now give 2 extra bone fragment"),
    AVAS_ACCUMULATOR(TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, "Can purchase Ava's accumulator",
            "Can be purchased from Ava in draynor manor"),
    GRAND_EXCHANGE_TELEPORT(TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, "Can switch the destination of Varrock Teleport to the GE"),
    EXPLORER_RING_3(TaskArea.MISTHALIN, AreaTaskTier.HARD, "Unlocks the Explorer's Ring 3",
            "50% run energy replenish 4 times a day",
            "Unlimited teleports to cabbage patch near Falador farm"),
    VARROCK_ARMOR_3(TaskArea.MISTHALIN, AreaTaskTier.HARD, "Unlocks the Varrock Armour 3",
            "10% chance of mining 2 ores at once, up to adamantite",
            "10% chance to smelt 2 bars at once, up to adamantite",
            "Can be worn in place of a chef's hat to access the Cooks' Guild"),
    //TEARS_OF_GUTHIX(AreaTaskTier.HARD, "10% increased experience from Tears of Guthix"),
    ZAFF_BATTLESTAVES_60(TaskArea.MISTHALIN, AreaTaskTier.HARD, "Zaff will sell 60 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_22(TaskArea.MISTHALIN, AreaTaskTier.HARD, "Skull sceptre will now hold up to 22 charges",
            "Skull sceptre parts now give 3 extra bone fragment"),
    COOKS_GUILD_BANK(TaskArea.MISTHALIN, AreaTaskTier.HARD, "Access to the Cooks' Guild bank"),
    EXPLORER_RING_4(TaskArea.MISTHALIN, AreaTaskTier.ELITE, "Unlocks the Explorer's Ring 4",
            "100% run energy replenish 3 times a day",
            "30 casts of High Level Alchemy per day without runes"),
    VARROCK_ARMOR_4(TaskArea.MISTHALIN, AreaTaskTier.ELITE, "Unlocks the Varrock Armour 4",
            "10% chance of mining 2 ores at once, up to amethyst",
            "10% chance to smelt 2 bars at once",
            "Acts as a prospector jacket for the purposes of experience bonus and clues"),
    ZAFF_BATTLESTAVES_120(TaskArea.MISTHALIN, AreaTaskTier.ELITE, "Zaff will sell 120 battlestaves every day for 7,000 coins each"),
    SKULL_SCEPTRE_CHARGES_26(TaskArea.MISTHALIN, AreaTaskTier.ELITE, "Skull sceptre will now hold up to 26 charges",
            "Skull sceptre parts now give 4 extra bone fragment"),

    /**
     * Morytania rewards
     */
    MORYTANIA_LEGS_1(TaskArea.MORYTANIA, AreaTaskTier.EASY, "Unlocks the Morytania Legs 1",
            "2 daily teleports to the Slime Pit beneath the Ectofuntus"),
    SLAYER_EXPERIENCE_25(TaskArea.MORYTANIA, AreaTaskTier.EASY, "2.5% more Slayer experience in the Slayer Tower"),
    //50% chance of a ghast ignoring you rather than attacking
    MORYTANIA_LEGS_2(TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, "Unlocks the Morytania Legs 2",
            "5 daily teleports to the Slime Pit beneath the Ectofuntus",
            "Acts as a ghostspeak amulet when worn"),
    //ROBIN_13(TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, "Robin will exchange 13 slime buckets and bonemeal for bones daily"),
    SLAYER_EXPERIENCE_50(TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, "5% more Slayer experience in the Slayer Tower"),
    MORYTANIA_LEGS_3(TaskArea.MORYTANIA, AreaTaskTier.HARD, "Unlocks the Morytania Legs 3",
            "Unlimited teleports to Burgh de Rott"),
    //ROBIN_26(TaskArea.MORYTANIA, AreaTaskTier.HARD, "Robin will exchange 26 slime buckets and bonemeal for bones daily"),
    //SHADE_PRAYER_EXP(TaskArea.MORYTANIA, AreaTaskTier.HARD, "50% more Prayer experience from burning shade remains"),
    BARROWS_RUNES(TaskArea.MORYTANIA, AreaTaskTier.HARD, "50% more runes from the Barrows chest"),
    SLAYER_EXPERIENCE_75(TaskArea.MORYTANIA, AreaTaskTier.HARD, "7.5% more Slayer experience in the Slayer Tower"),
    //Double Mort myre fungi when casting Bloom
    //BONECRUSHER(), shop reward
    //An additional item that, when carried, causes bones dropped from killed monsters to be automatically buried, granting half the Prayer experience that would have been granted for burying them normally; charged with a small amount of ecto-tokens
    //It can be claimed from a ghost disciple. Note: A Ghostspeak amulet or Morytania legs 3 must be worn when claiming the Bonecrusher.
    MORYTANIA_LEGS_4(TaskArea.MORYTANIA, AreaTaskTier.ELITE, "Unlocks the Morytania Legs 4",
            "Unlimited teleports to the slime pit beneath the Ectofuntus"),
    //ROBIN_39(TaskArea.MORYTANIA, AreaTaskTier.ELITE, "Robin will exchange 39 slime buckets and bonemeal for bones daily"),
    //SHADE_FIREMAKING_EXP(TaskArea.MORYTANIA, AreaTaskTier.ELITE, "50% more Firemaking experience from burning shade remains"),
    //BONECRUSHER_FULL_EXP(TaskArea.MORYTANIA, AreaTaskTier.ELITE, "Bones buried via the Bonecrusher grant full Prayer experience"),
    //HARMONY_ISLAND_HERB(TaskArea.MORYTANIA, AreaTaskTier.ELITE, "Access to the herb patch on Harmony Island"),
    SLAYER_EXPERIENCE_100(TaskArea.MORYTANIA, AreaTaskTier.ELITE, "10% more Slayer experience in the Slayer Tower"),

    /**
     * Tirannwn rewards
     */


    /**
     * Wilderness rewards
     */
    WILDERNESS_SWORD_1(TaskArea.WILDERNESS, AreaTaskTier.EASY, "Unlocks the Wilderness Sword 1",
            "Always slashes webs successfully"),
    //FREE_RUNES_40(TaskArea.WILDERNESS, AreaTaskTier.EASY, "40 random free runes from Lundail once per day"),
    //WILDERNESS_LEVER_CHOICE(TaskArea.WILDERNESS, AreaTaskTier.EASY, "Wilderness lever can teleport you to either Edgeville or Ardougne"),
    WILDERNESS_SWORD_2(TaskArea.WILDERNESS, AreaTaskTier.MEDIUM, "Unlocks the Wilderness Sword 2"),
    //FREE_RUNES_80(TaskArea.WILDERNESS, AreaTaskTier.MEDIUM, "80 random free runes from Lundail once per day"),
    //RESOURCE_AREA_DISCOUNT_20(TaskArea.WILDERNESS, AreaTaskTier.MEDIUM, "20% off entry to Resource Area"),
    //ENT_YIELD_BOOST(TaskArea.WILDERNESS, AreaTaskTier.MEDIUM, "Increases the chance of a successful yield from ents by 15%"),
    //Access to Spindel, Artio and Calvar'ion.
    //Access to the shortcut in the Deep Wilderness Dungeon (requires Agility 46 )
    //Can have 4 ecumenical keys at a time
    WILDERNESS_SWORD_3(TaskArea.WILDERNESS, AreaTaskTier.HARD, "Unlocks the Wilderness Sword 3",
            "One free teleport to the Fountain of Rune daily"),
    //FREE_RUNES_120(TaskArea.WILDERNESS, AreaTaskTier.HARD, "120 random free runes from Lundail once per day"),
    //RESOURCE_AREA_DISCOUNT_50(TaskArea.WILDERNESS, AreaTaskTier.HARD, "50% off entry to Resource Area"),
    //OBELISK_CHOICE(TaskArea.WILDERNESS, AreaTaskTier.HARD, "Able to choose your destination when teleporting through the Ancient Obelisks"),
    //NOTED_WINE_OF_ZAMMY(TaskArea.WILDERNESS, AreaTaskTier.HARD, "Wine of zamorak found in the Chaos Temple and Deep Wilderness Dungeon will be noted"),
    //Can have 5 ecumenical keys at a time
    //Access to a shortcut to the Lava Maze (requires Agility 82 )
    //Access to a shortcut to the Lava Dragon Isle (requires Agility 74 )
    //50% more lava shards per lava scale
    WILDERNESS_SWORD_4(TaskArea.WILDERNESS, AreaTaskTier.ELITE, "Unlocks the Wilderness Sword 4",
            "Unlimited free teleports to the Fountain of Rune"),
    //FREE_RUNES_200(TaskArea.WILDERNESS, AreaTaskTier.ELITE, "200 random free runes from Lundail once per day"),
    //RESOURCE_AREA_FREE(TaskArea.WILDERNESS, AreaTaskTier.ELITE, "Free entry to Resource Area"),
    //NOTED_DRAGON_BONES(TaskArea.WILDERNESS, AreaTaskTier.ELITE, "All dragon bones drops from dragons in the Wilderness are noted"),
    //DARK_CRAB_CATCH_RATE(TaskArea.WILDERNESS, AreaTaskTier.ELITE, "Increased dark crab catch rate"),

    /**
     * Zeah
     */
    NO_MINECART_FEE(TaskArea.ZEAH, AreaTaskTier.MEDIUM, "No charge when using the Lovakengj Minecart Network")
    ;

    private final TaskArea area;
    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    AreaReward(TaskArea area, AreaTaskTier tier, String description, String... additionalDescription) {
        this.area = area;
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }

    public boolean checkReward(Player player, String message) {
        return area.checkTierUnlock(player, tier, message);
    }

    public boolean hasReward(Player player) {
        return area.hasTierUnlocked(player, tier);
    }

    private static final Map<TaskArea, List<AreaReward>> AREA_REWARDS = new HashMap<>();

    public static void openRewards(Player player, TaskArea area) {
        List<String> lines = new ArrayList<>();
        AreaTaskTier tier = null;
        List<AreaReward> rewards = AREA_REWARDS.get(area);
        if (rewards == null || rewards.isEmpty()) {
            player.sendMessage("There are currently no unlocks for that region.");
            return;
        }
        for (AreaReward task : rewards) {
            if (tier != task.tier) {
                tier = task.tier;
                lines.add(Color.YELLOW.wrap(tier.toString() + " - " + area.getPointThreshold(tier) + " pts"));
            }
            lines.add(Color.DARK_RED.wrap(task.description));
            if (task.additionalDescription.length > 0) {
                lines.addAll(Arrays.asList(task.additionalDescription));
            }
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Region Task Unlocks - " + StringUtils.capitalizeFirst(area.name().toLowerCase())), lines);
        scroll.open(player);
    }

    static {
        // Populate map with task areas
        for (TaskArea taskArea : TaskArea.values()) {
            AREA_REWARDS.put(taskArea, new ArrayList<>());
        }
        // Populate map with rewards
        for (AreaReward areaReward : values()) {
            AREA_REWARDS.get(areaReward.area).add(areaReward);
        }
    }
}

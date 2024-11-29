package io.ruin.model.skills.cooking;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public enum Food {
    //Raw Fish
    RAW_SHRIMPS(1, 30.0, 317, 315, 7954, "the shrimps", "shrimps", 3, 31, 31, 31),
    RAW_KARAMBWANJI(1, 15.0, 3150, 21394, 23873, "a karambwanji", "karambwanji", 3, 99, 99, 99),
    RAW_SARDINE(1, 40.0, 327, 325, 369, "a sardine", "sardines", 3, 35, 35, 35),
    RAW_HERRING(5, 50.0, 345, 347, 357, "a herring", "herring", 3, 41, 41, 41),
    RAW_ANCHOVIES(1, 30.0, 321, 319, 323, "the anchovies", "anchovies", 3, 34, 34, 34),
    RAW_KARAMBWAN_P(1, 80.0, 3142, 3146, 3148, "a karambwan", "karambwan", 3, 99, 99, 99),
    RAW_GIANT_CARP(10, 10.0, Items.RAW_GIANT_CARP, Items.GIANT_CARP, -1, "a giant carp", "giant carp", 3, 1, 1, 1),
    RAW_KARAMBWAN(30, 190.0, 3142, 3144, 3148, "a karambwan", "karambwan", 3, 99, 99, 99),
    RAW_MACKEREL(10, 60.0, 353, 355, 357, "a mackerel", "mackerels", 3, 45, 45, 45),
    RAW_TROUT(15, 70.0, 335, 333, 343, "a trout", "trouts", 3, 50, 49, 49),
    RAW_COD(18, 75.0, 341, 339, 343, "a cod", "cods", 3, 52, 52, 52),
    RAW_PIKE(20, 80.0, 349, 351, 343, "a pike", "pikes", 3, 64, 64, 64),
    RAW_SLIMY_EEL(28, 95.0, 3379, 3381, 3383, "a slimy eel", "slimy eels", 3, 58, 58, 58),
    RAW_SALMON(25, 90.0, 331, 329, 343, "a salmon", "salmons", 3, 58, 58, 58),
    RAW_TUNA(30, 100.0, 359, 361, 367, "a tuna", "tunas", 3, 64, 64, 63),
    RAW_LOBSTER(40, 120.0, 377, 379, 381, "a lobster", "lobsters", 3, 74, 74, 64),
    RAW_BASS(43, 130.0, 363, 365, 367, "a bass", "basses", 3, 80, 80, 80),
    RAW_SWORDFISH(45, 140.0, 371, 373, 375, "a swordfish", "swordfishes", 3, 86, 86, 81),
    RAW_LAVA_EEL(53, 30.0, 2148, 2149, 3383, "a lava eel", "lava eels", 3, 58, 58, 58),
    RAW_MONKFISH(62, 150.0, 7944, 7946, 7948, "a monkfish", "monkfishes", 3, 92, 90, 90),
    RAW_SHARK(80, 210.0, 383, 385, 387, "a shark", "sharks", 3, 99, 99, 94),
    RAW_SEA_TURTLE(82, 211.3, 395, 397, 399, "a sea turtle", "sea turtles", 3, 99, 99, 99),
    RAW_ANGLERFISH(84, 230.0, 13439, 13441, 13443, "an anglerfish", "anglerfish", 3, 99, 99, 98),
    RAW_DARK_CRAB(85, 215.0, 11934, 11936, 11938, "a dark crab", "dark crabs", 15, 99, 99, 99),
    RAW_MANTA_RAY(91, 216.2, 389, 391, 393, "a manta ray", "manta rays", 3, 99, 99, 99),

    //Raw Meat
    SINEW_MEAT(1, 3.0, 2132, 9436, 9436, "sinew", "sinews", 3, 1, 1, 1),
    SINEW_BEAR_MEAT(1, 3.0, 2136, 9436, 9436, "sinew", "sinews", 3, 1, 1, 1),
    RAW_MEAT(1, 30.0, 2132, 2142, 2146, "a piece of meat", "meat", 3, 31, 31, 31),
    RAW_RAT_MEAT(1, 30.0, 2134, 2142, 2146, "a piece of rat meat", "rat meat", 3, 31, 31, 31),
    RAW_YAK_MEAT(1, 30.0, 10816, 2142, 2146, "a piece of yak meat", "yak meat", 3, 31, 31, 31),
    RAW_BEAR_MEAT(1, 30.0, 2136, 2142, 2146, "a piece of bear meat", "bear meat", 3, 31, 31, 31),
    RAW_CHICKEN(1, 30.0, 2138, 2140, 2144, "a chicken", "chickens", 10, 31, 31, 31),
    RAW_RABBIT(1, 30.0, 3226, 3228, 7222, "a rabbit", "rabbits", 3, 31, 31, 31),
    CRAB_MEAT(21, 100.0, 7518, 7521, 7520, "a piece of crab meat", "crab meat", 3, 31, 31, 31),
    RAW_UGTHANKI_MEAT(1, 40.0, Items.RAW_UGTHANKI_MEAT, Items.UGTHANKI_MEAT, 2146, "a piece of ugthanki meat", "ugthanki meat", 3, 99, 99, 95),
    RAW_MYSTERY_MEAT(1, 30.0, 24782, 24785, 2146, "a piece of mystery meat", "mystery meat", 3, 31, 31, 31),

    //Pies
    REDBERRY_PIE(10, 78.0, 2321, 2325, 2329, "a redberry pie", "", 3, 10, 10, 10),
    PIE_MEAT(20, 104.0, 2317, 2327, 2329, "a meat pie", "", 3, 20, 20, 20),
    MUD_PIE(29, 128.0, 2319, 7170, 2329, "a mud pie", "", 3, 29, 29, 29),
    APPLE_PIE(30, 130.0, 7168, 2323, 2329, "an apple pie", "", 3, 30, 30, 30),
    GARDEN_PIE(34, 128.0, 7186, 7188, 2329, "a garden pie", "", 3, 34, 34, 34),
    FISH_PIE(47, 164.0, 7186, 7188, 2329, "a fish pie", "", 3, 47, 47, 47),
    BOTANICAL_PIE(52, 180.0, Items.UNCOOKED_BOTANICAL_PIE, Items.BOTANICAL_PIE, 2329, "a botanical pie", "", 3, 52, 52, 52),
    MUSHROOM_PIE(60, 200.0, 21684, 21690, 2329, "a mushroom pie", "", 3, 60, 60, 60),
    ADMIRAL_PIE(70, 210.0, 7196, 7198, 2329, "an admiral pie", "", 3, 70, 70, 70),
    DRAGONFRUIT_PIE(73, 220.0, 22789, 22795, 2329, "a dragonfruit pie", "", 3, 73, 73, 73),
    WILD_PIE(85, 240.0, 7206, 7208, 2329, "a wild pie", "", 3, 85, 85, 85),
    SUMMER_PIE(95, 260.0, 7216, 7218, 2329, "a summer pie", "", 3, 95, 95, 95),

    //Stew
    STEW(25, 117.0, Items.UNCOOKED_STEW, Items.STEW, Items.BURNT_STEW, "a stew", "", 3, 58, 58, 55),

    //Pizza
    PLAIN_PIZZA(35, 143.0, 2287, 2289, 2305, "a plain pizza", "", 3, 35, 35, 35),

    //Bread
    PITTA_BREAD(58, 40.0, Items.PITTA_DOUGH, Items.PITTA_BREAD, Items.BURNT_PITTA_BREAD, "a loaf of pitta bread", "loaves of pitta bread", 3, 58, 58, 58),
    BREAD(1, 40.0, 2307, 2309, 2311, "a loaf of bread", "loaves of bread", 3, 40, 38, 35),

    //Cake
    ISHCAKE(31, 100.0, 7529, 7530, 7531, "a fishcake", "", 3, 31, 31, 31),
    CAKE(40, 180.0, 1889, 1891, 1903, "a cake", "", 3, 40, 40, 40),

    //Random
    NETTLE_TEA(20, 52.0, Items.NETTLEWATER, Items.NETTLE_TEA, Items.BOWL, "some nettle tea", "", 3, 54, 54, 48),
    SWEET_CORN(28, 104.0, 5986, 5988, 5990, "a piece of sweet corn", "", 3, 28, 28, 28),
    SEAWEED(1, 0.0, 401, 1781, 1781, "soda ash", "", 3, 1, 1, 1),
    GIANT_SEAWEED(1, 0.0, 21504, 1781, 1781, "soda ash", "", 3, 51, 51, 51),
    BAKED_POTATO(7, 15.0, Items.POTATO, Items.BAKED_POTATO, Items.BURNT_POTATO, "a potato", "", 3, 41, 41, 37),
    SCRAMBLED_EGG(13, 50, Items.UNCOOKED_EGG, Items.SCRAMBLED_EGG, Items.BURNT_EGG, "a scrambled egg", "", 3, 48, 48, 43),
    FRIED_MUSHROOMS(46, 60, Items.SLICED_MUSHROOMS, Items.FRIED_MUSHROOMS, Items.BURNT_MUSHROOM, "the fried mushrooms", "", 3, 90, 90, 86),
    FRIED_ONIONS(42, 60, Items.CHOPPED_ONION, Items.FRIED_ONIONS, Items.BURNT_ONION, "the fried onions", "", 3, 77, 77, 72),
    BARLEY_MALT(1, 0.0, Items.BARLEY, Items.BARLEY_MALT, Items.BARLEY_MALT, "the barley malt", "", 3, 1, 1, 1),
    COOKED_OOMLIE_WRAP(50, 30.0, Items.WRAPPED_OOMLIE, Items.COOKED_OOMLIE_WRAP, Items.BURNT_OOMLIE_WRAP, "the oomlie wrap", "", 3, 1, 1, 1),
    GRAAHK(41, 124, 29119, 29149, 29157, "the graahk", "", 3, 75, 75, 70, player -> false, "You must complete 25 hunter rumours to cook this."),
    KYATT(51, 143, 29125, 29152, 29157, "the kyatt", "", 3, 86, 86, 83, player -> false, "You must complete 25 hunter rumours to cook this."),
    PYRE_FOX(59, 154, 29110, 29137, 29161, "the pyre fox", "", 3, 93, 93, 90, player -> false, "You must complete 25 hunter rumours to cook this."),

    //Gnome
    HALF_BAKED_BATTA(1, 30.0, Items.RAW_BATTA, Items.HALF_BAKED_BATTA, Items.BURNT_BATTA, "the batta", "", 3, 40, 38, 34),
    HALF_BAKED_CRUNCHIES(1, 30.0, Items.RAW_CRUNCHIES, Items.HALF_BAKED_CRUNCHY, Items.BURNT_CRUNCHIES, "the crunchies", "", 3, 40, 38, 34),
    HALF_BAKED_GNOMEBOWL(1, 30.0, Items.RAW_GNOMEBOWL, Items.HALF_BAKED_BOWL, Items.BURNT_GNOMEBOWL, "the gnomebowl", "", 3, 40, 38, 34),
    DRUNK_DRAGON(32, 130.0, Items.MIXED_DRAGON_3, Items.DRUNK_DRAGON,-1, "the drunk dragon", "", 3, 1, 1, 1),
    CHOC_SATURDAY(33, 0.0, Items.MIXED_SATURDAY_2, Items.MIXED_SATURDAY_3, -1, "the mixed saturday", "", 3, 1, 1, 1),
    FRUIT_BATTA(25, 0.0, Items.HALF_MADE_BATTA, Items.UNFINISHED_BATTA_12, -1, Items.BATTA_TIN, "the fruit batta", "", 3, 1, 1, 1),
    TOAD_BATTA(26, 82.0, Items.HALF_MADE_BATTA_3, Items.TOAD_BATTA, -1, Items.BATTA_TIN, "the toad batta", "", 3, 1, 1, 1),
    WORM_BATTA(27, 0.0, Items.HALF_MADE_BATTA_2, Items.UNFINISHED_BATTA_13, -1, Items.BATTA_TIN, "the worm batta", "", 3, 1, 1, 1),
    VEGETABLE_BATTA(28, 0.0, Items.HALF_MADE_BATTA_5, Items.UNFINISHED_BATTA_15, -1, Items.BATTA_TIN, "the vegetable batta", "", 3, 1, 1, 1),
    CHEESE_AND_TOMATO_BATTA(29, 0.0, Items.HALF_MADE_BATTA_4, Items.UNFINISHED_BATTA_14, -1, Items.BATTA_TIN, "the cheese and tomato batta", "", 3, 1, 1, 1),
    WORM_HOLE(30, 0.0, Items.HALF_MADE_BOWL_2, Items.UNFINISHED_BOWL_6, -1, Items.GNOMEBOWL_MOULD, "the worm hole", "", 3, 1, 1, 1),
    VEGETABLE_BALL(35, 0.0, Items.HALF_MADE_BOWL_3, Items.UNFINISHED_BOWL_7, -1, Items.GNOMEBOWL_MOULD, "the vegetable ball", "", 3, 1, 1, 1),
    TANGLED_TOADS_LEGS(40, 105.0, Items.HALF_MADE_BOWL, Items.TANGLED_TOADS_LEGS, -1, Items.GNOMEBOWL_MOULD, "the tabgled toads legs", "", 3, 1, 1, 1),
    CHOCOLATE_BOMB(42, 0.0, Items.HALF_MADE_BOWL_4, Items.UNFINISHED_BOWL_8, -1, Items.GNOMEBOWL_MOULD, "the chocolate bomb", "", 3, 1, 1, 1),
    TOAD_CRUNCHIES(10, 0.0, Items.HALF_MADE_CRUNCHY_3, Items.UNFINISHED_CRUNCHY_6, -1, Items.CRUNCHY_TRAY, "the toad crunchies", "", 3, 1, 1, 1),
    SPICY_CRUNCHIES(12, 0.0, Items.HALF_MADE_CRUNCHY_2, Items.UNFINISHED_CRUNCHY_5, -1, Items.CRUNCHY_TRAY, "the spicy crunchies", "", 3, 1, 1, 1),
    WORM_CRUNCHIES(14, 0.0, Items.HALF_MADE_CRUNCHY_4, Items.UNFINISHED_CRUNCHY_7, -1, Items.CRUNCHY_TRAY, "the worm crunchies", "", 3, 1, 1, 1),
    CHOCCHIP_CRUNCHIES(16, 0.0, Items.HALF_MADE_CRUNCHY, Items.UNFINISHED_CRUNCHY_4, -1, Items.CRUNCHY_TRAY, "the chocchip crunchies", "", 3, 1, 1, 1),

    //Raids fish
    PYSK(1, 10, 20855, 20856, 20854, "a pysk", "pysk", 3, 31, 31, 31),
    SUPHI(15, 13.0, 20857, 20858, 20854, "a suphi", "suphi", 3, 35, 35, 35),
    LECKISH(30, 16.0, 20859, 20860, 20854, "a leckish", "leckish", 3, 50, 50, 50),
    BRAWK(45, 19.0, 20861, 20862, 20854, "a brawk", "brawk", 3, 65, 65, 65),
    MYCIL(60, 22.0, 20863, 20864, 20854, "a mycril", "mycril", 3, 80, 80, 80),
    ROQED(75, 25.0, 20865, 20866, 20854, "a roqed", "roqed", 3, 95, 95, 95),
    KYREN(90, 28.0, 20867, 20868, 20854, "a kyren", "kyren", 3, 104, 104, 104),

    //Raids bats
    GUANIC(1, 10, 20870, 20871, 20869, "a guanic", "guanic", 3, 31, 31, 31),
    PRAEAL(15, 13.0, 20872, 20873, 20869, "a praeal", "praeal", 3, 35, 35, 35),
    GIRAL(30, 16.0, 20874, 20875, 20869, "a giral", "giral", 3, 50, 50, 50),
    PHLUXIA(45, 19.0, 20876, 20877, 20869, "a phluxia", "phluxia", 3, 65, 65, 65),
    KRYKET(60, 22.0, 20878, 20879, 20869, "a kryket", "kryket", 3, 80, 80, 80),
    MURNG(75, 25.0, 20880, 20881, 20869, "a murng", "murng", 3, 95, 95, 95),
    PSYKK(90, 28.0, 20882, 20883, 20869, "a psykk", "psykk", 3, 104, 104, 104);

    public final int levelRequirement, rawID, cookedID, burntID, returnedSecondary, itemOffset, burnLevelFire, burnLevelRange, burnLevelCookingGauntlets;
    public final double experience;
    public final String descriptiveName, itemName, itemNamePlural, rawName;
    public final Predicate<Player> requirement;
    public final String requirementMessage;

    Food(int levelRequirement, double experience, int rawID, int cookedID, int burntID, String descriptiveName,
         String itemNamePlural, int itemOffset, int burnLevelFire, int burnLevelRange, int burnLevelCookingGauntlets, Predicate<Player> requirement, String requirementMessage) {
        this(levelRequirement, experience, rawID, cookedID, burntID, -1, descriptiveName, itemNamePlural, itemOffset, burnLevelFire, burnLevelRange, burnLevelCookingGauntlets, requirement, requirementMessage);
    }

    Food(int levelRequirement, double experience, int rawID, int cookedID, int burntID, String descriptiveName,
         String itemNamePlural, int itemOffset, int burnLevelFire, int burnLevelRange, int burnLevelCookingGauntlets) {
        this(levelRequirement, experience, rawID, cookedID, burntID, -1, descriptiveName, itemNamePlural, itemOffset, burnLevelFire, burnLevelRange, burnLevelCookingGauntlets);
    }

    Food(int levelRequirement, double experience, int rawID, int cookedID, int burntID, int returnedSecondary, String descriptiveName,
         String itemNamePlural, int itemOffset, int burnLevelFire, int burnLevelRange, int burnLevelCookingGauntlets) {
        this(levelRequirement, experience, rawID, cookedID, burntID, returnedSecondary, descriptiveName, itemNamePlural, itemOffset, burnLevelFire, burnLevelRange, burnLevelCookingGauntlets, null, "");
    }

    Food(int levelRequirement, double experience, int rawID, int cookedID, int burntID, int returnedSecondary, String descriptiveName,
         String itemNamePlural, int itemOffset, int burnLevelFire, int burnLevelRange, int burnLevelCookingGauntlets, Predicate<Player> requirement, String requirementMessage) {
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.rawID = rawID;
        this.cookedID = cookedID;
        this.burntID = burntID;
        this.returnedSecondary = returnedSecondary;
        this.rawName = ItemDefinition.get(rawID).name;
        this.itemName = ItemDefinition.get(cookedID).name.toLowerCase();
        this.descriptiveName = descriptiveName;
        this.itemNamePlural = itemNamePlural;
        this.itemOffset = itemOffset;
        this.burnLevelFire = burnLevelFire;
        this.burnLevelRange = burnLevelRange;
        this.burnLevelCookingGauntlets = burnLevelCookingGauntlets;
        this.requirement = requirement;
        this.requirementMessage = requirementMessage;
    }

    public static final Map<Integer, Double> COOKING_EXPERIENCE = new HashMap<>();

    static {
        for (Food food : Food.values()) {
            if (food.ordinal() > 24)    // End of fish
                break;
            COOKING_EXPERIENCE.put(food.rawID, food.experience);
        }
    }
}

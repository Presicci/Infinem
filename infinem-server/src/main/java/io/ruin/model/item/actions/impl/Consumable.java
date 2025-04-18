package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.map.Region;
import io.ruin.utility.Color;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.Hit;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.skillcapes.PrayerSkillCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.herblore.BarbarianMix;
import io.ruin.model.skills.herblore.Potion;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventRunnable;
import io.ruin.process.tickevent.TickEventType;

import java.util.function.Consumer;

public class Consumable {

    /**
     * Eating
     */

    static {
        // Penance eggs
        registerEat(Items.BLUE_EGG, 10, player -> {
            player.getStats().get(StatType.Defence).boost(5, 0.15);
            player.sendMessage("You eat the egg.");
        });
        registerEat(Items.GREEN_EGG, 20, "egg");
        registerEat(Items.RED_EGG, 7, player -> {
            player.getStats().get(StatType.Prayer).restore(7, 0.27);
            player.sendMessage("You eat the egg.");
        });

        // Larupia
        registerEat(29146, 6, player -> {
            player.sendMessage("You eat the larupia meat.");
            player.addTickEvent(new TickEvent(TickEventType.HUNTER_MEAT, 7, () -> player.incrementHp(5)));
        });
        // Graahk
        registerEat(29149, 8, player -> {
            player.sendMessage("You eat the graahk meat.");
            player.addTickEvent(new TickEvent(TickEventType.HUNTER_MEAT, 7, () -> player.incrementHp(6)));
        });
        // Kyatt
        registerEat(29152, 9, player -> {
            player.sendMessage("You eat the kyatt meat.");
            player.addTickEvent(new TickEvent(TickEventType.HUNTER_MEAT, 7, () -> player.incrementHp(8)));
        });
        // Pyre fox
        registerEat(29137, 11, player -> {
            player.sendMessage("You eat the pyre fox meat.");
            player.addTickEvent(new TickEvent(TickEventType.HUNTER_MEAT, 7, () -> player.incrementHp(8)));
        });
        // Sunlight antelope
        registerEat(29140, 12, player -> {
            player.sendMessage("You eat the antelope meat.");
            player.addTickEvent(new TickEvent(TickEventType.HUNTER_MEAT, 7, () -> player.incrementHp(9)));
        });
        // Moonlight antelope
        registerEat(29143, 14, player -> {
            player.sendMessage("You eat the antelope meat.");
            player.addTickEvent(new TickEvent(TickEventType.HUNTER_MEAT, 7, () -> {
                player.incrementHp(12);
                player.curePoison(0);
            }));
        });
        registerEat(Items.STRAWBERRY, 0, player -> {
            int hpLevel = player.getStats().get(StatType.Hitpoints).fixedLevel;
            player.sendMessage("You eat the strawberry.");
            if (player.incrementHp((int) (1 + (hpLevel * 0.06))) > 0)
                player.sendMessage("It heals some health.");
        });
        registerEat(Items.TOMATO, 2, "tomato");
        registerEat(Items.CHOCOLATE_BAR, 3, "chocolate bar");
        registerEat(Items.CHOCICE, 7, "chocice");
        registerEat(Items.COOKED_OOMLIE_WRAP, 14, p -> p.sendFilteredMessage("You eat the oomlie meat; it tastes very gamy."));
        registerEat(Items.CHOPPED_TOMATO, 2, "chopped tomato");
        registerEat(Items.GIANT_CARP, 6, "giant carp");
        registerEat(Items.LAVA_EEL, 11, "lava eel");
        registerEat(Items.FIELD_RATION, 10, "field ration");
        registerEat(Items.BANANA_STEW, Items.BOWL, 11, "banana stew");
        registerEat(Items.MONKEY_NUTS, 4, "monkey nuts");
        registerEat(Items.EQUA_LEAVES, 1, "equa leaves");
        registerEat(Items.TRIANGLE_SANDWICH, 6, "triangle sandwich");
        registerEat(Items.LIME, 2, "lime");
        registerEat(Items.LEMON_CHUNKS, 2, "lemon chunks");
        registerEat(Items.LEMON_SLICES, 2, "lemon slices");
        registerEat(Items.SLICED_BANANA, 2, "sliced banana");
        registerEat(Items.LIME_SLICES, 2, "lime slices");
        registerEat(Items.LIME_CHUNKS, 2, "lime chunks");
        registerEat(Items.ORANGE_CHUNKS, 2, "orange chunks");
        registerEat(Items.ORANGE_SLICES, 2, "orange slices");
        registerEat(Items.PINEAPPLE_RING, 2, "pineapple ring");
        registerEat(Items.PINEAPPLE_CHUNKS, 2, "pineapple chunks");
        registerEat(Items.COOKED_SLIMY_EEL, 10, "slimy eel");
        registerEat(Items.SPINACH_ROLL, 2, "spinach roll");
        registerEat(24785, 5, p -> p.sendFilteredMessage("It heals some health, and tastes concerningly nice."));
        registerEat(Items.CHOPPED_ONION, 1, p -> p.sendFilteredMessage("It's sad to see a grown " + (p.getAppearance().isMale() ? "man" : "woman") + " cry."));
        registerEat(1957, 1, p -> p.sendFilteredMessage("It's sad to see a grown " + (p.getAppearance().isMale() ? "man" : "woman") + " cry."));
        registerEat(1942, 1, p -> p.sendFilteredMessage("You eat the potato. Yuck!"));
        registerEat(1965, 1, p -> p.sendFilteredMessage("You eat the cabbage. Yuck!"));
        registerEat(10476, 1, 1, true, p -> {
            p.getMovement().restoreEnergy(10);
            p.getTaskManager().doLookupByUUID(77, 1);   // Eat some Purple Sweets
        });
        registerEat(Items.PAPAYA_FRUIT, 8, p -> p.getMovement().restoreEnergy(5));
        registerEat(1963, 2, "banana");
        registerEat(2162, 2, "king worm");
        registerEat(6883, 8, "peach");
        registerEat(1883, 19, p -> {
            p.sendMessage("You eat the kebab.");
            p.forceText("Yum!");
        });
        registerEat(1885, 19, p -> {
            p.sendMessage("You eat the kebab.");
            p.forceText("Yum!");
        });
        registerEat(1971, 0, player -> {
            int hpLevel = player.getStats().get(StatType.Hitpoints).fixedLevel;
            int rand = Random.get(1, 128);
            player.sendMessage("You eat the kebab.");
            if (rand <= 80) {
                if (player.incrementHp((int) (3 + (hpLevel * 0.07))) > 0)
                    player.sendMessage("It heals some health.");
            } else if (rand <= 112) {
                player.incrementHp((int) (6 + (hpLevel * 0.14)));
                player.sendMessage("That was a good kebab. You feel a lot better.");
            } else if (rand <= 116) {
                player.sendMessage("That kebab didn't seem to do a lot.");
            } else if (rand <= 120) {
                int randStat = Random.get(0, 21);
                if (randStat == StatType.Hitpoints.ordinal()) randStat = 22;    // Can't drain hitpoints
                player.getStats().get(randStat).drain(3);
                player.sendMessage("That tasted a bit dodgy. You feel a bit ill.");
                player.sendMessage("Eating the kebab has damaged your " + StatType.get(randStat).name() + " stat.");
            } else if (rand <= 124) {
                int randStat = Random.get(7, 22);
                player.getStats().get(randStat).drain(3);
                player.getStats().get(StatType.Attack).drain(3);
                player.getStats().get(StatType.Strength).drain(3);
                player.getStats().get(StatType.Defence).drain(3);
                player.sendMessage("That tasted a bit dodgy. You feel very ill.");
                player.sendMessage("Eating the kebab has done damage to some of your stats.");
            } else {
                player.getStats().get(StatType.Attack).boost(2, 0);
                player.getStats().get(StatType.Strength).boost(2, 0);
                player.getStats().get(StatType.Defence).boost(2, 0);
                player.incrementHp((int) (7 + (hpLevel * 0.24)));
                player.sendMessage("Wow, that was an amazing kebab! You feel really invigorated.");
            }
        });
        registerEat(4608, 0, player -> {
            int hpLevel = player.getStats().get(StatType.Hitpoints).fixedLevel;
            int rand = Random.get(1, 32);
            player.sendMessage("You eat the extra-hot kebab.");
            if (rand <= 20) {
                if (player.incrementHp((int) (3 + (hpLevel * 0.07))) > 0)
                    player.sendMessage("It heals some health.");
            } else if (rand <= 31) {
                // Nothing happens
            } else {
                int randStat = Random.get(0, 21);
                if (randStat == StatType.Hitpoints.ordinal()) randStat = 22;    // Can't drain hitpoints
                player.getStats().get(randStat).drain(3);
                player.sendMessage("That tasted a bit dodgy. You feel a bit ill.");
                player.sendMessage("Eating the kebab has damaged your " + StatType.get(randStat).name() + " stat.");
            }
        });
        // Varlamorian kebab
        registerEat(29900, 0, player -> {
            int hpLevel = player.getStats().get(StatType.Hitpoints).fixedLevel;
            int rand = Random.get(1, 32);
            player.sendMessage("You eat the kebab.");
            if (rand <= 20) {
                if (player.incrementHp((int) (3 + (hpLevel * 0.07))) > 0)
                    player.sendMessage("It heals some health.");
            } else if (rand <= 28) {
                player.incrementHp((int) (6 + (hpLevel * 0.14)));
                player.sendMessage("That was a good kebab. You feel a lot better.");
            } else if (rand <= 31) {
                player.getStats().get(StatType.Attack).boost(2, 0);
                player.getStats().get(StatType.Strength).boost(2, 0);
                player.getStats().get(StatType.Defence).boost(2, 0);
                player.incrementHp((int) (7 + (hpLevel * 0.24)));
                player.sendMessage("Wow, that was an amazing kebab! You feel really invigorated.");
            } else {
                player.sendMessage("It doesn't seem to do a lot.");
            }
        });
        registerEat(2108, 2, "orange");
        registerEat(22929, 10, "dragonfruit");
        registerEat(Items.STEW, Items.BOWL, 11, "stew");

        registerEat(2309, 5, "bread");
        registerCake(1891, 1893, 1895, 12, "cake");
        registerCake(1897, 1899, 1901, 15, "chocolate cake");

        registerPizza(2289, 2291, 14, "pizza");
        registerPizza(2293, 2295, 16, "meat pizza");
        registerPizza(2297, 2299, 18, "anchovy pizza");
        registerPizza(2301, 2303, 22, "pineapple pizza");

        registerPie(2325, 2333, 10, "redberry pie", null);
        registerPie(2327, 2331, 12, "meat pie", null);
        registerPie(2323, 2335, 14, "apple pie", null);
        registerPie(7178, 7180, 12, "garden pie", p -> p.getStats().get(StatType.Farming).boost(3, 0.0));
        registerPie(7188, 7190, 12, "fish pie", p -> p.getStats().get(StatType.Fishing).boost(3, 0.0));
        registerPie(Items.BOTANICAL_PIE, Items.HALF_A_BOTANICAL_PIE, 14, "botanical pie", p -> p.getStats().get(StatType.Herblore).boost(4, 0.0));
        registerPie(21690, 21687, 16, "mushroom pie", p -> p.getStats().get(StatType.Crafting).boost(4, 0.0));
        registerPie(7198, 7200, 16, "admiral pie", p -> p.getStats().get(StatType.Fishing).boost(5, 0.0));
        registerPie(22795, 22792, 20, "dragonfruit pie", p -> p.getStats().get(StatType.Fletching).boost(4, 0.0));
        registerPie(7208, 7210, 22, "wild pie", p -> {
            p.getStats().get(StatType.Ranged).boost(4, 0.0);
            p.getStats().get(StatType.Slayer).boost(5, 0.0);
        });
        registerPie(7218, 7220, 22, "summer pie", p -> p.getStats().get(StatType.Agility).boost(5, 0.0));

        registerDrink(Items.JUG_OF_WINE, Items.JUG, 11, 3, p -> p.getStats().get(StatType.Attack).drain(2));
        registerEat(7082, 1923, 5, "fried mushrooms");
        registerEat(2011, 1923, 19, "curry");

        registerEat(Items.BAKED_POTATO, 4, "baked potato");
        registerEat(Items.POTATO_WITH_BUTTER, 14, "potato with butter");
        registerEat(7054, 14, "chilli potato");
        registerEat(Items.EGG_POTATO, 16, "egg potato");
        registerEat(7058, 20, "mushroom potato");
        registerEat(6705, 16, "potato with cheese");
        registerEat(7060, 22, "tuna potato");

        registerEat(Items.COOKED_FISHCAKE, 11, "fishcake");
        registerEat(Items.COOKED_SWEETCORN, 10, "sweetcorn");
        registerEat(Items.SWEETCORN_2, 10, "sweetcorn");
        registerEat(Items.TUNA_AND_CORN, 13, "tuna and corn");
        registerEat(Items.FRIED_MUSHROOMS, 5, "fried mushroom");
        registerEat(Items.FRIED_ONIONS, 5, "fried onions");
        registerEat(Items.MUSHROOM_ONION, 11, "mushroom and onion");
        registerEat(Items.EGG_AND_TOMATO, 8, "egg and tomato");
        registerEat(Items.SCRAMBLED_EGG, 5, "scrambled egg");
        registerEat(Items.CHILLI_CON_CARNE, 5, "chilli con carne");
        registerEat(Items.SPICY_SAUCE, 2, "spicy sauce");
        registerEat(Items.MINCED_MEAT, 2, "minced meat");

        registerEat(2140, 4, "chicken");
        registerEat(2142, 4, "meat");
        registerEat(Items.UGTHANKI_MEAT, 3, "meat");

        registerEat(315, 3, "shrimps");
        registerEat(319, 1, "anchovies");
        registerEat(325, 4, "sardine");
        registerEat(347, 5, "herring");
        registerEat(355, 6, "mackerel");
        registerEat(333, 7, "trout");
        registerEat(339, 7, "cod");
        registerEat(351, 8, "pike");
        registerEat(329, 9, "salmon");
        registerEat(361, 10, "tuna");
        registerEat(379, 12, "lobster");
        registerEat(365, 13, "bass");
        registerEat(373, 14, "swordfish");
        registerEat(7946, 16, "monkfish");
        registerEat(385, 20, "shark");
        registerEat(397, 21, "sea turtle");
        registerEat(391, 22, "manta ray");
        registerEat(11936, 22, "dark crab");

        registerEat(20856, 4, "pysk");
        registerEat(20858, 8, "suphi");
        registerEat(20860, 8, "leckish");
        registerEat(20862, 12, "brawk");
        registerEat(20864, 17, "mycril");
        registerEat(20866, 20, "roqed");
        registerEat(20868, 23, "kyren");

        registerEat(20871, 4, "guanic");
        registerEat(20873, 8, "praeal");
        registerEat(20875, 8, "giral");
        registerEat(20877, 12, "phluxia");
        registerEat(20879, 17, "kryket");
        registerEat(20881, 20, "murng");
        registerEat(20883, 23, "psykk");

        registerEat(403, 4, "seaweed");
        registerEat(2152, 3, "toad's legs");

        // Anglerfish
        ItemDefinition.get(13441).consumable = true;
        ItemAction.registerInventory(13441, "eat", (player, item) -> {
            if(eatAngler(player, item))
                player.sendFilteredMessage("You eat the anglerfish.");
        });
        /*
         * Blighted Food
         */
        registerEatBlightedComboFood(24595, 18, "blighted karambwan");
        registerBlightedEat(24589, 22, "blighted manta ray");
        // Blighted Anglerfish
        ItemDefinition.get(24592).consumable = true;
        ItemAction.registerInventory(24592, "eat", (player, item) -> {
            if (player.wildernessLevel <= 0) {
                player.sendMessage("The blighted anglerfish can be eaten only in the Wilderness.");
                return;
            }
            if (eatAngler(player, item))
                player.sendFilteredMessage("You eat the blighted anglerfish.");
        });

        ItemAction.registerInventory(Items.PINEAPPLE, "eat", (player, item) -> {
            player.sendMessage("You try to take a bite... to no avail.");
        });

        registerCrabMeat(2, "crab meat", 7521, 7523, 7524, 7525, 7526);

        // Dairy
        registerEat(1985, 2, "cheese");
        registerEat(2130, 1, "cream");

        /**
         * Combo foods
         */
        registerEatComboFood(3144, 18, "karambwan");
        // Crunchies
        registerEatComboFood(2217, 8, "toad crunchies");
        registerEatComboFood(2243, 8, "toad crunchies");    // Premade
        registerEatComboFood(2213, 7, "spicy crunchies");
        registerEatComboFood(2241, 7, "spicy crunchies");   // Premade
        registerEatComboFood(2205, 8, "worm crunchies");
        registerEatComboFood(2237, 8, "worm crunchies");    // Premade
        registerEatComboFood(2209, 7, "chocchip crunchies");
        registerEatComboFood(2239, 7, "chocchip crunchies");    // Premade
        // Batta
        registerEatComboFood(2277, 11, "fruit batta");
        registerEatComboFood(2225, 11, "fruit batta");  // Premade
        registerEatComboFood(2255, 11, "toad batta");
        registerEatComboFood(2221, 11, "toad batta");   // Premade
        registerEatComboFood(2253, 11, "worm batta");
        registerEatComboFood(2219, 11, "worm batta");   // Premade
        registerEatComboFood(2281, 11, "vegetable batta");
        registerEatComboFood(2227, 11, "vegetable batta");    // Premade
        registerEatComboFood(2259, 11, "cheese and tomato batta");
        registerEatComboFood(2223, 11, "cheese and tomato batta");  // Premade
        // Misc gnome
        registerEatComboFood(2191, 12, "worm hole");
        registerEatComboFood(2233, 12, "worm hole");    // Premade
        registerEatComboFood(2195, 12, "vegetable ball");
        registerEatComboFood(2235, 12, "vegetable ball");   // Premade
        registerEatComboFood(2185, 15, "chocolate bomb");
        registerEatComboFood(2229, 15, "chocolate bomb");   // Premade
        registerEatComboFood(2187, 15, "tangled toad's legs");
        registerEatComboFood(2231, 15, "tangled toad's legs");  // Premade

        /**
         * Non-potion drinks
         */
        //Asgarnian ale
        registerDrink(1905, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Strength).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });

        //Asgarnian ale(m)
        registerDrink(5739, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Strength).boost(3, 0);
            p.getStats().get(StatType.Attack).drain(6);
        });

        //Axeman's folly
        registerDrink(5751, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Woodcutting).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(3);
            p.getStats().get(StatType.Strength).drain(3);
        });

        //Axeman's folly(m)
        registerDrink(5751, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Woodcutting).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(4);
            p.getStats().get(StatType.Strength).drain(4);
        });

        //Bloody bracer
        registerDrink(22430, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Prayer).drain(2, 0.04);
            p.sendMessage("The tincture tastes great, but you feel an evil within you stir.");
        });

        //Chef's delight
        registerDrink(5755, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Cooking).boost(1, 5);
            p.getStats().get(StatType.Attack).drain(2);
            p.getStats().get(StatType.Strength).drain(2);
        });

        //Chef's delight(m)
        registerDrink(5757, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Cooking).boost(2, 5);
            p.getStats().get(StatType.Attack).drain(3);
            p.getStats().get(StatType.Strength).drain(3);
        });

        //Cider
        registerDrink(5763, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Farming).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(2);
            p.getStats().get(StatType.Strength).drain(2);
        });

        //Cider(m)
        registerDrink(5765, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Farming).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(5);
            p.getStats().get(StatType.Strength).drain(5);
        });

        //Dragon bitter
        registerDrink(1911, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Strength).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });

        //Dragon bitter(m)
        registerDrink(5745, 1919, 2, 3, p -> {
            p.getStats().get(StatType.Strength).boost(3, 0);
            p.getStats().get(StatType.Attack).drain(6);
        });

        //Dwarven stout
        registerDrink(1913, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Smithing).boost(1, 0);
            p.getStats().get(StatType.Mining).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(2, 0.04);
            p.getStats().get(StatType.Strength).drain(2, 0.04);
            p.getStats().get(StatType.Defence).drain(2, 0.04);
        });

        //Dwarven stout(m)
        registerDrink(5747, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Smithing).boost(2, 0);
            p.getStats().get(StatType.Mining).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(7);
            p.getStats().get(StatType.Strength).drain(7);
            p.getStats().get(StatType.Defence).drain(7);
        });

        //Elven dawn
        registerDrink(23948, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Agility).boost(1, 0);
            p.getStats().get(StatType.Strength).drain(1);
        });

        //Greenman's ale
        registerDrink(1909, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Herblore).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(3);
            p.getStats().get(StatType.Strength).drain(3);
            p.getStats().get(StatType.Defence).drain(3);
        });

        //Greenman's ale(m)
        registerDrink(5743, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Herblore).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(2);
            p.getStats().get(StatType.Strength).drain(2);
            p.getStats().get(StatType.Defence).drain(2);
        });

        //Grog
        registerDrink(1915, 1919, 3, 3, p -> {
            p.getStats().get(StatType.Strength).boost(3, 0);
            p.getStats().get(StatType.Attack).drain(6);
        });

        //Moonlight mead
        registerDrink(2955, 1919, 4, 3, p -> {
            p.sendMessage("You drink the foul smelling brew. It tastes like something just died in your mouth.");
        });

        //Moonlight mead(m)
        registerDrink(5749, 1919, 6, 3, p -> {
            p.sendMessage("You drink the foul smelling brew. It tastes like something just died in your mouth.");
        });

        //Slayer's respite
        registerDrink(5759, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Slayer).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(2);
            p.getStats().get(StatType.Strength).drain(2);
        });

        //Slayer's respite(m)
        registerDrink(5761, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Slayer).boost(4, 0);
            p.getStats().get(StatType.Attack).drain(2);
            p.getStats().get(StatType.Strength).drain(2);
        });

        //Wizard's mind bomb
        registerDrink(1907, 1919, 1, 3, p -> {
            if (p.getStats().checkFixed(StatType.Magic, 50)) {
                p.getStats().get(StatType.Magic).boost(3, 0);
            } else {
                p.getStats().get(StatType.Magic).boost(2, 0);
            }
            p.getStats().get(StatType.Attack).drain(1, 0.05);
            p.getStats().get(StatType.Strength).drain(1, 0.05);
            p.getStats().get(StatType.Defence).drain(1, 0.05);
        });

        //Wizard's mind bomb(m)
        registerDrink(5741, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Magic).boost(3, 0.02);
            p.getStats().get(StatType.Attack).drain(5);
            p.getStats().get(StatType.Strength).drain(5);
            p.getStats().get(StatType.Defence).drain(5);
        });

        // Cup of tea
        registerDrink(712, 1980, 3, 3, p -> {
            p.getStats().get(StatType.Attack).boost(3, 0.0);
            p.forceText("Aaah, nothing like a nice cuppa tea!");
        });

        // Beer
        registerDrink(1917, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Strength).boost(2, 0.04);
            p.getStats().get(StatType.Attack).drain(1, 0.06);
        });

        // Sunbeam ale
        registerDrink(29409, 1919, 1, 3, p -> {
            p.getStats().get(StatType.Strength).boost(1, 0);
            p.getStats().get(StatType.Agility).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(1, 0.05);
        });

        // Vodka
        registerDrink(Items.VODKA, -1, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(1, 0.05);
            p.getStats().get(StatType.Attack).drain(3, 0.02);
        });

        // Rum
        registerDrink(Items.RUM, -1, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(1, 0.05);
            p.getStats().get(StatType.Attack).drain(3, 0.02);
        });

        // Whisky
        registerDrink(Items.WHISKY, -1, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(1, 0.05);
            p.getStats().get(StatType.Attack).drain(3, 0.02);
        });

        // Gin
        registerDrink(Items.GIN, -1, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(1, 0.05);
            p.getStats().get(StatType.Attack).drain(3, 0.02);
        });

        // Brandy
        registerDrink(Items.BRANDY, -1, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(1, 0.05);
            p.getStats().get(StatType.Attack).drain(3, 0.02);
        });

        // Moon-lite
        registerDrink(29418, -1, 5, 3, p -> {
            p.sendMessage("You drink the Moonlite. It's very strong and leaves you feeling a bit dizzy.");
            p.getStats().get(StatType.Strength).boost(5, 0);
            p.getStats().get(StatType.Attack).drain(4, 0);
        });

        // Metztonalli white
        registerDrink(29955, -1, 16, 3, p -> {
            p.sendMessage("You drink the Metztonalli white. You find yourself feeling refreshed.");
            p.getStats().get(StatType.Runecrafting).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Firemaking).drain(1, 0);
        });

        // Blackbird red
        registerDrink(29944, -1, 16, 3, p -> {
            p.sendMessage("You drink the Blackbird red. Rich flavours flutter across your tongue.");
            p.getStats().get(StatType.Hunter).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Slayer).drain(1, 0);
        });

        // Eclipse red
        registerDrink(29415, -1, 16, 3, p -> {
            p.sendMessage("You drink the Eclipse red. It tastes great, but maybe not 1,000 coins great.");
            p.getStats().get(StatType.Woodcutting).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Fletching).drain(1, 0);
        });

        // Blood pint
        registerDrink(24774, -1, 16, 3, p -> {
            p.sendMessage("You drink the Blood Pint. It tastes unsurprisingly awful and makes you feel a bit sick.");
            p.getStats().get(StatType.Attack).boost(2, 0.04);
            p.getStats().get(StatType.Strength).boost(2, 0.04);
            p.getStats().get(StatType.Magic).drain(2, 0.04);
            p.getStats().get(StatType.Prayer).drain(2, 0.04);
        });

        // Steamforge brew
        registerDrink(29412, 1919, 1, 3, p -> {
            p.sendMessage("You drink the Steamforge brew. It has an odd metallic taste to it.");
            p.getStats().get(StatType.Mining).boost(1, 0);
            p.getStats().get(StatType.Magic).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(2, 0.05);
            p.getStats().get(StatType.Defence).drain(2, 0.05);
        });

        // Trapper's tipple
        registerDrink(29277, 1919, 1, 3, p -> {
            p.sendMessage("You drink the Trapper's Tipple and feel ready to catch some creatures. You feel less able to fight said creatures.");
            p.getStats().get(StatType.Hunter).boost(2, 0);
            p.getStats().get(StatType.Attack).drain(2, 0);
            p.getStats().get(StatType.Strength).drain(1, 0);
        });

        // Sun-shine
        registerDrink(29421, -1, 1, 3, p -> {
            p.sendFilteredMessage("You drink the Sunshine. It's got quite the kick to it!");
            p.getStats().get(StatType.Strength).boost(1, 0.05);
            p.getStats().get(StatType.Attack).drain(4, 0);
        });

        // Lizardkicker
        registerDrink(25826, 1919, 0, 3, p -> {
            p.getStats().get(StatType.Ranged).boost(4, 0.00);
            p.getStats().get(StatType.Strength).drain(2, 0.04);
            p.getStats().get(StatType.Magic).drain(2, 0.04);
            p.sendMessage("You drink the Lizardkicker. It tastes like swampwater, but you feel like your eyesight has improved slightly.");
            p.getTaskManager().doLookupByUUID(947);     // Drink a LizardKicker
        });

        //Beer tankard
        registerDrink(3803, 3805, 4, 3, p -> {
            p.getStats().get(StatType.Strength).boost(1, 0.02);
            p.getStats().get(StatType.Attack).drain(1, 0.06);
        });

        //Keg of beer
        registerDrink(3801, -1, 15, 10, p -> {
            p.animate(1330);
            p.getStats().get(StatType.Strength).boost(2, 0.10);
            p.getStats().get(StatType.Attack).drain(5, 0.50);
            p.sendMessage("You chug the keg. You feel reinvigorated...");
            p.sendMessage("...but extremely drunk, too.");
        });

        // Chilhuac red
        registerDrink(29947, -1, 16, 3, p -> {
            p.sendMessage("You drink the Chilhuac red. You feel a warmth spreading through you.");
            p.getStats().get(StatType.Firemaking).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Cooking).drain(1, 0);
        });

        // Chichilihui rosé
        registerDrink(29963, -1, 16, 3, p -> {
            p.sendMessage("You drink the Chichilihui rosé. A graceful calmness settles over you.");
            p.getStats().get(StatType.Herblore).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Farming).drain(1, 0);
        });

        // Tonameyo white
        registerDrink(29958, -1, 16, 3, p -> {
            p.sendMessage("You drink the Tonameyo white. You feel a divine warmth surround you.");
            p.getStats().get(StatType.Prayer).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Thieving).drain(1, 0);
        });

        // Imperial rosé
        registerDrink(29966, -1, 16, 3, p -> {
            p.sendMessage("You drink the Imperial rosé. You find yourself captivated by the complex flavours.");
            p.getStats().get(StatType.Slayer).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Agility).drain(1, 0);
        });

        // Ixcoztic white
        registerDrink(29952, -1, 16, 3, p -> {
            p.sendMessage("You drink the Ixcoztic white. The citrusy profile perfectly energises your palate.");
            p.getStats().get(StatType.Farming).boost(1, 0);
            p.getStats().get(StatType.Attack).drain(5, 0);
            p.getStats().get(StatType.Herblore).drain(1, 0);
        });

        /*
         * Gnome drinks
         */
        registerDrink(Items.BLURBERRY_SPECIAL, Items.COCKTAIL_GLASS, 6, 3, p -> {
            p.getStats().get(StatType.Strength).boost(6, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
        registerDrink(Items.PREMADE_BLURB_SP, Items.COCKTAIL_GLASS, 6, 3, p -> {
            p.getStats().get(StatType.Strength).boost(6, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
        registerDrink(Items.CHOC_SATURDAY, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(7, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
        registerDrink(Items.PREMADE_CHOC_SDY, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(7, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
        registerDrink(Items.DRUNK_DRAGON, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(7, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
        registerDrink(Items.PREMADE_DR_DRAGON, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(7, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
        registerDrink(Items.NETTLEWATER, Items.BOWL, 1, 3, p -> {});
        registerDrink(Items.NETTLE_TEA, Items.BOWL, 3, 3, p -> {
            p.getMovement().restoreEnergy(5);
        });
        registerDrink(Items.FRUIT_BLAST, Items.COCKTAIL_GLASS, 9, 3, p -> {});
        registerDrink(Items.PREMADE_FR_BLAST, Items.COCKTAIL_GLASS, 9, 3, p -> {});
        registerDrink(Items.PINEAPPLE_PUNCH, Items.COCKTAIL_GLASS, 9, 3, p -> {});
        registerDrink(Items.PREMADE_P_PUNCH, Items.COCKTAIL_GLASS, 9, 3, p -> {});
        registerDrink(Items.SHORT_GREEN_GUY, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(4, 0);
            p.getStats().get(StatType.Attack).drain(3);
        });
        registerDrink(Items.PREMADE_SGG, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(4, 0);
            p.getStats().get(StatType.Attack).drain(3);
        });
        registerDrink(Items.WIZARD_BLIZZARD, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(6, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
        registerDrink(Items.PREMADE_WIZ_BLZD, Items.COCKTAIL_GLASS, 5, 3, p -> {
            p.getStats().get(StatType.Strength).boost(6, 0);
            p.getStats().get(StatType.Attack).drain(4);
        });
    }

    private static void registerEat(int id, int heal, String name) {
        registerEat(id, -1, heal, 3, false, p -> p.sendFilteredMessage("You eat the " + name + "."));
    }

    private static void registerEat(int id, int newId, int heal, String name) {
        registerEat(id, newId, heal, 3,false, p -> p.sendFilteredMessage("You eat the " + name + "."));
    }

    private static void registerEat(int id, int heal, Consumer<Player> eatAction) {
        registerEat(id, -1, heal, 3, false, eatAction);
    }

    private static void registerEat(int id, int heal, int ticks, boolean stackable, Consumer<Player> eatAction) {
        ItemDefinition.get(id).consumable = true;
        ItemAction.registerInventory(id, "eat", (player, item) -> {
            if(eat(player, item, -1, heal, ticks, stackable, false))
                eatAction.accept(player);
        });
    }

    private static void registerEat(int id, int newId, int heal, int ticks, boolean stackable, Consumer<Player> eatAction) {
        ItemDefinition.get(id).consumable = true;
        ItemAction.registerInventory(id, "eat", (player, item) -> {
            if(eat(player, item, newId, heal, ticks, stackable, false))
                eatAction.accept(player);
        });
    }

    private static void registerBlightedEat(int id, int heal, String name) {
        ItemDefinition.get(id).consumable = true;
        ItemAction.registerInventory(id, "eat", (player, item) -> {
            if (player.wildernessLevel <= 0) {
                player.sendMessage("The " + name + " can be eaten only in the Wilderness.");
                return;
            }
            if(eat(player, item, -1, heal, 3, false, false))
                player.sendFilteredMessage("You eat the " + name + ".");
        });
    }

    private static void registerDrink(int id, int newId, int heal, String name) {
        registerDrink(id, newId, heal, 3, p -> p.sendFilteredMessage("You drink the " + name + "."));
    }

    private static void registerDrink(int id, int newId, int heal, int ticks, Consumer<Player> eatAction) {
        ItemDefinition.get(id).consumable = true;
        ItemAction.registerInventory(id, "drink", (player, item) -> {
            if (eat(player, item, newId, heal, ticks, false, true))
                eatAction.accept(player);
        });
    }

    private static void registerEatComboFood(int id, int heal, String name) {
        ItemDefinition.get(id).consumable = true;
        ItemAction.registerInventory(id, "eat", (player, item) -> {
            if(eatComboFood(player, item, heal))
                player.sendFilteredMessage("You eat the " + name + ".");
        });
    }

    private static void registerEatBlightedComboFood(int id, int heal, String name) {
        ItemDefinition.get(id).consumable = true;
        ItemAction.registerInventory(id, "eat", (player, item) -> {
            if (player.wildernessLevel <= 0) {
                player.sendMessage("The " + name + " can be eaten only in the Wilderness.");
                return;
            }
            if(eatComboFood(player, item, heal))
                player.sendFilteredMessage("You eat the " + name + ".");
        });
    }

    private static void registerCake(int firstId, int secondId, int thirdId, int heal, String name) {
        heal /= 3;
        registerEat(firstId, secondId, heal, 2,false, p -> p.sendFilteredMessage("You eat part of the " + name + "."));
        registerEat(secondId, thirdId, heal, 2,false, p -> p.sendFilteredMessage("You eat some more of the " + name + "."));
        registerEat(thirdId, -1, heal, 3,false, p -> p.sendFilteredMessage("You eat the slice of " + name + "."));
    }

    private static void registerCrabMeat(int heal, String name, int... itemIds) {
        for (int itemId : itemIds) {
            ItemDefinition.get(itemId).consumable = true;
            ItemAction.registerInventory(itemId, "eat", (player, item) -> {
                if (eat(player, item,
                        item.getId() == 7526 ? -1 : item.getId() + (item.getId() == 7521 ? 2 : 1),
                        heal, 2, 2, false, false))
                    player.sendFilteredMessage("You eat some " + name + ".");
            });
        }
    }

    private static void registerPizza(int fullId, int halfId, int heal, String name) {
        heal /= 2;
        registerEat(fullId, halfId, heal, 1,false, p -> p.sendFilteredMessage("You eat half of the " + name + "."));
        registerEat(halfId, -1, heal, 2,false, p -> p.sendFilteredMessage("You eat the remaining " + name + "."));
    }

    private static void registerPie(int fullId, int halfId, int heal, String name, Consumer<Player> postEffect) {
        heal /= 2;
        registerEat(fullId, halfId, heal, 1, false, p -> {
            p.sendFilteredMessage("You eat half of the " + name + ".");
            if(postEffect != null)
                postEffect.accept(p);
        });
        registerEat(halfId, Items.PIE_DISH, heal, 2, false, p -> {
            p.sendFilteredMessage("You eat the remaining " + name + ".");
            if(postEffect != null)
                postEffect.accept(p);
        });
    }

    private static boolean eat(Player player, Item item, int newId, int heal, int ticks, boolean stackable, boolean drink) {
        return eat(player, item, newId, heal, ticks, 3, stackable, drink);
    }

    private static boolean eat(Player player, Item item, int newId, int heal, int ticks, int attackTicks, boolean stackable, boolean drink) {
        if(player.isLocked() || player.isStunned())
            return false;
        if(player.eatDelay.isDelayed() || player.karamDelay.isDelayed() || player.potDelay.isDelayed())
            return false;
        if ((item.getId() == 10531 || item.getId() == 10532 || item.getId() == 10533) && !Region.get(7508).bounds.inBounds(player)) {
            player.sendMessage("You can only eat this when fighting the penance queen.");
            return false;
        }
        if(DuelRule.NO_FOOD.isToggled(player)) {
            player.sendMessage("Food has been disabled for this duel!");
            return false;
        }
        if (stackable)
            item.remove(1);
        else if(newId == -1)
            item.remove();
        else
            item.setId(newId);
        if (drink) {
            animDrink(player);
        } else {
            animEat(player);
        }
        player.incrementHp(heal);
        player.eatDelay.delay(ticks);
        player.getCombat().delayAttack(attackTicks);
        return true;
    }

    private static boolean eatComboFood(Player player, Item item, int heal) {
        if(player.isLocked() || player.isStunned())
            return false;
        if(player.karamDelay.isDelayed())
            return false;
        if(DuelRule.NO_FOOD.isToggled(player)) {
            player.sendMessage("Food has been disabled for this duel!");
            return false;
        }
        item.remove();
        animEat(player);
        player.incrementHp(heal);
        player.karamDelay.delay(3);
        player.getCombat().delayAttack(player.eatDelay.isDelayed() ? 1 : 2); //delays combat 1 tick less than other food on rs
        return true;
    }

    private static boolean eatAngler(Player player, Item item) {
        if(player.isLocked() || player.isStunned())
            return false;
        if(player.eatDelay.isDelayed() || player.karamDelay.isDelayed() || player.potDelay.isDelayed())
            return false;
        if(DuelRule.NO_FOOD.isToggled(player)) {
            player.sendMessage("Food has been disabled for this duel!");
            return false;
        }
        item.remove();
        animEat(player);
        int hp = player.getHp();
        int maxHp = player.getMaxHp();
        int c;
        if(maxHp <= 24)
            c = 2;
        else if(maxHp <= 49)
            c = 4;
        else if(maxHp <= 74)
            c = 6;
        else if(maxHp <= 92)
            c = 8;
        else
            c = 13;
        int restore = (maxHp / 10) + c;
        int newHp = Math.min(hp + restore, maxHp + restore);
        player.setHp(newHp);
        player.eatDelay.delay(3);
        player.getCombat().delayAttack(3);
        return true;
    }

    public static void animEat(Player player) {
        if(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 4084)
            player.animate(1469);
        else if (player.seat != null)
            player.animate(player.seat.getEatAnimation(player));
        else
            player.animate(829);
        player.privateSound(2393);
        player.resetActions(true, player.getMovement().following != null, true);
    }

    /**
     * Drinking
     */

    static {
        registerPotion(Potion.ATTACK, p -> p.getStats().get(StatType.Attack).boost(3, 0.10));
        registerPotion(Potion.STRENGTH, p -> p.getStats().get(StatType.Strength).boost(3, 0.10));
        registerPotion(Potion.DEFENCE, p -> p.getStats().get(StatType.Defence).boost(3, 0.10));
        registerPotion(Potion.COMBAT, p -> {
            p.getStats().get(StatType.Attack).boost(3, 0.10);
            p.getStats().get(StatType.Strength).boost(3, 0.10);
        });

        registerPotion(Potion.SUPER_ATTACK, p -> p.getStats().get(StatType.Attack).boost(5, 0.15));
        registerPotion(Potion.SUPER_STRENGTH, p -> p.getStats().get(StatType.Strength).boost(5, 0.15));
        registerPotion(Potion.SUPER_DEFENCE, p -> p.getStats().get(StatType.Defence).boost(5, 0.15));
        registerPotion(Potion.SUPER_COMBAT, p -> {
            p.getStats().get(StatType.Attack).boost(5, 0.15);
            p.getStats().get(StatType.Strength).boost(5, 0.15);
            p.getStats().get(StatType.Defence).boost(5, 0.15);
        });

        registerPotion(Potion.RANGING, p -> p.getStats().get(StatType.Ranged).boost(4, 0.10));
        registerPotion(Potion.MAGIC, p -> p.getStats().get(StatType.Magic).boost(5, 0.0));

        registerPotion(Potion.DIVINE_SUPER_ATTACK, p -> p.getStats().get(StatType.Attack).boost(5, 0.15));
        registerPotion(Potion.DIVINE_SUPER_STRENGTH, p -> p.getStats().get(StatType.Strength).boost(5, 0.15));
        registerPotion(Potion.DIVINE_SUPER_DEFENCE, p -> p.getStats().get(StatType.Defence).boost(5, 0.15));
        registerPotion(Potion.DIVINE_RANGING, p -> p.getStats().get(StatType.Ranged).boost(4, 0.10));
        registerPotion(Potion.DIVINE_MAGIC, p -> p.getStats().get(StatType.Magic).boost(4, 0.0));
        registerPotion(Potion.DIVINE_SUPER_COMBAT, p -> {
            p.getStats().get(StatType.Attack).boost(5, 0.15);
            p.getStats().get(StatType.Strength).boost(5, 0.15);
            p.getStats().get(StatType.Defence).boost(5, 0.15);
        });
        registerPotion(Potion.DIVINE_BATTLEMAGE, p -> {
            p.getStats().get(StatType.Magic).boost(4, 0.0);
            p.getStats().get(StatType.Defence).boost(5, 0.15);
        });
        registerPotion(Potion.DIVINE_BASTION, p -> {
            p.getStats().get(StatType.Ranged).boost(4, 0.10);
            p.getStats().get(StatType.Defence).boost(5, 0.15);
        });

        registerPotion(Potion.AGILITY, p -> p.getStats().get(StatType.Agility).boost(3, 0.0));
        registerPotion(Potion.FISHING, p -> p.getStats().get(StatType.Fishing).boost(3, 0.0));
        registerPotion(Potion.HUNTER, p -> p.getStats().get(StatType.Hunter).boost(3, 0.0));

        registerPotion(Potion.ANTIPOISON, p -> p.curePoison((90 * 1000) / 600));
        registerPotion(Potion.RESTORE, p -> restore(p, false));
        registerPotion(Potion.ENERGY, p -> p.getMovement().restoreEnergy(10));
        registerPotion(Potion.PRAYER, p -> {
            Stat stat = p.getStats().get(StatType.Prayer);
            if(p.getEquipment().getId(Equipment.SLOT_RING) == 13202 // Ring of the gods (i)
                    || p.getInventory().contains(6714)  // Holy wrench
                    || PrayerSkillCape.wearingPrayerCape(p)
                    || PrayerSkillCape.hasPrayerCape(p))
                stat.restore(7, 0.27);
            else
                stat.restore(7, 0.25);
            p.getStats().get(StatType.Prayer).alter(stat.currentLevel);
        });

        registerPotion(Potion.SUPER_ANTIPOISON, p -> p.curePoison((360 * 1000) / 600));
        registerPotion(Potion.SUPER_ENERGY, p -> p.getMovement().restoreEnergy(20));
        registerPotion(Potion.SUPER_RESTORE, p -> restore(p, true));
        registerPotion(Potion.BLIGHTED_SUPER_RESTORE, p -> restore(p, true));
        registerPotion(Potion.SANFEW_SERUM, p -> {
            for (StatType type : StatType.values()) {
                if (type == StatType.Hitpoints)
                    continue;
                Stat stat = p.getStats().get(type);
                if (stat.currentLevel < stat.fixedLevel) {
                    if (type == StatType.Prayer
                            && (p.getEquipment().getId(Equipment.SLOT_RING) == 13202   // Ring of the gods (i)
                            || p.getInventory().contains(6714) // Holy wrench
                            || PrayerSkillCape.hasPrayerCape(p)
                            || PrayerSkillCape.wearingPrayerCape(p)))
                        stat.restore(4, 0.32);
                    else
                        stat.restore(4, 0.30);
                }
            }
            p.curePoison((90 * 1000) / 600);
        });
        registerPotion(Potion.ANTIDOTE_PLUS, p -> p.curePoison((540 * 1000) / 600));
        registerPotion(Potion.ANTIFIRE, p -> {
            int ticks = (360 * 1000) / 600;
            Config.ANTIFIRE.set(p, ticks / 30);
            p.addTickEventIfLonger(new TickEvent(
                    TickEventType.ANTIFIRE,
                    ticks,
                    () -> {
                        p.sendMessage("<col=7f007f>Your antifire potion has expired.");
                        p.privateSound(2607, 1, 0);
                        Config.ANTIFIRE.set(p, 0);
                    },
                    new TickEventRunnable(30, () -> {
                        p.sendMessage("<col=7f007f>Your antifire potion is about to expire.");
                        p.privateSound(3120, 3, 0);
                    })
            ));
        });
        registerPotion(Potion.SUPER_ANTIFIRE, p -> {
            int ticks = (180 * 1000) / 600;
            Config.SUPER_ANTIFIRE.set(p, ticks / 30);
            p.addTickEventIfLonger(new TickEvent(
                    TickEventType.ANTIFIRE,
                    ticks,
                    () -> {
                        p.sendMessage("<col=7f007f>Your super antifire potion has expired.");
                        p.privateSound(2607, 1, 0);
                        Config.SUPER_ANTIFIRE.set(p, 0);
                    },
                    new TickEventRunnable(30, () -> {
                        p.sendMessage("<col=7f007f>Your super antifire potion is about to expire.");
                        p.privateSound(3120, 3, 0);
                    })
            ));
        });

        registerPotion(Potion.STAMINA, p -> {
            p.getMovement().restoreEnergy(20);
            Config.STAMINA_POTION.set(p, 1);
            p.addTickEventIfLonger(new TickEvent(
                    TickEventType.STAMINA_POTION,
                    200,
                    () -> {
                        p.sendMessage("<col=8f4808>Your stamina potion has expired.");
                        p.privateSound(2672, 3, 0);
                        Config.STAMINA_POTION.set(p, 0);
                    },
                    new TickEventRunnable(17, () -> {
                        p.sendMessage("<col=8f4808>Your stamina potion is about to expire.");
                        p.privateSound(3120, 3, 0);
                    })
            ));
        });
        registerPotion(Potion.ANTIDOTE_PLUS_PLUS, p -> {
            p.curePoison((730 * 1000) / 600);
            p.curePoison((730 * 1000) / 600);
        });
        registerPotion(Potion.SARADOMIN_BREW, p -> {
            p.getStats().get(StatType.Hitpoints).boost(2, 0.15);
            p.getStats().get(StatType.Defence).boost(2, 0.20);
            p.getStats().get(StatType.Attack).drain(2, 0.10);
            p.getStats().get(StatType.Strength).drain(2, 0.10);
            p.getStats().get(StatType.Ranged).drain(2, 0.10);
            p.getStats().get(StatType.Magic).drain(2, 0.10);
        });

        registerPotion(Potion.ZAMORAK_BREW, p -> {
            p.getStats().get(StatType.Attack).boost(2, 0.2);
            p.getStats().get(StatType.Strength).boost(2, 0.12);
            p.getStats().get(StatType.Defence).drain((int)(2 + (p.getStats().get(StatType.Defence).fixedLevel * 0.1)));
            p.hit(new Hit().fixedDamage((int) (2 + (p.getMaxHp() * 0.1))));
        });

        registerPotion(Potion.EXTENDED_ANTIFIRE, p -> {
            int ticks = (720 * 1000) / 600;
            Config.ANTIFIRE.set(p, ticks / 30);
            p.addTickEventIfLonger(new TickEvent(
                    TickEventType.ANTIFIRE,
                    ticks,
                    () -> {
                        p.sendMessage("<col=7f007f>Your antifire potion has expired.");
                        p.privateSound(2607, 1, 0);
                        Config.ANTIFIRE.set(p, 0);
                    },
                    new TickEventRunnable(30, () -> {
                        p.sendMessage("<col=7f007f>Your antifire potion is about to expire.");
                        p.privateSound(3120, 3, 0);
                    })
            ));
        });
        registerPotion(Potion.EXTENDED_SUPER_ANTIFIRE, p -> {
            int ticks = (360 * 1000) / 600;
            Config.SUPER_ANTIFIRE.set(p, ticks / 30);
            p.addTickEventIfLonger(new TickEvent(
                    TickEventType.ANTIFIRE,
                    ticks,
                    () -> {
                        p.sendMessage("<col=7f007f>Your super antifire potion has expired.");
                        p.privateSound(2607, 1, 0);
                        Config.SUPER_ANTIFIRE.set(p, 0);
                    },
                    new TickEventRunnable(30, () -> {
                        p.sendMessage("<col=7f007f>Your super antifire potion is about to expire.");
                        p.privateSound(3120, 3, 0);
                    })
            ));
        });
        registerPotion(Potion.MAGIC_ESSENCE, p -> p.getStats().get(StatType.Magic).boost(3, 0));
        registerPotion(Potion.ANTI_VENOM, p -> p.cureVenom((36 * 1000) / 600, (720 * 1000) / 600));
        registerPotion(Potion.SUPER_ANTI_VENOM, p -> p.cureVenom((180 * 1000) / 600, (900 * 1000) / 600));

        registerPotion(Potion.GUTHIX_REST, p -> { //todo give this it's own method with it's own correct drink messages
            p.getStats().get(StatType.Hitpoints).boost(5, 0.0);
            p.getMovement().restoreEnergy(5);
            // If player is venomed, reduce to a poison
            if (p.isVenomed()) {
                p.venomToPoison();
                p.sendFilteredMessage("The tea helps to dilute the venom.");
            } else if (p.isPoisoned()) {    // If player is poisoned, reduce damage by 1
                --p.poisonDamage;
                p.sendFilteredMessage("The tea helps to dilute the poison.");
            }
        });

        /**
         * Raids potions
         */
        registerPotion(Potion.ELDER_MINUS, p -> {
            p.getStats().get(StatType.Attack).boost(4, 0.10);
            p.getStats().get(StatType.Strength).boost(4, 0.10);
            p.getStats().get(StatType.Defence).boost(4, 0.10);
        });
        registerPotion(Potion.ELDER_REGULAR, p -> {
            p.getStats().get(StatType.Attack).boost(5, 0.13);
            p.getStats().get(StatType.Strength).boost(5, 0.13);
            p.getStats().get(StatType.Defence).boost(5, 0.13);
        });
        registerPotion(Potion.ELDER_PLUS, p -> {
            p.getStats().get(StatType.Attack).boost(6, 0.16);
            p.getStats().get(StatType.Strength).boost(6, 0.16);
            p.getStats().get(StatType.Defence).boost(6, 0.16);
        });

        registerPotion(Potion.TWISTED_MINUS, p -> p.getStats().get(StatType.Ranged).boost(4, 0.10));
        registerPotion(Potion.TWISTED_REGULAR, p -> p.getStats().get(StatType.Ranged).boost(5, 0.13));
        registerPotion(Potion.TWISTED_PLUS, p -> p.getStats().get(StatType.Ranged).boost(6, 0.16));

        registerPotion(Potion.KODAI_MINUS, p -> p.getStats().get(StatType.Magic).boost(4, 0.10));
        registerPotion(Potion.KODAI_REGULAR, p -> p.getStats().get(StatType.Magic).boost(5, 0.13));
        registerPotion(Potion.KODAI_PLUS, p -> p.getStats().get(StatType.Magic).boost(6, 0.16));

        registerPotion(Potion.REVITALISATION_MINUS, p -> restore(p, false));
        registerPotion(Potion.REVITALISATION_REGULAR, p -> restore(p, false));
        registerPotion(Potion.REVITALISATION_PLUS, p -> restore(p, true));

        registerPotion(Potion.PRAYER_ENHANCE_MINUS, p -> {
            Stat stat = p.getStats().get(StatType.Prayer);
            if(p.getEquipment().getId(Equipment.SLOT_RING) == 13202)
                stat.restore(3, 0.22);
            else
                stat.restore(3, 0.20);
            p.getStats().get(StatType.Prayer).alter(stat.currentLevel);
        });
        registerPotion(Potion.PRAYER_ENHANCE_REGULAR, p -> {
            Stat stat = p.getStats().get(StatType.Prayer);
            if(p.getEquipment().getId(Equipment.SLOT_RING) == 13202)
                stat.restore(5, 0.25);
            else
                stat.restore(5, 0.23);
            p.getStats().get(StatType.Prayer).alter(stat.currentLevel);
        });
        registerPotion(Potion.PRAYER_ENHANCE_PLUS, p -> {
            Stat stat = p.getStats().get(StatType.Prayer);
            if(p.getEquipment().getId(Equipment.SLOT_RING) == 13202)
                stat.restore(7, 0.27);
            else
                stat.restore(7, 0.25);
            p.getStats().get(StatType.Prayer).alter(stat.currentLevel);
        });

        registerPotion(Potion.XERIC_AID_MINUS, p -> {
            p.getStats().get(StatType.Hitpoints).boost(2, 0.5);
            p.getStats().get(StatType.Defence).boost(2, 0.10);
            p.getStats().get(StatType.Attack).drain(0.3);
            p.getStats().get(StatType.Strength).drain(0.3);
            p.getStats().get(StatType.Ranged).drain(0.3);
            p.getStats().get(StatType.Magic).drain(0.3);
        });
        registerPotion(Potion.XERIC_AID_REGULAR, p -> {
            p.getStats().get(StatType.Hitpoints).boost(2, 0.10);
            p.getStats().get(StatType.Defence).boost(2, 0.15);
            p.getStats().get(StatType.Attack).drain(0.6);
            p.getStats().get(StatType.Strength).drain(0.6);
            p.getStats().get(StatType.Ranged).drain(0.6);
            p.getStats().get(StatType.Magic).drain(0.6);
        });
        registerPotion(Potion.XERIC_AID_PLUS, p -> {
            p.getStats().get(StatType.Hitpoints).boost(2, 0.15);
            p.getStats().get(StatType.Defence).boost(2, 0.20);
            p.getStats().get(StatType.Attack).drain(0.10);
            p.getStats().get(StatType.Strength).drain(0.10);
            p.getStats().get(StatType.Ranged).drain(0.10);
            p.getStats().get(StatType.Magic).drain(0.10);
        });
        registerPotion(Potion.ANTIPOISON_MINUS, p -> p.curePoison((90 * 1000) / 600));
        registerPotion(Potion.ANTIPOISON_RAID, p -> p.curePoison((360 * 1000) / 600));
        registerPotion(Potion.ANTIPOISON_PLUS, p -> p.curePoison((540 * 1000) / 600));
        registerPotion(Potion.OVERLOAD_MINUS, p -> {
            p.getStats().get(StatType.Attack).boost(4, 0.10);
            p.getStats().get(StatType.Strength).boost(4, 0.10);
            p.getStats().get(StatType.Defence).boost(4, 0.10);
            p.getStats().get(StatType.Ranged).boost(4, 0.10);
            p.getStats().get(StatType.Magic).boost(4, 0.10);
        });
        registerPotion(Potion.OVERLOAD_REGULAR, p -> {
            p.getStats().get(StatType.Attack).boost(5, 0.13);
            p.getStats().get(StatType.Strength).boost(5, 0.13);
            p.getStats().get(StatType.Defence).boost(5, 0.13);
            p.getStats().get(StatType.Ranged).boost(5, 0.13);
            p.getStats().get(StatType.Magic).boost(5, 0.13);
        });
        registerPotion(Potion.OVERLOAD_PLUS, p -> {
            p.getStats().get(StatType.Attack).boost(6, 0.16);
            p.getStats().get(StatType.Strength).boost(6, 0.16);
            p.getStats().get(StatType.Defence).boost(6, 0.16);
            p.getStats().get(StatType.Ranged).boost(6, 0.16);
            p.getStats().get(StatType.Magic).boost(6, 0.16);
        });
        registerPotion(Potion.OVERLOAD_NMZ, p -> {
            p.getStats().get(StatType.Attack).boost(5, 0.15);
            p.getStats().get(StatType.Strength).boost(5, 0.15);
            p.getStats().get(StatType.Defence).boost(5, 0.15);
            p.getStats().get(StatType.Ranged).boost(5, 0.15);
            p.getStats().get(StatType.Magic).boost(5, 0.15);
        });
        registerPotion(Potion.SUPER_MAGIC, p -> p.getStats().get(StatType.Magic).boost(5, 0.15));
        registerPotion(Potion.SUPER_RANGING, p -> p.getStats().get(StatType.Ranged).boost(5, 0.15));
        registerPotion(Potion.ABSORPTION, p -> {
            Config.NMZ_ABSORPTION.increment(p, 50);
            p.sendMessage(Color.DARK_GREEN.wrap("You now have " + Config.NMZ_ABSORPTION.get(p) + " hitpoints of damage absorption left."));
        });
    }

    private static void restore(Player player, boolean superEffect) {
        restore(player, 10, 0.30, superEffect);
    }

    public static void restore(Player player, int flat, double percent, boolean superEffect) {
        for(StatType type : StatType.values()) {
            if(type == StatType.Hitpoints)
                continue;
            Stat stat = player.getStats().get(type);
            if(stat.currentLevel < stat.fixedLevel) {
                if(superEffect) {
                    if(type == StatType.Prayer
                            && (player.getEquipment().getId(Equipment.SLOT_RING) == 13202   // Ring of the gods (i)
                            || player.getInventory().contains(6714) // Holy wrench
                            || PrayerSkillCape.hasPrayerCape(player)
                            || PrayerSkillCape.wearingPrayerCape(player)))
                        stat.restore(8, 0.27);
                    else
                        stat.restore(8, 0.25);
                    continue;
                }
                if(type != StatType.Prayer)
                    stat.restore(flat, percent);
            }
        }
    }

    private static void registerDrink(Potion potion, int id, int newId, Consumer<Player> effect) {
        ItemDefinition.get(id).consumable = true;
        ItemAction.registerInventory(id, "drink", (player, item) -> {
            if(drink(player, potion, item, newId))
                effect.accept(player);
        });
    }

    private static void registerEmpty(int id) {
        ItemDefinition def = ItemDefinition.get(id);
        if(def == null)
            return;
        if(!def.hasOption("empty"))
            return;
        if (ItemDefinition.get(id).name.toLowerCase().contains("guthix rest")) {
            ItemAction.registerInventory(id, "empty", (player, item) -> {
                item.setId(1980);
                player.sendMessage("You empty the cup.");
            });
        } else {
            ItemAction.registerInventory(id, "empty", (player, item) -> {
                item.setId(229);
                player.sendMessage("You empty the vial.");
            });
        }

    }

    private static void registerPotion(Potion potion, Consumer<Player> effect) {
        registerDrink(potion, potion.vialIds[3], potion.vialIds[2], p -> {
            effect.accept(p);
            p.sendFilteredMessage("You drink some of your " + potion.potionName + ".");
            p.sendFilteredMessage("You have 3 doses of potion left.");
        });
        registerDrink(potion, potion.vialIds[2], potion.vialIds[1], p -> {
            effect.accept(p);
            p.sendFilteredMessage("You drink some of your " + potion.potionName + ".");
            p.sendFilteredMessage("You have 2 doses of potion left.");
        });
        registerDrink(potion, potion.vialIds[1], potion.vialIds[0], p -> {
            effect.accept(p);
            p.sendFilteredMessage("You drink some of your " + potion.potionName + ".");
            p.sendFilteredMessage("You have 1 dose of potion left.");
        });
        registerDrink(potion, potion.vialIds[0], 229, p -> {
            effect.accept(p);
            p.sendFilteredMessage("You drink some of your " + potion.potionName + ".");
            p.sendFilteredMessage("You have finished your potion.");
        });
        for(int vial : potion.vialIds) {
            registerEmpty(vial);
        }
        BarbarianMix mix = BarbarianMix.MIXES.get(potion);
        if (mix != null) {
            registerDrink(potion, mix.vialIds[1], mix.vialIds[0], p -> {
                effect.accept(p);
                p.incrementHp(mix.getHeal());
                p.sendFilteredMessage("You drink the lumpy potion.");
                p.sendFilteredMessage("You have 1 dose of potion left.");
            });
            registerDrink(potion, mix.vialIds[0], 229, p -> {
                effect.accept(p);
                p.incrementHp(mix.getHeal());
                p.sendFilteredMessage("You drink the lumpy potion.");
                p.sendFilteredMessage("You have finished your potion.");
            });
            for(int vial : mix.vialIds) {
                registerEmpty(vial);
            }
        }
    }

    /**
     * Checks if the player is currently under the affect of any divine potions
     * and if the player is trying to drink said divine potion/
     * @param player The player drinking the potion.
     * @param potion The potion the player is drinking.
     * @return True if potion is not a divine, or if player does not have the buff for that divine.
     */
    private static boolean divinePreCheck(Player player, Potion potion) {
        if (player.divineRangingBoostActive && potion == Potion.DIVINE_RANGING) {
            player.sendMessage("Your ranging boost is still active.");
            return false;
        }
        if (player.divineMagicBoostActive && potion == Potion.DIVINE_MAGIC) {
            player.sendMessage("Your magic boost is still active.");
            return false;
        }
        if (player.divineAttackBoostActive && potion == Potion.DIVINE_SUPER_ATTACK) {
            player.sendMessage("Your attack boost is still active.");
            return false;
        }
        if (player.divineStrBoostActive && potion == Potion.DIVINE_SUPER_STRENGTH) {
            player.sendMessage("Your strength boost is still active.");
            return false;
        }
        if (player.divineDefBoostActive && potion == Potion.DIVINE_SUPER_DEFENCE) {
            player.sendMessage("Your defence boost is still active.");
            return false;
        }
        if (player.divineSuperCmbBoostActive && potion == Potion.DIVINE_SUPER_COMBAT) {
            player.sendMessage("Your super combat boost is still active.");
            return false;
        }
        if (player.divineBattlemageBoostActive && potion == Potion.DIVINE_BATTLEMAGE) {
            player.sendMessage("Your battlemage boost is still active.");
            return false;
        }if (player.divineBastionBoostActive && potion == Potion.DIVINE_BASTION) {
            player.sendMessage("Your bastion boost is still active.");
            return false;
        }
        return true;
    }

    private static boolean drink(Player player, Potion potion, Item item, int newId) {
        if(player.isLocked() || player.isStunned())
            return false;
        if(player.potDelay.isDelayed() || player.karamDelay.isDelayed())
            return false;
        if(DuelRule.NO_DRINKS.isToggled(player)) {
            player.sendMessage("Drinks have been disabled for this duel!");
            return false;
        }
        if (potion == Potion.BLIGHTED_SUPER_RESTORE) {
            if (player.wildernessLevel <= 0) {
                player.sendMessage("The blighted potion can be used only in the Wilderness.");
                return false;
            }
        }
        if (!divinePreCheck(player, potion)) {
            return false;
        }
        if (player.prayerEnhanceBoostActive && (potion == Potion.PRAYER_ENHANCE_MINUS || potion == Potion.PRAYER_ENHANCE_REGULAR || potion == Potion.PRAYER_ENHANCE_PLUS)) {
            player.sendMessage("Your prayer enhance boost is still active.");
            return false;
        }
        if((potion == Potion.SARADOMIN_BREW || potion == Potion.GUTHIX_REST) && DuelRule.NO_FOOD.isToggled(player)) {
            player.sendMessage("Food has been disabled for this duel!");
            return false;
        }
        boolean isOverload = potion == Potion.OVERLOAD_PLUS || potion == Potion.OVERLOAD_REGULAR || potion == Potion.OVERLOAD_MINUS || potion == Potion.OVERLOAD_NMZ;
        if(player.overloadBoostActive && isOverload) {
            player.sendMessage("Your overload boost is still active.");
            return false;
        }
        boolean isNMZ = potion == Potion.SUPER_RANGING || potion == Potion.ABSORPTION || potion == Potion.SUPER_MAGIC || potion == Potion.OVERLOAD_NMZ;
        if (isNMZ && player.getTemporaryAttribute("nmz") == null) {
            player.sendMessage("You can only drink this potion while dreaming.");
            return false;
        }
        if (potion.ordinal() >= Potion.ELDER_MINUS.ordinal() && (player.raidsParty == null || !player.raidsParty.getRaid().inRaid(player))) {
            player.sendMessage("You can only drink this potion while in a raid.");
            return false;
        }
        if(newId == -1 || (newId == 229 && player.hasAttribute(AttributeKey.BREAK_VIALS)))
            item.remove();
        else if (potion == Potion.GUTHIX_REST)
            item.setId(newId == 229 ? 1980 : newId);
        else if(potion.raidsPotion)
            item.setId(newId == 229 ? 20800 : newId);
        else
            item.setId(newId);
        // NMZ potions dont reset actions
        if (isNMZ && !isOverload) {
            drinkNMZ(player);
            return true;
        }
        animDrink(player);
        player.potDelay.delay(3);
        if (isOverload)
            overload(player, potion);
        if (potion == Potion.PRAYER_ENHANCE_MINUS || potion == Potion.PRAYER_ENHANCE_REGULAR || potion == Potion.PRAYER_ENHANCE_PLUS)
            prayerEnhance(player, potion);
        if (potion == Potion.DIVINE_SUPER_ATTACK || potion == Potion.DIVINE_SUPER_STRENGTH || potion == Potion.DIVINE_SUPER_DEFENCE
                || potion == Potion.DIVINE_SUPER_COMBAT || potion == Potion.DIVINE_RANGING || potion == Potion.DIVINE_MAGIC
                || potion == Potion.DIVINE_BATTLEMAGE || potion == Potion.DIVINE_BASTION)
            divine(player, potion);
        return true;
    }

    private static void overload(Player player, Potion potion) {
        World.startEvent(event -> {
            player.overloadBoostActive = true;
            for (int i = 0; i < 5; i++) {
                player.animate(3170);
                player.graphics(560);
                player.hit(new Hit().fixedDamage(10).ignoreAbsorption());
                event.delay(2);
            }
            if (potion == Potion.OVERLOAD_NMZ) {
                if (player.getTemporaryAttribute("nmz") == null) {
                    player.sendMessage("You can only drink overload potions while dreaming.");
                    return;
                }
                for (int i = 0; i < 20; i++) {
                    if (player.getTemporaryAttribute("nmz") == null) {
                        player.sendMessage("Your overload boost has worn off.");
                        player.overloadBoostActive = false;
                        player.getStats().get(StatType.Attack).restore();
                        player.getStats().get(StatType.Strength).restore();
                        player.getStats().get(StatType.Defence).restore();
                        player.getStats().get(StatType.Ranged).restore();
                        player.getStats().get(StatType.Magic).restore();
                        return;
                    }
                    player.getStats().get(StatType.Attack).boost(5, 0.15);
                    player.getStats().get(StatType.Strength).boost(5, 0.15);
                    player.getStats().get(StatType.Defence).boost(5, 0.15);
                    player.getStats().get(StatType.Ranged).boost(5, 0.15);
                    player.getStats().get(StatType.Magic).boost(5, 0.15);
                    event.delay(25);

                    if (i == 19) {
                        player.incrementHp(50);
                    }
                }
            } else if (potion == Potion.OVERLOAD_PLUS) {
                for (int i = 0; i < 20; i++) {
                    if (player.raidsParty == null || player.raidsParty.getRaid() == null) {
                        player.sendMessage("Your overload boost has worn off.");
                        player.overloadBoostActive = false;
                        return;
                    }
                    player.getStats().get(StatType.Attack).boost(6, 0.16);
                    player.getStats().get(StatType.Strength).boost(6, 0.16);
                    player.getStats().get(StatType.Defence).boost(6, 0.16);
                    player.getStats().get(StatType.Ranged).boost(6, 0.16);
                    player.getStats().get(StatType.Magic).boost(6, 0.16);
                    event.delay(25);

                    if (i == 19) {
                        player.incrementHp(50);
                    }
                }
            } else if (potion == Potion.OVERLOAD_MINUS) {
                for (int i = 0; i < 20; i++) {
                    if (player.raidsParty == null || player.raidsParty.getRaid() == null) {
                        player.sendMessage("Your overload boost has worn off.");
                        player.overloadBoostActive = false;
                        return;
                    }
                    player.getStats().get(StatType.Attack).boost(4, 0.10);
                    player.getStats().get(StatType.Strength).boost(4, 0.10);
                    player.getStats().get(StatType.Defence).boost(4, 0.10);
                    player.getStats().get(StatType.Ranged).boost(4, 0.10);
                    player.getStats().get(StatType.Magic).boost(4, 0.10);
                    event.delay(25);

                    if (i == 19) {
                        player.incrementHp(50);
                    }
                }
            } else if (potion == Potion.OVERLOAD_REGULAR) {
                for (int i = 0; i < 20; i++) {
                    if (player.raidsParty == null || player.raidsParty.getRaid() == null) {
                        player.sendMessage("Your overload boost has worn off.");
                        player.overloadBoostActive = false;
                        return;
                    }
                    player.getStats().get(StatType.Attack).boost(5, 0.13);
                    player.getStats().get(StatType.Strength).boost(5, 0.13);
                    player.getStats().get(StatType.Defence).boost(5, 0.13);
                    player.getStats().get(StatType.Ranged).boost(5, 0.13);
                    player.getStats().get(StatType.Magic).boost(5, 0.13);
                    event.delay(25);

                    if (i == 19) {
                        player.incrementHp(50);
                    }
                }
            }
            player.overloadBoostActive = false;
            player.sendMessage("Your overload boost has worn off.");
        });
    }

    private static void divine(Player player, Potion potion) {
        World.startEvent(event -> {
            player.animate(3170);
            player.graphics(560);
            player.hit(new Hit().fixedDamage(10));

            switch (potion) {
                case DIVINE_SUPER_ATTACK:
                    player.divineAttackBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineAttackBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Attack).boost(5, 0.15);
                        event.delay(25);
                    }
                    player.divineAttackBoostActive = false;
                    break;
                case DIVINE_SUPER_STRENGTH:
                    player.divineStrBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineStrBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Strength).boost(5, 0.15);
                        event.delay(25);
                    }
                    player.divineStrBoostActive = false;
                    break;
                case DIVINE_SUPER_DEFENCE:
                    player.divineDefBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineDefBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Defence).boost(5, 0.15);
                        event.delay(25);
                    }
                    player.divineDefBoostActive = false;
                    break;
                case DIVINE_RANGING:
                    player.divineRangingBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineRangingBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Ranged).boost(4, 0.10);
                        event.delay(25);
                    }
                    player.divineRangingBoostActive = false;
                    break;
                case DIVINE_MAGIC:
                    player.divineMagicBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineMagicBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Magic).boost(4, 0.0);
                        event.delay(25);
                    }
                    player.divineMagicBoostActive = false;
                    break;
                case DIVINE_SUPER_COMBAT:
                    player.divineSuperCmbBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineSuperCmbBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Attack).boost(5, 0.15);
                        player.getStats().get(StatType.Strength).boost(5, 0.15);
                        player.getStats().get(StatType.Defence).boost(5, 0.15);
                        event.delay(25);
                    }
                    player.divineSuperCmbBoostActive = false;
                    break;
                case DIVINE_BASTION:
                    player.divineBastionBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineBastionBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Ranged).boost(4, 0.10);
                        player.getStats().get(StatType.Defence).boost(5, 0.15);
                        event.delay(25);
                    }
                    player.divineBastionBoostActive = false;
                    break;
                case DIVINE_BATTLEMAGE:
                    player.divineBattlemageBoostActive = true;
                    for (int i = 0; i < 20; i++) {
                        if(DuelRule.NO_DRINKS.isToggled(player)) {
                            player.divineBattlemageBoostActive = false;
                            return;
                        }
                        player.getStats().get(StatType.Magic).boost(4, 0.0);
                        player.getStats().get(StatType.Defence).boost(5, 0.15);
                        event.delay(25);
                    }
                    player.divineBattlemageBoostActive = false;
                    break;
            }
            player.sendMessage("Your " + potion.potionName + " has worn off.");
        });
    }

    private static void prayerEnhance(Player player, Potion potion) {
        World.startEvent(event -> {
            player.prayerEnhanceBoostActive = true;
            if (potion == Potion.PRAYER_ENHANCE_PLUS) {
                int count = 500;
                while(true) {
                    if (count == -1 || player.raidsParty == null || player.raidsParty.getRaid() == null) {
                        player.sendMessage("Your prayer enhance boost has worn off.");
                        player.prayerEnhanceBoostActive = false;
                        break;
                    }
                    if (count % 6 == 0)
                        player.getStats().get(StatType.Prayer).restore(1);
                    count--;
                    event.delay(1);
                }
            } else if (potion == Potion.PRAYER_ENHANCE_REGULAR) {
                int count = 483;
                while(true) {
                    if (count == -1 || player.raidsParty == null || player.raidsParty.getRaid() == null) {
                        player.sendMessage("Your prayer enhance boost has worn off.");
                        player.prayerEnhanceBoostActive = false;
                        break;
                    }
                    if (count % 6 == 0)
                        player.getStats().get(StatType.Prayer).restore(1);
                    count--;
                    event.delay(1);
                }
            } else if (potion == Potion.PRAYER_ENHANCE_MINUS) {
                int count = 458;
                while(true) {
                    if (count == -1 || player.raidsParty == null || player.raidsParty.getRaid() == null) {
                        player.sendMessage("Your prayer enhance boost has worn off.");
                        player.prayerEnhanceBoostActive = false;
                        break;
                    }
                    if (count % 6 == 0)
                        player.getStats().get(StatType.Prayer).restore(1);
                    count--;
                    event.delay(1);
                }
            }
        });
    }

    public static void drinkNMZ(Player player) {
        player.animate(829);
        player.privateSound(2401);
        player.potDelay.delay(1);
    }

    private static void animDrink(Player player) {
        if(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 4084)
            player.animate(1469);
        else if (player.seat != null)
            player.animate(player.seat.getEatAnimation(player));
        else
            player.animate(829);
        player.privateSound(2401);
        player.resetActions(true, player.getMovement().following != null, true);
    }

}
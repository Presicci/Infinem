package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/22/2021
 */
public class ItemCombineAction {

    private enum ItemCombine {
        /**
         * Gnome
         */
        RAW_BATTA("You use the dough on the tin to make a raw batta.",
                Arrays.asList(new ItemPair(Items.BATTA_TIN, Items.RAW_BATTA), new ItemPair(Items.GIANNE_DOUGH, -1))),
        RAW_CRUNCHIES("You use the dough on the tray to make a raw crunchies.",
                Arrays.asList(new ItemPair(Items.CRUNCHY_TRAY, Items.RAW_CRUNCHIES), new ItemPair(Items.GIANNE_DOUGH, -1))),
        RAW_GNOMEBOWL("You use the dough on the mould to make a raw gnomebowl.",
                Arrays.asList(new ItemPair(Items.GNOMEBOWL_MOULD, Items.RAW_GNOMEBOWL), new ItemPair(Items.GIANNE_DOUGH, -1))),

        /**
         * Dough
         */
        BREAD_DOUGH_BUCKET("You mix the flour and water to make dough.", 1, Collections.singletonList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(-1, Items.BREAD_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET)),
                Arrays.asList(new ItemPair(-1, Items.PASTRY_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET)),
                Arrays.asList(new ItemPair(-1, Items.PITTA_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET)),
                Arrays.asList(new ItemPair(-1, Items.PIZZA_BASE), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET))),
        BREAD_DOUGH_BOWL("You mix the flour and water to make dough.", 1, Collections.singletonList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(-1, Items.BREAD_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL)),
                Arrays.asList(new ItemPair(-1, Items.PASTRY_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL)),
                Arrays.asList(new ItemPair(-1, Items.PITTA_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL)),
                Arrays.asList(new ItemPair(-1, Items.PIZZA_BASE), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL))),
        BREAD_DOUGH_JUG("You mix the flour and water to make dough.", 1, Collections.singletonList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(-1, Items.BREAD_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG)),
                Arrays.asList(new ItemPair(-1, Items.PASTRY_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG)),
                Arrays.asList(new ItemPair(-1, Items.PITTA_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG)),
                Arrays.asList(new ItemPair(-1, Items.PIZZA_BASE), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG))),

        /**
         * Pies
         */
        PIE_SHELL("You use the dough on the dish to make a pie shell.",
                Arrays.asList(new ItemPair(Items.PIE_DISH, Items.PIE_SHELL), new ItemPair(Items.PASTRY_DOUGH, -1))),
        BERRY_PIE("You use the berries on the pie shell to make a uncooked berry pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 10, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.UNCOOKED_BERRY_PIE), new ItemPair(Items.REDBERRIES, -1))),
        MEAT_PIE("You use the meat on the pie shell to make a uncooked meat pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 20, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.UNCOOKED_MEAT_PIE), new ItemPair(Items.COOKED_MEAT, -1))),
        PART_MUD_PIE_1("You use the compost on the pie shell to make part of a mud pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 29, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_MUD_PIE), new ItemPair(Items.COMPOST, -1))),
        PART_MUD_PIE_2_BUCKET("You use the water on the pie shell to make part of a mud pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 29, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_MUD_PIE_2), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET))),
        PART_MUD_PIE_2_BOWL("You use the water on the pie shell to make part of a mud pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 29, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_MUD_PIE_2), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL))),
        PART_MUD_PIE_2_JUG("You use the water on the pie shell to make part of a mud pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 29, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_MUD_PIE_2), new ItemPair(Items.JUG_OF_WATER, Items.JUG))),
        MUD_PIE("You use the clay on the pie shell to make a mud pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 29, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.MUD_PIE), new ItemPair(Items.CLAY, -1))),
        APPLE_PIE("You use the apple on the pie shell to make a uncooked apple pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 30, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.UNCOOKED_APPLE_PIE), new ItemPair(Items.COOKING_APPLE, -1))),
        PART_GARDEN_PIE_1("You use the tomato on the pie shell to make part of a garden pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 34, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_GARDEN_PIE), new ItemPair(Items.TOMATO, -1))),
        PART_GARDEN_PIE_2("You use the onion on the pie shell to make part of a garden pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 34, 0)),
                Arrays.asList(new ItemPair(Items.PART_GARDEN_PIE, Items.PART_GARDEN_PIE_2), new ItemPair(Items.ONION, -1))),
        GARDEN_PIE("You use the apple on the pie shell to make a uncooked apple pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 34, 0)),
                Arrays.asList(new ItemPair(Items.PART_GARDEN_PIE_2, Items.RAW_GARDEN_PIE), new ItemPair(Items.CABBAGE, -1))),
        PART_FISH_PIE_1("You use the trout on the pie shell to make part of a fish pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 47, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_FISH_PIE), new ItemPair(Items.TROUT, -1))),
        PART_FISH_PIE_2("You use the cod on the pie shell to make part of a fish pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 47, 0)),
                Arrays.asList(new ItemPair(Items.PART_FISH_PIE, Items.PART_FISH_PIE_2), new ItemPair(Items.COD, -1))),
        FISH_PIE("You use the potato on the pie shell to make a raw fish pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 47, 0)),
                Arrays.asList(new ItemPair(Items.PART_FISH_PIE_2, Items.RAW_FISH_PIE), new ItemPair(Items.POTATO, -1))),
        BOTANICAL_PIE("You use the golovanova fruit top on the pie shell to make a raw botanical pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 52, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.UNCOOKED_BOTANICAL_PIE), new ItemPair(Items.GOLOVANOVA_FRUIT_TOP, -1))),
        MUSHROOM_PIE("You use the sulliuscep cap on the pie shell to make a raw mushroom pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 60, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, 21684), new ItemPair(21626, -1))),
        PART_ADMIRAL_PIE_1("You use the salmon on the pie shell to make part of a admiral pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 70, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_ADMIRAL_PIE), new ItemPair(Items.SALMON, -1))),
        PART_ADMIRAL_PIE_2("You use the tuna on the pie shell to make part of a admiral pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 70, 0)),
                Arrays.asList(new ItemPair(Items.PART_ADMIRAL_PIE, Items.PART_ADMIRAL_PIE_2), new ItemPair(Items.TUNA, -1))),
        ADMIRAL_PIE("You use the potato on the pie shell to make a raw admiral pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 70, 0)),
                Arrays.asList(new ItemPair(Items.PART_ADMIRAL_PIE_2, Items.RAW_ADMIRAL_PIE), new ItemPair(Items.POTATO, -1))),
        DRAGONFRUIT_PIE("You use the dragonfruit on the pie shell to make a raw dragonfruit pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 73, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, 22789), new ItemPair(22929, -1))),
        PART_WILD_PIE_1("You use the bear meat on the pie shell to make part of a wild pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 85, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_WILD_PIE), new ItemPair(Items.RAW_BEAR_MEAT, -1))),
        PART_WILD_PIE_2("You use the chompy meat on the pie shell to make part of a wild pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 85, 0)),
                Arrays.asList(new ItemPair(Items.PART_WILD_PIE, Items.PART_WILD_PIE_2), new ItemPair(Items.RAW_CHOMPY, -1))),
        WILD_PIE("You use the rabbit meat on the pie shell to make a raw wild pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 85, 0)),
                Arrays.asList(new ItemPair(Items.PART_WILD_PIE_2, Items.RAW_WILD_PIE), new ItemPair(Items.RAW_RABBIT, -1))),
        PART_SUMMER_PIE_1("You use the strawberry on the pie shell to make part of a summer pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 95, 0)),
                Arrays.asList(new ItemPair(Items.PIE_SHELL, Items.PART_SUMMER_PIE), new ItemPair(Items.STRAWBERRY, -1))),
        PART_SUMMER_PIE_2("You use the watermelon on the pie shell to make part of a summer pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 95, 0)),
                Arrays.asList(new ItemPair(Items.PART_SUMMER_PIE, Items.PART_SUMMER_PIE_2), new ItemPair(Items.WATERMELON, -1))),
        SUMMER_PIE("You use the apple on the pie shell to make a raw summer pie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 95, 0)),
                Arrays.asList(new ItemPair(Items.PART_SUMMER_PIE_2, Items.RAW_SUMMER_PIE), new ItemPair(Items.COOKING_APPLE, -1))),


        /**
         * Stews
         */
        INCOMPLETE_STEW("You use the potato on the bowl of water to make an incomplete stew.", Collections.singletonList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(Items.BOWL_OF_WATER, Items.INCOMPLETE_STEW), new ItemPair(Items.POTATO, -1))),
        UNCOOKED_STEW("You use the chicken on the incomplete stew to make an uncooked stew.", Collections.singletonList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(Items.INCOMPLETE_STEW, Items.UNCOOKED_STEW), new ItemPair(Items.COOKED_CHICKEN, -1))),
        UNCOOKED_STEW2("You use the meat on the incomplete stew to make an uncooked stew.", Collections.singletonList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(Items.INCOMPLETE_STEW, Items.UNCOOKED_STEW), new ItemPair(Items.COOKED_MEAT, -1))),


        /**
         * Pizza
         */
        INCOMPLETE_PIZZA("You use the tomato on the pizza base to make an incomplete pizza.", Collections.singletonList(new SkillRequired(StatType.Cooking, 35, 0)),
                Arrays.asList(new ItemPair(Items.PIZZA_BASE, Items.INCOMPLETE_PIZZA), new ItemPair(Items.TOMATO, -1))),
        UNCOOKED_PIZZA("You use the cheese on the incomplete pizza to make an uncooked pizza.", Collections.singletonList(new SkillRequired(StatType.Cooking, 35, 0)),
                Arrays.asList(new ItemPair(Items.INCOMPLETE_PIZZA, Items.UNCOOKED_PIZZA), new ItemPair(Items.CHEESE, -1))),
        MEAT_PIZZA("You use the chicken on the pizza to make a meat pizza.", Collections.singletonList(new SkillRequired(StatType.Cooking, 45, 26)),
                Arrays.asList(new ItemPair(Items.PLAIN_PIZZA, Items.MEAT_PIZZA), new ItemPair(Items.COOKED_CHICKEN, -1))),
        MEAT_PIZZA2("You use the meat on the pizza to make a meat pizza.", Collections.singletonList(new SkillRequired(StatType.Cooking, 45, 26)),
                Arrays.asList(new ItemPair(Items.PLAIN_PIZZA, Items.MEAT_PIZZA), new ItemPair(Items.COOKED_MEAT, -1))),
        ANCHOVY_PIZZA("You use the anchovy on the pizza to make an anchovy pizza.", Collections.singletonList(new SkillRequired(StatType.Cooking, 55, 39)),
                Arrays.asList(new ItemPair(Items.PLAIN_PIZZA, Items.ANCHOVY_PIZZA), new ItemPair(Items.ANCHOVIES, -1))),
        PINEAPPLE_PIZZA("You use the pineapple the on pizza to make a pineapple pizza.", Collections.singletonList(new SkillRequired(StatType.Cooking, 65, 52)),
                Arrays.asList(new ItemPair(Items.PLAIN_PIZZA, Items.PINEAPPLE_PIZZA), new ItemPair(Items.PINEAPPLE_CHUNKS, -1))),
        PINEAPPLE_PIZZA2("You use the pineapple the on pizza to make a pineapple pizza.", Collections.singletonList(new SkillRequired(StatType.Cooking, 65, 52)),
                Arrays.asList(new ItemPair(Items.PLAIN_PIZZA, Items.PINEAPPLE_PIZZA), new ItemPair(Items.PINEAPPLE_RING, -1))),

        /**
         * Potatoes
         */
        POTATO_WITH_BUTTER("You use the butter on the potato to make potato with butter.", Collections.singletonList(new SkillRequired(StatType.Cooking, 39, 40.0)),
                Arrays.asList(new ItemPair(Items.BAKED_POTATO, Items.POTATO_WITH_BUTTER), new ItemPair(Items.PAT_OF_BUTTER, -1))),
        CHILLI_POTATO("You use the chili on the potato to make chilli potato.", Collections.singletonList(new SkillRequired(StatType.Cooking, 41, 15)),
                Arrays.asList(new ItemPair(Items.POTATO_WITH_BUTTER, Items.CHILLI_POTATO), new ItemPair(Items.CHILLI_CON_CARNE, -1))),
        POTATO_WITH_CHEESE("You use the cheese on the potato to make potato with cheese.", Collections.singletonList(new SkillRequired(StatType.Cooking, 47, 40)),
                Arrays.asList(new ItemPair(Items.POTATO_WITH_BUTTER, Items.POTATO_WITH_CHEESE), new ItemPair(Items.CHEESE, -1))),
        EGG_POTATO("You use the egg on the potato to make egg potato.", Collections.singletonList(new SkillRequired(StatType.Cooking, 51, 45)),
                Arrays.asList(new ItemPair(Items.POTATO_WITH_BUTTER, Items.EGG_POTATO), new ItemPair(Items.EGG_AND_TOMATO, -1))),
        MUSHROOM_POTATO("You use the mushroom on the potato to make mushroom potato.", Collections.singletonList(new SkillRequired(StatType.Cooking, 64, 55)),
                Arrays.asList(new ItemPair(Items.POTATO_WITH_BUTTER, Items.MUSHROOM_POTATO), new ItemPair(Items.MUSHROOM_ONION, -1))),
        TUNA_POTATO("You use the tuna on the potato to make tuna potato.", Collections.singletonList(new SkillRequired(StatType.Cooking, 68, 10)),
                Arrays.asList(new ItemPair(Items.POTATO_WITH_BUTTER, Items.TUNA_POTATO), new ItemPair(Items.TUNA_AND_CORN, -1))),

        /**
         * Cake
         */
        UNCOOKED_CAKE("You use the ingredients on the cake tin to make an uncooked cake.", Collections.singletonList(new SkillRequired(StatType.Cooking, 40, 0)),
                Arrays.asList(new ItemPair(Items.CAKE_TIN, Items.UNCOOKED_CAKE), new ItemPair(Items.EGG, -1), new ItemPair(Items.BUCKET_OF_MILK, Items.BUCKET), new ItemPair(Items.POT_OF_FLOUR, Items.POT))),
        CHOCOLATE_CAKE("You use the chocolate dust on the cake to make a chocolate cake.", Collections.singletonList(new SkillRequired(StatType.Cooking, 50, 30)),
                Arrays.asList(new ItemPair(Items.CAKE, Items.CHOCOLATE_CAKE), new ItemPair(Items.CHOCOLATE_DUST, -1))),
        CHOCOLATE_CAKE2("You use the chocolate bar on the cake to make a chocolate cake.", Collections.singletonList(new SkillRequired(StatType.Cooking, 50, 30)),
                Arrays.asList(new ItemPair(Items.CAKE, Items.CHOCOLATE_CAKE), new ItemPair(Items.CHOCOLATE_BAR, -1))),

        /**
         * Kebabs
         */
        CHOPPED_UGTHANKI("You use the ugthanki on an empty bowl to make chopped ugthanki.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.CHOPPED_UGTHANKI), new ItemPair(Items.UGTHANKI_MEAT, -1))),
        UGTHANKI_AND_ONION("You use the meat on the chopped onion to make ugthanki and onion.",
                Arrays.asList(new ItemPair(Items.CHOPPED_ONION, Items.UGTHANKI_ONION), new ItemPair(Items.UGTHANKI_MEAT, -1))),
        UGTHANKI_AND_ONION_2("You use the onion on the chopped ugthanki to make ugthanki and onion.",
                Arrays.asList(new ItemPair(Items.CHOPPED_UGTHANKI, Items.UGTHANKI_ONION), new ItemPair(Items.ONION, -1))),
        UGTHANKI_AND_TOMATO("You use the tomato on the chopped ugthanki to make ugthanki and tomato.",
                Arrays.asList(new ItemPair(Items.CHOPPED_UGTHANKI, Items.UGTHANKI_TOMATO), new ItemPair(Items.TOMATO, -1))),
        KEBAB_MIX_TOMATO("You use the tomato on the ugthanki and onion to make a kebab mix.", Collections.singletonList(new SkillRequired(StatType.Cooking, 58, 0)),
                Arrays.asList(new ItemPair(Items.UGTHANKI_ONION, Items.KEBAB_MIX), new ItemPair(Items.TOMATO, -1))),
        KEBAB_MIX_ONION("You use the onion on the ugthanki and tomato to make a kebab mix.", Collections.singletonList(new SkillRequired(StatType.Cooking, 58, 0)),
                Arrays.asList(new ItemPair(Items.UGTHANKI_TOMATO, Items.KEBAB_MIX), new ItemPair(Items.ONION, -1))),
        KEBAB_MIX_MEAT("You use the meat on the onion and tomato to make a kebab mix.", Collections.singletonList(new SkillRequired(StatType.Cooking, 58, 0)),
                Arrays.asList(new ItemPair(Items.ONION_TOMATO, Items.KEBAB_MIX), new ItemPair(Items.UGTHANKI_MEAT, -1))),
        KEBAB("You add the kebab mix to the pitta to make a kebab.", Collections.singletonList(new SkillRequired(StatType.Cooking, 58, 40)),
                Arrays.asList(new ItemPair(Items.PITTA_BREAD, Items.UGTHANKI_KEBAB), new ItemPair(Items.KEBAB_MIX, -1))),
        SUPER_KEBAB_1("You cover the kebab in red hot saunce.",
                Arrays.asList(new ItemPair(Items.KEBAB, Items.SUPER_KEBAB), new ItemPair(Items.RED_HOT_SAUCE, -1))),
        SUPER_KEBAB_2("You cover the kebab in red hot saunce.",
                Arrays.asList(new ItemPair(Items.UGTHANKI_KEBAB, Items.SUPER_KEBAB), new ItemPair(Items.RED_HOT_SAUCE, -1))),
        SUPER_KEBAB_3("You cover the kebab in red hot saunce.",
                Arrays.asList(new ItemPair(Items.UGTHANKI_KEBAB_2, Items.SUPER_KEBAB), new ItemPair(Items.RED_HOT_SAUCE, -1))),

        /**
         * Misc
         */
        UNCOOKED_EGG("You use the egg on the empty bowl to make uncooked egg.", Collections.singletonList(new SkillRequired(StatType.Cooking, 13, 0)),
                Arrays.asList(new ItemPair(Items.BOWL, Items.UNCOOKED_EGG), new ItemPair(Items.EGG, -1))),
        CHOPPED_ONION("You use the onion on an empty bowl to make chopped onion.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.CHOPPED_ONION), new ItemPair(Items.ONION, -1))),
        CHOPPED_GARLIC("You use the garlic on an empty bowl to make chopped garlic.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.CHOPPED_GARLIC), new ItemPair(Items.GARLIC, -1))),
        CHOPPED_TUNA("You use the tuna on an empty bowl to make chopped tuna.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.CHOPPED_TUNA), new ItemPair(Items.TUNA, -1))),
        CHOPPED_TOMATO("You use the tomato on an empty bowl to make chopped tomato.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.CHOPPED_TOMATO), new ItemPair(Items.TOMATO, -1))),
        MINCED_MEAT("You use the meat on an empty bowl to make minced meat.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.MINCED_MEAT), new ItemPair(Items.COOKED_MEAT, -1))),
        SLICED_MUSHROOMS("You use the mushrooms on an empty bowl to make sliced mushrooms.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.SLICED_MUSHROOMS), new ItemPair(Items.MUSHROOM, -1))),
        SWEETCORN_BOWL("You add the sweetcorn to the bowl.",
                Arrays.asList(new ItemPair(Items.BOWL, Items.SWEETCORN_2), new ItemPair(Items.COOKED_SWEETCORN, -1))),

        SPICY_SAUCE("You use the gnome spice on the garlic to make spicy sauce.", Collections.singletonList(new SkillRequired(StatType.Cooking, 9, 25)),
                Arrays.asList(new ItemPair(Items.CHOPPED_GARLIC, Items.SPICY_SAUCE), new ItemPair(Items.GNOME_SPICE, -1))),
        CHILLI_CON_CARNE("You use the meat on the spicy sauce to make chili con carne.", Collections.singletonList(new SkillRequired(StatType.Cooking, 11, 0)),
                Arrays.asList(new ItemPair(Items.SPICY_SAUCE, Items.CHILLI_CON_CARNE), new ItemPair(Items.COOKED_MEAT, -1))),
        CHILLI_CON_CARNE_MEAT("You use the minced meat on the spicy sauce to make chili con carne.", Collections.singletonList(new SkillRequired(StatType.Cooking, 11, 0)),
                Arrays.asList(new ItemPair(Items.SPICY_SAUCE, Items.CHILLI_CON_CARNE), new ItemPair(Items.MINCED_MEAT, -1))),
        EGG_AND_TOMATO("You use the tomato on the scrambled eggs to make egg and tomato.", Collections.singletonList(new SkillRequired(StatType.Cooking, 23, 0)),
                Arrays.asList(new ItemPair(Items.SCRAMBLED_EGG, Items.EGG_AND_TOMATO), new ItemPair(Items.TOMATO, -1))),
        ONION_AND_TOMATO("You use the tomato on the chopped onion to make onion and tomato.", Collections.singletonList(new SkillRequired(StatType.Cooking, 1, 0)),
                Arrays.asList(new ItemPair(Items.CHOPPED_ONION, Items.ONION_TOMATO), new ItemPair(Items.TOMATO, -1))),
        MUSHROOM_AND_ONION("You use the mushroom on the onion to make mushroom and onion.", Collections.singletonList(new SkillRequired(StatType.Cooking, 57, 0)),
                Arrays.asList(new ItemPair(Items.FRIED_MUSHROOMS, Items.MUSHROOM_ONION), new ItemPair(Items.FRIED_ONIONS, -1))),
        TUNA_AND_CORN("You use the tuna on the sweetcorn to make tuna and corn.", Collections.singletonList(new SkillRequired(StatType.Cooking, 67, 0)),
                Arrays.asList(new ItemPair(Items.CHOPPED_TUNA, Items.TUNA_AND_CORN), new ItemPair(Items.COOKED_SWEETCORN, -1))),
        RAW_FISHCAPE("You mix the items to form a raw fishcake.", Collections.singletonList(new SkillRequired(StatType.Cooking, 31, 0)),
                Arrays.asList(new ItemPair(Items.BREADCRUMBS, Items.RAW_FISHCAKE), new ItemPair(Items.GROUND_COD, -1), new ItemPair(Items.GROUND_KELP, -1), new ItemPair(Items.GROUND_CRAB_MEAT, -1))),
        WRAPPED_OOMLIE("You use the palm leaf on the raw oomlie to make wrapped oomlie.", Collections.singletonList(new SkillRequired(StatType.Cooking, 50, 10)),
                Arrays.asList(new ItemPair(Items.RAW_OOMLIE, Items.WRAPPED_OOMLIE), new ItemPair(Items.PALM_LEAF, -1))),
        NETTLE_WATER("You add the nettles to the water.", Collections.singletonList(new SkillRequired(StatType.Cooking, 20, 1)),
                Arrays.asList(new ItemPair(Items.BOWL_OF_WATER, Items.NETTLEWATER), new ItemPair(Items.NETTLES, -1))),

        /**
         * Crafting
         */
        LIGHT_ORB("You attach the wire to the orb.",
                Collections.singletonList(new SkillRequired(StatType.Crafting, 87, 104)),
                Arrays.asList(new ItemPair(Items.EMPTY_LIGHT_ORB, Items.LIGHT_ORB), new ItemPair(Items.CAVE_GOBLIN_WIRE, -1))),
        SPIKY_VAMBRACES("You stitch the claws into the vambraces.", 1249, -1,
                Collections.singletonList(new SkillRequired(StatType.Crafting, 32, 6)),
                Arrays.asList(new ItemPair(Items.LEATHER_VAMBRACES, Items.SPIKY_VAMBRACES), new ItemPair(Items.KEBBIT_CLAWS, -1))),
        GREEN_SPIKY_VAMBRACES("You stitch the claws into the vambraces.", 1249, -1,
                Collections.singletonList(new SkillRequired(StatType.Crafting, 32, 6)),
                Arrays.asList(new ItemPair(Items.GREEN_DHIDE_VAMB, Items.GREEN_SPIKY_VAMBS), new ItemPair(Items.KEBBIT_CLAWS, -1))),
        BLUE_SPIKY_VAMBRACES("You stitch the claws into the vambraces.", 1249, -1,
                Collections.singletonList(new SkillRequired(StatType.Crafting, 32, 6)),
                Arrays.asList(new ItemPair(Items.BLUE_DHIDE_VAMB, Items.BLUE_SPIKY_VAMBS), new ItemPair(Items.KEBBIT_CLAWS, -1))),
        RED_SPIKY_VAMBRACES("You stitch the claws into the vambraces.", 1249, -1,
                Collections.singletonList(new SkillRequired(StatType.Crafting, 32, 6)),
                Arrays.asList(new ItemPair(Items.RED_DHIDE_VAMB, Items.RED_SPIKY_VAMBS), new ItemPair(Items.KEBBIT_CLAWS, -1))),
        BLACK_SPIKY_VAMBRACES("You stitch the claws into the vambraces.", 1249, -1,
                Collections.singletonList(new SkillRequired(StatType.Crafting, 32, 6)),
                Arrays.asList(new ItemPair(Items.BLACK_DHIDE_VAMB, Items.BLACK_SPIKY_VAMBS), new ItemPair(Items.KEBBIT_CLAWS, -1))),

        ;

        public final int tickInterval, animation, graphics, inventorySpaceRequired;
        public final String combineMessage, warningMessage;
        public final List<SkillRequired> skillsRequired;
        public final List<ItemPair>[] items;

        ItemCombine(int tickInterval, int animation, int graphics, String combineMessage, String warningMessage, int inventorySpaceRequired, List<SkillRequired> skillsRequired, List<ItemPair>... items) {
            this.tickInterval = tickInterval;
            this.animation = animation;
            this.graphics = graphics;
            this.combineMessage = combineMessage;
            this.warningMessage = warningMessage;
            this.inventorySpaceRequired = inventorySpaceRequired;
            this.skillsRequired = skillsRequired;
            this.items = items;
        }

        ItemCombine(String combineMessage, int animation, int graphics, List<SkillRequired> skillsRequired, List<ItemPair>... items) {
            this(2, animation, graphics, combineMessage, "", 0, skillsRequired, items);
        }

        ItemCombine(String combineMessage, int inventorySpaceRequired, List<SkillRequired> skillsRequired, List<ItemPair>... items) {
            this(2, -1, -1, combineMessage, "", inventorySpaceRequired, skillsRequired, items);
        }

        ItemCombine(String combineMessage, List<SkillRequired> skillsRequired, List<ItemPair>... items) {
            this(2, -1, -1, combineMessage, "", 0, skillsRequired, items);
        }

        ItemCombine(String combineMessage, List<ItemPair>... items) {
            this(2, -1, -1, combineMessage, "", 0, null, items);
        }

        public void combine(Player player, List<ItemPair> itemReqs) {
            for (ItemPair i : itemReqs) {   // If the player runs out of items, break loop
                if (i.required != null && !player.getInventory().contains(i.required)) {
                    return;
                }
            }
            if (this.animation != -1) {  // If an animation exists, play it
                player.animate(this.animation);
            }
            if (this.graphics != -1) {   // If a graphic exists, play it
                player.animate(this.graphics);
            }
            if (!this.combineMessage.equalsIgnoreCase("")) { // If a combine message exists, send it
                player.sendFilteredMessage(this.combineMessage);
            }
            for (ItemPair i : itemReqs) {
                if (i.required != null && i.replacement != null) {
                    player.getInventory().findItem(i.required.getId()).setId(i.replacement.getId());
                } else if (i.required == null) {
                    player.getInventory().addOrDrop(i.replacement);
                } else {
                    player.getInventory().remove(i.required);
                }
            }
            if (this == ItemCombine.PINEAPPLE_PIZZA)
                player.getTaskManager().doLookupByUUID(99, 1);  // Make a Pineapple Pizza
            player.resetAnimation();
        }
    }

    static {
        for(ItemCombine itemCombine : ItemCombine.values()) {
            List<SkillItem> skillItems = new ArrayList<SkillItem>();
            for (List<ItemPair> items : itemCombine.items) {
                SkillItem item = new SkillItem(items.get(0).replacement.getId()).addAction((player, amount, event) -> {
                    whileLoop:
                    while(amount-- > 0) {
                        for (ItemPair i : items) {   // If the player runs out of items, break loop
                            if (i.required != null && !player.getInventory().contains(i.required)) {
                                break whileLoop;
                            }
                        }
                        if (itemCombine.animation != -1) {  // If an animation exists, play it
                            player.animate(itemCombine.animation);
                        }
                        if (itemCombine.graphics != -1) {   // If a graphic exists, play it
                            player.animate(itemCombine.graphics);
                        }
                        if (!itemCombine.combineMessage.equalsIgnoreCase("")) { // If a combine message exists, send it
                            player.sendFilteredMessage(itemCombine.combineMessage);
                        }
                        for (ItemPair i : items) {
                            if (i.required != null && i.replacement != null) {
                                player.getInventory().findItem(i.required.getId()).setId(i.replacement.getId());
                            } else if (i.required == null) {
                                player.getInventory().addOrDrop(i.replacement);
                            } else {
                                player.getInventory().remove(i.required);
                            }
                        }
                        event.delay(itemCombine.tickInterval);
                    }
                    player.resetAnimation();
                });
                skillItems.add(item);
            }
            for (List<ItemPair> itemReqs : itemCombine.items) {
                int mainId = 0;
                List<Integer> itemIds = new ArrayList<Integer>();
                for (ItemPair ip : itemReqs) {
                    if (ip.required != null) {
                        if (mainId == 0) {
                            mainId = ip.required.getId();
                        } else {
                            itemIds.add(ip.required.getId());
                        }
                    }
                }
                for (int itemId : itemIds) {
                    ItemItemAction.register(mainId, itemId, (player, item1, item2) -> {
                        if (itemCombine.skillsRequired != null) {
                            for (SkillRequired skill : itemCombine.skillsRequired) {
                                if(!player.getStats().check(skill.statType, skill.levelReq, itemReqs.get(0).replacement.getId(), "make the " + ItemDef.get(itemReqs.get(0).replacement.getId()) + ""))
                                    return;
                            }
                        }
                        if (player.getInventory().getFreeSlots() < itemCombine.inventorySpaceRequired) {
                            player.sendMessage("You do not have enough inventory space to do that.");
                            return;
                        }
                        if(player.getInventory().hasMultiple(itemReqs.get(1).required.getId()) || skillItems.size() > 1) {
                            SkillItem[] si = new SkillItem[skillItems.size()];
                            SkillDialogue.make(player, skillItems.toArray(si));
                        } else {
                            itemCombine.combine(player, itemReqs);
                        }
                    });
                }
            }
        }
    }

    /**
     * The skill required to combine the items.
     */
    private static class SkillRequired {
        public StatType statType;
        public int levelReq;
        public double experience;

        public SkillRequired(StatType statType, int levelReq, double experience) {
            this.statType = statType;
            this.levelReq = levelReq;
            this.experience = experience;
        }
    }

    private static class ItemPair {
        public Item required, replacement;

        public ItemPair(Item required, Item replacement) {
            this.required = required;
            this.replacement = replacement;
        }

        public ItemPair(int required, int replacement) {
            if (required == -1) {
                this.required = null;
            } else {
                this.required = new Item(required, 1);
            }
            if (replacement == -1) {
                this.replacement = null;
            } else {
                this.replacement = new Item(replacement, 1);
            }
        }
    }
}

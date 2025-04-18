package io.ruin.model.skills.cooking;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.skillcapes.CookingSkillCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

public class Cooking {

    private static final String[] FIRE_NAMES = { "fire", "cooking pot",
            // House fires
            "firepit", "firepit with hook", "firepit with pot",
            // Varlamore
            "campfire"
    };
    private static final String[] RANGE_NAMES = { "range", "cooking range", "stove", "sulphur vent", "gnome cooker", "clay oven",
            // House ranges
            "steel range", "fancy range", "large oven", "small oven",
            // Varlamore
            "oven"
    };
    public static final int COOKING_GAUNLETS = 775;

    static {
        for (Food food : Food.values()) {
            for (String name : FIRE_NAMES) {
                ItemObjectAction.register(food.rawID, name, (player, item, obj) -> Cooking.cook(player, food, obj, 897, true));
                ObjectAction.register(name, "cook", (player, obj) -> findCookable(player, obj, 897, true));
            }
            for (String name : RANGE_NAMES) {
                ItemObjectAction.register(food.rawID, name, (player, item, obj) -> Cooking.cook(player, food, obj, 896, false));
                ObjectAction.register(name, "cook", (player, obj) -> findCookable(player, obj, 896, false));
            }
        }
    }

    public static void cook(Player player, Food food, GameObject obj, int anim, boolean fire) {
        if (!player.getStats().check(StatType.Cooking, food.levelRequirement, "cook " + food.descriptiveName))
            return;
        SkillItem i = new SkillItem(food.rawID).name(food.rawName).
                addAction((p, amount, event) -> startCooking(p, food, obj, amount, anim, fire));
        if (food.equals(Food.RAW_MEAT)) {
            SkillItem sinew = new SkillItem(Food.SINEW_MEAT.cookedID).name(Food.SINEW_MEAT.itemName).
                    addAction((p, amount, event) -> startCooking(p, Food.SINEW_MEAT, obj, amount, anim, fire));
            SkillDialogue.make(player, i, sinew);
        } else if (food.equals(Food.RAW_BEAR_MEAT)) {
            SkillItem sinew = new SkillItem(Food.SINEW_BEAR_MEAT.cookedID).name(Food.SINEW_BEAR_MEAT.itemName).
                    addAction((p, amount, event) -> startCooking(p, Food.SINEW_BEAR_MEAT, obj, amount, anim, fire));
            SkillDialogue.make(player, i, sinew);
        } else if (food.equals(Food.RAW_KARAMBWAN)) {
            SkillItem poison = new SkillItem(Food.RAW_KARAMBWAN_P.cookedID).name(Food.RAW_KARAMBWAN_P.itemName).
                    addAction((p, amount, event) -> startCooking(p, Food.RAW_KARAMBWAN_P, obj, amount, anim, fire));
            SkillDialogue.make(player, i, poison);
        } else {
            if (player.getInventory().hasMultiple(food.rawID))
                SkillDialogue.cook(player, i);
            else
                startCooking(player, food, obj, 1, anim, fire);
        }
    }

    private static void startCooking(Player player, Food food, GameObject obj, int amountToCook, int anim, boolean fire) {
        if (!player.getStats().check(StatType.Cooking, food.levelRequirement, "cook " + food.descriptiveName))
            return;
        if (food.requirement != null && !food.requirement.test(player)) {
            player.dialogue(new MessageDialogue("You don't know how to cook this.<br>" + food.requirementMessage));
            return;
        }
        RandomEvent.attemptTrigger(player);
        player.startEvent(e -> {
            int amount = amountToCook;
            int prodCount = 0;
            boolean prodMaster = player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER);
            while (amount-- > 0) {
                Item rawFood = player.getInventory().findItem(food.rawID);
                if (rawFood == null) {
                    player.sendMessage("You don't have any more " + food.itemNamePlural + " to cook.");
                    break;
                }
                if (obj == null)
                    break;

                player.animate(anim);
                if (cookedFood(player, food, fire)) {
                    if (food == Food.GIANT_SEAWEED) {
                        rawFood.remove();
                        player.getInventory().addOrDrop(Items.SODA_ASH, 6);
                    } else {
                        rawFood.setId(food.cookedID);
                        if (ProductionMaster.roll(player))
                            prodCount++;
                    }
                    player.getStats().addXp(StatType.Cooking, food.experience * bonus(player, fire), true);
                    if (!prodMaster) player.sendFilteredMessage(cookingMessage(food));
                    PlayerCounter.COOKED_FOOD.increment(player, 1);
                    player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.COOKITEM, ItemDefinition.get(food.cookedID).name);
                    player.cookStreak++;
                    if (player.cookStreak >= 5)
                        player.getTaskManager().doLookupByUUID(21, 1);  // Cook 5 pieces of food in a row without burning them
                } else {
                    rawFood.setId(food.burntID);
                    if (food.burntID != food.cookedID) {
                        player.sendFilteredMessage("You accidentally burn the " + food.itemName + ".");
                    }
                    player.getTaskManager().doLookupByUUID(20, 1);  // Burn Some Food
                    player.cookStreak = 0;
                    PlayerCounter.BURNT_FOOD.increment(player, 1);
                }
                if (food == Food.DRUNK_DRAGON)
                    player.getTaskManager().doLookupByUUID(937);    // Make a Gnome Cocktail
                int returnedSecondary = food.returnedSecondary;
                if (returnedSecondary != -1)
                    player.getInventory().addOrDrop(new Item(returnedSecondary));
                if (fire)
                    PlayerCounter.COOKED_ON_FIRE.increment(player, 1);
                if (!prodMaster) {
                    e.delay(4);
                }
            }
            ProductionMaster.extra(player, prodCount, food.cookedID, StatType.Cooking, food.experience * prodCount, TaskCategory.COOKITEM);
        });
    }

    private static double bonus(Player player, boolean fire) {
        double bonus = 1.0;
        return bonus;
    }


    private static String cookingMessage(Food food) {
        if (food == Food.RAW_LOBSTER)
            return "You roast a lobster.";
        else if (food == Food.PIE_MEAT)
            return "You successfully bake a tasty meat pie.";
        else if (food == Food.REDBERRY_PIE)
            return "You successfully bake a delicious redberry pie.";
        else if (food == Food.SEAWEED || food == Food.GIANT_SEAWEED)
            return "You burn the seaweed to soda ash.";
        else
            return "You successfully cook " + food.descriptiveName + ".";
    }

    private static boolean cookedFood(Player player, Food food, Boolean fire) {
        if(food.burntID == -1)
            return true;
        if (CookingSkillCape.wearsCookingCape(player))
            return true;
        double burnBonus = 0.0;
        int levelReq = food.levelRequirement;
        int burnStop = getBurnStop(player, food, fire);
        if (burnStop <= levelReq) return true;
        if (!fire)
            burnBonus = 3.0;
        double burnChance = (55.0 - burnBonus);
        double cookingLevel = player.getStats().get(StatType.Cooking).currentLevel;
        double randNum = Random.get() * 100.0;

        burnChance -= ((cookingLevel - levelReq) * (burnChance / (burnStop - levelReq)));
        return burnChance <= randNum;
    }

    private static int getBurnStop(Player player, Food food, Boolean cookingOnRange) {
        Item gloves = player.getEquipment().get(Equipment.SLOT_HANDS);
        if (gloves != null && gloves.getId() == COOKING_GAUNLETS)
            return food.burnLevelCookingGauntlets;
        return cookingOnRange ? food.burnLevelRange : food.burnLevelFire;
    }

    public static void findCookable(Player player, GameObject obj, int anim, boolean fire) {
        List<SkillItem> items = new ArrayList<>();
        for (Food food : Food.values()) {
            if (player.getInventory().contains(food.rawID) && food != Food.SINEW_BEAR_MEAT && food != Food.SINEW_MEAT && food != Food.RAW_KARAMBWAN_P) {
                SkillItem i = new SkillItem(food.rawID).name(food.rawName).
                        addAction((p, amount, event) -> startCooking(p, food, obj, amount, anim, fire));
                items.add(i);
                if (food.equals(Food.RAW_MEAT)) {
                    SkillItem sinew = new SkillItem(Food.SINEW_MEAT.cookedID).name(Food.SINEW_MEAT.itemName).
                            addAction((p, amount, event) -> startCooking(p, Food.SINEW_MEAT, obj, amount, anim, fire));
                    items.add(sinew);
                } else if (food.equals(Food.RAW_BEAR_MEAT)) {
                    SkillItem sinew = new SkillItem(Food.SINEW_BEAR_MEAT.cookedID).name(Food.SINEW_BEAR_MEAT.itemName).
                            addAction((p, amount, event) -> startCooking(p, Food.SINEW_BEAR_MEAT, obj, amount, anim, fire));
                    items.add(sinew);
                } else if (food.equals(Food.RAW_KARAMBWAN)) {
                    SkillItem poison = new SkillItem(Food.RAW_KARAMBWAN_P.cookedID).name(Food.RAW_KARAMBWAN_P.itemName).
                            addAction((p, amount, event) -> startCooking(p, Food.RAW_KARAMBWAN_P, obj, amount, anim, fire));
                    items.add(poison);
                }
            }
        }
        if (items.size() > 0) {
            SkillDialogue.cook(player, items.toArray(new SkillItem[0]));
        } else {
            player.sendMessage("You do not have anything to cook.");
        }
    }
}

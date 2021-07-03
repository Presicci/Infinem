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
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/22/2021
 */
public class ItemCombineAction {

    private enum ItemCombine {
        BREAD_DOUGH_BUCKET("You mix the flour and water to make dough.", 1, Arrays.asList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(-1, Items.BREAD_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET)),
                Arrays.asList(new ItemPair(-1, Items.PASTRY_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET)),
                Arrays.asList(new ItemPair(-1, Items.PITTA_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET)),
                Arrays.asList(new ItemPair(-1, Items.PIZZA_BASE), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BUCKET_OF_WATER, Items.BUCKET))),

        BREAD_DOUGH_BOWL("You mix the flour and water to make dough.", 1, Arrays.asList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(-1, Items.BREAD_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL)),
                Arrays.asList(new ItemPair(-1, Items.PASTRY_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL)),
                Arrays.asList(new ItemPair(-1, Items.PITTA_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL)),
                Arrays.asList(new ItemPair(-1, Items.PIZZA_BASE), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.BOWL_OF_WATER, Items.BOWL))),

        BREAD_DOUGH_JUG("You mix the flour and water to make dough.", 1, Arrays.asList(new SkillRequired(StatType.Cooking, 1, 1)),
                Arrays.asList(new ItemPair(-1, Items.BREAD_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG)),
                Arrays.asList(new ItemPair(-1, Items.PASTRY_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG)),
                Arrays.asList(new ItemPair(-1, Items.PITTA_DOUGH), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG)),
                Arrays.asList(new ItemPair(-1, Items.PIZZA_BASE), new ItemPair(Items.POT_OF_FLOUR, Items.POT), new ItemPair(Items.JUG_OF_WATER, Items.JUG))),
        ;

        public int tickInterval, animation, graphics, inventorySpaceRequired;
        public String combineMessage, warningMessage;
        public List<SkillRequired> skillsRequired;
        public List<ItemPair>[] items;

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

        ItemCombine(String combineMessage, int inventorySpaceRequired, List<SkillRequired> skillsRequired, List<ItemPair>... items) {
            this(2, -1, -1, combineMessage, "", inventorySpaceRequired, skillsRequired, items);
        }

        public void combine(Player player, List<ItemPair> itemReqs) {
            for (ItemPair i : itemReqs) {   // If the player runs out of items, break loop
                if (i.required.getId() != -1 && !player.getInventory().contains(i.required)) {
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
                player.sendMessage(this.combineMessage);
            }
            for (ItemPair i : itemReqs) {
                if (i.required.getId() != -1 && i.replacement.getId() != 1) {
                    player.getInventory().findItem(i.required.getId()).setId(i.replacement.getId());
                } else if (i.required.getId() == -1) {
                    player.getInventory().addOrDrop(i.replacement);
                } else if (i.replacement.getId() == -1) {
                    player.getInventory().remove(i.required);
                }
            }
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
                            if (i.required.getId() != -1 && !player.getInventory().contains(i.required)) {
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
                            player.sendMessage(itemCombine.combineMessage);
                        }
                        for (ItemPair i : items) {
                            if (i.required.getId() != -1 && i.replacement.getId() != 1) {
                                player.getInventory().findItem(i.required.getId()).setId(i.replacement.getId());
                            } else if (i.required.getId() == -1) {
                                player.getInventory().addOrDrop(i.replacement);
                            } else if (i.replacement.getId() == -1) {
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
                ItemItemAction.register(itemReqs.get(1).required.getId(), itemReqs.get(2).required.getId(), (player, item1, item2) -> {
                    for (SkillRequired skill : itemCombine.skillsRequired) {
                        if(!player.getStats().check(skill.statType, skill.levelReq, itemReqs.get(0).replacement.getId(), "make the " + ItemDef.get(itemReqs.get(0).replacement.getId()) + ""))
                            return;
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
            this.required = new Item(required, 1);
            this.replacement = new Item(replacement, 1);
        }
    }
}

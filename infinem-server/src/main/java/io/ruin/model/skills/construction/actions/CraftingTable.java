package io.ruin.model.skills.construction.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CraftingTable {
    CLOCKWORKS(Items.CLOCKWORK, 8, 15, new Item(Items.STEEL_BAR)),
    TOY_HORSEY(2526, 10, 15, new Item(960)),
    TOY_SOLDIER(7759, 13, 15, new Item(8792), new Item(960)),
    WOODEN_CAT(10891, 16, 15, new Item(960), new Item(6814)),
    TOY_DOLL(7763, 18, 15, new Item(8792), new Item(960)),
    SEXTANT(2574, 23, 15, new Item(2353)),
    WATCH(2575, 28, 15, new Item(8792), new Item(2353)),
    CLOCKWORK_SUIT(10595, 30, 15, new Item(8792), new Item(960), new Item(950)),
    TOY_MOUSE(7767, 33, 15, new Item(8792), new Item(960)),
    TOY_CAT(7771, 85, 15, new Item(8792), new Item(860));

    private final int product, craftingLevel;
    private final double craftingExperience;
    private final Item[] requiredItems;

    CraftingTable(int product, int craftingLevel, double craftingExperience, Item... requiredItems) {
        this.product = product;
        this.craftingLevel = craftingLevel;
        this.craftingExperience = craftingExperience;
        this.requiredItems = requiredItems;
    }

    private void make(Player player, int amount) {
        if (!player.getStats().check(StatType.Crafting, craftingLevel, "make that."))
            return;
        for (Item item : requiredItems) {
            if (!player.getInventory().hasItem(item.getId(), item.getAmount())) {
                player.sendMessage("You need " + Utils.grammarCorrectListForItems(Arrays.asList(requiredItems)) + " to make that.");
                return;
            }
        }
        player.startEvent(e -> {
            player.animate(4103);
            e.delay(1);
            int made = 0;
            while (made++ < amount) {
                player.animate(-1);
                for (Item item : requiredItems) {
                    if (!player.getInventory().hasItem(item.getId(), item.getAmount())) {
                        return;
                    }
                }
                for (Item item : requiredItems) {
                    player.getInventory().remove(item.getId(), item.getAmount());
                }
                player.animate(4109);
                player.getInventory().add(product);
                player.getStats().addXp(StatType.Crafting, craftingExperience, true);
            }
        });
    }

    private static List<CraftingTable> DISABLED = Arrays.asList(TOY_DOLL, TOY_CAT, TOY_MOUSE, TOY_SOLDIER, WATCH, SEXTANT, CLOCKWORK_SUIT);

    static void make(Player player, CraftingTable... items) {
        List<SkillItem> skillItems = new ArrayList<>();
        for (CraftingTable item : items) {
            if (DISABLED.contains(item)) continue;
            skillItems.add(new SkillItem(item.product).addAction((p, amount, event) -> item.make(player, amount)));
        }
        SkillDialogue.make(player, skillItems.toArray(new SkillItem[0]));
    }
}

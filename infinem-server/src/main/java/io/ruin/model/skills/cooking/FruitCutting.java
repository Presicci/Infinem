package io.ruin.model.skills.cooking;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2023
 */
public class FruitCutting {

    public static void cut(Player player, CuttableFruit cuttableFruit) {
        SkillItem[] skillItems = new SkillItem[cuttableFruit.getProducts().size()];
        for (int index = 0; index < skillItems.length; index++) {
            Item item = cuttableFruit.getProducts().get(index);
            String itemName = item.getDef().name;
            SkillItem i = new SkillItem(item.getId()).name(itemName.contains("chunks") ? "Slice it into chunks" : itemName.contains("ring") ? "Slice it into 4 rings" : "Slice it into slices").
                    addAction((p, amount, event) -> startCutting(p, cuttableFruit, item, amount));
            skillItems[index] = i;
        }
        SkillDialogue.make("How would you like to cut the " + ItemDefinition.get(cuttableFruit.getItemId()).name + "?", player, skillItems);
    }

    public static void startCutting(Player player, CuttableFruit cuttableFruit, Item item, int amount) {
        if (!player.getInventory().hasFreeSlots(item.getAmount() - 1)) {
            player.sendMessage("You don't have enough inventory space to cut it like that.");
            return;
        }
        player.startEvent(event -> {
            int made = 0;
            while (made++ < amount) {
                Item knife = player.getInventory().findItem(Tool.KNIFE);
                if (knife == null) {
                    break;
                }
                Item fruit = player.getInventory().findItem(cuttableFruit.getItemId());
                if (fruit == null) {
                    break;
                }
                if (!player.getInventory().hasFreeSlots(item.getAmount() - 1)) {
                    player.sendMessage("You have run out of inventory space.");
                    break;
                }
                fruit.remove(1);
                player.getInventory().add(item);
                player.animate(1248);
                player.sendFilteredMessage("You slice up the " + ItemDefinition.get(cuttableFruit.getItemId()).name + ".");
                event.delay(2);
            }
        });
    }

    static {
        for (CuttableFruit cuttableFruit : CuttableFruit.values()) {
            ItemItemAction.register(Items.KNIFE, cuttableFruit.getItemId(), ((player, primary, secondary) -> cut(player, cuttableFruit)));
        }
    }
}

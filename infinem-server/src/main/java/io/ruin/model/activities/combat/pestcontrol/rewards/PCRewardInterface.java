package io.ruin.model.activities.combat.pestcontrol.rewards;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/5/2024
 */
public class PCRewardInterface {

    /**
     * Opens the rewards interface and displays all items / perks buyable with Pest Control points.
     * @param player The player being shown the rewards interface
     */
    private static void displayRewardsShop(Player player) {
        player.removeTemporaryAttribute("PC_SHOP_SLOT");
        player.openInterface(InterfaceType.MAIN, Interface.PEST_CONTROL_REWARDS);
        player.getPacketSender().sendAccessMask(Interface.PEST_CONTROL_REWARDS, 3, 0, 82, 2);
        player.getPacketSender().sendAccessMask(Interface.PEST_CONTROL_REWARDS, 6, 0, 31, 2);
        player.getPacketSender().sendVarp(261, player.getAttributeIntOrZero("PEST_POINTS"));
    }

    /**
     * Handles the selection of a shop item within the Void Knights' Reward Options
     * @param player The player being selecting the items in the shop
     */
    private static void selectShopItem(Player player, int slot) {
        player.putTemporaryAttribute("PC_SHOP_SLOT", slot);
    }

    private static void purchaseStaticPack(Player player, PCReward reward, LootTable lootTable) {
        if (player.getAttributeIntOrZero("PEST_POINTS") < reward.getCost()) {
            player.sendMessage("You do not have enough Pest Points to purchase this "+ reward.getName() +".");
            return;
        }
        List<Item> loot = lootTable.rollItems(true);
        List<Integer> lootIds = new ArrayList<>();
        loot.forEach(i -> lootIds.add(i.getId()));
        if (!player.getInventory().hasRoomForAll(lootIds.stream().mapToInt(i->i).toArray())) {
            player.sendMessage("You need " + loot.size() + " free inventory slots to claim this reward.");
            return;
        }
        for (Item item : loot) {
            player.getInventory().addOrDrop(item);
        }
        player.incrementNumericAttribute("PEST_POINTS", -reward.getCost());
        player.removeTemporaryAttribute("PC_SHOP_SLOT");
    }

    /**
     * Attempts to purchase the selected item if the player has enough points to do so. If completed, we deduct the cost and reset
     * their selection.
     * @param player The player confirming the shop purchase
     */
    private static void confirmShopPurchase(Player player) {
        int slot = player.getTemporaryAttributeIntOrZero("PC_SHOP_SLOT");
        if (slot != 0) {
            if (slot < 37) {    // Experience rewards
                PCExperienceReward reward = PCExperienceReward.REWARDS.get(slot);
                reward.purchase(player, slot);
            } else {
                PCReward reward = PCReward.REWARDS.get(slot);
                switch (reward) {
                    case HERB_PACK:
                        purchaseStaticPack(player, reward, PCReward.HERB_PACK_LOOT);
                        break;
                    case MINERAL_PACK:
                        purchaseStaticPack(player, reward, PCReward.MINERAL_PACK_LOOT);
                        break;
                    case SEED_PACK:
                        if (player.getAttributeIntOrZero("PEST_POINTS") < reward.getCost()) {
                            player.sendMessage("You do not have enough Pest Points to purchase this "+ reward.getName() +".");
                            return;
                        }
                        if (!player.getInventory().hasFreeSlots(3)) {
                            player.sendMessage("You need 3 free inventory slots to claim this reward.");
                            return;
                        }
                        List<Item> loot = PCReward.SEED_PACK_LOOT.rollItems(true);
                        for (Item item : loot) {
                            player.getInventory().addOrDrop(item);
                        }
                        player.incrementNumericAttribute("PEST_POINTS", -reward.getCost());
                        player.removeTemporaryAttribute("PC_SHOP_SLOT");
                        break;
                    default:
                        if (player.getInventory().isFull()) {
                            player.sendMessage("You do not have enough space in your inventory.");
                            return;
                        }
                        if (player.getAttributeIntOrZero("PEST_POINTS") < reward.getCost()) {
                            player.sendMessage("You do not have enough Pest Points to purchase this "+ reward.getName() +".");
                            return;
                        }
                        player.getInventory().add(new Item(reward.getItemId()));
                        player.getCollectionLog().collect(reward.getItemId());
                        player.incrementNumericAttribute("PEST_POINTS", -reward.getCost());
                        player.removeTemporaryAttribute("PC_SHOP_SLOT");
                        break;
                }
            }
        }
    }

    static {
        NPCAction.register(1755, "exchange", (player, npc) -> displayRewardsShop(player));
        InterfaceHandler.register(Interface.PEST_CONTROL_REWARDS, h -> {
            h.actions[3] = (SlotAction) PCRewardInterface::selectShopItem;
            h.actions[6] = (SimpleAction) PCRewardInterface::confirmShopPurchase;
        });
    }
}

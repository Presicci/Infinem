package io.ruin.model.activities.nightmarezone;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class NightmareZoneRewards {

    static {
        InterfaceHandler.register(Interface.NIGHTMARE_ZONE_REWARDS, h -> {
            h.actions[4] = (DefaultAction) (p, option, slot, itemId) -> {
                action(p, 4, option, itemId);
            };
            h.actions[5] = (DefaultAction) (p, option, slot, itemId) -> {
                action(p, 5, option, itemId);
            };
            h.actions[6] = (DefaultAction) (p, option, slot, itemId) -> {
                action(p, 6, option, itemId);
            };
        });
    }

    public static void imbueItem(Player player, int id) {
        if (!player.getInventory().contains(id))
            return;
        NMZUpgradable upgradable = NMZUpgradable.getUpgradable(id);
        if (upgradable == null) {
            player.sendMessage("This item currently cannot be upgraded, if this is a mistake, please contact staff.");
            return;
        }
        String name = new Item(id).getDef().name;

        if (player.nmzRewardPoints < upgradable.price) {
            player.sendMessage("You must have at least " + NumberUtils.formatNumber(upgradable.price) + " NMZ Points to upgrade your " + name);
            return;
        }

        player.nmzRewardPoints -= upgradable.price;
        player.getInventory().remove(id, 1);
        player.getInventory().add(new Item(upgradable.upgraded));
        player.sendMessage("You have upgraded your " + name + " for " + NumberUtils.formatNumber(upgradable.price) + " points.");
        refresh(player);
    }

    public static void purchaseReward(Player player, int id, int amount) {
        NMZResource resource = NMZResource.getUpgradable(id);
        if (resource == null) {
            player.sendMessage("This item currently cannot be purchased, if this is a mistake, please contact staff.");
            return;
        }
        if (resource.price * amount < 0)
            amount = Integer.MAX_VALUE / resource.price;
        String name = new Item(id).getDef().name;
        if (player.nmzRewardPoints < amount * resource.price)
            amount = player.nmzRewardPoints/ resource.price;
        if (amount <= 0) {
            player.sendMessage("You must have at least " + NumberUtils.formatNumber(resource.price) + " NMZ Points to purchase " + name);
            return;
        }
        if ((!new Item(id).noteable() && !ItemDef.get(id).stackable) && amount > player.getInventory().getFreeSlots())
            amount = player.getInventory().getFreeSlots();
        Item item = new Item(id, amount).note();
        if (!player.getInventory().hasRoomFor(item)) {
            player.sendMessage("You currently have no space in your inventory.");
            return;
        }


        player.nmzRewardPoints -= resource.price * amount;
        player.getInventory().add(item);
        refresh(player);
    }

    public static void purchaseBenefit(Player player, int id, int amount) {
        NMZBenefits benefit = NMZBenefits.getBenefits(id);
        if (amount > 5)
            amount = player.getInventory().getFreeSlots();
        if (amount < 0)
            amount = player.getInventory().getFreeSlots();
        if (benefit == null) {
            player.sendMessage("This item currently cannot be purchased, if this is a mistake, please contact staff.");
            return;
        }
        String name = new Item(id).getDef().name;
        if (player.nmzRewardPoints < amount * benefit.getPrice())
            amount = player.nmzRewardPoints/ benefit.getPrice();
        if (amount <= 0) {
            player.sendMessage("You must have at least " + NumberUtils.formatNumber(benefit.getPrice()) + " NMZ Points to purchase " + name);
            return;
        }
        if (new Item(id).noteable() && amount > player.getInventory().getFreeSlots())
            amount = player.getInventory().getFreeSlots();
        if (player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You currently have no space in your inventory.");
            return;
        }
        player.nmzRewardPoints -= benefit.getPrice() * amount;
        player.getInventory().add(new Item(id - 3, amount).note());
        //player.sendMessage("You now have " + benefit.getConfig().increment(player, amount) + " doses of " + name.split(Pattern.quote("("))[0].trim() + ".");
        refresh(player);
    }

    public static void open(Player player) {
        /**
         * sends the nightmare zone rewards interface
         */
        player.openInterface(InterfaceType.MAIN, Interface.NIGHTMARE_ZONE_REWARDS);
        refresh(player);
    }

    public static void refresh(Player player) {
        /**
         * setting the nmz reward points (config refresher)
         */
        Config.NMZ_REWARD_POINTS_TOTAL.set(player, player.nmzRewardPoints);
        for (int i = 0; i < 200; i++) {
            /** 4 = Resources, 5 = Upgrades, 6 = Benefits */
            player.getPacketSender().sendAccessMask(Interface.NIGHTMARE_ZONE_REWARDS, NightmareZoneObjects.RESOURCES, 0, i, 1086);
            player.getPacketSender().sendAccessMask(Interface.NIGHTMARE_ZONE_REWARDS, NightmareZoneObjects.UPGRADES, 0, i, 1086);
            player.getPacketSender().sendAccessMask(Interface.NIGHTMARE_ZONE_REWARDS, NightmareZoneObjects.BENEFITS, 0, i, 1086);

            /**
             * Send item prices
             * let test
             * *
             */
            ArrayList<Item> upgradables = new ArrayList<Item>();
            for (NMZUpgradable upgradable : NMZUpgradable.values()) {
                upgradables.add( new Item(upgradable.id,upgradable.price));
            }
            for (NMZResource resource : NMZResource.values()) {
                upgradables.add( new Item(resource.id,resource.price));
            }
            for (NMZBenefits benefits : NMZBenefits.values()) {
                upgradables.add( new Item(benefits.getId(),benefits.getPrice()));
            }
            player.getPacketSender().sendItems(-1, 4, 464, upgradables.toArray(new Item[0]));

        }
    }


    public static void action(Player player, int childId, int option, int itemId) {
        if (childId == 5) {
            if (option == 1) {
                imbueItem(player, itemId);
            } else {
                new Item(itemId).examine(player);
            }
        } else if (childId == 4) {
            if (option == 1) {
                purchaseReward(player, itemId, 1);
            } else if (option == 2) {
                purchaseReward(player, itemId, 5);
            } else if (option == 3) {
                purchaseReward(player, itemId, 10);
            } else if (option == 4) {
                purchaseReward(player, itemId, 50);
            } else if (option == 5) {
                player.integerInput("How many would you like to purchase?", amt -> purchaseReward(player, itemId, amt));
            } else {
                new Item(itemId).examine(player);
            }
        } else if (childId == 6)
            if (option == 1) {
                purchaseBenefit(player, itemId, 1);
            } else if (option == 2) {
                purchaseBenefit(player, itemId, 5);
            } else if (option == 3) {
                purchaseBenefit(player, itemId, 10);
            } else if (option == 4) {
                purchaseBenefit(player, itemId, 50);
            } else if (option == 5) {
                player.integerInput("How many would you like to purchase?", amt -> purchaseBenefit(player, itemId, amt));
            } else {
                new Item(itemId).examine(player);
            }

    }

}

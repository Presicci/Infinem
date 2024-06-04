package io.ruin.model.activities.combat.nightmarezone;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.ItemImbuing;

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
        ItemImbuing imbueable = ItemImbuing.getImbueable(id);
        if (imbueable == null) {
            player.sendMessage("This item currently cannot be upgraded, if this is a mistake, please contact staff.");
            return;
        }
        String name = new Item(id).getDef().name;
        int points = Config.NMZ_REWARD_POINTS_TOTAL.get(player);
        if (points < imbueable.nmzCost) {
            player.sendMessage("You must have at least " + NumberUtils.formatNumber(imbueable.nmzCost) + " NMZ Points to upgrade your " + name);
            return;
        }
        Config.NMZ_REWARD_POINTS_TOTAL.increment(player, -imbueable.nmzCost);
        player.getInventory().remove(id, 1);
        player.getInventory().add(new Item(imbueable.nmzImbue));
        player.sendMessage("You have upgraded your " + name + " for " + NumberUtils.formatNumber(imbueable.nmzCost) + " points.");
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
        int points = Config.NMZ_REWARD_POINTS_TOTAL.get(player);
        if (points < amount * resource.price)
            amount = points / resource.price;
        if (amount <= 0) {
            player.sendMessage("You must have at least " + NumberUtils.formatNumber(resource.price) + " NMZ Points to purchase " + name);
            return;
        }
        if ((!new Item(id).noteable() && !ItemDefinition.get(id).stackable) && amount > player.getInventory().getFreeSlots())
            amount = player.getInventory().getFreeSlots();
        Item item = new Item(id, amount).note();
        if (!player.getInventory().hasRoomFor(item)) {
            player.sendMessage("You currently have no space in your inventory.");
            return;
        }
        Config.NMZ_REWARD_POINTS_TOTAL.increment(player, -(resource.price * amount));
        player.getInventory().add(item);
        refresh(player);
    }

    public static void purchasePotion(Player player, int id, int amount) {
        NMZPotion benefit = NMZPotion.getBenefits(id);
        if (benefit == null) {
            player.sendMessage("This item currently cannot be purchased, if this is a mistake, please contact staff.");
            return;
        }
        int storedAmount = benefit.getConfig().get(player);
        String name = new Item(id).getDef().name;
        int points = Config.NMZ_REWARD_POINTS_TOTAL.get(player);
        if (points < amount * benefit.getPrice())
            amount = points / benefit.getPrice();
        if (amount <= 0) {
            player.sendMessage("You must have at least " + NumberUtils.formatNumber(benefit.getPrice()) + " NMZ Points to purchase " + name);
            return;
        }
        if (storedAmount >= 255) {
            player.sendMessage("You already have the maximum potion doses stored.");
            return;
        }
        if (amount + storedAmount > 255) {
            amount = 255 - storedAmount;
        }
        Config.NMZ_REWARD_POINTS_TOTAL.increment(player, -(benefit.getPrice() * amount));
        player.sendMessage("You now have " + benefit.getConfig().increment(player, amount) + " doses of " + name.split(Pattern.quote("("))[0].trim() + ".");
        refresh(player);
    }

    public static void open(Player player) {
        /**
         * sends the nightmare zone rewards interface
         */
        if (player.getBankPin().requiresVerification(NightmareZoneRewards::open))
            return;
        player.openInterface(InterfaceType.MAIN, Interface.NIGHTMARE_ZONE_REWARDS);
        refresh(player);
    }

    public static void refresh(Player player) {
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
            for (ItemImbuing imbueable : ItemImbuing.values()) {
                upgradables.add( new Item(imbueable.regularId, imbueable.nmzCost));
            }
            for (NMZResource resource : NMZResource.values()) {
                upgradables.add( new Item(resource.id,resource.price));
            }
            for (NMZPotion benefits : NMZPotion.values()) {
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
                purchasePotion(player, itemId, 1);
            } else if (option == 2) {
                purchasePotion(player, itemId, 5);
            } else if (option == 3) {
                purchasePotion(player, itemId, 10);
            } else if (option == 4) {
                purchasePotion(player, itemId, 50);
            } else if (option == 5) {
                player.integerInput("How many would you like to purchase?", amt -> purchasePotion(player, itemId, amt));
            } else {
                new Item(itemId).examine(player);
            }

    }

}

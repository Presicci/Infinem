package io.ruin.services;

import com.google.common.collect.Lists;
import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.XenPost;
import io.ruin.cache.ItemID;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Store {

    public static void claim(Player player, NPC npc) {
        if (npc != null) {
            player.dialogue(new NPCDialogue(npc, "Attempting to claim donation rewards, please wait...").hideContinue());
        } else {
            player.dialogue(new MessageDialogue("Attempting to claim donation rewards, please wait...").hideContinue());
        }
        CompletableFuture.runAsync(() -> {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("username", player.getName());
            String result = XenPost.post("claim_donations", map);
            Server.worker.execute(() -> {
                if (Character.isDigit(result.charAt(0)) || result.charAt(0) == '-') {
                    if (result.charAt(0) == '-') {
                        if (npc != null) {
                            player.dialogue(new NPCDialogue(npc, "You don't have any donation rewards to claim. Type ::store to visit the shop."));
                        } else {
                            player.dialogue(new MessageDialogue("You don't have any donation rewards to claim. Type ::store to visit the shop."));
                        }
                    } else {
                        try {
                            String[] elements = result.split(",");
                            if (elements.length < 2) {
                                if (npc != null) {
                                    player.dialogue(new NPCDialogue(npc, "Something went wrong, please try again later."));
                                } else {
                                    player.dialogue(new MessageDialogue("Something went wrong, please try again later."));
                                }
                            }
                            int paidAmt = Integer.parseInt(elements[0]) * 10;
                            int claimedItems = 0;
                            for (int index = 1 ; index < elements.length; index++) {
                                if (elements[index].length() < 2) continue;
                                String[] item = elements[index].split(":");
                                int storeId = Integer.parseInt(item[0]);
                                int amt = Integer.parseInt(item[1]);
                                paidAmt += handleReward(player, storeId, amt);
                                claimedItems += amt;
                            }
                            player.storeAmountSpent += paidAmt;
                            if (claimedItems > 0) {
                                if (npc != null) {
                                    player.dialogue(new NPCDialogue(npc, "You claimed " + claimedItems + " " + (claimedItems > 1 ? "rewards" : "reward") + ", and " + paidAmt + " credit.<br>You now have " + player.storeAmountSpent + " total donation credit."));
                                } else {
                                    player.dialogue(new MessageDialogue("You claimed " + claimedItems + " " + (claimedItems > 1 ? "rewards" : "reward") + ", and " + paidAmt + " credit.<br>You now have " + player.storeAmountSpent + " total donation credit."));
                                }
                            } else {
                                if (npc != null) {
                                    player.dialogue(new NPCDialogue(npc, "You claimed " + paidAmt + " credit.<br>You now have " + player.storeAmountSpent + " total donation credit."));
                                } else {
                                    player.dialogue(new MessageDialogue("You claimed " + paidAmt + " credit.<br>You now have " + player.storeAmountSpent + " total donation credit."));
                                }
                            }
                            PlayerGroup group = getGroup(player);
                            if(group != null && !player.isGroup(group)) {
                                player.join(group);
                                group.sync(player);
                                player.sendMessage("Congratulations, you've unlocked a new donator rank: <img=" + group.clientImgId + ">");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println(player.getName() + " had an error claiming their donation rewards, output: " + result + " | " + e.getMessage());
                        }
                    }
                } else {
                    if (npc != null) {
                        player.dialogue(new NPCDialogue(npc, "Something went wrong, please try again later."));
                    } else {
                        player.dialogue(new MessageDialogue("Something went wrong, please try again later."));
                    }
                }
            });
        });
    }

    public static int handleReward(Player player,  int storeId, int amt) {
        switch (storeId) {
            case 1:
                addItem(player, 26554, amt);
                return 50;
            case 2:
                addItem(player, 26554, amt);
                addItem(player, 26557, amt);
                return 250;
            case 3:
                addItem(player, 26554, amt);
                addItem(player, 26557, amt);
                addItem(player, 26560, amt);
                return 500;
            case 4:
                addItem(player, 32033, amt);
                return -(50 * amt);
            case 5:
                addItem(player, 32035, amt);
                return -(100 * amt);
            case 6:
                addItem(player, 32037, amt);
                return -(250 * amt);
            case 7:
                addItem(player, 26421, amt);
                break;
            case 8:
                addItem(player, 12954, amt);
                break;
            case 9:
                addItem(player, 6570, amt);
                break;
            case 10:    // Dwarf dwarf cannon
                break;
            case 11:    // Angler outfit
                addItem(player, Items.ANGLER_HAT, amt);
                addItem(player, Items.ANGLER_TOP, amt);
                addItem(player, Items.ANGLER_WADERS, amt);
                addItem(player, Items.ANGLER_BOOTS, amt);
                break;
            case 12:    // Carpenter outfit
                addItem(player, 24872, amt);
                addItem(player, 24874, amt);
                addItem(player, 24876, amt);
                addItem(player, 24878, amt);
                break;
            case 13:    // Graceful outfit
                addItem(player, 11850, amt);
                addItem(player, 11852, amt);
                addItem(player, 11854, amt);
                addItem(player, 11856, amt);
                addItem(player, 11858, amt);
                addItem(player, 11860, amt);
                break;
            case 14:    // Prospector outfit
                addItem(player, Items.PROSPECTOR_HELMET, amt);
                addItem(player, Items.PROSPECTOR_JACKET, amt);
                addItem(player, Items.PROSPECTOR_LEGS, amt);
                addItem(player, Items.PROSPECTOR_BOOTS, amt);
                break;
            case 15:    // Lumberjack outfit
                addItem(player, Items.LUMBERJACK_HAT, amt);
                addItem(player, Items.LUMBERJACK_TOP, amt);
                addItem(player, Items.LUMBERJACK_LEGS, amt);
                addItem(player, Items.LUMBERJACK_BOOTS, amt);
                break;
            case 16:    // Rogue outfit
                addItem(player, 5553, amt);
                addItem(player, 5554, amt);
                addItem(player, 5555, amt);
                addItem(player, 5556, amt);
                addItem(player, 5557, amt);
                break;
            case 17:    // Void ornament kit
                addItem(player, 26479, amt * 6);
                break;
            case 18:    // Zealot outfit
                addItem(player, 25438, 1);
                addItem(player, 25434, 1);
                addItem(player, 25436, 1);
                addItem(player, 25440, 1);
                break;
            case 19:    // Skilling outfit bundle
                // Angler outfit
                addItem(player, Items.ANGLER_HAT, amt);
                addItem(player, Items.ANGLER_TOP, amt);
                addItem(player, Items.ANGLER_WADERS, amt);
                addItem(player, Items.ANGLER_BOOTS, amt);
                // Carpenter
                addItem(player, 24872, amt);
                addItem(player, 24874, amt);
                addItem(player, 24876, amt);
                addItem(player, 24878, amt);
                // Graceful
                addItem(player, 11850, amt);
                addItem(player, 11852, amt);
                addItem(player, 11854, amt);
                addItem(player, 11856, amt);
                addItem(player, 11858, amt);
                addItem(player, 11860, amt);
                // Prospector
                addItem(player, Items.PROSPECTOR_HELMET, amt);
                addItem(player, Items.PROSPECTOR_JACKET, amt);
                addItem(player, Items.PROSPECTOR_LEGS, amt);
                addItem(player, Items.PROSPECTOR_BOOTS, amt);
                // Lumberjack
                addItem(player, Items.LUMBERJACK_HAT, amt);
                addItem(player, Items.LUMBERJACK_TOP, amt);
                addItem(player, Items.LUMBERJACK_LEGS, amt);
                addItem(player, Items.LUMBERJACK_BOOTS, amt);
                // Rogue
                addItem(player, 5553, amt);
                addItem(player, 5554, amt);
                addItem(player, 5555, amt);
                addItem(player, 5556, amt);
                addItem(player, 5557, amt);
                // Zealot
                addItem(player, 25438, 1);
                addItem(player, 25434, 1);
                addItem(player, 25436, 1);
                addItem(player, 25440, 1);
                // Pyromancer
                addItem(player, Items.PYROMANCER_HOOD, 1);
                addItem(player, Items.PYROMANCER_GARB, 1);
                addItem(player, Items.PYROMANCER_ROBE, 1);
                addItem(player, Items.PYROMANCER_BOOTS, 1);
                addItem(player, Items.WARM_GLOVES, 1);
                break;
            case 20:    // Pyromancer outfit
                addItem(player, Items.PYROMANCER_HOOD, 1);
                addItem(player, Items.PYROMANCER_GARB, 1);
                addItem(player, Items.PYROMANCER_ROBE, 1);
                addItem(player, Items.PYROMANCER_BOOTS, 1);
                addItem(player, Items.WARM_GLOVES, 1);
                break;
            case 21:    // Void
                addItem(player, Items.VOID_KNIGHT_GLOVES, 1);
                addItem(player, Items.VOID_KNIGHT_ROBE, 1);
                addItem(player, Items.VOID_KNIGHT_TOP, 1);
                addItem(player, Items.VOID_RANGER_HELM, 1);
                addItem(player, Items.VOID_MELEE_HELM, 1);
                addItem(player, Items.VOID_MAGE_HELM, 1);
                break;
            case 22:    // Elite void
                addItem(player, Items.VOID_KNIGHT_GLOVES, 1);
                addItem(player, Items.ELITE_VOID_ROBE, 1);
                addItem(player, Items.ELITE_VOID_TOP, 1);
                addItem(player, Items.VOID_RANGER_HELM, 1);
                addItem(player, Items.VOID_MELEE_HELM, 1);
                addItem(player, Items.VOID_MAGE_HELM, 1);
                break;
            case 23:    // Transmog imbuement scrolls
                addItem(player, 32040, 5);
                break;
            case 24:    // Combat relic reset tablet
                addItem(player, 32041, 1);
                break;
            case 25:    // Red partyhat
                addItem(player, 1038, 1);
                player.getTransmogCollection().addToCollection(1038);
                break;
            case 26:    // Yellow partyhat
                addItem(player, 1040, 1);
                player.getTransmogCollection().addToCollection(1040);
                break;
            case 27:    // Blue partyhat
                addItem(player, 1042, 1);
                player.getTransmogCollection().addToCollection(1042);
                break;
            case 28:    // Green partyhat
                addItem(player, 1044, 1);
                player.getTransmogCollection().addToCollection(1044);
                break;
            case 29:    // Purple partyhat
                addItem(player, 1046, 1);
                player.getTransmogCollection().addToCollection(1046);
                break;
            case 30:    // White partyhat
                addItem(player, 1048, 1);
                player.getTransmogCollection().addToCollection(1048);
                break;
            case 31:    // Santa hat
                addItem(player, 1050, 1);
                player.getTransmogCollection().addToCollection(1050);
                break;
        }
        return 0;
    }

    private static void addItem(Player player, int itemId, int amt) {
        if (player.getInventory().hasRoomFor(itemId, amt)) {
            player.getInventory().add(itemId, amt);
        } else {
            player.getBank().add(itemId, amt);
        }
    }

    public static PlayerGroup getGroup(Player player) {
        /*if(player.storeAmountSpent >= 10000)
            return  PlayerGroup.ZENYTE;
        if(player.storeAmountSpent >= 5000)
            return PlayerGroup.ONYX;
        if(player.storeAmountSpent >= 2500)
            return PlayerGroup.DRAGONSTONE;*/
        if(player.storeAmountSpent >= 1000)
            return PlayerGroup.DIAMOND;
        if(player.storeAmountSpent >= 500)
            return PlayerGroup.RUBY;
        if(player.storeAmountSpent >= 250)
            return PlayerGroup.EMERALD;
        if(player.storeAmountSpent >= 50)
            return PlayerGroup.SAPPHIRE;
        return null;
    }

    /**
     * Purchases
     */

    public static void claimPurchases(Player player, NPC npc, StoreConsumer consumer) {
        if(!World.isLive() && !player.isAdmin()) {
            player.dialogue(new NPCDialogue(npc, "Sorry, you can't claim store purchases on this world."));
            return;
        }
        player.lock();
        player.dialogue(new NPCDialogue(npc, "Attempting to claim store purchases, please wait...").hideContinue());
        Server.siteDb.execute(new DatabaseStatement() {

            List<Item> items = Lists.newArrayList();
            List<Integer> orders = Lists.newArrayList();
            int spent = 0;

            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT ol.price * ol.quantity price, ol.quantity quantity, ol.order_id id, ol.product_item_id FROM orders JOIN order_lines ol ON orders.id = ol.order_id  WHERE player_name = ? AND status = 'Approved';", ResultSet.TYPE_SCROLL_SENSITIVE);
                    statement.setString(1, player.getName());
                    resultSet = statement.executeQuery();
                    while (resultSet.next()) {

                        int itemId = resultSet.getInt("product_item_id");
                        int itemAmount = resultSet.getInt("quantity");
                        int orderId = resultSet.getInt("id");
                        int price = resultSet.getInt("price");
                        spent += price;

                        orders.add(orderId);
                        if (itemId == 1233) { // Starter package
                            items.add(new Item(290, 1));
                            items.add(new Item(6199, 1));
                            items.add(new Item(4151, 1));
                            items.add(new Item(537, 120));
                        } else if (itemId == 1234) { // Green Skin
                            player.putAttribute("GREEN_SKIN", 1);
                            player.sendMessage("Right click the Make-over mage to use your new green skin!");
                        } else if (itemId == 1235) { // Blue Skin
                            player.putAttribute("BLUE_SKIN", 1);
                            player.sendMessage("Right click the Make-over mage to use your new blue skin!");
                        } else if (itemId == 1236) { // Purple Skin
                            player.putAttribute("PURPLE_SKIN", 1);
                            player.sendMessage("Right click the Make-over mage to use your new purple skin!");
                        } else if (itemId == 1237) { // black Skin
                            player.putAttribute("BLACK_SKIN", 1);
                            player.sendMessage("Right click the Make-over mage to use your new black skin!");
                        } else if (itemId == 1238) { // white Skin
                            player.putAttribute("WHITE_SKIN", 1);
                            player.sendMessage("Right click the Make-over mage to use your new white skin!");
                        } else if (itemId == 1239) { // Custom title
                            player.hasCustomTitle = true;
                            player.sendMessage("You can now use custom titles! Speak to the Make-over Mage to use it");
                        } else if (itemId == 75000) { //void melee
                            items.add(new Item(11665, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75001) { //void Range
                            items.add(new Item(11664, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75002) { //void Mage
                            items.add(new Item(11663, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75003) { //Full Void
                            items.add(new Item(11663, itemAmount));
                            items.add(new Item(11664, itemAmount));
                            items.add(new Item(11665, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75004) {
                            items.add(new Item(20095, itemAmount));
                            items.add(new Item(20098, itemAmount));
                            items.add(new Item(20101, itemAmount));
                            items.add(new Item(20104, itemAmount));
                            items.add(new Item(20107, itemAmount));
                        } else if (itemId == 75005) {
                            items.add(new Item(11826, itemAmount));
                            items.add(new Item(11828, itemAmount));
                            items.add(new Item(20130, itemAmount));
                        } else if (itemId == 75006) {
                            items.add(new Item(ItemID.ARMADYL_CHAINSKIRT, itemAmount));
                            items.add(new Item(ItemID.ARMADYL_CHESTPLATE, itemAmount));
                            items.add(new Item(ItemID.ARMADYL_HELMET, itemAmount));
                        } else if (itemId == 13072) {
                            items.add(new Item(13073, itemAmount));
                            items.add(new Item(13072, itemAmount));
                            items.add(new Item(8842, itemAmount));
                            items.add(new Item(11663, itemAmount));
                            items.add(new Item(11664, itemAmount));
                            items.add(new Item(11665, itemAmount));
                        } else if (itemId == 75007) { //angler fish x250
                            items.add(new Item(13442, 250 * itemAmount));
                        } else if (itemId == 75008) { //karambwan x250
                            items.add(new Item(3145, 250 * itemAmount));
                        } else if (itemId == 75009) { //zulrah scales x5000
                            items.add(new Item(12934, 5000 * itemAmount));
                        } else if (itemId == 75010) { //dragon darts x150
                            items.add(new Item(11230, 150 * itemAmount));
                        } else if (itemId == 75011) { //dragon arrows x1000
                            items.add(new Item(11212, 1000 * itemAmount));
                        } else if (itemId == 75012) { //dragon javelin x150
                            items.add(new Item(19484, 150 * itemAmount));
                        } else {
                            if (itemId == 10834) {
                                player.diceHost = true;
                            }
                            items.add(new Item(itemId, itemAmount));
                        }
                    }
                    finish(false);
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                finish(true);
                Server.logError("", t);
            }

            private void finish(boolean error) {
                Server.worker.execute(() -> {
                    consumer.accept(items, spent, error);
                    player.unlock();
                });
            }
        });
    }

    @FunctionalInterface
    public interface StoreConsumer {
        void accept(List<Item> items, long spent, boolean error);
    }

}

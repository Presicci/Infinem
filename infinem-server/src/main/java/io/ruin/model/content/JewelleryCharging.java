package io.ruin.model.content;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;

import java.util.Arrays;
import java.util.List;

public enum JewelleryCharging {
    GLORY(11978, 1712, 1704, 1706, 1708, 1710, 1712, 11976),
    GLORY_T(11964, 10354, 10362, 10360, 10358, 10356, 10354, 11966),
    RING_OF_WEALTH(11980, -1, 2572, 11988, 11986, 11984, 11982),
    RING_OF_WEALTH_I(20786, -1, 12785, 20790, 20789, 20788, 20787),
    SKILLS_NECKLACE(11968, 11105, 11113, 11111, 11109, 11107, 11105, 11970),
    SKILLS_NECKLACE_N(-1, 11106, 11114, 11112, 11110, 11108),
    COMBAT_BRACELET(11972, 11118, 11126, 11124, 11122, 11120, 11118, 11974),
    COMBAT_BRACELET_N(-1, 11119, 11127, 11125, 11123, 11121);

    private final int runeCharged, charged;
    private final int[] chargeableJewellery;

    JewelleryCharging(int runeCharged, int charged, int... chargeableJewellery) {
        this.runeCharged = runeCharged;
        this.charged = charged;
        this.chargeableJewellery = chargeableJewellery;
    }

    private static void charge(Player player, JewelleryCharging chargeable, GameObject object) {
        boolean fountainOfRune = object.id == 26782;
        if (!fountainOfRune && chargeable.charged == -1) {
            player.sendMessage("This jewellery can only be recharged at the fountain of rune.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You hold the jewellery against the " + (object.id == 2638 ? "totem..." : "fountain..."));
            event.delay(1);
            player.animate(832);
            int[] chargeableJewellery = Arrays.copyOfRange(chargeable.chargeableJewellery, 0, 4);
            List<Item> inventoryJewellery = player.getInventory().collectItems(fountainOfRune ? chargeable.chargeableJewellery : chargeableJewellery);
            boolean eternal = false;
            for (Item item : inventoryJewellery) {
                if (chargeable == JewelleryCharging.GLORY && fountainOfRune && Random.rollDie(25000)) {
                    item.setId(Items.AMULET_OF_ETERNAL_GLORY);
                    player.getTaskManager().doLookupByUUID(903, 1); // Create an Amulet of Eternal Glory
                    player.getCollectionLog().collect(Items.AMULET_OF_ETERNAL_GLORY);
                    eternal = true;
                } else {
                    item.setId(fountainOfRune ? chargeable.runeCharged : chargeable.charged);
                }
            }
            if (eternal) {
                player.dialogue(
                        new ItemDialogue().one(chargeable.charged, "You feel a power emanating from the fountain as it recharges your jewellery."),
                        new ItemDialogue().one(Items.AMULET_OF_ETERNAL_GLORY, "The power of the fountain is transferred into an amulet of eternal glory. It will now have unlimited charges.")
                );
            } else {
                player.dialogue(new ItemDialogue().one(chargeable.charged, "You feel a power emanating from the " + (object.id == 2638 ? "totem" : "fountain") + " as it recharges your jewellery."));
            }
            player.unlock();
        });
    }

    private static void chargeNoted(Player player, JewelleryCharging chargeable) {
        if (chargeable.charged == -1) {
            player.sendMessage("This jewellery can only be recharged at the fountain of rune.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You hold the jewellery against the fountain...");
            event.delay(1);
            player.animate(832);
            List<Item> inventoryJewellery = player.getInventory().collectItems(chargeable.chargeableJewellery);
            int amount = 0;
            for (Item item : inventoryJewellery) {
                amount += item.getAmount();
                item.remove();
            }
            player.getInventory().add(chargeable.charged, amount);
            player.dialogue(new ItemDialogue().one(chargeable.charged - 1, "You feel a power emanating from the fountain as it recharges your jewellery."));
            player.unlock();
        });
    }

    private static void chargeScroll(Player player) {
        player.dialogue(new YesNoDialogue("Charge jewellery?", "This scroll will charge all dragonstone jewellery in your inventory.", Items.CHARGE_DRAGONSTONE_JEWELLERY_SCROLL, 1, () -> {
            int firstItem = -1;
            for (JewelleryCharging jewelleryCharging : values()) {
                if (jewelleryCharging == COMBAT_BRACELET_N || jewelleryCharging == SKILLS_NECKLACE_N || jewelleryCharging == GLORY || jewelleryCharging == GLORY_T)
                    continue;
                int[] chargeableJewellery = Arrays.copyOfRange(jewelleryCharging.chargeableJewellery, 0, 4);
                for (int itemId : chargeableJewellery) {
                    List<Item> inventoryJewellery = player.getInventory().collectItems(itemId);
                    if (inventoryJewellery != null && inventoryJewellery.size() > 0) {
                        if (firstItem == -1)
                            firstItem = jewelleryCharging.charged;
                        for (Item item : inventoryJewellery) {
                            item.setId(jewelleryCharging.charged);
                        }
                    }
                }
            }
            if (firstItem != -1) {
                player.getInventory().remove(Items.CHARGE_DRAGONSTONE_JEWELLERY_SCROLL, 1);
                player.dialogue(new ItemDialogue().one(firstItem, "Your scroll disintegrates as it recharges your jewellery."));
            } else {
                player.dialogue(new MessageDialogue("You don't have any jewellery that the scroll can recharge."));
            }
        }));
    }

    static {
        // Fountain of Rune
        for (JewelleryCharging jewelleryCharging : values()) {
            if (jewelleryCharging == COMBAT_BRACELET_N) continue;
            for (int itemId : jewelleryCharging.chargeableJewellery) {
                ItemObjectAction.register(itemId, 26782, (player, uncharged, obj) -> charge(player, jewelleryCharging, obj));
            }
        }
        // Glory
        for (int itemId : Arrays.copyOfRange(GLORY.chargeableJewellery, 0, 4)) {
            // Fountain of Heroes
            ItemObjectAction.register(itemId, 2939, (player, uncharged, obj) -> charge(player, GLORY, obj));
            // Fountain of Uhld
            ItemObjectAction.register(itemId, 31625, (player, uncharged, obj) -> charge(player, GLORY, obj));
        }
        for (int itemId : Arrays.copyOfRange(GLORY_T.chargeableJewellery, 0, 4)) {
            // Fountain of Heroes
            ItemObjectAction.register(itemId, 2939, (player, uncharged, obj) -> charge(player, GLORY_T, obj));
            // Fountain of Uhld
            ItemObjectAction.register(itemId, 31625, (player, uncharged, obj) -> charge(player, GLORY_T, obj));
        }
        // Skills necklace
        for (int itemId : Arrays.copyOfRange(SKILLS_NECKLACE.chargeableJewellery, 0, 4)) {
            // Totem pole
            ItemObjectAction.register(itemId, 2638, (player, uncharged, obj) -> charge(player, SKILLS_NECKLACE, obj));
            // Fountain of Uhld
            ItemObjectAction.register(itemId, 31625, (player, uncharged, obj) -> charge(player, SKILLS_NECKLACE, obj));
        }
        // Skills necklace (noted)
        for (int itemId : Arrays.copyOfRange(SKILLS_NECKLACE_N.chargeableJewellery, 0, 4)) {
            // Fountain of Uhld
            ItemObjectAction.register(itemId, 31625, (player, uncharged, obj) -> chargeNoted(player, SKILLS_NECKLACE_N));
        }
        // Combat bracelet
        for (int itemId : Arrays.copyOfRange(COMBAT_BRACELET.chargeableJewellery, 0, 4)) {
            // Totem pole
            ItemObjectAction.register(itemId, 2638, (player, uncharged, obj) -> charge(player, COMBAT_BRACELET, obj));
            // Fountain of Uhld
            ItemObjectAction.register(itemId, 31625, (player, uncharged, obj) -> charge(player, COMBAT_BRACELET, obj));
        }
        // Combat bracelet (noted)
        for (int itemId : Arrays.copyOfRange(COMBAT_BRACELET_N.chargeableJewellery, 0, 4)) {
            // Fountain of Uhld
            ItemObjectAction.register(itemId, 31625, (player, uncharged, obj) -> chargeNoted(player, COMBAT_BRACELET_N));
        }
        // Charge dragonstone jewellery scroll
        ItemAction.registerInventory(Items.CHARGE_DRAGONSTONE_JEWELLERY_SCROLL, "read", ((player, item) -> chargeScroll(player)));
    }
}

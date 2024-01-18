package io.ruin.model.entity.npc.actions.zanaris.puropuro;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.*;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/17/2024
 */
public class ElnockStorage {

    private static final Config NET_STORAGE = Config.varpbit(11767, true);
    private static final Config REPELLENT_STORAGE = Config.varpbit(11768, true);
    private static final Config JAR_STORAGE = Config.varpbit(11770, true);

    private static final Config WITHDRAW_AMOUNT = Config.varpbit(11795, false);

    protected static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.ELNOCK_STORAGE);
        player.openInterface(InterfaceType.INVENTORY, Interface.ELNOCK_STORAGE_INVENTORY);
    }

    /**
     * Depositing
     */

    private static void depositNet(Player player, int option, int itemId) {
        if (option == 10) {
            new Item(itemId, 1).examine(player);
            return;
        }
        if (!player.getInventory().contains(itemId) && !player.getEquipment().contains(itemId)) {
            player.dialogue(false, new MessageDialogue("You don't have a net to deposit."));
            return;
        }
        if (NET_STORAGE.get(player) != 0) {
            player.dialogue(false, new MessageDialogue("You already have a net stored."));
            return;
        }
        if (player.getEquipment().contains(itemId))
            player.getEquipment().remove(itemId, 1);
        else
            player.getInventory().remove(itemId, 1);
        NET_STORAGE.set(player, itemId == Items.MAGIC_BUTTERFLY_NET ? 2 : 1);
    }

    private static void depositRepellent(Player player, int option) {
        int itemId = Items.IMP_REPELLENT;
        if (option == 10) {
            new Item(itemId, 1).examine(player);
            return;
        }
        if (!player.getInventory().contains(itemId)) {
            player.dialogue(false, new MessageDialogue("You don't have any repellent to deposit."));
            return;
        }
        if (REPELLENT_STORAGE.get(player) != 0) {
            player.dialogue(false, new MessageDialogue("You already have a repellent stored."));
            return;
        }
        player.getInventory().remove(itemId, 1);
        REPELLENT_STORAGE.set(player, 1);
    }

    private static void depositJar(Player player, int option) {
        int itemId = Items.IMPLING_JAR;
        switch (option) {
            case 10:    // Examine
                new Item(itemId, 1).examine(player);
                break;
            case 1:     // 1
                depositJars(player,1);
                break;
            case 2:     // 5
                depositJars(player, 5);
                break;
            case 3:     // x
                player.integerInput("How many jars would you like to deposit?", i -> depositJars(player, i));
                break;
            case 4:     // All
                depositJars(player, 1000);
                break;
        }
    }

    private static void depositJars(Player player, int amount) {
        int itemId = Items.IMPLING_JAR;
        int currentlyStored = JAR_STORAGE.get(player);
        if (currentlyStored >= 1000) {
            player.dialogue(false, new MessageDialogue("You already have the maximum of 1,000 jars stored."));
            return;
        }
        Inventory inventory = player.getInventory();
        int invAmount = inventory.getAmount(itemId);
        int notedAmount = inventory.getAmount(ItemDef.get(itemId).notedId);
        if (invAmount + notedAmount <= 0) {
            player.dialogue(false, new MessageDialogue("You don't have any jars to deposit."));
            return;
        }
        if (amount > invAmount + notedAmount) {
            amount = invAmount + notedAmount;
        }
        if (amount + currentlyStored > 1000) {
            amount = 1000 - currentlyStored;
        }
        int amountRemoved = inventory.remove(ItemDef.get(itemId).notedId, amount);
        amount -= amountRemoved;
        if (amount > 0)
            amountRemoved += inventory.remove(itemId, amount);
        int newAmt = JAR_STORAGE.increment(player, amountRemoved);
    }

    private static void depositAll(Player player) {
        Inventory inventory = player.getInventory();
        Equipment equipment = player.getEquipment();
        if (inventory.containsAny(false, Items.MAGIC_BUTTERFLY_NET, Items.BUTTERFLY_NET) || equipment.containsAny(false, Items.MAGIC_BUTTERFLY_NET, Items.BUTTERFLY_NET)) {
            depositNet(player, 1, (player.getInventory().contains(Items.MAGIC_BUTTERFLY_NET) || player.getEquipment().contains(Items.MAGIC_BUTTERFLY_NET))
                    ? Items.MAGIC_BUTTERFLY_NET : Items.BUTTERFLY_NET);
        }
        if (inventory.contains(Items.IMP_REPELLENT)) {
            depositRepellent(player, 1);
        }
        if (inventory.containsAny(true, Items.IMPLING_JAR)) {
            depositJars(player, 1000);
        }
    }

    /**
     * Withdrawing
     */

    private static void withdrawNet(Player player, int option) {
        int net = NET_STORAGE.get(player);
        if (net == 0) {
            player.dialogue(false, new MessageDialogue("You do not have a net stored."));
            return;
        }
        int netId = net == 1 ? Items.BUTTERFLY_NET : Items.MAGIC_BUTTERFLY_NET;
        if (option == 10) {
            new Item(netId, 1).examine(player);
            return;
        }
        if (player.getEquipment().get(Equipment.SLOT_WEAPON) == null) {
            player.getEquipment().set(Equipment.SLOT_WEAPON, new Item(netId));
            NET_STORAGE.set(player, 0);
            return;
        }
        if (!player.getInventory().hasFreeSlots(1)) {
            player.dialogue(false, new MessageDialogue("You need a free inventory slot to withdraw that."));
            return;
        }
        player.getInventory().add(netId);
        NET_STORAGE.set(player, 0);
    }

    private static void withdrawRepellent(Player player, int option) {
        int repellent = REPELLENT_STORAGE.get(player);
        if (option == 10) {
            new Item(Items.IMP_REPELLENT, 1).examine(player);
            return;
        }
        if (repellent == 0) {
            player.dialogue(false, new MessageDialogue("You do not have a repellent stored."));
            return;
        }
        if (!player.getInventory().hasFreeSlots(1)) {
            player.dialogue(false, new MessageDialogue("You need a free inventory slot to withdraw that."));
            return;
        }
        player.getInventory().add(Items.IMP_REPELLENT);
        REPELLENT_STORAGE.set(player, 0);
    }

    private static void withdrawJar(Player player, int option) {
        if (option == 10) {
            new Item(Items.IMPLING_JAR, 1).examine(player);
            return;
        }
        int jars = JAR_STORAGE.get(player);
        if (jars == 0) {
            player.dialogue(false, new MessageDialogue("You do not have any jars stored."));
            return;
        }
        if (option == 1) {
            int amountVarp = WITHDRAW_AMOUNT.get(player);
            int amount = amountVarp == 0 ? 1 : amountVarp == 1 ? 5 : amountVarp == 2 ? 1000 : 1;
            if (amountVarp == 3) {
                player.integerInput("How many jars would you like to withdraw?", i -> withdrawJars(player, i));
            } else {
                withdrawJars(player, amount);
            }
        } else if (option == 9) {
            withdrawJarsAsNote(player);
        } else {
            int amount = option == 2 ? 5 : 28;
            if (option == 3) {
                player.integerInput("How many jars would you like to withdraw?", i -> withdrawJars(player, i));
            } else {
                withdrawJars(player, amount);
            }
        }
    }

    private static void withdrawJarsAsNote(Player player) {
        int jars = JAR_STORAGE.get(player);
        if (!player.getInventory().hasRoomFor(Items.IMPLING_JAR_NOTE)) {
            player.dialogue(false, new MessageDialogue("Your inventory is too full to withdraw banknotes."));
            return;
        }
        player.getInventory().add(Items.IMPLING_JAR_NOTE, jars);
        JAR_STORAGE.set(player, 0);
    }

    private static void withdrawJars(Player player, int amount) {
        int storedAmt = JAR_STORAGE.get(player);
        if (amount > storedAmt) {
            amount = storedAmt;
        }
        int freeSpace = player.getInventory().getFreeSlots();
        if (amount > freeSpace) {
            amount = freeSpace;
        }
        if (amount == 0) {
            player.sendMessage("Your inventory is too full to withdraw any more jars.");
            return;
        }
        player.getInventory().add(Items.IMPLING_JAR, amount);
        JAR_STORAGE.decrement(player, amount);
    }

    protected static void withdrawAll(Player player) {
        int net = NET_STORAGE.get(player);
        int netId = net == 1 ? Items.BUTTERFLY_NET : Items.MAGIC_BUTTERFLY_NET;
        if (net != 0) {
            if (player.getEquipment().get(Equipment.SLOT_WEAPON) == null) {
                player.getEquipment().set(Equipment.SLOT_WEAPON, new Item(netId));
                NET_STORAGE.set(player, 0);
            } else if (player.getInventory().hasFreeSlots(1)) {
                player.getInventory().add(netId);
                NET_STORAGE.set(player, 0);
            }
        }
        int repellent = REPELLENT_STORAGE.get(player);
        if (repellent != 0 && player.getInventory().hasFreeSlots(1)) {
            player.getInventory().add(Items.IMP_REPELLENT);
            REPELLENT_STORAGE.set(player, 0);
        }
        int jars = JAR_STORAGE.get(player);
        if (jars > 0) {
            int freeSpace = player.getInventory().getFreeSlots();
            if (freeSpace == 0) return;
            int amt = Math.min(jars, freeSpace);
            player.getInventory().add(Items.IMPLING_JAR, amt);
            JAR_STORAGE.decrement(player, amt);
        }
    }

    static {
        InterfaceHandler.register(Interface.ELNOCK_STORAGE, h -> {
            h.actions[3] = (OptionAction) ElnockStorage::withdrawNet;
            h.actions[4] = (OptionAction) ElnockStorage::withdrawRepellent;
            h.actions[5] = (OptionAction) ElnockStorage::withdrawJar;
            h.actions[6] = (SimpleAction) ElnockStorage::withdrawAll;
        });
        InterfaceHandler.register(Interface.ELNOCK_STORAGE_INVENTORY, h -> {
            h.actions[1] = (OptionAction) (player, option) -> depositNet(player, option,
                    (player.getInventory().contains(Items.MAGIC_BUTTERFLY_NET) || player.getEquipment().contains(Items.MAGIC_BUTTERFLY_NET))
                            ? Items.MAGIC_BUTTERFLY_NET : Items.BUTTERFLY_NET);
            h.actions[2] = (OptionAction) ElnockStorage::depositRepellent;
            h.actions[3] = (OptionAction) ElnockStorage::depositJar;
            h.actions[4] = (SimpleAction) ElnockStorage::depositAll;
        });
    }
}

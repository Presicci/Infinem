package io.ruin.model.entity.npc.actions;

import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.content.tasksystem.tasks.areas.AreaTaskTier;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/8/2022
 */
public class Ava {

    private static void sendAvasDevices(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.AVAS_DEVICES);
        player.getPacketSender().sendString(Interface.AVAS_DEVICES, 6,  "999 coins");
        player.getPacketSender().sendString(Interface.AVAS_DEVICES, 11,  "5000 coins");
        player.getPacketSender().sendString(Interface.AVAS_DEVICES, 16,  "25000 coins and Vorkath's head");
    }

    private static void purchaseDevice(Player player, int amount, int index) {
        if (index >= 1 && !TaskArea.MISTHALIN.checkTierUnlock(player, AreaTaskTier.MEDIUM, "purchase this Ava's device.")) return;
        if (!TaskArea.MISTHALIN.checkTierUnlock(player, AreaTaskTier.EASY, "purchase an Ava's attractor.")) return;
        int coinsAmt = index == 0 ? 999 : index == 1 ? 5000 : 25000;
        if (player.getInventory().getFreeSlots() < amount)
            amount = player.getInventory().getFreeSlots();
        if (amount <= 0) {
            player.sendMessage("You don't have enough inventory space to buy this.");
            return;
        }
        int cost = amount*coinsAmt;
        if (!player.getInventory().contains(995, cost)) {
            if (player.getInventory().contains(995, coinsAmt)) {
                amount = Math.min(amount, player.getInventory().getAmount(995) / coinsAmt);
                cost = amount*coinsAmt;
            } else {
                player.sendMessage("You don't have enough coins to buy this.");
                return;
            }
        }
        if (index == 2) {
            if (player.getInventory().contains(21907)) {
                amount = Math.min(amount, player.getInventory().getAmount(21907));
                cost = amount*coinsAmt;
            } else {
                player.sendMessage("You need Vorkath's head to make this device.");
                return;
            }
            player.getInventory().remove(21907, amount);
        }
        player.getInventory().remove(995, cost);
        player.getInventory().add(index == 0 ? Items.AVAS_ATTRACTOR : index == 1 ? Items.AVAS_ACCUMULATOR : Items.AVAS_ASSEMBLER, amount);
    }

    private static void purchase(Player player, int option, int index) {
        switch (option) {
            case 1:
                purchaseDevice(player, 1, index);
                break;
            case 2:
                purchaseDevice(player, 5, index);
                break;
            case 3:
                player.integerInput("How many would you like to buy?", amt -> purchaseDevice(player, amt, index));
                break;
            case 4:
                purchaseDevice(player, 28, index);
                break;
        }
    }

    static {
        InterfaceHandler.register(Interface.AVAS_DEVICES, h -> {
            h.actions[2] = (OptionAction) (p, o) -> purchase(p, o, 0);
            h.actions[7] = (OptionAction) (p, o) -> purchase(p, o, 1);
            h.actions[12] = (OptionAction) (p, o) -> purchase(p, o, 2);
        });
        NPCAction.register(4407, "devices", ((player, npc) -> sendAvasDevices(player)));
    }
}

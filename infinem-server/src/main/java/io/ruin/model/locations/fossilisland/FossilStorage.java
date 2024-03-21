package io.ruin.model.locations.fossilisland;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/21/2024
 */
public class FossilStorage {

    private static final Map<Integer, Config> FOSSIL_CONFIGS = new HashMap<Integer, Config>() {{
        put(21570, Config.varpbit(5832, true));
        put(21572, Config.varpbit(5833, true));
        put(21574, Config.varpbit(5834, true));
        put(21576, Config.varpbit(5835, true));
        put(21578, Config.varpbit(5836, true));
        put(21580, Config.varpbit(5837, true));
        put(21582, Config.varpbit(5838, true));
        put(21584, Config.varpbit(5839, true));
        put(21586, Config.varpbit(5840, true));
        put(21588, Config.varpbit(5841, true));
        put(21600, Config.varpbit(5842, true));
        put(21602, Config.varpbit(5843, true));
        put(21604, Config.varpbit(5844, true));
        put(21606, Config.varpbit(5845, true));
        put(21608, Config.varpbit(5846, true));
        put(21590, Config.varpbit(5847, true));
        put(21592, Config.varpbit(5848, true));
        put(21594, Config.varpbit(5849, true));
        put(21596, Config.varpbit(5850, true));
        put(21598, Config.varpbit(5851, true));
        put(21610, Config.varpbit(5852, true));
        put(21612, Config.varpbit(5853, true));
        put(21614, Config.varpbit(5854, true));
        put(21616, Config.varpbit(5855, true));
        put(21618, Config.varpbit(5856, true));
        put(21620, Config.varpbit(5952, true));
        put(21562, Config.varpbit(5828, true));
        put(21564, Config.varpbit(5829, true));
        put(21566, Config.varpbit(5830, true));
        put(21568, Config.varpbit(5831, true));
    }};

    private static void open(Player player) {
        if (player.getBankPin().requiresVerification(p -> open(player)))
            return;
        player.openInterface(InterfaceType.MAIN, 605);
        player.getPacketSender().sendAccessMask(605, 11, 0, 29,
                AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5, AccessMasks.ClickOp10);
    }

    private static void depositAll(Player player) {
        Set<Integer> fossils = FOSSIL_CONFIGS.keySet();
        for (Item item : player.getInventory().getItems()) {
            if (item == null || !fossils.contains(item.getId())) continue;
            Config config = FOSSIL_CONFIGS.get(item.getId());
            int storedAmount = config.get(player);
            int depositAmount = item.getAmount();
            if (item.getAmount() + storedAmount > 1023) {
                if (depositAmount == 1) continue;
                depositAmount = 1023 - storedAmount;
            }
            if (depositAmount <= 0) continue;
            item.remove(depositAmount);
            config.increment(player, depositAmount);
        }
    }

    private static void withdrawAll(Player player) {
        for (Integer itemId : FOSSIL_CONFIGS.keySet()) {
            Config config = FOSSIL_CONFIGS.get(itemId);
            int withdrawAmt = config.get(player);
            if (withdrawAmt <= 0) continue;
            int freeSlots = player.getInventory().getFreeSlots();
            if (freeSlots <= 0) return;
            if (freeSlots < withdrawAmt) withdrawAmt = freeSlots;
            config.increment(player, -withdrawAmt);
            player.getInventory().add(itemId, withdrawAmt);
        }
    }

    private static void withdraw(Player player, int itemId, int amount) {
        Config config = FOSSIL_CONFIGS.get(itemId);
        int storedAmount = config.get(player);
        if (storedAmount == 0) {
            player.sendMessage("You don't have any of that fossil stored.");
            return;
        }
        if (storedAmount < amount) amount = storedAmount;
        int freeSlots = player.getInventory().getFreeSlots();
        if (freeSlots == 0) {
            player.sendMessage("You don't have any free inventory space.");
            return;
        }
        if (freeSlots < amount) amount = freeSlots;
        if (amount <= 0) return;
        config.increment(player, -amount);
        player.getInventory().add(itemId, amount);
        player.sendMessage("You withdraw " + (amount > 1 ? amount : "a") + " fossils from storage.");
    }

    private static void handleItemClick(Player player, int option, int slot, int itemId) {
        switch (option) {
            case 1:
                withdraw(player, itemId, 1);
                break;
            case 2:
                withdraw(player, itemId, 5);
                break;
            case 3:
                withdraw(player, itemId, 10);
                break;
            case 4:
                withdraw(player, itemId, 1023);
                break;
            case 5:
                player.integerInput("Enter amount:", amount -> withdraw(player, itemId, amount));
                break;
            case 10:
                new Item(itemId).examine(player);
                break;
        }
    }

    static {
        ObjectAction.register(30987, "open", (player, obj) -> open(player));
        InterfaceHandler.register(605, h -> {
            h.actions[11] = (DefaultAction) FossilStorage::handleItemClick;
            h.actions[4] = (SimpleAction) FossilStorage::withdrawAll;
            h.actions[6] = (SimpleAction) FossilStorage::depositAll;
        });
    }
}

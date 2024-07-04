package io.ruin.model.skills.construction.mahoganyhomes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
public class MahoganyHomesShop {

    @AllArgsConstructor
    private enum MHShopItem {
        HELMET(24872, 400),
        SHIRT(24874, 800),
        TROUSERS(24876, 600),
        BOOTS(24878, 200),
        PLANK_SACK(24882, 350),
        SUPPLY_CRATE(24884, 25),
        SAW(24880, 500),
        HOSIDIUS_BLUEPRINTS(24885, 2000);

        private final int itemId, cost;

        private static List<Item> getCostList() {
            List<Item> list = new ArrayList<>();
            for (MHShopItem item : values()) {
                list.add(new Item(0, item.cost));
            }
            return list;
        }
    }

    private static final String SELECTED_ITEM_KEY = "MHS_SELECTED";

    public static void openShop(Player player) {
        player.removeTemporaryAttribute(SELECTED_ITEM_KEY);
        player.openInterface(InterfaceType.MAIN, 673);
        player.getPacketSender().sendAccessMask(673, 6, 0, 7, AccessMasks.ClickOp1);
        player.getPacketSender().sendVarp(261, MahoganyHomes.getPoints(player));
        player.getPacketSender().sendItems(247, MHShopItem.getCostList());
    }

    private static void purchase(Player player, int slot) {
        int points = MahoganyHomes.getPoints(player);
        MHShopItem item = MHShopItem.values()[slot];
        if (points < item.cost) {
            player.sendMessage("You don't have enough carpenter points to purchase that.");
            return;
        }
        if (!player.getInventory().hasRoomFor(item.itemId)) {
            player.sendMessage("You don't have enough inventory space for that.");
            return;
        }
        int newPoints = player.incrementNumericAttribute(MahoganyHomes.POINTS_KEY, -item.cost);
        player.getPacketSender().sendVarp(261, newPoints);
        player.getInventory().add(item.itemId);
        player.getCollectionLog().collect(item.itemId);
    }

    static {
        InterfaceHandler.register(673, h -> {
            h.actions[6] = (SlotAction) MahoganyHomesShop::purchase;
        });
    }
}

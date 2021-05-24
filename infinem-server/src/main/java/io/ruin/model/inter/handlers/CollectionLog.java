package io.ruin.model.inter.handlers;

import com.google.protobuf.Struct;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/23/2021
 */
public class CollectionLog {

    //struct, string - enum -

    public static void openLog(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.COLLECTION_LOG);
    }

    public static void setTab(Player player, int option) {
        Config.COLLECTION_LOG_TAB.set(player, option);
        player.getPacketSender().sendClientScript(2389,
                "i",
                option
        );
        // Scroll 36, List 35
        // 620 is item container
        // 11 list tooltip, 12 list, 13 scroll
        //Config.COLLECTION_LOG_KC.set(player, 5);  SET TO THE PLAYERS KC
        showList(player);
    }

    public static void showList(Player player) {
        int listId = 471 + Config.COLLECTION_LOG_TAB.get(player);
        player.getPacketSender().sendClientScript(2730,
                "iiiiii",
                Interface.COLLECTION_LOG << 16 | 10,
                Interface.COLLECTION_LOG << 16 | 11,
                Interface.COLLECTION_LOG << 16 | 12,
                Interface.COLLECTION_LOG << 16 | 13,
                listId,    // List id
                0       // Element of the list to show items for
        );
        player.getPacketSender().sendAccessMask(Interface.COLLECTION_LOG, 11, 0, 35, 2);
    }

    public static void showItems(Player player, int slotId) {
        int listId = 471 + Config.COLLECTION_LOG_TAB.get(player);
        //player.getPacketSender().sendItems(Interface.COLLECTION_LOG, 35, 620, new Item(4151, 160));
        player.getPacketSender().sendClientScript(2730,
                "iiiiii",
                Interface.COLLECTION_LOG << 16 | 10,
                Interface.COLLECTION_LOG << 16 | 11,
                Interface.COLLECTION_LOG << 16 | 12,
                Interface.COLLECTION_LOG << 16 | 13,
                listId,    // List id
                slotId       // Element of the list to show items for
        );
        player.getPacketSender().sendAccessMask(Interface.COLLECTION_LOG, 11, 0, 35, 2);
    }

    static {
        InterfaceHandler.register(Interface.COLLECTION_LOG, h -> {
            h.actions[4] = (SimpleAction) (player) -> CollectionLog.setTab(player, 0);
            h.actions[5] = (SimpleAction) (player) -> CollectionLog.setTab(player, 1);
            h.actions[6] = (SimpleAction) (player) -> CollectionLog.setTab(player, 2);
            h.actions[7] = (SimpleAction) (player) -> CollectionLog.setTab(player, 3);
            h.actions[8] = (SimpleAction) (player) -> CollectionLog.setTab(player, 4);
            h.actions[11] = (SlotAction) (player, slot) -> CollectionLog.showItems(player, slot);
        });
    }

}

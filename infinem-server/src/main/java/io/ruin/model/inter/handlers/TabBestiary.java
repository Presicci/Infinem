package io.ruin.model.inter.handlers;

import io.ruin.model.content.bestiary.BestiaryDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.journal.dropviewer.DropViewer;
import io.ruin.model.inter.utils.Config;

public class TabBestiary {

    public static void sendTab(Player player) {
        populateList(player);
        player.getPacketSender().sendAccessMask(1009, 14, 0, 12, 2);
        player.getPacketSender().sendAccessMask(1009, 2, 0, BestiaryDef.ENTRIES.size(), 2);
    }

    private static void populateList(Player player) {
        player.getPacketSender().sendClientScript(10067, "is", BestiaryDef.ENTRIES.size(), player.getBestiary().generateInterfaceString());
    }

    public static void attemptRefresh(Player player) {
        if (player.isVisibleInterface(1009)) {
            populateList(player);
        }
    }

    static {
        InterfaceHandler.register(1009, interfaceHandler -> {
            interfaceHandler.actions[6] = (SimpleAction) DropViewer::open;
            interfaceHandler.actions[8] = (SimpleAction) DropViewer::open;
            interfaceHandler.actions[14] = (SlotAction) (player, slot) -> {
                Config.BESTIARY_SORT.set(player, slot-1);
                sendTab(player);
            };
        });
    }
}

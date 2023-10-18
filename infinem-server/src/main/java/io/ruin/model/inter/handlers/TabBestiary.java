package io.ruin.model.inter.handlers;

import io.ruin.model.content.bestiary.BestiaryDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.journal.dropviewer.DropViewer;
import io.ruin.model.inter.utils.Config;

public class TabBestiary {

    public static void sendBestiaryTab(Player player) {
        player.getPacketSender().sendClientScript(10067, "is", BestiaryDef.ENTRIES.size(), player.getBestiary().generateBestiaryInterfaceString());
        player.getPacketSender().sendAccessMask(1009, 14, 0, 12, 2);
    }

    static {
        InterfaceHandler.register(1009, interfaceHandler -> {
            interfaceHandler.actions[6] = (SimpleAction) DropViewer::open;
            interfaceHandler.actions[14] = (SlotAction) (player, slot) -> {
                Config.BESTIARY_SORT.set(player, slot-1);
                sendBestiaryTab(player);
            };
        });
    }
}

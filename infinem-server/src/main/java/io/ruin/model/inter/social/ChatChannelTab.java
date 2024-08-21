package io.ruin.model.inter.social;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.network.central.CentralSender;

public class ChatChannelTab {

    private static void openSettings(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.CLAN_CHAT_SETTINGS);
        CentralSender.sendClanSettingUpdate(player.getUserId());
        player.getPacketSender().sendAccessMask(94, 28, 0, 100, AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5, AccessMasks.ClickOp6, AccessMasks.ClickOp7);
    }

    static {
        InterfaceHandler.register(Interface.CLAN_CHAT, h -> h.actions[20] = (SimpleAction) ChatChannelTab::openSettings);
        InterfaceHandler.register(Interface.CLAN_CHAT_SETTINGS, h -> {
            h.actions[10] = (OptionAction) (player, option) -> {
                if(option == 1) {
                    player.nameInput("Enter chat prefix:", name -> {
                        name = name.replaceAll("[^a-zA-Z0-9\\s]", "");
                        name = name.substring(0, Math.min(name.length(), 12));
                        if(name.isEmpty()) {
                            player.retryStringInput("Invalid chat prefix, try again:");
                            return;
                        }
                        player.getPacketSender().sendString(Interface.CLAN_CHAT_SETTINGS, 10, name);
                        CentralSender.sendClanName(player.getUserId(), name);
                    });
                    return;
                }
                player.getPacketSender().sendString(Interface.CLAN_CHAT_SETTINGS, 10, "Chat disabled");
                CentralSender.sendClanName(player.getUserId(), "");
            };
            h.actions[13] = (OptionAction) (player, option) -> CentralSender.sendClanSetting(player.getUserId(), 0, (option - 2));
            h.actions[16] = (OptionAction) (player, option) -> CentralSender.sendClanSetting(player.getUserId(), 1, (option - 2));
            h.actions[19] = (OptionAction) (player, option) -> CentralSender.sendClanSetting(player.getUserId(), 2, (option - 2));
            h.actions[28] = (OptionAction) (player, option) -> player.sendMessage("Rank changing is not currently supported.");
        });
    }

}
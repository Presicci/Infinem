package io.ruin.model.inter.handlers.settings;


import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.handlers.settings.impl.ClickSetting;
import io.ruin.model.inter.handlers.settings.impl.DropdownSetting;
import io.ruin.utility.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.WidgetID;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.network.incoming.handlers.DisplayHandler;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Settings {

    private static final int INTERFACE_ID = WidgetID.SETTINGS_GROUP_ID;

    public static void open(Player player) {
        if (player.isVisibleInterface(INTERFACE_ID)) return;
        resetSettings(player);
        player.openInterface(InterfaceType.MAIN_STRETCHED, INTERFACE_ID);

        player.getPacketSender().sendAccessMask(INTERFACE_ID, 23, 0, 8, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(INTERFACE_ID, 19, 0, 1000, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(INTERFACE_ID, 25, -1, -1, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(INTERFACE_ID, 21, 0, 62, AccessMasks.ClickOp1);
        for (int i = 2; i <= 41; i += 3) {
            player.getPacketSender().sendAccessMask(INTERFACE_ID, 28, i, i, AccessMasks.ClickOp1);
        }
    }

    private static void resetSettings(Player player) {
        player.putTemporaryAttribute("SETTING_CHILD", -1);
        resetChatInput(player);
    }

    private static void resetChatInput(Player player) {
        player.getPacketSender().sendClientScript(2158);
        Config.SettingSearch.set(player, 0);
        Config.SettingSearch1.set(player, 0);
    }

    static {
        InterfaceHandler.register(INTERFACE_ID, h -> {
            h.closedAction = ((player, integer) -> resetSettings(player));
            h.simpleAction(4, Player::closeInterfaces);
            h.simpleAction(25, player -> player.putTemporaryAttribute("SETTING_CHILD", -1));
            h.simpleAction(10, player -> {
                Config.SettingSearch.set(player, 1);
                Config.SettingSearch1.set(player, 1);
            });
            h.actions[19] = (DefaultAction) (player, option, slot, item) -> {
                player.putTemporaryAttribute("SETTING_CHILD", slot);
                if (Config.SettingSearch.get(player) == 0) {
                    int menu = player.getTemporaryAttributeIntOrZero("SETTING_MENU");
                    for (ClickSetting setting : ClickSetting.values()) {
                        if (menu == setting.getMenuIndex() && slot == setting.getChildIndex()) {
                            if (setting.getConsumer() != null)
                                setting.getConsumer().accept(player);
                            else
                                player.sendMessage("This setting is not currently implemented.");
                            return;
                        }
                    }
                } else {
                    for (ClickSetting setting : ClickSetting.values()) {
                        if (slot == setting.getSearchIndex()) {
                            if (setting.getConsumer() != null)
                                setting.getConsumer().accept(player);
                            else
                                player.sendMessage("This setting is not currently implemented.");
                            return;
                        }
                    }
                }
            };
            /*h.actions[21] = (DefaultAction) (player, option, slot, item) -> {
                if (Config.SettingSearch.get(player) == 0) {
                    int menu = player.getTemporaryAttributeIntOrZero("SETTING_MENU");
                    for (SettingSlider setting : SettingSlider.values()) {
                        if (menu == setting.menuIndex && slot >= setting.childIndex && slot <= setting.childIndex + 20) {
                            if (setting.consumer != null)
                                setting.consumer.accept(player, slot - setting.childIndex);
                            return;
                        }
                    }
                } else {
                    for (SettingSlider setting : SettingSlider.values()) {
                        if (slot >= setting.searchIndex && slot <= setting.searchIndex + 20) {
                            if (setting.consumer != null)
                                setting.consumer.accept(player, slot - setting.searchIndex);
                            return;
                        }
                    }
                }
            };*/
            h.actions[23] = (DefaultAction) (player, option, slot, item) -> {
                Config.SettingSearch.set(player, 0);
                Config.SettingSearch1.set(player, 0);
                player.putTemporaryAttribute("SETTING_MENU", slot);
                Config.varpbit(9656, false).set(player, slot);
            };
            h.actions[28] = (DefaultAction) (player, option, slot, item) -> {
                int dropSlot = (slot - 2) / 3;
                int menu = player.getTemporaryAttributeIntOrZero("SETTING_MENU");
                int child = player.getTemporaryAttributeIntOrZero("SETTING_CHILD");
                if (Config.SettingSearch.get(player) == 0) {
                    for (DropdownSetting setting : DropdownSetting.values()) {
                        if (menu == setting.getMenuIndex() && child == setting.getChildIndex()) {
                            if (setting.getConsumer() != null)
                                setting.getConsumer().accept(player, dropSlot);
                            else
                                player.sendMessage("This setting is not currently implemented.");
                            return;
                        }
                    }
                } else {
                    for (DropdownSetting setting : DropdownSetting.values()) {
                        if (child == setting.getSearchIndex()) {
                            if (setting.getConsumer() != null)
                                setting.getConsumer().accept(player, dropSlot);
                            else
                                player.sendMessage("This setting is not currently implemented.");
                            return;
                        }
                    }
                }
            };
        });
        LoginListener.register(player -> {
            player.sendVarps();
            player.getPacketSender().sendClientScript(3145, "i", -1);
        });
    }
}

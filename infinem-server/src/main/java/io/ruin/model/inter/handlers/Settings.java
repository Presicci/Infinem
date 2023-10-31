package io.ruin.model.inter.handlers;


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

public class Settings {

    private enum Setting {
        // Activities
        HITSPLAT_TINTING(0, 23, 23),
        BOSS_HEALTH_OVERLAY(0, 24, 24),
        // Audio
        MUSIC_UNLOCK_MESSAGE(1, 5, 34),
        // Chat
        PROFANITY_FILTER(2, 1, 36, Config.PROFANITY_FILTER::toggle),
        CHAT_EFFECTS(2, 3, 38, Config.CHAT_EFFECTS::toggle),
        SPLIT_PRIVATE_CHAT(2, 4, 39, (player -> {
            Config.SPLIT_PRIVATE_CHAT.toggle(player);
            player.getPacketSender().sendClientScript(83);
        })),
        HIDE_PRIVATE_CHAT(2, 5, 40, Config.HIDE_PRIVATE_CHAT::toggle),
        PRECISE_TIMING(2, 7, 42),
        SEPERATING_HOURS(2, 8, 43),
        COLLECTION_LOG_CHAT_NOTIFICATION(2, 9, 44, (player) -> Config.COLLECTION_LOG_SETTINGS.toggleBit(player, 1)),
        LOOT_DROP_CHAT_NOTIFICATION(2, 10, 45),
        LOOT_DROP_CHAT_MIN_VALUE(2, 11, 46),
        LOOT_DROP_CHAT_UNTRADEABLE(2, 12, 47),
        FILTER_BOSS_KILL_COUNT(2, 13, 48),
        COMBAT_ACHIEVEMENT_FAILURE(2, 14, 49),
        COMBAT_ACHIEVEMENT_REPEAT_FAILURE(2, 15, 50),
        COMBAT_ACHIEVEMENT_REPEAT_COMPLETE(2, 16, 51),
        OPAQUE_PUBLIC_CHAT(2, 19, 54),
        OPAQUE_PRIVATE_CHAT(2, 20, 55),
        OPAQUE_AUTO_CHAT(2, 21, 56),
        OPAQUE_BROADCAST_CHAT(2, 22, 57),
        OPAQUE_FRIEND_CHAT(2, 23, 58),
        OPAQUE_CLAN_CHAT(2, 24, 59),
        OPAQUE_GUEST_CHAT(2, 25, 60),
        OPAQUE_TRADE_CHAT(2, 26, 61),
        OPAQUE_CHALLENGE_CHAT(2, 27, 62),
        OPAQUE_RESET(2, 28, 63),
        TRANSPARENT_PUBLIC_CHAT(2, 31, 66),
        TRANSPARENT_PRIVATE_CHAT(2, 32, 67),
        TRANSPARENT_AUTO_CHAT(2, 33, 68),
        TRANSPARENT_BROADCAST_CHAT(2, 34, 69),
        TRANSPARENT_FRIEND_CHAT(2, 35, 70),
        TRANSPARENT_CLAN_CHAT(2, 36, 71),
        TRANSPARENT_GUEST_CHAT(2, 37, 72),
        TRANSPARENT_TRADE_CHAT(2, 38, 73),
        TRANSPARENT_CHALLENGE_CHAT(2, 39, 74),
        TRANSPARENT_RESET(2, 40, 75),
        SPLIT_PRIVATE(2, 43, 78),
        SPLIT_BROADCAST(2, 44, 79),
        SPLIT_RESET(2, 45, 80),
        // Controls
        MOUSE_BUTTONS(3, 3, 84, Config.MOUSE_BUTTONS::toggle),
        MOUSE_CAMERA(3, 4, 85, Config.MOUSE_CAMERA::toggle),
        SHIFT_DROP(3, 5, 86, Config.SHIFT_DROP::toggle),
        PET_OPTIONS(3, 7, 88, Config.PET_OPTIONS::toggle),
        RESTORE_DEFAULT_KEYBINDS(3, 26, 107, (player -> {
            player.dialogue(false, new OptionsDialogue(
                    new Option("Use OSRS Keybinds", () -> {
                        for (Config c : Config.KEYBINDS)
                            c.reset(player);
                    }),
                    new Option("Use Pre-EoC Keybinds", () -> {
                        for (int i = 0; i < Config.KEYBINDS.length; i++) {
                            Config config = Config.KEYBINDS[i];
                            if (i == 0)
                                config.set(player, 5);
                            else if (i >= 3 && i <= 6)
                                config.set(player, i - 2);
                            else
                                config.set(player, 0);
                        }
                    }),
                    new Option("Keep Current Keybinds")
            ));
        })),
        CLOSE_SIDE_PANEL_WITH_HOTKEY(3, 27, 108),
        ESCAPE_CLOSES_INTERFACE(3, 28, 109),
        // Display
        HIDE_ROOFS(4, 4, 114, Config.HIDE_ROOFS::toggle),
        ZOOMING_DISABLED(4, 5, 115, Config.ZOOMING_DISABLED::toggle),
        // Gameplay
        //ACCEPT_AID(), Removed?
        SUPPLY_PILES(5, 2, 120),
        // Interfaces
        DATA_ORBS(6, 8, 129, Config.DATA_ORBS::toggle),
        STORE_ORB(6, 11, 132, Config.STORE_ORB::toggle),
        WIKI_BUTTON(6, 12, 133),
        COLLECTION_LOG_POPUP(6, 13, 134, (player -> Config.COLLECTION_LOG_SETTINGS.toggleBit(player, 2))),
        COMBAT_ACHIEVEMENT_POPUP(6, 14, 135),
        REMAINING_XP_TOOLTIP(6, 16, 137, Config.REMAINING_XP_TOOLTIP::toggle),
        PRAYER_TOOLTIPS(6, 18, 139, Config.PRAYER_TOOLTIPS::toggle),
        SPECIAL_ATTACK_TOOLTIPS(6, 19, 140, Config.SPECIAL_ATTACK_TOOLTIPS::toggle),
        CLICK_THROUGH_CHATBOX(6, 21, 142, (player -> {
            if (Config.TRANSPARENT_CHATBOX.get(player) == 1)
                Config.CLICK_THROUGH_CHATBOX.toggle(player);
        })),
        TRANSPARENT_CHAT_BOX(6, 22, 143, Config.TRANSPARENT_CHATBOX::toggle),
        TRANSPARENT_SIDE_PANEL(6, 23, 144, Config.TRANSPARENT_SIDE_PANEL::toggle),
        // Warnings
        TELEPORT_TO_TARGET(7, 2, 185),
        DAREEYAK_TELEPORT(7, 3, 186),
        CARRALLANGAR_TELEPORT(7, 4, 187),
        ANNAKARL_TELEPORT(7, 5, 188),
        GHORROCK_TELEPORT(7, 6, 189),
        ENABLE_TELEPORT_WARNINGS(7, 7, 190),
        DISABLE_TELEPORT_WARNINGS(7, 8, 191),
        DAREEYAK_TABLET(7, 11, 194),
        CARRALLANGAR_TABLET(7, 12, 195),
        ANNAKARL_TABLET(7, 13, 196),
        GHORROCK_TABLET(7, 14, 197),
        CEMETARY_TABLET(7, 15, 198),
        WILDERNESS_CRABS_TABLET(7, 16, 199),
        ICE_PLATEAU_TABLET(7, 17, 200),
        ENABLE_TABLET_WARNINGS(7, 18, 201),
        DISABLE_TABLET_WARNINGS(7, 19, 202),
        DROP_ITEM_WARNING(7, 21, 204),
        DROP_ITEM_VALUE(7, 22, 205),
        ALCH_UNTRADEABLES(7, 23, 206, Config.ALCH_UNTRADEABLES::toggle),
        ALCH_THRESHOLD(7, 24, 207, (player ->
                player.integerInput("Set value threshold for alchemy warnings:", (i) ->
                        Config.ALCH_THRESHOLD.set(player, i))
        ));

        private final int menuIndex, childIndex, searchIndex;
        private final Consumer<Player> consumer;

        Setting(int menuIndex, int childIndex, int searchIndex, Consumer<Player> consumer) {
            this.menuIndex = menuIndex;
            this.childIndex = childIndex;
            this.searchIndex = searchIndex;
            this.consumer = consumer;
        }

        Setting(int menuIndex, int childIndex, int searchIndex) {
            this(menuIndex, childIndex, searchIndex, null);
        }
    }

    private enum SettingDropdown {
        LMS_FOG_COLOR(0, 28, 28),
        MUSIC_AREA_MODE(1, 4, 33),
        PLAYER_ATTACK_OPTION(3, 1, 82, Config.PLAYER_ATTACK_OPTION::set),
        NPC_ATTACK_OPTION(3, 2, 83, Config.NPC_ATTACK_OPTION::set),
        KEYBIND_1(3, 12, 93, (player, i) -> keybind(player, i, 0)),
        KEYBIND_2(3, 13, 94, (player, i) -> keybind(player, i, 1)),
        KEYBIND_3(3, 14, 95, (player, i) -> keybind(player, i, 2)),
        KEYBIND_4(3, 15, 96, (player, i) -> keybind(player, i, 3)),
        KEYBIND_5(3, 16, 97, (player, i) -> keybind(player, i, 4)),
        KEYBIND_6(3, 17, 98, (player, i) -> keybind(player, i, 5)),
        KEYBIND_7(3, 18, 99, (player, i) -> keybind(player, i, 6)),
        KEYBIND_8(3, 19, 100, (player, i) -> keybind(player, i, 7)),
        KEYBIND_9(3, 20, 101, (player, i) -> keybind(player, i, 8)),
        KEYBIND_10(3, 21, 102, (player, i) -> keybind(player, i, 9)),
        KEYBIND_11(3, 22, 103, (player, i) -> keybind(player, i, 10)),
        KEYBIND_12(3, 23, 104, (player, i) -> keybind(player, i, 11)),
        KEYBIND_13(3, 24, 105, (player, i) -> keybind(player, i, 12)),
        KEYBIND_14(3, 25, 106, (player, i) -> keybind(player, i, 13)),
        DISPLAY(6, 1, 122, (player, i) -> DisplayHandler.setDisplayMode(player, i + 1)),
        CHATBOX_SCROLLBAR(6, 24, 145, Config.CHATBOX_SCROLLBAR::set),
        SIDE_PANEL_BORDER(6, 25, 146);

        private final int menuIndex, childIndex, searchIndex;
        private final BiConsumer<Player, Integer> consumer;

        SettingDropdown(int menuIndex, int childIndex, int searchIndex, BiConsumer<Player, Integer> consumer) {
            this.menuIndex = menuIndex;
            this.childIndex = childIndex;
            this.searchIndex = searchIndex;
            this.consumer = consumer;
        }

        SettingDropdown(int menuIndex, int childIndex, int searchIndex) {
            this(menuIndex, childIndex, searchIndex, null);
        }

        private static void keybind(Player player, int dropSlot, int configId) {
            Config config = Config.KEYBINDS[configId];
            if (dropSlot > 0) {
                // Clear any keybinds with the
                for (Config c : Config.KEYBINDS) {
                    if (c != null && c != config && c.get(player) == dropSlot) {
                        c.set(player, 0);
                        break;
                    }
                }
            }
            config.set(player, dropSlot);
        }
    }

    private static final int INTERFACE_ID = WidgetID.SETTINGS_GROUP_ID;

    public static void open(Player player) {
        if (player.isVisibleInterface(INTERFACE_ID)) return;
        resetSettings(player);
        player.openInterface(InterfaceType.MAIN, INTERFACE_ID);

        player.getPacketSender().sendAccessMask(INTERFACE_ID, 23, 0, 7, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(INTERFACE_ID, 19, 0, 1000, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(INTERFACE_ID, 25, -1, -1, AccessMasks.ClickOp1);
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
            h.actions[23] = (DefaultAction) (player, option, slot, item) -> {
                Config.SettingSearch.set(player, 0);
                Config.SettingSearch1.set(player, 0);
                player.putTemporaryAttribute("SETTING_MENU", slot);
                Config.varpbit(9656, false).set(player, slot);
            };
            h.actions[19] = (DefaultAction) (player, option, slot, item) -> {
                player.putTemporaryAttribute("SETTING_CHILD", slot);
                if (Config.SettingSearch.get(player) == 0) {
                    int menu = player.getTemporaryAttributeIntOrZero("SETTING_MENU");
                    for (Setting setting : Setting.values()) {
                        if (menu == setting.menuIndex && slot == setting.childIndex) {
                            if (setting.consumer != null)
                                setting.consumer.accept(player);
                            return;
                        }
                    }
                } else {
                    for (Setting setting : Setting.values()) {
                        if (slot == setting.searchIndex) {
                            if (setting.consumer != null)
                                setting.consumer.accept(player);
                            return;
                        }
                    }
                }
            };
            h.actions[28] = (DefaultAction) (player, option, slot, item) -> {
                int dropSlot = (slot - 2) / 3;
                int menu = player.getTemporaryAttributeIntOrZero("SETTING_MENU");
                int child = player.getTemporaryAttributeIntOrZero("SETTING_CHILD");
                if (Config.SettingSearch.get(player) == 0) {
                    for (SettingDropdown setting : SettingDropdown.values()) {
                        if (menu == setting.menuIndex && child == setting.childIndex) {
                            if (setting.consumer != null)
                                setting.consumer.accept(player, dropSlot);
                            return;
                        }
                    }
                } else {
                    for (SettingDropdown setting : SettingDropdown.values()) {
                        if (child == setting.searchIndex) {
                            if (setting.consumer != null)
                                setting.consumer.accept(player, dropSlot);
                            return;
                        }
                    }
                }
            };
        });
    }
}

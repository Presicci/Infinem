package io.ruin.model.inter.handlers.settings.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.utility.Color;
import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/15/2024
 */
public enum ClickSetting {
    CLEAR_HIGHLIGHTED_NPCS(0, 21, 21),
    HITSPLAT_TINTING(0, 41, 41, Config.HITSPLAT_TINTING::toggle),
    MAX_HIT_HITSPLATS(0, 42, 42, Config.MAX_HIT_HITSPLAT::toggle),
    MAX_HIT_HITSPLATS_THRESHOLD(0, 43, 43,  (player ->
            player.integerInput("Set value threshold for max hits (2-500):", (i) ->
                    Config.MAX_HIT_HITSPLATS_MINIMUM_THRESHOLD.set(player, Math.max(2, Math.min(500, i))))
    )),
    BOSS_HEALTH_OVERLAY(0, 46, 46), // VB 12389 0 = on, 1 = off
    BOSS_HEALTH_OVERLAY_NAME(0, 48, 48),    // VB 14706, 0 = on, 1 = off
    BOSS_HEALTH_OVERLAY_COMPACT(0, 50, 50), // VB 14707 0, 1
    BEGINNER_CLUE_SCROLL_WARNING(0, 66, 66),    // VB 10693 0 = on, 1 = off
    EASY_CLUE_SCROLL_WARNING(0, 67, 67),    // VB 10694 0 = on, 1 = off
    MEDIUM_CLUE_SCROLL_WARNING(0, 68, 68),    // VB 10695 0 = on, 1 = off
    HARD_CLUE_SCROLL_WARNING(0, 69, 69),    // VB 10723 0 = on, 1 = off
    ELITE_CLUE_SCROLL_WARNING(0, 70, 70),    // VB 10724 0 = on, 1 = off
    MASTER_CLUE_SCROLL_WARNING(0, 71, 71),    // VB 10725 0 = on, 1 = off
    // Audio
    MUSIC_LOOPING(1, 7, 88, Config.MUSIC_LOOP::toggle),
    MUSIC_UNLOCK_MESSAGE(1, 5, 89, Config.MUSIC_UNLOCK_MESSAGE::toggle),
    // Chat
    PROFANITY_FILTER(2, 1, 92, Config.PROFANITY_FILTER::toggle),
    CHAT_EFFECTS(2, 3, 94, Config.CHAT_EFFECTS::toggle),
    SPLIT_PRIVATE_CHAT(2, 4, 95, (player -> {
        Config.SPLIT_PRIVATE_CHAT.toggle(player);
        player.getPacketSender().sendClientScript(83);
    })),
    HIDE_PRIVATE_CHAT(2, 5, 96, Config.HIDE_PRIVATE_CHAT::toggle),
    PRECISE_TIMING(2, 7, 98),   // vb 11866
    SEPARATING_HOURS(2, 8, 99),     // vb 11890 0 = on, 1 = off
    COLLECTION_LOG_CHAT_NOTIFICATION(2, 11, 102, (player) -> Config.COLLECTION_LOG_SETTINGS.toggleBit(player, 1)),
    LOOT_DROP_CHAT_NOTIFICATION(2, 12, 103),    // VB 5399
    LOOT_DROP_CHAT_MIN_VALUE(2, 13, 104),   // VB 9647 = 1 when selecting? | VB 5400 = value
    LOOT_DROP_CHAT_UNTRADEABLE(2, 14, 105), // VB 5402
    FILTER_BOSS_KILL_COUNT(2, 15, 106), // VB 4930
    COMBAT_ACHIEVEMENT_FAILURE(2, 16, 107), // VB 12454
    COMBAT_ACHIEVEMENT_REPEAT_FAILURE(2, 17, 108),  // VB 12457
    COMBAT_ACHIEVEMENT_REPEAT_COMPLETE(2, 18, 109), // VB 12456
    CHATBOX_MODE(2, 19, 110),   // VB 13120
    IGNORE_REMOVE_BANNED_ACCOUNTS(2, 20, 111),  // VB 8059 0 = on, 1 = off
    IGNORE_REMOVE_MUTED_ACCOUNTS(2, 21, 112),   // VB 8060
    OPAQUE_PUBLIC_CHAT(2, 24, 115, player -> pickColor(player, 87, Config.OPAQUE_CHAT_COLORS[0], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_PRIVATE_CHAT(2, 25, 116, player -> pickColor(player, 89, Config.OPAQUE_CHAT_COLORS[1], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_AUTO_CHAT(2, 26, 117, player -> pickColor(player, 92, Config.OPAQUE_CHAT_COLORS[2], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_BROADCAST_CHAT(2, 27, 118, player -> pickColor(player, 94, Config.OPAQUE_CHAT_COLORS[3], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_FRIEND_CHAT(2, 28, 119, player -> pickColor(player, 97, Config.OPAQUE_CHAT_COLORS[4], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_CLAN_CHAT(2, 29, 120, player -> pickColor(player, 99, Config.OPAQUE_CHAT_COLORS[5], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_GUEST_CHAT(2, 30, 121, player -> pickColor(player, 105, Config.OPAQUE_CHAT_COLORS[6], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_CLAN_BROADCAST(2, 31, 122, player -> pickColor(player, 105, Config.OPAQUE_CHAT_COLORS[7], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_IM_GROUP_CHAT(2, 32, 123, player -> pickColor(player, 105, Config.OPAQUE_CHAT_COLORS[8], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_IM_GROUP_BROADCASTS(2, 33, 124, player -> pickColor(player, 105, Config.OPAQUE_CHAT_COLORS[9], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_TRADE_CHAT(2, 34, 125, player -> pickColor(player, 101, Config.OPAQUE_CHAT_COLORS[10], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    OPAQUE_CHALLENGE_CHAT(2, 35, 126, player -> pickColor(player, 103, Config.OPAQUE_CHAT_COLORS[11], p -> Config.TRANSPARENT_CHATBOX.get(p) == 0|| player.isFixedScreen())),
    OPAQUE_RESET(2, 36, 127, player -> resetColors(player, "opaque", Config.OPAQUE_CHAT_COLORS, p -> Config.TRANSPARENT_CHATBOX.get(p) == 0 || player.isFixedScreen())),
    TRANSPARENT_PUBLIC_CHAT(2, 39, 130, player -> pickColor(player, 88, Config.TRANSPARENT_CHAT_COLORS[0], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_PRIVATE_CHAT(2, 40, 131, player -> pickColor(player, 90, Config.TRANSPARENT_CHAT_COLORS[1], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_AUTO_CHAT(2, 41, 132, player -> pickColor(player, 93, Config.TRANSPARENT_CHAT_COLORS[2], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_BROADCAST_CHAT(2, 42, 133, player -> pickColor(player, 95, Config.TRANSPARENT_CHAT_COLORS[3], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_FRIEND_CHAT(2, 43, 134, player -> pickColor(player, 98, Config.TRANSPARENT_CHAT_COLORS[4], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_CLAN_CHAT(2, 44, 135, player -> pickColor(player, 100, Config.TRANSPARENT_CHAT_COLORS[5], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_GUEST_CHAT(2, 45, 136, player -> pickColor(player, 106, Config.TRANSPARENT_CHAT_COLORS[6], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_CLAN_BROADCASTS(2, 46, 137, player -> pickColor(player, 106, Config.TRANSPARENT_CHAT_COLORS[7], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_IM_GROUP_CHAT(2, 47, 138, player -> pickColor(player, 106, Config.TRANSPARENT_CHAT_COLORS[8], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_IM_GROUP_BROADCASTS(2, 48, 139, player -> pickColor(player, 106, Config.TRANSPARENT_CHAT_COLORS[9], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_TRADE_CHAT(2, 49, 140, player -> pickColor(player, 102, Config.TRANSPARENT_CHAT_COLORS[10], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_CHALLENGE_CHAT(2, 50, 141, player -> pickColor(player, 104, Config.TRANSPARENT_CHAT_COLORS[11], p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    TRANSPARENT_RESET(2, 51, 142, player -> resetColors(player, "transparent", Config.TRANSPARENT_CHAT_COLORS, p -> Config.TRANSPARENT_CHATBOX.get(p) == 1 && !player.isFixedScreen())),
    SPLIT_PRIVATE(2, 54, 145, player -> pickColor(player, 91, Config.SPLIT_CHAT_COLORS[0], p -> Config.SPLIT_PRIVATE_CHAT.get(p) == 1)),
    SPLIT_BROADCAST(2, 55, 146, player -> pickColor(player, 96, Config.SPLIT_CHAT_COLORS[1])),
    SPLIT_RESET(2, 56, 147, player -> resetColors(player, "split", Config.SPLIT_CHAT_COLORS)),
    // Controls
    PLAYER_TRADE_OPTIONS(3, 1, 149),    // VB 6580 0 = on, 1 = off
    PLAYER_REPORT_OPTIONS(3, 2, 150),   // VB 1334 0 = on, 1 = off
    PK_SKULL_PREVENTION(3, 5, 153),     // VB 13131
    MOUSE_BUTTONS(3, 6, 154, Config.MOUSE_BUTTONS::toggle),
    MOUSE_CAMERA(3, 7, 155, Config.MOUSE_CAMERA::toggle),
    SHIFT_DROP(3, 8, 156, Config.SHIFT_DROP::toggle),
    PET_OPTIONS(3, 13, 161, Config.PET_OPTIONS::toggle),
    RESTORE_DEFAULT_KEYBINDS(3, 38, 186, (player -> {
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
    CLOSE_SIDE_PANEL_WITH_HOTKEY(3, 39, 187),   // VB 4611 0 = on, 1 = off
    ESCAPE_CLOSES_INTERFACE(3, 40, 188, Config.ESCAPE_CLOSES::toggle),
    // Display
    HIDE_ROOFS(4, 4, 193, Config.HIDE_ROOFS::toggle),
    ZOOMING_DISABLED(4, 5, 194, Config.ZOOMING_DISABLED::toggle),
    // Gameplay
    ACCEPT_AID(5, 2, 204, Config.ACCEPT_AID::toggle),
    SUPPLY_PILES(5, 3, 205),    // VB 11893 0 = on, 1 = off
    AUTO_EQUIP_WORN_ITEMS_FROM_GRAVESTONE(5, 4, 206), // VB 9776
    DEEP_WILDY_LEVEL(5, 5, 207),
    PHANTOM_MUSPAH_MESSAGES(5, 6, 208), // VB 14711
    MAKE_X_DARTS_BOLTS(5, 7, 209),  // VB 10971
    MAKE_X_BARBARIAN_POTIONS(5, 8, 210),    // VB 5983
    AMMO_PICKING_BEHAVIOUR(5, 9, 211),  // VB 5697
    RUNE_PICKING_BEHAVIOUR(5, 10, 212), // VB 5698
    EMPTY_RUNE_POUCHES_ON_DEATH(5, 11, 213),    // VB 15311
    EMPTY_ESSENCE_POUCHES_ON_DEATH(5, 12, 214),    // VB 15315
    EMPTY_BOLT_POUCHES_ON_DEATH(5, 13, 215),    // VB 15313
    EMPTY_HERB_SACKS_ON_DEATH(5, 14, 216),    // VB 15316
    EMPTY_SEED_BOXES_ON_DEATH(5, 15, 217),    // VB 15314
    EMPTY_TACKLE_BOXES_ON_DEATH(5, 16, 218),    // VB 15312
    LOOTING_BAG_STORE_ALL(5, 17, 219),  // VB 6068
    LOOTING_BAG_IGNORE_SUPPLIES(5, 18, 220),    // VB 15310
    AUTOMATICALLY_SMASH_EMPTY_VIALS(5, 20, 222),
    AUTOMATICALLY_SMASH_EMPTY_PLANT_POTS(5, 21, 223),
    ENERGY_THRESHOLD_TO_REENABLE_RUNNING(5, 22, 224),   // VB 9647 = 1 when selecting | VB 11031 = value
    COMPASS_SET_VERTICAL_ANGLE(5, 24, 226), // VB 15323
    BABA_YAGA_CAMERA_EFFECT(5, 25, 227),    // VB 14197 0 = on, 1 = off
    FISHING_TRAWLER_CAMERA_EFFECT(5, 26, 228),  // VB 4814 0 = on, 1 = off
    BARROWS_CAMERA_EFFECT(5, 27, 229),  // VB 14198 0 = on, 1 = off
    BETA_WORLD_CONVERT_SAVE(5, 30, 232),
    BETA_WORLD_RESET_SAVE(5, 31, 233),
    // Interfaces
    DATA_ORBS(6, 11, 245, Config.DATA_ORBS::toggle),    // script 1039 = on?, 1041 = off?
    STORE_ORB(6, 14, 248, Config.STORE_ORB::toggle),
    WIKI_BUTTON(6, 15, 249),    // VB 10113 0 = on, 1 = off
    ACTIVITY_ADVISER(6, 16, 250),   // VB 5368 0 = on, 1 = off
    BLOCK_PROMOTIONAL_MESSAGES(6, 17, 251), // VB 9597
    COLLECTION_LOG_POPUP(6, 18, 252, (player -> Config.COLLECTION_LOG_SETTINGS.toggleBit(player, 2))),
    COMBAT_ACHIEVEMENT_POPUP(6, 19, 253),   // VB 12455 0 = on, 1 = off
    ACCEPT_TRADE_DELAY(6, 20, 254), // VB 13130 0 = on, 1 = off
    DISABLE_LEVEL_UP_INTERFACE(6, 22, 256), // VB 9452
    NEW_SKILL_GUIDE_INTERFACE(6, 23, 257, Config.NEW_SKILL_GUIDE::toggle),
    HIDE_UNSTARTED_QUESTS(6, 28, 262),
    HIDE_IN_PROGRESS_QUESTS(6, 29, 263),
    HIDE_COMPLETED_QUESTS(6, 30, 264),
    HIDE_MINIQUESTS(6, 31, 265),
    HIDE_QUESTS(6, 32, 266),
    HIDE_QUEST_LIST_HEADERS(6, 35, 269),
    DISABLE_QUEST_LIST_TEXT_SHADOW(6, 36, 270),
    UNSTARTED_QUEST_COLOR(6, 37, 271),
    IN_PROGRESS_QUEST_COLOR(6, 38, 272),
    COMPLETED_QUEST_COLOR(6, 39, 273),
    UNAVAILABLE_QUEST_COLOR(6, 40, 274),
    RESET_QUEST_COLORS(6, 41, 275),
    REMAINING_XP_TOOLTIP(6, 43, 277, Config.REMAINING_XP_TOOLTIP::toggle),
    PRAYER_TOOLTIPS(6, 46, 280, Config.PRAYER_TOOLTIPS::toggle),
    SPECIAL_ATTACK_TOOLTIPS(6, 47, 281, Config.SPECIAL_ATTACK_TOOLTIPS::toggle),
    CLICK_THROUGH_CHATBOX(6, 49, 283, (player -> {
        if (Config.TRANSPARENT_CHATBOX.get(player) == 1)
            Config.CLICK_THROUGH_CHATBOX.toggle(player);
    })),
    TRANSPARENT_CHAT_BOX(6, 50, 284, Config.TRANSPARENT_CHATBOX::toggle),
    TRANSPARENT_SIDE_PANEL(6, 51, 285, Config.TRANSPARENT_SIDE_PANEL::toggle),
    // Warnings
    TELEPORT_TO_TARGET(7, 2, 348),
    DAREEYAK_TELEPORT(7, 3, 349),
    CARRALLANGAR_TELEPORT(7, 4, 350),
    ANNAKARL_TELEPORT(7, 5, 351),
    GHORROCK_TELEPORT(7, 6, 352),
    WILDY_LEVERS(7, 7, 353),
    WILDY_LEVERS_HIGH_RISK_WORLDS(7, 8, 354),
    ENABLE_TELEPORT_WARNINGS(7, 9, 355),
    DISABLE_TELEPORT_WARNINGS(7, 10, 356),
    DAREEYAK_TABLET(7, 13, 359),
    CARRALLANGAR_TABLET(7, 14, 360),
    ANNAKARL_TABLET(7, 15, 361),
    GHORROCK_TABLET(7, 16, 362),
    CEMETARY_TABLET(7, 17, 363),
    WILDERNESS_CRABS_TABLET(7, 18, 364),
    ICE_PLATEAU_TABLET(7, 19, 365),
    ENABLE_TABLET_WARNINGS(7, 20, 366),
    DISABLE_TABLET_WARNINGS(7, 21, 367),
    DROP_ITEM_WARNING(7, 23, 369),
    DROP_ITEM_VALUE(7, 24, 370),
    ALCH_UNTRADEABLES(7, 25, 371, Config.ALCH_UNTRADEABLES::toggle),
    ALCH_THRESHOLD(7, 26, 372, (player ->
            player.integerInput("Set value threshold for alchemy warnings:", (i) ->
                    Config.ALCH_THRESHOLD.set(player, i))
    )),
    LOGIN_WARNING_IF_ITEM_RETRIEVAL_SERVICE(7, 27, 373),
    CHARTER_SHIP_CONFIRMATION(7, 29, 375),
    GRAVESTONE_CONFIRMATION(7, 30, 376),
    FARMER_PAYMENT_CONFIRMATION(7, 31, 377),
    WORLD_SWITCHER_CONFIRMATION(7, 32, 378),
    POISON_KARAMBWAN_CONFIRMATION(7, 33, 379),
    EXIT_FEROX(7, 36, 382),
    EXIT_FEROX_HIGH_RISK(7, 37, 383),
    WILDY_CANOE(7, 38, 384),
    WILDY_CANOE_HIGH_RISK(7, 39, 385),
    GE_BUY_OFFER_WARNING(7, 40, 386),
    GE_SELL_OFFER_WARNING(7, 41, 387),
    // Popout
    // Infinem
    MAP_KEY(8, 1, 389, Config.MAP_KEY_OPEN_BY_DEFAULT::toggle),
    BESTIARY_KC(8, 3, 391, Config.BESTIARY_KC::toggle),
    BESTIARY_NEW_ENTRY(8, 4, 392, Config.BESTIARY_NEW_ENTRY::toggle),
    TRANSMOG_POPUP(8, 6, 394, Config.TRANSMOG_POPUP::toggle),
    INFO_BROADCAST(8, 8, 396, Config.INFORMATION_BROADCASTS::toggle),
    SKILL_BROADCAST(8, 9, 397, Config.SKILLING_BROADCASTS::toggle),
    DROP_BROADCAST(8, 10, 398, Config.DROP_BROADCASTS::toggle),
    FRIEND_BROADCAST(8, 11, 399, Config.FRIEND_BROADCASTS::toggle),
    ACTIVITY_SPOTLIGHT_BROADCAST(8, 12, 400, Config.ACTIVITY_SPOTLIGHT_BROADCAST::toggle);

    @Getter private final int menuIndex, childIndex, searchIndex;
    @Getter private final Consumer<Player> consumer;

    ClickSetting(int menuIndex, int childIndex, int searchIndex, Consumer<Player> consumer) {
        this.menuIndex = menuIndex;
        this.childIndex = childIndex;
        this.searchIndex = searchIndex;
        this.consumer = consumer;
    }

    ClickSetting(int menuIndex, int childIndex, int searchIndex) {
        this(menuIndex, childIndex, searchIndex, null);
    }

    private static void pickColor(Player player, int identifier, Config config, Predicate<Player> predicate) {
        if (predicate.test(player)) pickColor(player, identifier, config);
    }

    private static void pickColor(Player player, int identifier, Config config) {
        Config.varpbit(12284, false).set(player, 1);
        Config.varpbit(9657, false).setInstant(player, identifier);
        player.openInterface(134, 8, 288);
        player.consumerInt = integer -> {
            if (integer < Integer.MAX_VALUE) {
                config.set(player, integer + 1);
            }
            player.closeInterface(134, 8);
            Config.varpbit(12284, false).set(player, 0);
        };
        player.retryIntConsumer = false;
        player.getPacketSender().sendClientScript(4185, "ii", 134 << 16 | 8, config.get(player) - 1);
    }

    private static void resetColors(Player player, String type, Config[] colors, Predicate<Player> predicate) {
        if (predicate.test(player)) resetColors(player, type, colors);
    }

    private static void resetColors(Player player, String type, Config[] colors) {
        player.dialogue(false, new OptionsDialogue(Color.DARK_RED.wrap("Are you sure you want to reset your " + type + " " + (type.equalsIgnoreCase("split") ? " chat" : " chatbox") + " colours?"),
                new Option("Yes.", () -> {
                    for (Config color : colors) {
                        color.set(player, 0);
                    }
                    player.sendMessage("Default " + type + " colours restored.");
                }),
                new Option("No.")
        ));
    }
}

package io.ruin.model.inter.handlers.settings.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.incoming.handlers.DisplayHandler;
import lombok.Getter;

import java.util.function.BiConsumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/15/2024
 */
public enum DropdownSetting {
    BOSS_OVERLAY_DISPLAY_TYPE(0, 49, 49),
    LMS_FOG_COLOR(0, 61, 61),
    MUSIC_AREA_MODE(1, 6, 87),
    HIT_SOUNDS(1, 9, 90),
    PLAYER_ATTACK_OPTION(3, 3, 151, Config.PLAYER_ATTACK_OPTION::set),
    NPC_ATTACK_OPTION(3, 4, 152, Config.NPC_ATTACK_OPTION::set),
    KEYBIND_1(3, 24, 172, (player, i) -> keybind(player, i, 0)),
    KEYBIND_2(3, 25, 173, (player, i) -> keybind(player, i, 1)),
    KEYBIND_3(3, 26, 174, (player, i) -> keybind(player, i, 2)),
    KEYBIND_4(3, 27, 175, (player, i) -> keybind(player, i, 3)),
    KEYBIND_5(3, 28, 176, (player, i) -> keybind(player, i, 4)),
    KEYBIND_6(3, 29, 177, (player, i) -> keybind(player, i, 5)),
    KEYBIND_7(3, 30, 178, (player, i) -> keybind(player, i, 6)),
    KEYBIND_8(3, 31, 179, (player, i) -> keybind(player, i, 7)),
    KEYBIND_9(3, 32, 180, (player, i) -> keybind(player, i, 8)),
    KEYBIND_10(3, 33, 181, (player, i) -> keybind(player, i, 9)),
    KEYBIND_11(3, 34, 182, (player, i) -> keybind(player, i, 10)),
    KEYBIND_12(3, 35, 183, (player, i) -> keybind(player, i, 11)),
    KEYBIND_13(3, 36, 184, (player, i) -> keybind(player, i, 12)),
    KEYBIND_14(3, 37, 185, (player, i) -> keybind(player, i, 13)),
    PRONOUNS(5, 1, 203, Config.PRONOUNS::set),
    DISPLAY(6, 1, 235, (player, i) -> DisplayHandler.updateGameframe(player, i == 2 ? 1 : i == 5 ? 2 : 3)),
    QUEST_LIST_SORTING(6, 25, 259),
    SHOW_QUESTS_LACK_REQUIREMENTS(6, 26, 260),
    SHOW_QUESTS_LACK_RECOMMENDED(6, 27, 261),
    QUEST_LIST_TEXT_SIZE(6, 34, 268),
    CHATBOX_SCROLLBAR(6, 52, 286, Config.CHATBOX_SCROLLBAR::set),
    SIDE_PANEL_BORDER(6, 53, 287);

    @Getter private final int menuIndex, childIndex, searchIndex;
    @Getter private final BiConsumer<Player, Integer> consumer;

    DropdownSetting(int menuIndex, int childIndex, int searchIndex, BiConsumer<Player, Integer> consumer) {
        this.menuIndex = menuIndex;
        this.childIndex = childIndex;
        this.searchIndex = searchIndex;
        this.consumer = consumer;
    }

    DropdownSetting(int menuIndex, int childIndex, int searchIndex) {
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

package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DailyResetListener;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.object.actions.impl.PrayerAltar;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class MagicSkillcape {
    private static final int CAPE = StatType.Magic.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Magic.trimmedCapeId;
    private static final String KEY = "MAGIC_CAPE";

    static {
        ItemAction.registerInventory(CAPE, "spellbook", (player, item) -> swapSelection(player));
        ItemAction.registerEquipment(CAPE, "spellbook", (player, item) -> swapSelection(player));
        ItemAction.registerInventory(CAPE, "check", (player, item) -> check(player));
        ItemAction.registerEquipment(CAPE, "check", (player, item) -> check(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "spellbook", (player, item) -> swapSelection(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "spellbook", (player, item) -> swapSelection(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "check", (player, item) -> check(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "check", (player, item) -> check(player));
        DailyResetListener.register(player -> player.removeAttribute(KEY));
    }

    private static void check(Player player) {
        int uses = player.getAttributeIntOrZero(KEY);
        if (uses >= 5) {
            player.timeTillDailyReset("You've used all 5 spellbook swaps today.<br><br>");
        } else {
            player.timeTillDailyReset("You have " + (5 - uses) + " spellbook swaps left today.<br><br>");
        }
    }

    public static void swapSelection(Player player) {
        if(player.getAttributeIntOrZero(KEY) >= 5) {
            player.timeTillDailyReset("You've used all 5 spellbook swaps today.<br><br>");
            return;
        }
        int spellBook = Config.MAGIC_BOOK.get(player);
        if (spellBook == SpellBook.MODERN.ordinal()) {
            player.dialogue(new OptionsDialogue("Choose spellbook:",
                    new Option("Ancient", () -> swap(player, SpellBook.ANCIENT, "Ancient")),
                    new Option("Lunar", () -> swap(player, SpellBook.LUNAR, "Lunar")),
                    new Option("<str>Arceuus", player::closeDialogue)
            ));
        } else if(spellBook == SpellBook.ANCIENT.ordinal()) {
            player.dialogue(new OptionsDialogue("Choose spellbook:",
                    new Option("Modern", () -> swap(player, SpellBook.MODERN, "Modern")),
                    new Option("Lunar", () -> swap(player, SpellBook.LUNAR, "Lunar")),
                    new Option("<str>Arceuus", player::closeDialogue)
            ));
        } else if(spellBook == SpellBook.LUNAR.ordinal()) {
            player.dialogue(new OptionsDialogue("Choose spellbook:",
                    new Option("Modern", () -> swap(player, SpellBook.MODERN, "Modern")),
                    new Option("Ancient", () -> swap(player, SpellBook.ANCIENT, "Ancient")),
                    new Option("<str>Arceuus", player::closeDialogue)
            ));
        }
    }

    private static void swap(Player player, SpellBook spellBook, String name) {
        PrayerAltar.switchBook(player, spellBook, false);
        player.sendMessage(name + " spellbook activated.");
        player.incrementNumericAttribute(KEY, 1);
    }
}

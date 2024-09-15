package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.magic.SpellBook;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/31/2024
 */
public class ArcaneGrimoire {

    private static final int GRIMOIRE = 26551;

    private static void changeSpellbook(Player player) {
        if (!player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE)) {
            player.sendMessage("You need the Archmage relic and be out of the wilderness to use the Grimoire.");
            return;
        }
        List<Option> options = new ArrayList<>();
        for (SpellBook book : SpellBook.values()) {
            if (book.isActive(player)) continue;
            options.add(new Option(StringUtils.capitalizeFirst(book.name), () -> {
                player.putAttribute("LAST_SPELLBOOK", Config.MAGIC_BOOK.get(player));
                book.setActive(player);
            }));
        }
        player.dialogue(new OptionsDialogue(options).keepOpenWhenHit());
    }

    private static void lastSpellbook(Player player) {
        if (!player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE)) {
            player.sendMessage("You need the Archmage relic and be out of the wilderness to use the Grimoire.");
            return;
        }
        Config.MAGIC_BOOK.set(player, player.getAttributeIntOrZero("LAST_SPELLBOOK"));
        TabCombat.updateAutocast(player, false);
    }

    private static void checkLast(Player player) {
        if (!player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE)) {
            player.sendMessage("You need the Archmage relic and be out of the wilderness to use the Grimoire.");
            return;
        }
        int spellbookIndex = player.getAttributeIntOrZero("LAST_SPELLBOOK");
        SpellBook spellBook = SpellBook.values()[spellbookIndex];
        player.sendMessage("Your last spellbook was: " + StringUtils.capitalizeFirst(spellBook.name));
    }

    static {
        ItemAction.registerInventory(GRIMOIRE, "change spellbook", (player, item) -> changeSpellbook(player));
        ItemAction.registerInventory(GRIMOIRE, "previous spellbook", (player, item) -> lastSpellbook(player));
        ItemAction.registerInventory(GRIMOIRE, "check previous", (player, item) -> checkLast(player));
        ItemAction.registerEquipment(GRIMOIRE, "change spellbook", (player, item) -> changeSpellbook(player));
        ItemAction.registerEquipment(GRIMOIRE, "previous spellbook", (player, item) -> lastSpellbook(player));
        ItemAction.registerEquipment(GRIMOIRE, "check previous", (player, item) -> checkLast(player));
    }
}

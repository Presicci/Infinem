package io.ruin.model.entity.npc.actions.zeah;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.magic.SpellBook;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/1/2024
 */
public class Tyss {

    private static void changeSpellbook(Player player) {
        if (SpellBook.ARCEUUS.isActive(player)) {
            SpellBook.MODERN.setActive(player);
        } else {
            SpellBook.ARCEUUS.setActive(player);
        }
    }

    static {
        NPCAction.register(7050, "spellbook", (player, npc) -> changeSpellbook(player));
    }
}

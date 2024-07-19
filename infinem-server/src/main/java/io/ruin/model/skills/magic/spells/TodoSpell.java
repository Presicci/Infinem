package io.ruin.model.skills.magic.spells;

import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.magic.Spell;

public class TodoSpell extends Spell {

    private void message(Player player, String action, String name) {
        if (player.isStaff()) {
            player.sendMessage("Todo " + action + " action spell: " + name);
        } else {
            player.sendMessage("This spell is not yet implemented.");
        }
    }

    public TodoSpell(String name) {
        clickAction = (p, i) -> message(p, "click", name);
        itemAction = (p, i) -> message(p, "item", name);
        entityAction = (p, e) -> message(p, "entity", name);
        objectAction = (p, o) -> message(p, "object", name);
    }
}
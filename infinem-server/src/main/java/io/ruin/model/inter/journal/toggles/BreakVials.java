package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BreakVials extends JournalEntry {

    @Override
    public void send(Player player) {
        if(player.hasAttribute(AttributeKey.BREAK_VIALS))
            send(player, "Break Vials", "Enabled", Color.GREEN);
        else
            send(player, "Break Vials", "Disabled", Color.RED);
    }

    @Override
    public void select(Player player) {
        if (player.hasAttribute(AttributeKey.BREAK_VIALS))
            player.removeAttribute(AttributeKey.BREAK_VIALS);
        else
            player.putAttribute(AttributeKey.BREAK_VIALS, 1);
        send(player);
    }

}
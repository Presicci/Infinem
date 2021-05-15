package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;

public class DropTable extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Search drop tables");
    }

    @Override
    public void select(Player player) {
        Journal.BESTIARY.send(player);
    }
}

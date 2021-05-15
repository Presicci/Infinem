package io.ruin.model.inter.journal.bestiary;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;

public class BackButton extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Back...");
    }

    @Override
    public void select(Player player) {
        Journal.MAIN.send(player);
    }
}
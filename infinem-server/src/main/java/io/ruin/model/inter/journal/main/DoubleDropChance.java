package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.entity.player.DoubleDrops;

public class DoubleDropChance extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Double Drop Chance: " + Color.GREEN.wrap(DoubleDrops.getChance(player) + "%"));
    }

    @Override
    public void select(Player player) {
        player.sendMessage("Your double drop chance is " + Color.GREEN.wrap(DoubleDrops.getChance(player) + "%."));
    }
}
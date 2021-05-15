package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class DoubleDrops extends JournalEntry {

    public static final DoubleDrops INSTANCE = new DoubleDrops();

    @Override
    public void send(Player player) {
        send(player, "Double drops: ", getDoubleDrops(), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.sendMessage("Double monster drops is currently " + getDoubleDrops() + ".");
    }

    private static String getDoubleDrops() {
        if (World.doubleDrops) {
            return Color.GREEN.wrap("Enabled");
        } else {
            return Color.RED.wrap("Disabled");
        }
    }
}

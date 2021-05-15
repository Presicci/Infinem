package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class DoubleSlayerPoints extends JournalEntry {

    public static final DoubleSlayerPoints INSTANCE = new DoubleSlayerPoints();

    @Override
    public void send(Player player) {
        send(player, "Double slayer points: ", getDoubleSlayerPoints(), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.sendMessage("Double slayer points is currently " + getDoubleSlayerPoints() + ".");
    }

    private static String getDoubleSlayerPoints() {
        if (World.doubleSlayer) {
            return Color.GREEN.wrap("Enabled");
        } else {
            return Color.RED.wrap("Disabled");
        }
    }
}

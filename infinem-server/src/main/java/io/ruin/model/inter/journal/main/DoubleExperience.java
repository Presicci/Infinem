package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class DoubleExperience extends JournalEntry {

    public static final DoubleExperience INSTANCE = new DoubleExperience();

    @Override
    public void send(Player player) {
        send(player, "Double experience: ", getDoubleXp(), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.sendMessage("Double pest control points is currently " + getDoubleXp() + ".");
    }

    private static String getDoubleXp() {
        int xp = World.xpMultiplier;
        if (xp == 1) {
            return Color.GREEN.wrap("Enabled");
        } else if (xp > 1) {
            return Color.GREEN.wrap( xp+ "x");
        } else {
            return Color.RED.wrap("Disabled");
        }
    }
}
package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class DoublePestControl extends JournalEntry {

    public static final DoublePestControl INSTANCE = new DoublePestControl();

    @Override
    public void send(Player player) {
        send(player, "Double pest control: ", getDoublePcPoints(), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.sendMessage("Double pest control points is currently " + getDoublePcPoints() + ".");
    }

    private static String getDoublePcPoints() {
        if (World.doublePest) {
            return Color.GREEN.wrap("Enabled");
        } else {
            return Color.RED.wrap("Disabled");
        }
    }
}
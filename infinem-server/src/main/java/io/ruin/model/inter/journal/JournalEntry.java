package io.ruin.model.inter.journal;

import io.ruin.utility.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;

public abstract class JournalEntry {
    public boolean category;
    public int childId;

    protected final void send(Player player, String text) {
        if (category)
            player.getPacketSender().sendClientScript(135, "ii", Interface.SERVER_TAB << 16 | childId, 496);
        player.getPacketSender().sendString(Interface.SERVER_TAB, childId, text);
    }

    protected final void send(Player player, String text, Color color) {
        if (category)
            player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 496);
        player.getPacketSender().sendString(Interface.SERVER_TAB, childId, color.wrap(text));
    }

    protected final void send(Player player, String key, String value, Color color) {
        if (category)
            player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 496);
        player.getPacketSender().sendString(Interface.SERVER_TAB, childId, ("<col=D37E2A>" + key + ":</col> " + color.wrap(value)));
    }

    protected final void send(Player player, String key, int value, Color color) {
        if (value == 0)
            color = Color.RED;
        if (category)
            player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 496);
        player.getPacketSender().sendString(Interface.SERVER_TAB, childId, ("<col=D37E2A>" + key + ":</col> " + color.wrap(String.valueOf(value))));
    }

    public abstract void send(Player player);

    public abstract void select(Player player);

}
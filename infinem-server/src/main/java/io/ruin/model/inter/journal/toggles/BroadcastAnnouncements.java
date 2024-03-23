package io.ruin.model.inter.journal.toggles;

import io.ruin.utility.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BroadcastAnnouncements extends JournalEntry {

    @Override
    public void send(Player player) {
        if(!player.broadcastAnnouncements)
            send(player, "Announcements", "Disabled", Color.RED);
        else
            send(player, "Announcements", "Enabled", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.broadcastAnnouncements = !player.broadcastAnnouncements;
        if(player.broadcastAnnouncements)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted Announcements."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted Announcements."));
        send(player);
    }

}

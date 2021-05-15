package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class Website extends JournalEntry {

    private String url = "";
    private String name = "";

    public Website(String name, String url) {
        this.url = url;
        this.name = name;
    }

    @Override
    public void send(Player player) {
        send(player, name);
    }

    @Override
    public void select(Player player) {
        player.openUrl(url);
    }
}

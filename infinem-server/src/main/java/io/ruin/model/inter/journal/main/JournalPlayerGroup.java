package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.journal.JournalEntry;

public class JournalPlayerGroup extends JournalEntry {
    @Override
    public void send(Player player) {
        PlayerGroup pg = player.getPrimaryGroup();
        send(player, "Rank: "
                + (pg.clientImgId > 0 ? ("<img=" + pg.clientImgId + ">") : "")
                + pg.toString());
    }

    @Override
    public void select(Player player) {

    }
}
